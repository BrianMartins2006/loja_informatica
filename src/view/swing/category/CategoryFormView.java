package view.swing.category;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.CategoryController;
import model.Category;

public class CategoryFormView extends JDialog implements ICategoryFormView {
	private final JTextField nameField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");
    private CategoryController controller;
    private final boolean isNew;
    private final CategoryListView parent;
    private Category category;
	
    public CategoryFormView(CategoryListView parent, Category category, CategoryController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setCategoryFormView(this);

        this.parent = parent;
        this.category = category;
        this.isNew = (category == null);

        setTitle(isNew ? "Nova Categoria" : "Editar Categoria");
        setSize(350, 220);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        add(descriptionField, gbc);
        
       

        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) setCategoryInForm(category);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }
    
    
    
    
    
	@Override
	public Category getCategoryFromForm() {
		if (category == null) category = new Category(0);
        category.setName(nameField.getText());
        category.setDescription(descriptionField.getText());
        return category;
	}

	@Override
	public void setCategoryInForm(Category category) {
		nameField.setText(category.getName());
        descriptionField.setText(category.getDescription());
		
	}

	@Override
	public void showInfoMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void showErrorMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void close() {
		parent.refresh();
        dispose();
		
	}

}
