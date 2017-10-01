/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

/**
 *
 * @author Mxsxs2
 */
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail{
    private String Recipient="";
    private String From="";
    private String SendingMessage="";
    private String Subject="";
    public void setValues(String To, String From, String SendingMessage, String Subject){
        this.Recipient=To;
        this.From=From;
        this.SendingMessage=SendingMessage;
        this.Subject=Subject;
    }
   public boolean send(){
      Properties props = new Properties();
                props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
                                @Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("smtp@mxsxs2.info","nemsmtp123");
				}
			});
                //session.setDebug(true);
 
     try {
         
         MimeMessage message = new MimeMessage(session);
                     message.setFrom(new InternetAddress(this.From));
                     message.addRecipient(Message.RecipientType.TO,
                                          new InternetAddress(this.Recipient));
                     message.setSubject(this.Subject);
                     message.setContent(this.SendingMessage,
                                        "text/html" );

         Transport.send(message);
         return true;
      }catch (MessagingException mex) {
         System.out.println(mex.getMessage());
          mex.printStackTrace();
      }
      return false;
   }
}