package com.webproje.arackiralama.Core.utilities.result.abstracts;

public abstract class Result {
	private String message;
	private Boolean success;
	
	public Result(boolean success) {
		this.success = success;
	}
	
	public Result( boolean success,String message) {
		this(success);
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
}
