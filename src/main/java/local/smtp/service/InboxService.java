package local.smtp.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import local.smtp.model.Inbox;
import local.smtp.repository.InboxRepository;

@Service
@Transactional
public class InboxService {
	@Autowired
	private InboxRepository repo;
	
	public void save(Inbox data){
		repo.saveAndFlush(data);
	}
	
	public List<Inbox> findAll(){
		return repo.findAll();
	}
	
	public Inbox findOne(long id){
		return repo.findOne(id);
	}
	
	public void clearAll(){
		repo.deleteAllInBatch();
	}
	
	public List<Inbox> findByRecipient(String subject, String recipient, Date createDateFrom, Date createDateTo){
		subject = "%"+subject+"%";
		recipient = "%"+recipient+"%";
		if (createDateFrom ==null)
			createDateFrom = new Date(0);
		if (createDateTo ==null)
			createDateTo = new DateTime(9999,12,31,0,0,0).toDate();
		return repo.findByRecipientInPeriod(subject, recipient, createDateFrom, createDateTo);
	}
}
