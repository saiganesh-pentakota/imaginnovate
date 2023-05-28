package com.imaginnovate.exception;

public enum AppError {

	MANDATORY("%s field value should be mandatory");

	private String errDesc;

	private AppError(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

}
