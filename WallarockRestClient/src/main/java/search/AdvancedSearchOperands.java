package search;

import java.io.Serializable;

public class AdvancedSearchOperands implements Serializable {
	private static final long serialVersionUID = 1L;
	private int category;

	private String query;
	
	public AdvancedSearchOperands() {
		super();
	}

	public AdvancedSearchOperands(int category,String query) {
		super();
		this.category = category;
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	
	
	
}
