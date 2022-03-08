package utils;

import configurations.EnvConfig;
import dto.OrderDTO;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSenderUtils {

    private static final String USERNAME = EnvConfig.getEMAIL_USERNAME();
    private static final String PASSWORD = EnvConfig.getEMAIL_PASSWORD();

    public static boolean send(String email, OrderDTO order) {
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true"); // TLS

            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("huynnse150807@fpt.edu.vn", "VegeShop"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("VegeShop - Confirm order");
            message.setText("ID: " + order.getOrderID() + "\n"
                    + "Date: " + order.getFormatedOrderDate() + "\n"
                    + "Address: " + order.getAddress() + "\n"
                    + "Total: " + order.getFormattedTotal());

            Transport.send(message);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
