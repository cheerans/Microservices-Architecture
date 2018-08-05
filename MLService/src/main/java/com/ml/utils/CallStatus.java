package com.ml.utils;

public enum CallStatus {
	
	FAIL("Service Failure"), 
	SUCCESS("Service Success");
	
	private String desc;
	
	CallStatus(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
