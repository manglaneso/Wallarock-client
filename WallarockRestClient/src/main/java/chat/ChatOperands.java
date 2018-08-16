package chat;
import java.io.Serializable;

public class ChatOperands implements Serializable {
	private static final long serialVersionUID = 1L;
	private String user1;
	private String user2;
	private String message;
	

	public ChatOperands() {
	}

	public ChatOperands(String user1, String user2, String message) {
		super();
		this.user1 = user1;
		this.user2 = user2;
		this.message = message;
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
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
			
}
