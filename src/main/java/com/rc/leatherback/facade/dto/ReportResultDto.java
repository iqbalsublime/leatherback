package com.rc.leatherback.facade.dto;

import java.io.Serializable;

public class ReportResultDto implements Serializable {
	private static final long serialVersionUID = -560180999564585261L;

	private String drawerKey;

	public String getDrawerKey() {
		return drawerKey;
	}

	public void setDrawerKey(String drawerKey) {
		this.drawerKey = drawerKey;
	}

}
