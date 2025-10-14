package view.swing.product;

import java.util.List;

import model.Product;

public interface IProductListView {
	void setProductList(List<Product> product);
    void showMessage(String msg);

}