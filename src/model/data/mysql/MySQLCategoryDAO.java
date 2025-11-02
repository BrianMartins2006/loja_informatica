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
import model.data.CategoryDAO;
import model.data.DAOUtils;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLCategoryDAO implements CategoryDAO{

	@Override
	public void save(Category category) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlIsert = " INSERT INTO "
					        + " categoria VALUES "
					        + " (DEFAULT, ?, ?); ";

			preparedStatement = connection.prepareStatement(sqlIsert);
			preparedStatement.setString(1, category.getName());
			preparedStatement.setString(2, category.getDescription());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao inserir categoria no BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement); 
			DAOUtils.close(connection);
		}
		
	}

	@Override
	public void update(Category category) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlUpdate = " UPDATE categoria "
					         + " set "
					         + " nome = ?, "
					         + " descricao = ?"
					         + " WHERE id = ?; ";

			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setString(1, category.getName());
			preparedStatement.setString(2, category.getDescription());
			preparedStatement.setInt(3, category.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar categoria no BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
		
	}

	@Override
	public void delete(Category category) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlUpdate = " DELETE FROM categoria WHERE id = ?; ";

			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setInt(1, category.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao deletar categoria do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}	
		
	}

	@Override
	public List<Category> findAll() throws ModelException {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		List<Category> categoriesList = new ArrayList<>();

		try {
			connection = MySQLConnectionFactory.getConnection();

			statement = connection.createStatement();
			String sqlSelect = "SELECT * FROM categoria ORDER BY nome;";
			rs = statement.executeQuery(sqlSelect);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("nome");
				String description = rs.getString("descricao");

				Category category = new Category(id);
				category.setName(name);
				category.setDescription(description);

				categoriesList.add(category);
			}

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao carregar categorias do BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(statement);
			DAOUtils.close(connection);
		}

		return categoriesList;
	}

	@Override
	public Category findById(int id) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Category category = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlSelect = "SELECT * FROM categoria WHERE id = ?;";
			preparedStatement = connection.prepareStatement(sqlSelect);
			preparedStatement.setInt(1, id);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				String name = rs.getString("nome");
				String description = rs.getString("descricao");
				

				category = new Category(id);
				category.setName(name);
				category.setDescription(description);
			}
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao buscar categoria por id no BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}

		return category;
	}

}
