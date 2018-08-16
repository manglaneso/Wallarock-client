package search;

import java.io.Serializable;

public class SimpleSearchOperands implements Serializable {
	private static final long serialVersionUID = 1L;
	private String query;
	
	public SimpleSearchOperands() {
		super();
	}
	
	public SimpleSearchOperands(int category,String query) {
		super();
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
}
