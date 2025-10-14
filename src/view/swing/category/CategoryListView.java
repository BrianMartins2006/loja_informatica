package view.swing.category;

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

import controller.CategoryController;
import model.Category;

public class CategoryListView extends JDialog implements ICategoryListView {
	private CategoryController controller;
    private final CategoryTableModel tableModel = new CategoryTableModel();
    private final JTable table = new JTable(tableModel);

    public CategoryListView(JFrame parent) {
        super(parent, "Categoria", true);
        this.controller = new CategoryController();
        this.controller.setCategoryListView(this);

        setSize(650, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Categoria");
        addButton.addActionListener(e -> {
            CategoryFormView form = new CategoryFormView(this, null, controller);
            form.setVisible(true);
        });

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
                Category category = tableModel.getCategoryAt(row);
                CategoryFormView form = new CategoryFormView(this, category, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Category category = tableModel.getCategoryAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir categoria?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deleteCategory(category);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadCategory();
    }

	@Override
	public void setCategoryList(List<Category> category) {
		tableModel.setCategory(category);
		
	}

	@Override
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
		
	}
	
	// Atualiza lista após cadastro/edição/exclusão
    public void refresh() {
        controller.loadCategory();
    }
	
	// Tabela de categorias
    static class CategoryTableModel extends AbstractTableModel {
        private final String[] columns = {"ID", "Nome", "Descrição"};
        private List<Category> category = new ArrayList<>();

        public void setCategory(List<Category> category) {
            this.category = category;
            fireTableDataChanged();
        }

        public Category getCategoryAt(int row) {
            return category.get(row);
        }
        
       

        @Override public int getRowCount() { return category.size(); }

        @Override public int getColumnCount() { return columns.length; }

        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Category c = category.get(row);
            switch (col) {
                case 0: return c.getId();
                case 1: return c.getName();
                case 2: return c.getDescription();
                default: return null;
            }
        }
        @Override public boolean isCellEditable(int row, int col) { return false; }
    }
	

}
