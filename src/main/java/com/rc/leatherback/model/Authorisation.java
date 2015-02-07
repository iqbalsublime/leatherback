package com.rc.leatherback.model;

public class Authorisation extends AbstractEntity {
	private static final long serialVersionUID = 4516716361100379880L;

	private long userId;

	private boolean accessUserModule;
	private boolean accessReportModule;

	private boolean createPrescription;
	private boolean deletePrescription;
	private boolean modifyPrescription;
	private boolean listPrescription;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isAccessUserModule() {
		return accessUserModule;
	}

	public void setAccessUserModule(boolean accessUserModule) {
		this.accessUserModule = accessUserModule;
	}

	public boolean isAccessReportModule() {
		return accessReportModule;
	}

	public void setAccessReportModule(boolean accessReportModule) {
		this.accessReportModule = accessReportModule;
	}

	public boolean isCreatePrescription() {
		return createPrescription;
	}

	public void setCreatePrescription(boolean createPrescription) {
		this.createPrescription = createPrescription;
	}

	public boolean isDeletePrescription() {
		return deletePrescription;
	}

	public void setDeletePrescription(boolean deletePrescription) {
		this.deletePrescription = deletePrescription;
	}

	public boolean isModifyPrescription() {
		return modifyPrescription;
	}

	public void setModifyPrescription(boolean modifyPrescription) {
		this.modifyPrescription = modifyPrescription;
	}

	public boolean isListPrescription() {
		return listPrescription;
	}

	public void setListPrescription(boolean listPrescription) {
		this.listPrescription = listPrescription;
	}
}
