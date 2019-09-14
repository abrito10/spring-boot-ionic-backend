package com.abrito10.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.abrito10.cursomc.domain.Pedido;

public interface EmailService  {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
