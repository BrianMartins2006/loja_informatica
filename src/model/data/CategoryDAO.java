package model.data;

import java.util.List;

import model.ModelException;
import model.Category;

public interface CategoryDAO {
	void save(Category category) throws ModelException;
	void update(Category category) throws ModelException;
	void delete(Category category) throws ModelException;
	List<Category> findAll() throws ModelException;
    Category findById(int id) throws ModelException;
}
