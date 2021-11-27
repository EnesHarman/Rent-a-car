package com.webproje.arackiralama.Core.utilities.result.concretes;

import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;

public class SuccessResult extends Result {

	public SuccessResult() {
		super(true);
	}
	
	public SuccessResult(String message) {
		super(true, message);
	}

}
