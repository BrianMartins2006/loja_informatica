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
import model.User;
import model.ModelException;

public class RegisterView extends JDialog {
    private boolean authenticated = false; 
    private final JTextField emailField = new JTextField(20);
    private final JTextField questionField = new JTextField(20);
    private final JTextField responseField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    
    private final AuthController authController = new AuthController(); 

    public RegisterView() {
        setTitle("LOJA - Cadastro");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Digite uma pergunta para recuperação de senha:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(questionField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Digite sua resposta:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(responseField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(passwordField, gbc);


        JPanel buttons = new JPanel();
        JButton SaveBtn = new JButton("Salvar");
        JButton cancelBtn = new JButton("Cancelar");
        buttons.add(SaveBtn);
        buttons.add(cancelBtn); 

        SaveBtn.addActionListener(e -> performRegister()); 

        cancelBtn.addActionListener(e -> {
            dispose();
        });

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }
    
   
    private void performRegister() {
        String email = emailField.getText();
        String senhaPura = new String(passwordField.getPassword()); 
        String pergunta = questionField.getText();
        String respostaPura = responseField.getText(); 
        
        User newUser = new User(0); 
        newUser.setEmail(email);
        newUser.setPasswordHash(senhaPura);
        newUser.setSecurityquestion(pergunta);
        newUser.setResponseHash(respostaPura);
        
        try {
            authController.registerUser(newUser); 
            
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Erro de validação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}