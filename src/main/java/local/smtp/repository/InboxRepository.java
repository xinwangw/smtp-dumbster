package local.smtp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import local.smtp.model.Inbox;

public interface InboxRepository extends JpaRepository<Inbox, Long> {

	@Query("select b from Inbox b where lower(b.recipient) like lower(:recipient) "
			+ "and b.createDate between :createDateFrom and :createDateTo ")
	List<Inbox> findByRecipientInPeriod(@Param("recipient") String recipient,
			@Param("createDateFrom") Date createDateFrom, @Param("createDateTo") Date createDateTo);

}
