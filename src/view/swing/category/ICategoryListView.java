package view.swing.category;

import java.util.List;

import model.Category;

public interface ICategoryListView {
	 void setCategoryList(List<Category> category);
	    void showMessage(String msg);

}
