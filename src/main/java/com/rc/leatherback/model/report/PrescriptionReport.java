package com.rc.leatherback.model.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rc.core.util.DateTimeUtil;

public class PrescriptionReport {
    private String lotNumber;
    private String date;
    private String partNumber;
    private String totalAmount;
    private String totalPrice;
    private String averageCost;
    private String hand;
    private String totalAmountAfterHanded;
    private List<PrescriptionDetailReport> details;

    public PrescriptionReport(String lotNumber, Date date, String partNumber, double totalAmount, double totalPrice,
                    double averageCost, double hand, double totalAmountAfterHanded) {
        this.lotNumber = lotNumber;
        this.date = DateTimeUtil.toString(date, "yyyy-MM-dd");
        this.partNumber = partNumber;
        this.totalAmount = String.valueOf(totalAmount);
        this.totalPrice = String.valueOf(totalPrice);
        this.averageCost = String.valueOf(averageCost);
        this.hand = String.valueOf(hand);
        this.totalAmountAfterHanded = String.valueOf(totalAmountAfterHanded);
        this.details = new ArrayList<PrescriptionDetailReport>();
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = dateFormat.format(date);
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = String.valueOf(totalAmount);
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = String.valueOf(totalPrice);
    }

    public String getAverageCost() {
        return averageCost;
    }

    public String getHand() {
        return hand;
    }

    public String getTotalAmountAfterHanded() {
        return totalAmountAfterHanded;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost = String.valueOf(averageCost);
    }

    public List<PrescriptionDetailReport> getDetails() {
        return details;
    }

    public void setDetails(List<PrescriptionDetailReport> details) {
        this.details = details;
    }

    public void addDetail(PrescriptionDetailReport detail) {
        details.add(detail);
    }
}
