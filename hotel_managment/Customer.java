package com.hotel_managment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.mobilephone.RandomNumber;

public class Customer {

	private String customerName;
	private long customerPhoneNumber;
	private long aadharNumber;
	private String emailId;
	private int stayPerDay;
	private double rent;
	private String rentType;
	private boolean isAvailable;
	String userOTP;
	String orignalOTP;

	public Customer() {

	}

	public Customer(String cName, long cPhoneNumber, long aadharNumber, String emailId, int stayPerDay) {
		super();
		this.customerName = cName;
		this.customerPhoneNumber = cPhoneNumber;
		this.aadharNumber = aadharNumber;
		this.emailId = emailId;
		this.stayPerDay = stayPerDay;
//		this.OTP = OTP;

	}

	Scanner sc = new Scanner(System.in);

	public String getcName() {
		return customerName;
	}

	public long getcPhoneNumber() {
		return customerPhoneNumber;
	}

	public long getAadharNumber() {
		return aadharNumber;
	}

	public int getStayPerDay() {
		return stayPerDay;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setRent(int rType) {
		if (rType == 1111) {
			rent = 1700;
			rentType = "LUXURY";
		} else {
			rentType = "NORMAL";
			rent = 1300;
		}
	}

	public String getRentType() {
		return rentType;
	}

	public Scanner getSc() {
		return sc;
	}

	public double getRent() {

		return stayPerDay * rent;
	}

	public int checkPhoneNumber() {
		String str = "" + customerPhoneNumber + "";

		return str.length();
	}

	public int checAaadharNumber() {
		String str = "" + aadharNumber;

		return str.length();
	}

	public void setCustomerDetails() throws ClassNotFoundException {

		System.out.println("=========================================");
		System.out.println("\tCustomer Details form");
		System.out.println("=========================================\n");
		System.out.print("Enter your Name : ");
		customerName = sc.nextLine();
		System.out.print("Enter your Phone Number(+91) : ");
		customerPhoneNumber = sc.nextLong();
		while (true) {
			if (checkPhoneNumber() != 10) {
				System.out.print("*You entered wrong number, Please enter 10 digit mobile number(+91) : ");
				customerPhoneNumber = sc.nextLong();
			} else {
				break;
			}
		}
		System.out.print("Enter your Aadhaar Number : ");
		aadharNumber = sc.nextLong();
		while (true) {
			if (checAaadharNumber() != 12) {
				System.out.print("*You entered wrong number, Please enter 12 digit Aadhaar number : ");
				aadharNumber = sc.nextLong();
			} else {
				break;
			}
		}
		System.out.print("Enter your email ID : ");
		emailId = sc.next();

		while (true) {
			if (isValidEmail(emailId)) {
				orignalOTP = ProjectMenu.getOTP();
				EmailNotification.sendNotification(emailId, "Room-Booking Confirmation Email!", "" + orignalOTP
						+ " is your one time password (OTP) to Book the room. Please enter OTP to proceed");
				break;
			} else {
				System.out.print("*You entered wrong email, Please enter correct email : ");
				emailId = sc.next();
			}

		}
		System.out.print("For how many days do you want to stay : ");
		stayPerDay = sc.nextInt();

		System.out.print(
				"\n---------------------------------------------------------------------------------------------------------------------------------\r\n"
						+ "\n\n");

		@SuppressWarnings("unused")
		Customer cust = new Customer(customerName, customerPhoneNumber, aadharNumber, emailId, stayPerDay);

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "c##faizan",
					"faizu123");

			String sql = "INSERT INTO customer_info (customerName, phoneNumber, aadharNumber, email, stayPerDay) "
					+ "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerName);
			pstmt.setLong(2, customerPhoneNumber);
			pstmt.setLong(3, aadharNumber);
			pstmt.setString(4, emailId);
			pstmt.setInt(5, stayPerDay);

			int rowsAffected = pstmt.executeUpdate();
			System.out.println("Rows Affected: " + rowsAffected);

			pstmt.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String getEmailId() {
		return emailId;
	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		Pattern pattern = Pattern.compile(emailRegex);

		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	public static String getRoomId() {

		String str = "RBS";

		int[] randomId = RandomNumber.getRandomNumber(5, 9);

		for (int id : randomId) {
			str = str + "" + id;
		}
		return str;

	}

	public boolean verifyOTP() {

		int attempt = 1;
		while (attempt <= 3) {
			System.out.print("Enter the OTP to book the room: ");
			userOTP = sc.next();

			if (userOTP.equals(orignalOTP)) {
				return true;
			} else {
				System.out.print("Wrong OTP. ");

				if (attempt < 3) {
					System.out.println("Try again.");
				} else {
					System.out.print("Type 'Resend' for a new OTP, or any other key to exit: ");
					String resend = sc.next();

					if ("Resend".equalsIgnoreCase(resend)) {
						String otp2 = ProjectMenu.getOTP();
						System.out.println("New OTP: " + otp2);
						System.out.print("Enter OTP: ");
						userOTP = sc.next();

						if (userOTP.equals(otp2)) {
							return true;
						}
					} else {
						System.out.println("Exiting Program...");
						System.exit(0);
					}
				}

				attempt++;
			}
		}
		return false;
	}

	@Override
	public String toString() {

		if (verifyOTP()) {
			System.out.print("======================================================"
					+ "\nYour room is booked. Your receipt is sent to your email."
					+ "\n======================================================");

			return "\nCustomer Name : " + customerName + "\nYour Room ID :" + getRoomId() + "\nPhoneNumber : "
					+ customerPhoneNumber + "\nAadharNumber is : " + aadharNumber + "\nemail ID is : " + emailId
					+ "\nYou are stay for " + stayPerDay + " Days" + "\nRent per day : " + getRent() / stayPerDay
					+ "\nYour room type is : " + getRentType()
					+ "\n======================================================" + "\nYour Bill Amount is Rs. "
					+ getRent() + "\n======================================================"
					+ "\nThank you for using our Room!!\nThank You!!"
					+ "\n======================================================\n\n";

		}
		return "OOPS!! Try After Sometime!!!!!";
	}

}
