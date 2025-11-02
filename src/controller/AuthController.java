package controller;

import model.User;
import model.ModelException;
import model.data.DAOFactory;
import model.data.UserDAO;
import Util.SenhaUtil;

public class AuthController {
    
    private final UserDAO userDAO = DAOFactory.createUserDAO();
    public User registerUser(User newUser) throws ModelException {
        newUser.validate(); 
        
        String senhaPura = newUser.getPasswordHash(); 
        String respostaPura = newUser.getResponseHash(); 
        
        if (senhaPura != null && !senhaPura.isEmpty()) {
            newUser.setPasswordHash(SenhaUtil.generateHash(senhaPura));
        }
        
        if (respostaPura != null && !respostaPura.isEmpty()) {
            newUser.setResponseHash(SenhaUtil.generateHash(respostaPura));
        }

        userDAO.save(newUser);
        return newUser;
    }

    
    public User authenticate(String email, String senhaPura) throws ModelException {
        User user = userDAO.findSecurityDataByEmail(email);

        if (user == null) {
            return null;
        }

        boolean isMatch = SenhaUtil.verifyPassword(senhaPura, user.getPasswordHash());

        if (isMatch) {
            return user; 
        } else {
            return null; 
        }
    }
    
    
    public User findUserForRecovery(String email) throws ModelException {
        return userDAO.findSecurityDataByEmail(email);
    }
    
    
    public boolean resetPassword(User user, String respostaDigitada, String novaSenhaPura) throws ModelException {
        boolean respostaCorreta = SenhaUtil.verifyPassword(respostaDigitada, user.getResponseHash());

        if (!respostaCorreta) {
            return false; 
        }
        
        if (novaSenhaPura == null || novaSenhaPura.length() < 6) { 
             throw new IllegalArgumentException("A nova senha deve ter pelo menos 6 caracteres.");
        }
        String novoHashSenha = SenhaUtil.generateHash(novaSenhaPura);
        
        user.setPasswordHash(novoHashSenha);
        
        userDAO.update(user);
        
        return true;
    }
}