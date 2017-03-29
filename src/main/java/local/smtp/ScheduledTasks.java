package local.smtp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.mail.internet.MimeUtility;

import org.apache.commons.io.FileUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

import local.smtp.model.Inbox;
import local.smtp.repository.InboxRepository;
import local.smtp.service.InboxService;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final String MAIN_PATH = "./target/inbox/";

    @Autowired
    private SimpleSmtpServer server;
    
    @Autowired
    private InboxService service;

    @Autowired
	private SessionFactory sessionFactory;

    @Scheduled(fixedRate = 15000)
    public void reportCurrentTime() {
        Iterator emailIter = server.getReceivedEmail();
        while (emailIter.hasNext()) {
        	Date current = new Date();
        	log.info("Get email: The time is now {}", dateFormat.format(current));
        	SmtpMessage email = (SmtpMessage)emailIter.next();
        	log.info("To: "+email.getHeaderValue("To"));
        	log.info("From: "+email.getHeaderValue("From"));
        	String subject = email.getHeaderValue("Subject"); 
        	log.info("Subject before decode: "+subject);
        	try {
        		subject = MimeUtility.decodeText(email.getHeaderValue("Subject"));
			} catch (UnsupportedEncodingException e1) {
				log.error(e1.getMessage(), e1);
			}
        	log.info("Subject: "+subject);
        	log.info(email.getBody());
        	try {
        		FileUtils.forceMkdir(new File(MAIN_PATH));
        		File file = FileUtils.getFile(MAIN_PATH + URLEncoder.encode(subject+"-"+current.getTime())+".html");
				FileUtils.write(file, wrapEmailTo(email.getHeaderValue("To"))+wrapEmailFrom(email.getHeaderValue("From"))+wrapEmailDate(current)+wrapSubject(subject)+email.getBody(), "UTF8");
				Inbox inboxData = new Inbox();
				inboxData.setCreateDate(current);
				inboxData.setSubject(subject);
				Blob blob = Hibernate.getLobCreator(this.sessionFactory.openSession()).createBlob(email.getBody().getBytes());
				inboxData.setEmailBody(blob);
				inboxData.setFrom(email.getHeaderValue("From"));
				inboxData.setRecipient(email.getHeaderValue("To"));
				service.save(inboxData);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
        	emailIter.remove();
        }
    }
    
    private String wrapEmailTo(String to){
    	return "<html><div><span>To:</span><span>"+to+"</span></div></html>";
    }
    
    private String wrapEmailFrom(String from){
    	return "<html><div><span>From:</span><span>"+from+"</span></div></html>";
    }
    
    private String wrapEmailDate(Date date){
    	return "<html><div><span>Received:</span><span>"+date.toString()+"</span></div></html>";
    }
    
    private String wrapSubject(String subject){
    	return "<html><p/><div><span>Subject:</span><span>"+subject+"</span></div></html>";
    }
}