package view.swing.product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.ProductController;
import model.Product;

public class ProductListView extends JDialog implements IProductListView{
	private ProductController controller;
    private final ProductTableModel tableModel = new ProductTableModel();
    private final JTable table = new JTable(tableModel);

    public ProductListView(JFrame parent) {
        super(parent, "Product", true);
        this.controller = new ProductController();
        this.controller.setProductListView(this);

        setSize(650, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Produto");
        addButton.addActionListener(e -> {
            ProductFormView form = new ProductFormView(this, null, controller);
            form.setVisible(true);
        });

        // Menu de contexto
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem deleteItem = new JMenuItem("Excluir");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Product product = tableModel.getProductAt(row);
                ProductFormView form = new ProductFormView(this, product, controller);
                form.setVisible(true);
            }
        });
        
        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
            	Product product = tableModel.getProductAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir produto?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deleteProduct(product);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadProduct();
    }
	
    @Override
	public void setProductList(List<Product> product) {
		tableModel.setProduct(product);
		
	}

	@Override
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
		
	}
	
	  // Atualiza lista após cadastro/edição/exclusão
    public void refresh() {
        controller.loadProduct();
    }
    
	// Tabela de produtos
	static class ProductTableModel extends AbstractTableModel {
	    private final String[] columns = {"Nome", "Preço", "Estoque", "Categoria"};
	    private List<Product> product = new ArrayList<>();

	    public void setProduct(List<Product> product) {
	        this.product = product;
	        fireTableDataChanged();
	    }

	    public Product getProductAt(int row) {
	        return product.get(row);
	    }

	    @Override
	    public int getRowCount() {
	        return product.size();
	    }

	    @Override
	    public int getColumnCount() {
	        return columns.length;
	    }

	    @Override
	    public String getColumnName(int col) {
	        return columns[col];
	    }

	    @Override
	    public Object getValueAt(int row, int col) {
	        Product p = product.get(row);
	        switch (col) {
	            case 0: return p.getName();              // Nome
	            case 1: return String.format("%.2f", p.getPrice()); // Preço
	            case 2: return p.getStock();             // Estoque
	            case 3: return p.getCategory() != null ? p.getCategory().getName() : ""; // Categoria
	            default: return null;
	        }
	    }

	    @Override
	    public boolean isCellEditable(int row, int col) {
	        return false;
	    }
	}
}
