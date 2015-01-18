package com.rc.leatherback.model;

import java.util.Date;
import java.util.List;

public class Prescription extends AbstractEntity {
    private static final long serialVersionUID = -7307508650671957926L;

    private String lotNumber;
    private Date date;
    private String partNumber;
    private String partNumberHead;
    private String partNumberBody;
    private double totalAmount;
    private double totalPrice;
    private double averageCost;
    private double hand;
    private double totalAmountAfterHanded;
    private List<PrescriptionDetail> details;

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartNumberHead() {
        return partNumberHead;
    }

    public void setPartNumberHead(String partNumberHead) {
        this.partNumberHead = partNumberHead;
    }

    public String getPartNumberBody() {
        return partNumberBody;
    }

    public void setPartNumberBody(String partNumberBody) {
        this.partNumberBody = partNumberBody;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }

    public double getHand() {
        return hand;
    }

    public void setHand(double hand) {
        this.hand = hand;
    }

    public double getTotalAmountAfterHanded() {
        return totalAmountAfterHanded;
    }

    public void setTotalAmountAfterHanded(double totalAmountAfterHanded) {
        this.totalAmountAfterHanded = totalAmountAfterHanded;
    }

    public List<PrescriptionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PrescriptionDetail> details) {
        this.details = details;
    }
}
