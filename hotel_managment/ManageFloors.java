package com.hotel_managment;

public class ManageFloors {

	private String roomType;
	private Customer customer;

	public ManageFloors() {
		this.roomType = "";
		this.customer = new Customer(null, 0, 0, null, 0); 
	}

	public void floorName(Hotel hotelReference) {

		hotelReference.floor();

	}

	void typeOfFloor() {

	}

	public void toStringCustomer() {
		System.out.println(customer);
	}

	public Customer getCustomer() {
		return customer;

	}

	public void setCustomer(Customer customer) {

		this.customer = customer;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

//	public String getFloorNumberCode() {
//		return floorNumberCode;
//	}
//
//	public void setFloorNumberCode(String floorNumberCode) {
//		this.floorNumberCode = floorNumberCode;
//	}
//
//	public String getRoomCode() {
//		return roomCode;
//	}
//
//	public void setRoomCode(String roomCode) {
//		this.roomCode = roomCode;
//	}

//	public void bookTicket() {
//
//		if (roomType.contentEquals("LUXURY")) {
//
//			if (floorNumberCode.contentEquals("A")) {
//
//			} else if (floorNumberCode.contentEquals("B")) {
//
//			} else if (floorNumberCode.contentEquals("C")) {
//
//			}
//		} else {
//			// code for NORMAL type room.
//		}
//	}

}
