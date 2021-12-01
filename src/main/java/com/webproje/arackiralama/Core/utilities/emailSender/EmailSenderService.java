package com.webproje.arackiralama.Core.utilities.emailSender;

public interface EmailSenderService {
	public void sendEmail(String toEmail, String body, String subject);
}
