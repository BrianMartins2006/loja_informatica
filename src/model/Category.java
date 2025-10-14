package model;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private String description;
    private List<Product> products;
    
    public Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProducts() {
        return products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public void validate() {    
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome da categoria não pode ser vazio.");
        }
    }
    
    @Override
    public String toString() {
        return name;
    }
}
