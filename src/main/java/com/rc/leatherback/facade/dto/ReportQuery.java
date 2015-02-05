package com.rc.leatherback.facade.dto;

import java.io.Serializable;
import java.util.Date;

public class ReportQuery implements Serializable {
	private static final long serialVersionUID = -5180216106801167962L;

	private Date startDate;
	private Date endDate;
	private String lotNumber;
	private String partNumber;
	private String partNumberHead;
	private String partNumberBody;
	private boolean showPrice;
	private int searchBy;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
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

	public boolean isShowPrice() {
		return showPrice;
	}

	public void setShowPrice(boolean showPrice) {
		this.showPrice = showPrice;
	}

	public int getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(int searchBy) {
		this.searchBy = searchBy;
	}

}
