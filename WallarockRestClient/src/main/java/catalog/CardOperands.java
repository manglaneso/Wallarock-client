package catalog;

import java.io.Serializable;

public class CardOperands implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	public CardOperands() {
		
	}
	
	public CardOperands(int id) {
		super();
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
