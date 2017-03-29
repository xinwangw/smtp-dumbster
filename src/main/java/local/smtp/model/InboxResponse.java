package local.smtp.model;

import java.util.List;

public class InboxResponse {
	private List<Inbox> data;

	public List<Inbox> getData() {
		return data;
	}

	public void setData(List<Inbox> data) {
		this.data = data;
	}
	
}
