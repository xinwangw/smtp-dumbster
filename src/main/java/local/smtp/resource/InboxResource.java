package local.smtp.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/email/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Inbox> inbox(@PathVariable Long id){
		return new ResponseEntity(service.findOne(id), HttpStatus.OK);
	}
}
