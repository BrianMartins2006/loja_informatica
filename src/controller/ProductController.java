package controller;

import java.util.List;

import model.Category;
import model.ModelException;
import model.Product;
import model.data.DAOFactory;
import model.data.ProductDAO;
import view.swing.product.IProductFormView;
import view.swing.product.IProductListView;

public class ProductController {
	 private final ProductDAO productDAO = DAOFactory.createProductDAO();
	    private IProductListView productListView;
	    private IProductFormView productFormView;

	    // Listagem
	    public void loadProduct() {
	        try {
	            List<Product> product = productDAO.findAll();
	            productListView.setProductList(product);
	        } catch (ModelException e) {
	            productListView.showMessage("Erro ao carregar produtos: " + e.getMessage());
	        }
	    }

	    // Salvar ou atualizar
	    public void saveOrUpdate(boolean isNew) { 
	        Product product = productFormView.getProductFromForm();
	        if (product == null) {
	            productFormView.showErrorMessage("Produto não pode ser nulo!");
	            return;
	        } 

	        try {
	            product.validate();
	        } catch (IllegalArgumentException e) {
	            productFormView.showErrorMessage("Erro de validação: " + e.getMessage());
	            return;
	        }

	        try {
	            if (isNew) {
	                productDAO.save(product);
	            } else {
	                productDAO.update(product);
	            }
	            productFormView.showInfoMessage("Produto salvo com sucesso!");
	            productFormView.close();
	        } catch (ModelException e) {
	            productFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
	        }
	    }

	    // Excluir
	    public void deleteProduct(Product product) {
	        try {
	            productDAO.delete(product);
	            productListView.showMessage("Produto excluído!");
	            loadProduct();
	        } catch (ModelException e) {
	            productListView.showMessage("Erro ao excluir produto: " + e.getMessage());
	        }
	    }

	    public void setProductFormView(IProductFormView productFormView) {
	        this.productFormView = productFormView;
	    }

	    public void setProductListView(IProductListView productListView) {
	        this.productListView = productListView;
	    }

	    public List<Category> getAllCategory() {
	        try {
	            return DAOFactory.createCategoryDAO().findAll();
	        } catch (ModelException e) {
	            productFormView.showErrorMessage("Erro ao carregar categorias: " + e.getMessage());
	            return List.of();
	        }
	    }

}
