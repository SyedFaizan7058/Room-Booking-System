package com.hotel_managment;

import java.util.Scanner;
import com.mobilephone.RandomNumber;

public class ProjectMenu {
	static Customer customer = new Customer(null, 0, 0, null, 0);

	public static String getOTP() {																

		String OTP = "";
		int[] randomNumbers = RandomNumber.getRandomNumber(6, 9);

		for (int randomNumber : randomNumbers) {

			OTP = OTP + "" + randomNumber;
		}

		return OTP;
	}

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		ManageFloors manageFloor = new ManageFloors();

		displayWelcomeMessage();

		String accept = getUserChoice(scanner);

		while (true) {
			if (accept.equalsIgnoreCase("YES")) {
				bookRoom(scanner, manageFloor, customer);
				break;
			} else if (accept.equalsIgnoreCase("NO")) {
				System.out.println("Thank you for considering \"Room Booking System (RBS)\"!");
				System.out.println("Please feel free to visit us again. Have a wonderful day!");
				break;
			} else {
				System.out.println("*Invalid choice! Please provide a correct response.");
				accept = getUserChoice(scanner);
			}
		}

		scanner.close();
	}

	private static void displayWelcomeMessage() {
		System.out.println("******************************************************");
		System.out.println("*                                                    *");
		System.out.println("*      Welcome to the Room Booking System (RBS)      *");
		System.out.println("*                                                    *");
		System.out.println("******************************************************\n");

		System.out.println("Explore the ultimate comfort in room bookings with us.");
		System.out.println("Our goal is to make your stay exceptional and memorable.\n");

		System.out.println("Are you ready to experience excellence in room service?");
		System.out.println("Please respond with 'YES' to proceed or 'NO' to exit.\n");
		System.out.println(" ____________________ __________________ _______________");
		System.out.println("|                    |                  |               |");
		System.out.println("|        Options     |       Type       |     Captcha   |");
		System.out.println("|____________________|__________________|_______________|");
		System.out.println("|                    |                  |               |");
		System.out.println("|         YES        |    Accept        |   [ _ _ _ ]   |");
		System.out.println("|         NO         |    Decline       |   [ _ _ _ ]   |");
		System.out.println("|____________________|__________________|_______________|");
		System.out.print("\nYour choice: ");
	}

	private static String getUserChoice(Scanner scanner) {
		return scanner.next().toUpperCase();
	}

	private static void bookRoom(Scanner scanner, ManageFloors manageFloor, Customer customer) throws Exception {
		customer.setCustomerDetails();
		displayRoomInformation();
		int code = getRoomCode(scanner);

		while (true) {
			if (code == 1111 || code == 9999) {
				customer.setRent(code);
				setRoomAndFloorDetails(scanner, customer, code, manageFloor);
				break;
			} else {
				System.out.println("*Code is wrong!!!\n");
				System.out.print("Type correct Room Code: ___");
				code = scanner.nextInt();
			}
		}
	}

	private static void displayRoomInformation() {
		System.out.println("\nTypes of Room Available.");
		System.out.println("========================");
		System.out.println(" ________________________________________________________________");
		System.out.println("|          |             |                                       |");
		System.out.println("|   Code   |   Room Type |               FACILITY                |");
		System.out.println("|__________|_____________|_______________________________________|");
		System.out.println("|          |             |                                       |");
		System.out.println("|   1111   |   LUXURY    | Wifi, TV, Swimming Pool, AC, Breakfast|");
		System.out.println("|          |             |       Lunch, Dinner, Window Look.     |");
		System.out.println("|__________|_____________|_______________________________________|");
		System.out.println("|          |             |                                       |");
		System.out.println("|   9999   |   NORMAL    |    Wifi, Breakfast , Lunch, Dinner    |");
		System.out.println("|__________|_____________|_______________________________________|");
		System.out.print("\nType Room Code ___");
	}

	private static int getRoomCode(Scanner scanner) {
		return scanner.nextInt();
	}

	private static void setRoomAndFloorDetails(Scanner scanner, Customer customer, int code, ManageFloors manageFloor) {
		manageFloor.setRoomType(code == 1111 ? "LUXURY" : "NORMAL");
		manageFloor.setCustomer(customer);
		displayAvailableFloors(manageFloor.getRoomType());
		String floorCode = getFloorCode(scanner);
		displayAvailableRooms(floorCode, manageFloor);
		getRoomCodeOnSelectedFloor(scanner);
		System.out.println("OTP is sned to your email!!");
		displayBookingDetails(manageFloor);
	}

	private static void displayAvailableFloors(String roomType) {
		System.out.println(String.format(
				"\nWe have available floors for %s type rooms. Choose any floor where you want to stay.\n", roomType));
		System.out.println(" ____________________________");
		System.out.println("|          |                 |");
		System.out.println("|   Code   |      Floor      |");
		System.out.println("|__________|_________________|");

		for (char floorCode = 'A'; floorCode <= 'C'; floorCode++) {
			System.out.println(String.format("|          |                 |"));
			System.out.println(String.format("|    %c    |   %s Floor      |", floorCode, getFloorName(floorCode)));
			System.out.println(String.format("|__________|_________________|"));
		}
	}

	private static String getFloorName(char floorCode) {
		switch (floorCode) {
		case 'A':
			return "First";
		case 'B':
			return "Second";
		case 'C':
			return "Third";
		default:
			return "Unknown";
		}
	}

	private static String getFloorCode(Scanner scanner) {
		System.out.print("Type a floor Code here: ");
		String floorCode = scanner.next().toUpperCase();

		while (!isValidFloorCode(floorCode)) {
			System.out.print("\n*Invalid Floor Code! Please type a valid floor Code (A, B, C): ");
			floorCode = scanner.next().toUpperCase();
		}

		return floorCode;
	}

	private static boolean isValidFloorCode(String floorCode) {
		return floorCode.length() == 1 && (floorCode.charAt(0) >= 'A' && floorCode.charAt(0) <= 'C');
	}

	private static void displayAvailableRooms(String floorCode, ManageFloors manageFloor) {

		System.out.println(String.format("\nYou chose %s floor Code is %s.\n", manageFloor.getRoomType(), floorCode));
		System.out.println("Here are the available rooms.");
		System.out.println(" ____________________________");
		System.out.println("|          |                 |");
		System.out.println("|   Code   |    Room Type    |");
		System.out.println("|__________|_________________|");

		for (int i = 1; i <= 3; i++) {
			String roomCode = floorCode + "0" + i;
			System.out.println(String.format("|          |                 |"));
			System.out.println(String.format("|   %s  |   Room %d          |", roomCode, i));
			System.out.println(String.format("|__________|_________________|"));
		}
	}

	private static String getRoomCodeOnSelectedFloor(Scanner scanner) {
		System.out.print("\nType a room Code: ___ ");
		return scanner.next();
	}

	private static void displayBookingDetails(ManageFloors manageFloor) {
		System.out.println(
				"\n---------------------------------------------------------------------------------------------------------------------------------");
		
		EmailNotification.sendNotification(customer.getEmailId(), "RBS Booking",
				"Congratulation your room is booked successfully!!\n"
						+ "\n======================================================"
						+ "\n\t\tCustomer Bill \n======================================================"
						+ manageFloor.getCustomer());

	}
}
