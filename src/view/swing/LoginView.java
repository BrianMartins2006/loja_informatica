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

public class LoginView extends JDialog {
    private boolean authenticated = false;
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final AuthController authController = new AuthController();

    public LoginView() {
        setTitle("LOJA - Login");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(passwordField, gbc);

        JPanel buttons = new JPanel();
        JButton loginBtn = new JButton("Entrar");
        JButton cancelBtn = new JButton("Cancelar");
        JButton registerBtn = new JButton("Cadastrar");
        JButton forgotpasswordBtn = new JButton("Esqueci senha");
        buttons.add(loginBtn);
        buttons.add(registerBtn);
        buttons.add(forgotpasswordBtn);
        buttons.add(cancelBtn);
        
      
        loginBtn.addActionListener(e -> performLogin());

        cancelBtn.addActionListener(e -> {
            authenticated = false;
            dispose();
        });
        
        registerBtn.addActionListener(e -> {
            RegisterView registerView = new RegisterView();
            registerView.setVisible(true);
        });

        forgotpasswordBtn.addActionListener(e -> {
            ForgotPasswordView forgotView = new ForgotPasswordView(this);  
            forgotView.setVisible(true);
        });

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

  
    private void performLogin() {
        String email = emailField.getText();
        String senhaDigitada = new String(passwordField.getPassword());
        
        if (email.isEmpty() || senhaDigitada.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.WARNING_MESSAGE);
             return;
        } 
        
        try {
            User user = authController.authenticate(email, senhaDigitada);

            if (user != null) {
                authenticated = true;
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar dados de autenticação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}