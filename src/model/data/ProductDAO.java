package model.data;

import java.util.List;

import model.ModelException;
import model.Product;

public interface ProductDAO {
	void save(Product product) throws ModelException;
	void update(Product product) throws ModelException;
	void delete(Product product) throws ModelException;
	List<Product> findAll() throws ModelException;
	List<Product> findByUserId(int categoryId) throws ModelException;
}
