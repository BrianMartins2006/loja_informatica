package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.ModelException;
import model.User;
import model.data.DAOUtils;
import model.data.UserDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLUserDAO implements UserDAO {

	@Override
	public void save(User user) throws ModelException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = MySQLConnectionFactory.getConnection();

	        String sqlIsert = " INSERT INTO users (email, senha_hash, pergunta_seguranca, resposta_hash) "
	                + " VALUES (?, ?, ?, ?); ";

	        preparedStatement = connection.prepareStatement(sqlIsert, Statement.RETURN_GENERATED_KEYS);
	        
	        preparedStatement.setString(1, user.getEmail());
	        preparedStatement.setString(2, user.getPasswordHash());
	        preparedStatement.setString(3, user.getSecurityquestion());
	        preparedStatement.setString(4, user.getResponseHash());

	        preparedStatement.executeUpdate();
	        
	        try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
	            if (rs.next()) {
	                user.setId(rs.getInt(1));
	            }
	        }

	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao cadastrar user no BD.", sqle);
	    } finally {
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }
	}

	@Override
	public void update(User user) throws ModelException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = MySQLConnectionFactory.getConnection();

	        String sqlUpdate = " UPDATE users "
	                + " set "
	                + " senha_hash = ?, "
	                + " pergunta_seguranca = ?, "
	                + " resposta_hash = ? "
	                + " WHERE id = ?; ";

	        preparedStatement = connection.prepareStatement(sqlUpdate);
	        
	        preparedStatement.setString(1, user.getPasswordHash());
	        preparedStatement.setString(2, user.getSecurityquestion());
	        preparedStatement.setString(3, user.getResponseHash());
	        preparedStatement.setInt(4, user.getId());

	        int rows = preparedStatement.executeUpdate();
	        
	        if (rows == 0) {
	            throw new ModelException("Usuário com ID " + user.getId() + " não encontrado para atualização.");
	        }

	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao atualizar user no BD.", sqle);
	    } finally {
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }		
	}

	@Override
	public User findSecurityDataByEmail(String email) throws ModelException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    User user = null;

	    try {
	        connection = MySQLConnectionFactory.getConnection();

	        String sqlSelect = "SELECT id, senha_hash, pergunta_seguranca, resposta_hash FROM users WHERE email = ?;";
	        preparedStatement = connection.prepareStatement(sqlSelect);
	        preparedStatement.setString(1, email);

	        rs = preparedStatement.executeQuery(); 

	        if (rs.next()) {
	            int id = rs.getInt("id");
	            String senhaHash = rs.getString("senha_hash");
	            String perguntaSeguranca = rs.getString("pergunta_seguranca");
	            String respostaHash = rs.getString("resposta_hash");

	            user = new User(id);
	            user.setEmail(email);
	            user.setPasswordHash(senhaHash);
	            user.setSecurityquestion(perguntaSeguranca);
	            user.setResponseHash(respostaHash);
	        }
	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao buscar dados de segurança por email no BD.", sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }

	    return user;
	}

	}

