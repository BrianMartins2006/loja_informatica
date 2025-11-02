package view.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.AuthController;
import model.ModelException;
import model.User;

public class ForgotPasswordView extends JDialog {
    
    private final AuthController authController = new AuthController();
    private User userInRecovery; 

    private final JTextField emailField = new JTextField(20);
    private final JButton findUserBtn = new JButton("Buscar Pergunta");

    private final JLabel questionLabel = new JLabel("Pergunta:");
    private final JTextField responseField = new JTextField(20);
    private final JPasswordField newPasswordField = new JPasswordField(20);
    private final JButton resetPasswordBtn = new JButton("Redefinir Senha");

    public ForgotPasswordView(LoginView loginView) {
        super(loginView, "LOJA - Recuperar Senha", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; formPanel.add(emailField, gbc);
        gbc.gridx = 2; formPanel.add(findUserBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3; 
        questionLabel.setVisible(false);
        formPanel.add(questionLabel, gbc);

        gbc.gridy = 2; gbc.gridwidth = 1; formPanel.add(new JLabel("Resposta:"), gbc);
        gbc.gridx = 1; formPanel.add(responseField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Nova Senha:"), gbc);
        gbc.gridx = 1; formPanel.add(newPasswordField, gbc);
        
        gbc.gridx = 2; formPanel.add(resetPasswordBtn, gbc);

     
        setRecoveryFieldsVisible(false);

        findUserBtn.addActionListener(e -> findUserAction());
        resetPasswordBtn.addActionListener(e -> resetPasswordAction());

        add(formPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(loginView);
    }
    
    private void setRecoveryFieldsVisible(boolean visible) {
        questionLabel.setVisible(visible);
        responseField.setVisible(visible);
        newPasswordField.setVisible(visible);
        resetPasswordBtn.setVisible(visible);
        responseField.setText("");
        newPasswordField.setText("");
        if (visible) pack(); 
    }

    
    private void findUserAction() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o email.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            userInRecovery = authController.findUserForRecovery(email);

            if (userInRecovery != null && userInRecovery.getSecurityquestion() != null) {
                questionLabel.setText("Pergunta: " + userInRecovery.getSecurityquestion());
                setRecoveryFieldsVisible(true);
                emailField.setEnabled(false); 
                findUserBtn.setEnabled(false); 
            } else {
                JOptionPane.showMessageDialog(this, "Email não cadastrado ou sem pergunta de segurança.", "Erro", JOptionPane.ERROR_MESSAGE);
                setRecoveryFieldsVisible(false);
            }
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void resetPasswordAction() {
        String respostaDigitada = responseField.getText();
        String novaSenhaPura = new String(newPasswordField.getPassword());
        
        if (respostaDigitada.isEmpty() || novaSenhaPura.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a resposta e a nova senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try { 
            boolean success = authController.resetPassword(
                userInRecovery, 
                respostaDigitada, 
                novaSenhaPura
            );
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Senha redefinida com sucesso! Use a nova senha para logar.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Resposta de segurança incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
             JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(this, "Erro ao redefinir a senha: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}