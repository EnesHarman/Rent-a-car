package com.webproje.arackiralama.Core.utilities.result.concretes;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;

public class SuccessDataResult<T> extends DataResult<T>{
	
	public SuccessDataResult() {
		super(true);
	}
	
	public SuccessDataResult(T data) {
		super(true, data);
	}

	public SuccessDataResult( String message) {
		super(true,message);
	}
	
	public SuccessDataResult( String message, T data) {
		super(true, message, data);
	}
}
