package com.example.back_end.service.discount;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendVoucherEmail(
            String recipientEmail,
            String voucherCode,
            String discountDetails,
            Instant startDate,
            Instant endDate,
            BigDecimal discountPercentage,
            BigDecimal discountAmount
    ) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("kiennkph42576@fpt.edu.vn");
        helper.setTo(recipientEmail);

        helper.setSubject("Vistore Sales Website");

        helper.setText(createEmailBody(voucherCode, discountDetails, startDate, endDate, discountPercentage, discountAmount), true);

        javaMailSender.send(message);
    }

    @Async
    public void sendVoucherEmails(
            List<String> recipientEmails,
            String voucherCode,
            String discountDetails,
            Instant startDate,
            Instant endDate,
            BigDecimal discountPercentage,
            BigDecimal discountAmount) throws MessagingException {

        for (String email : recipientEmails) {
            sendVoucherEmail(email, voucherCode, discountDetails, startDate, endDate, discountPercentage, discountAmount);
        }
    }
    private String formatCurrency(BigDecimal amount) {
        if (amount == null) return null;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(amount);
    }
    private String createEmailBody(
            String voucherCode,
            String discountDetails,
            Instant startDate,
            Instant endDate,
            BigDecimal discountPercentage,
            BigDecimal discountAmount) {

        String discountInfo;

        if (discountPercentage != null) {
            discountInfo = "<strong>" + discountPercentage.stripTrailingZeros().toPlainString() + "% OFF</strong>";
        } else if (discountAmount != null) {
            discountInfo = "<strong>" + formatCurrency(discountAmount) + "</strong>";
        } else {
            discountInfo = "Không có thông tin giảm giá.";
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.systemDefault());

        String formattedStartDate = formatter.format(startDate);
        String formattedEndDate = formatter.format(endDate);

        return String.format("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Xác nhận đăng ký</title>
                </head>
                <body style="font-family: 'Arial', sans-serif; color: #333; background-color: #f4f4f9; margin: 0; padding: 20px;">
                    <div style="max-width: 500px; background-color: #fff; border-radius: 20px; box-shadow: 0 6px 18px rgba(0,0,0,0.1); margin: 0 auto; padding: 20px; text-align: center;">
                        <h2 style="color: green; margin-top: 20px;">Vistore Shop</h2>
                        <div style="color: #2B8284; font-size: 48px; margin-top: 30px;">
                            <img src="https://cdn-icons-png.flaticon.com/512/122/122598.png" alt="//" style="width: 20%%;">
                        </div>
                        <h4 style="color: #000; font-size: 35px; margin-top: 10px;">%s</h4>
                        <p style="font-size: 16px; color: #666; margin: 15px 0;">Thương hiệu của chúng tôi đang tri ân khách hàng mới. Đối với những người đã là thành viên với chúng tôi, chúng tôi đang giảm giá trên toàn bộ website .</p>
                        <p style="font-size: 16px; color: #666; margin-bottom: 30px;">Thời gian bán kéo dài từ %s đến %s.</p>
                       
                        <div style="color: #666; margin-top: 20px;">Món quà của chúng tôi dành cho bạn:</div>
                        <div style="border: 2px dashed #94c0f9; border-radius: 20px; padding: 20px; margin: 20px 0; background-color: #e1ebff; width: 80%%; margin: 0 auto;">
                            <div style="font-size: 18px; font-weight: 600;">Thẻ quà tặng</div>
                            <div style="font-size: 48px; font-weight: bold; color: #020202; margin: 10px 0;">%s</div>
                        </div>
                       
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 20px;">
                            <div style="font-size: 20px;">Mã thẻ quà tặng:</div>
                        </div>
                        <div style="font-weight: bold; margin: 20px 0; background-color: #ecf0f9; padding: 10px; border-radius: 10px;">
                            <h2 style="color: red;">%s</h2>
                        </div>
                       
                        <a href="http://localhost:3001" style="background-color: #2B8284; color: white; padding: 12px 24px; border-radius: 5px; text-decoration: none; font-size: 18px; display: inline-block; margin: 20px 0; transition: background-color 0.3s;">Mua ngay</a>
                      
                    </div>
                </body>
                </html>
                """, discountDetails, formattedStartDate, formattedEndDate, discountInfo, voucherCode);
    }
}