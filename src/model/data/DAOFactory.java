package model.data;

import model.data.mysql.MySQLCategoryDAO;
import model.data.mysql.MySQLProductDAO;
import model.data.mysql.MySQLUserDAO;

public final class DAOFactory {
	
	public static UserDAO createUserDAO() {
		return new MySQLUserDAO();
	}
	
	public static CategoryDAO createCategoryDAO() {
		return new MySQLCategoryDAO();
	} 
	
	public static ProductDAO createProductDAO() {
		return new MySQLProductDAO();
	}
}
