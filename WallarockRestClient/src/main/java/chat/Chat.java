package chat;

import java.io.Serializable;
import java.util.List;

public class Chat implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String id;
	
	private String user1;

	private String user2;

	public List<String> messages;
	
	public Chat() {
	}

	public Chat(String id, String user1, String user2, List<String> messages) {
		super();
		this.id = id;
		this.user1 = user1;
		this.user2 = user2;
		this.messages = messages;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	public String getUser2() {
		return user2;
	}
	
	public void setUser2(String user2) {
		this.user2 = user2;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public void appendMessage(String sender, String message) {
		this.messages.add(sender + ": " + message);
	}
		
}
