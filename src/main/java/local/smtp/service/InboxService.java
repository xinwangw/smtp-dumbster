package local.smtp.service;

import java.util.List;

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
}
