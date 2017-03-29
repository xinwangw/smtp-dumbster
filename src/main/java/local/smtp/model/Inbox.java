package local.smtp.model;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="inbox_data")
public class Inbox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	long id;
	
	@Column(name = "create_date")
    private Date createDate;
	
	@Column(name = "subject")
    private String subject;
	
	@Column(name = "email_body")
	@Lob
	@JsonSerialize(using = CustomBlobSerializer.class)
    private Blob emailBody;
	
	@Column(name = "recipient")
	private String recipient;
	
	@Column(name = "fromAddress")
	private String from;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Blob getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(Blob emailBody) {
		this.emailBody = emailBody;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	
}
