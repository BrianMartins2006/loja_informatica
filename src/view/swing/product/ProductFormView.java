package view.swing.product;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ProductController;
import model.Category;
import model.Product;

public class ProductFormView extends JDialog implements IProductFormView {
    private final JTextField nameField = new JTextField(20);
    private final JTextField priceField = new JTextField(20);
    private final JTextField stockField = new JTextField(20);
    private final JComboBox<Category> categoryComboBox = new JComboBox<>();
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");

    private final ProductController controller;
    private final boolean isNew;
    private final ProductListView parent;
    private Product product;

    public ProductFormView(ProductListView parent, Product product, ProductController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setProductFormView(this);

        this.parent = parent;
        this.product = product;
        this.isNew = (product == null);

        setTitle(isNew ? "Novo Produto" : "Editar Produto");
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0: Categoria
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1;
        add(categoryComboBox, gbc);

        // Preenche ComboBox com categorias
        List<Category> categories = controller.getAllCategory();
        DefaultComboBoxModel<Category> comboModel = new DefaultComboBoxModel<>();
        for (Category c : categories) {
            comboModel.addElement(c);
        }
        categoryComboBox.setModel(comboModel);

        // Linha 1: Nome
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Linha 2: Preço
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        add(priceField, gbc);

        // Linha 3: Estoque
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Estoque:"), gbc);
        gbc.gridx = 1;
        add(stockField, gbc);

        // Linha 4: Botões
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(btnPanel, gbc);

        if (!isNew) setProductInForm(product);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

    @Override
    public Product getProductFromForm() {
        if (product == null) product = new Product(0);

        try {
            product.setName(nameField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setStock(Integer.parseInt(stockField.getText()));
            product.setCategory((Category) categoryComboBox.getSelectedItem());
        } catch (NumberFormatException e) {
            showErrorMessage("Preço e estoque devem ser números válidos.");
            return null;
        }

        return product;
    }

    @Override
    public void setProductInForm(Product product) {
        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        stockField.setText(String.valueOf(product.getStock()));

        if (product.getCategory() != null) {
            boolean found = false;
            for (int i = 0; i < categoryComboBox.getItemCount(); i++) {
                Category c = categoryComboBox.getItemAt(i);
                if (c.getId() == product.getCategory().getId()) {
                    categoryComboBox.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                categoryComboBox.addItem(product.getCategory());
                categoryComboBox.setSelectedItem(product.getCategory());
            }
        }
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
