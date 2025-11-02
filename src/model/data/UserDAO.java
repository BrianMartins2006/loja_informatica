package model.data;

import model.ModelException;
import model.User;

public interface UserDAO {
	void save(User user) throws ModelException;
	void update(User user) throws ModelException;
	User findSecurityDataByEmail(String email) throws ModelException;
}
