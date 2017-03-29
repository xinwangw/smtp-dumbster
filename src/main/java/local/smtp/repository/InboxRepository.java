package local.smtp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import local.smtp.model.Inbox;

public interface InboxRepository extends JpaRepository<Inbox, Long>{
	
}
