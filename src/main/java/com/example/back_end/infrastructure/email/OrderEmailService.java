package com.example.back_end.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendOrderConfirmationEmail(String recipientEmail, String orderDetails) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("kiennkph42576@fpt.edu.vn");
        helper.setTo(recipientEmail);
        helper.setSubject("Order Confirmation - Vistore");
        helper.setText(createOrderEmailBody(orderDetails), true);

        javaMailSender.send(message);
    }

    private String createOrderEmailBody(String orderDetails) {
        return String.format("""
                    <html>
                    <body style="font-family: Arial, sans-serif; color: #333; padding: 20px; background-color: #f7f7f7;">
                            <div style="padding: 20px;">
                                %s
                            </div>
                    </body>
                    </html>
                """, orderDetails);
    }
}