import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class GmailRunnable implements Runnable {
    private String to = "";
    private String cc = "";
    private String bcc = "";
    private String subject = "";
    private String message = "";
     
 
    @Override
    public void run() {
        this.sendMail();
         
    }
    public void setParameters(String to, String cc, String bcc, String subject,
            String message) {
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.message = message;
 
    }
    private void sendMail() {
 
        try {
            Session session = getSession();
            Message mailMessage = new MimeMessage(session);
    //      mailMessage.setFrom(new InternetAddress(from));
 
            // Set the mail recipients
            setRecipients(mailMessage, to, cc, bcc);
 
            mailMessage.setSubject(subject);
 
            mailMessage.setSentDate(new Date());
            mailMessage.setText(message);
            mailMessage.saveChanges();
            Transport.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
    private Session getSession() {
 
        String host = "74.125.25.109";// set ip address (server ip for
                                        // smtp.gmail.com)
        final String userName = "username";// put username here. dont put
                                            // @domain.com only username.
        final String password = "password";// put pwd here
        final String smtpPort = "465"; // put port number.
        final String sslFactory = "javax.net.ssl.SSLSocketFactory";
 
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.socketFactory.port", smtpPort);
        props.put("mail.smtp.socketFactory.class", sslFactory);
        props.put("mail.smtp.socketFactory.fallback", "false");
 
        Session session = Session.getInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
 
        return session;
    }
    private void setRecipients(Message mailMessage, String to, String cc,
            String bcc) throws Exception {
 
        if (mailMessage != null) {
            try {
                if (to != null) {
                    List toList = new ArrayList();
                    StringTokenizer tokenizer = new StringTokenizer(to, ",");
                    while (tokenizer.hasMoreTokens()) {
                        toList.add(new InternetAddress(tokenizer.nextToken()));
                    }
                    mailMessage.setRecipients(Message.RecipientType.TO,
                            (InternetAddress[]) toList
                                    .toArray(new InternetAddress[0]));
                }
                // set CC recipients
                if (cc != null) {
                    List ccList = new ArrayList();
                    StringTokenizer tokenizer = new StringTokenizer(cc, ",");
                    while (tokenizer.hasMoreTokens()) {
                        ccList.add(new InternetAddress(tokenizer.nextToken()));
                    }
                    mailMessage.setRecipients(Message.RecipientType.CC,
                            (InternetAddress[]) ccList
                                    .toArray(new InternetAddress[0]));
                }
 
                // set BCC recipients
                if (bcc != null) {
                    List bccList = new ArrayList();
                    StringTokenizer tokenizer = new StringTokenizer(bcc, ",");
                    while (tokenizer.hasMoreTokens()) {
                        bccList.add(new InternetAddress(tokenizer.nextToken()));
                    }
                    mailMessage.setRecipients(Message.RecipientType.BCC,
                            (InternetAddress[]) bccList
                                    .toArray(new InternetAddress[0]));
                }
            } catch (MessagingException e) {
            } catch (Exception e) {
            }
        }
    }
 
 
    public static void main(String[] args) {
        GmailRunnable gmailRunnable = new GmailRunnable();
        gmailRunnable.setParameters("morwal89@gmail.com", "","","first call", "hi this is first call");
        Thread t = new Thread(gmailRunnable);
        t.start(); 
    }
 
}
