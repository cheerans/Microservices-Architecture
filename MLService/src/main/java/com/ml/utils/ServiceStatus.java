package com.ml.utils;

public class ServiceStatus {
	
	private CallStatus status;
	
	private String serviceName;
	
	private String msg;
	
	private String description;

	public ServiceStatus(CallStatus status, String serviceName, String msg, String description) {
		super();
		this.status = status;
		this.serviceName = serviceName;
		this.msg = msg;
		this.description = description;
	}
	
	public static ServiceStatus buildFailure(String svcName) {
		
		return new ServiceStatus(CallStatus.FAIL,svcName,CallStatus.FAIL.getDesc(),CallStatus.FAIL.getDesc());
	}	

	public CallStatus getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceStatus [\"status\":").append(status).append(", serviceName\":").append(serviceName)
				.append(", msg\":").append(msg).append(", description\":").append(description).append("]");
		return builder.toString();
	}
}