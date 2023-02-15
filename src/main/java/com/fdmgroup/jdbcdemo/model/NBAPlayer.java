package com.fdmgroup.jdbcdemo.model;

public class NBAPlayer {
	private int playerId;
	private String firstName;
	private String lastName;
	private int careerPoints;
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Override
	public String toString() {
		return "NBAPlayer [playerId=" + playerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", careerPoints=" + careerPoints + "]";
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getCareerPoints() {
		return careerPoints;
	}
	public void setCareerPoints(int careerPoints) {
		this.careerPoints = careerPoints;
	}
}
