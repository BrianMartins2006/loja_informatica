package model;

public class User {
	private int id;
	private String email;
	private String passwordHash;
	private String securityquestion;
	private String responseHash;

	
	public User(int id) {
		this.id = id;
	}
	
	public User(int id,String email, String passwordHash, String securityquestion, String responseHash) {
		this.id = id;
		this.email = email;
		this.passwordHash = passwordHash;
		this.securityquestion = securityquestion;
		this.responseHash = responseHash;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getSecurityquestion() {
		return securityquestion;
	}

	public void setSecurityquestion(String securityquestion) {
		this.securityquestion = securityquestion;
	}

	public String getResponseHash() {
		return responseHash;
	}

	public void setResponseHash(String responseHash) {
		this.responseHash = responseHash;
	}
	

	public void validate() {
		
		if (email == null || email.isBlank() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new IllegalArgumentException("O email do usuário é inválido.");
		}	
        
		if (securityquestion == null || securityquestion.isBlank()) {
	        throw new IllegalArgumentException("A pergunta de segurança não pode ser vazia");
	    }
		
		if (responseHash == null || responseHash.isBlank()) {
	        throw new IllegalArgumentException("A resposta não pode ser vazia.");
	    }
		
		if (passwordHash == null || passwordHash.isBlank()) {
	        throw new IllegalArgumentException("A senha não pode ser vazia.");
	    }
		
	}

}