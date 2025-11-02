package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.ModelException;
import model.Product;
import model.data.DAOFactory;
import model.data.DAOUtils;
import model.data.ProductDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLProductDAO implements ProductDAO {

	@Override
	public void save(Product product) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlIsert = " INSERT INTO "
			        + " produto VALUES "
			        + " (DEFAULT, ?, ?,?,?); ";

			preparedStatement = connection.prepareStatement(sqlIsert);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setDouble(2, product.getPrice());
			preparedStatement.setInt(3, product.getStock());
			preparedStatement.setInt(4, product.getCategory().getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao inserir produto do BD.", sqle);
		} catch (ModelException me) { 
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
		
	}

	@Override
	public void update(Product product) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();
			
			String sqlUpdate = "UPDATE produto "
					+"SET "
					+"nome = ?, " 
					+"preco = ?, " 
					+"estoque = ?, " 
					+"categoria_id = ? "
					+"WHERE id = ?;";
			
			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setDouble(2, product.getPrice());
			preparedStatement.setInt(3, product.getStock());
			preparedStatement.setInt(4, product.getCategory().getId());
			preparedStatement.setInt(5, product.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar produto no BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
		
	}

	@Override
	public void delete(Product product) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();  

			String sqlDelete = "delete from produto where id = ?;";

			preparedStatement = connection.prepareStatement(sqlDelete);
			preparedStatement.setInt(1, product.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao excluir produto do BD.", sqle);
		} finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
		
	}

	@Override
	public List<Product> findAll() throws ModelException {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		List<Product> productList = new ArrayList<>();

		try {
			connection = MySQLConnectionFactory.getConnection();

			statement = connection.createStatement();
			String sqlSeletc = " SELECT * FROM produto order by nome asc ; ";

			rs = statement.executeQuery(sqlSeletc);

			setUpCategory(rs, productList);
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao carregar produtos do BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(statement);
			DAOUtils.close(connection);
		}

		return productList;
	}

	@Override
	public List<Product> findByCategoryId(int categoryId) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Product> producList = new ArrayList<>();

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlSeletc = " SELECT * FROM produto WHERE categoria_id = ?; ";
			preparedStatement = connection.prepareStatement(sqlSeletc);
			preparedStatement.setInt(1, categoryId);

			rs = preparedStatement.executeQuery();

			setUpCategory(rs, producList);

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao carregar produtos do BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}

		return producList;
	}
	
	private void setUpCategory(ResultSet rs, List<Product> productList)
            throws SQLException, ModelException {
		
		while (rs.next()) {
			int productId = rs.getInt("id"); 
			String productName= rs.getString("nome");
			Double productPrice= rs.getDouble("preco");
			int productStock = rs.getInt("estoque");
			int categoryId = rs.getInt("categoria_id");

			Product newProduct = new Product(productId);
			newProduct.setName(productName);
			newProduct.setPrice(productPrice);
			newProduct.setStock(productStock);

			Category category = DAOFactory.createCategoryDAO().findById(categoryId);
			newProduct.setCategory(category);
			
			productList.add(newProduct);
		}
	}
}
