/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.UnsupportedEncodingException;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import models.Account;
import service.AccountService;

/**
 *
 * @author teren
 */
@Stateless
public class EmailUtil {

    @Resource(name = "java/mail/swhp")
    Session mailSession;

    /**
     *
     * @param account
     * @throws javax.mail.MessagingException
     * @throws java.io.UnsupportedEncodingException
     */
    public void sendEmail_AccountDeleted(Account account) throws MessagingException, UnsupportedEncodingException {
        System.out.println("Sending email to: " + account.getEmail());
        Message message = new MimeMessage(mailSession);

        message.setSubject("Kwetter account deleted");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(account.getEmail(), account.getUsername()));
        message.setText("Hello, " + account.getUsername() + "\n \n Your account has been deleted. For further information, please contact us. \n \n Kind regards, \n the Kwetter team");

        System.out.println(message);
        Transport.send(message);
    }

    /**
     * Creates a new instance of EmailBean
     */
    public EmailUtil() {
    }

}
