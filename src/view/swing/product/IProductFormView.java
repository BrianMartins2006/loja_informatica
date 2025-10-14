package view.swing.product;

import model.Product;

public interface IProductFormView {
	Product getProductFromForm();
    void setProductInForm(Product product);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();

}
