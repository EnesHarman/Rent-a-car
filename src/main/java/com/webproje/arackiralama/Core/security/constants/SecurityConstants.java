package com.webproje.arackiralama.Core.security.constants;

import java.util.Date;

public class SecurityConstants {
	public static final String secretKey = "MySoSecretKey";
	public static Date jwtExpireDate = new Date(System.currentTimeMillis()+10*60*1000);
}
