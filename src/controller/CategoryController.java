package controller;

import java.util.List;

import model.Category;
import model.ModelException;
import model.data.CategoryDAO;
import model.data.DAOFactory;
import view.swing.category.ICategoryFormView;
import view.swing.category.ICategoryListView;

public class CategoryController {
	private final CategoryDAO categoryDAO = DAOFactory.createCategoryDAO();
    private ICategoryListView categoryListView;
    private ICategoryFormView categoryFormView;

    // Listagem
    public void loadCategory() {
        try {
            List<Category> category = categoryDAO.findAll();
            categoryListView.setCategoryList(category);
        } catch (ModelException e) {
            categoryListView.showMessage("Erro ao carregar categoria: " + e.getMessage());
        }
    }

    // Salvar ou atualizar
    public void saveOrUpdate(boolean isNew) {
        Category category = categoryFormView.getCategoryFromForm();

        try {
            category.validate();
        } catch (IllegalArgumentException e) {
            categoryFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
                categoryDAO.save(category);
            } else {
                categoryDAO.update(category);
            }
            categoryFormView.showInfoMessage("Categoria salva com sucesso!");
            categoryFormView.close();
        } catch (ModelException e) {
            categoryFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }

    // Excluir
    public void deleteCategory(Category category) {
        try {
            categoryDAO.delete(category);
            categoryListView.showMessage("Categoria excluída!");
            loadCategory();
        } catch (ModelException e) {
            categoryListView.showMessage("Erro ao excluir: " + e.getMessage());
        }
    }

    public void setCategoryFormView(ICategoryFormView categoryFormView) {
        this.categoryFormView = categoryFormView;
    }

    public void setCategoryListView(ICategoryListView categoryListView) {
        this.categoryListView = categoryListView;
    }
}


