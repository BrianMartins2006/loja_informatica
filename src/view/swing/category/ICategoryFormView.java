package view.swing.category;

import model.Category;

public interface ICategoryFormView {
	Category getCategoryFromForm();
    void setCategoryInForm(Category category);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
