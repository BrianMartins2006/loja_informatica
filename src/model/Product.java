package model;

public class Product {
    private int id;
    private String name;
    private Double price;
    private Integer stock;
    private Category category;
    
    public Product(int id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public void validate() {    
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome do produto não pode estar vazio.");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("O preço do produto não pode ser nulo ou negativo.");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("O estoque não pode estar nulo ou menor que zero.");
        }
        if (category == null || category.getId() <= 0) {
            throw new IllegalArgumentException("Categoria inválida para o produto.");
        }
    }
        
    @Override
    public String toString() {
        return name;
    }
}
