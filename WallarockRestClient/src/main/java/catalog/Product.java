package catalog;

import java.io.Serializable;

import client.Users;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String description;

	private String imagePath;

	private int price;

	private String title;

	private int category;
	
	private int status;
	
	private Users user;
	
	public Product(int id, String description, String imagePath, int price, String title, int category, int status) {
		super();
		this.id = id;
		this.description = description;
		this.imagePath = imagePath;
		this.price = price;
		this.title = title;
		this.category = category;
		this.status = status;
	}

	public Product() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
}
