package local.smtp.resource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import local.smtp.model.Inbox;
import local.smtp.model.InboxResponse;
import local.smtp.service.InboxService;

@RestController
@RequestMapping("/api")
public class InboxResource {
	@Autowired
    private InboxService service;
	
	@RequestMapping(value = "/inbox", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<InboxResponse> inbox(){
		InboxResponse response = new InboxResponse();
		response.setData(service.findAll());
		return new ResponseEntity<InboxResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/email/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Inbox> inbox(@PathVariable Long id){
		return new ResponseEntity<Inbox>(service.findOne(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inbox", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deleteAll(){
		service.clearAll();
		return new ResponseEntity<String>("Deleted All.", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inbox/filter", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<InboxResponse> findByRecipient(@RequestParam String subject, @RequestParam String recipient, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fromDate, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date toDate){
		InboxResponse response = new InboxResponse();
		response.setData(service.findByRecipient(subject, recipient, fromDate, toDate));
		return new ResponseEntity<InboxResponse>(response, HttpStatus.OK);
	}
}
