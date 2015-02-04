package com.rc.leatherback.model.report;

public class PrescriptionDetailReport {
	private String item;
	private String prescription;
	private String amount;
	private String price;
	private String note;

	public PrescriptionDetailReport(int item, String prescription, double amount, double price, String note) {
		this.item = String.valueOf(item);
		this.prescription = prescription;
		this.amount = String.valueOf(amount);
		this.price = String.valueOf(price);
		this.note = note;
	}

	public String getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = String.valueOf(item);
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = String.valueOf(amount);
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.amount = String.valueOf(price);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
