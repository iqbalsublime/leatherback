package com.rc.leatherback.model;


public class PrescriptionDetail extends AbstractEntity {
    private static final long serialVersionUID = 3628333218811528395L;

    private long prescriptionId;
    private String name;
    private double amount;
    private double price;
    private String note;

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "PrescriptionDetail{" + "id=" + id + "prescriptionId=" + prescriptionId + "name=" + name + " amount=" + amount
                        + "price=" + price + " note=" + note + '}';
    }
}
