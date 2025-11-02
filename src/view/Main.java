package view;

import Util.SenhaUtil;

public class Main {

	public static void main(String[] args) 
			throws Exception {
		
			String senha = "minhaSenha123"; 
	        String hash = SenhaUtil.generateHash(senha);

	        System.out.println("Senha original: " + senha);
	        System.out.println("Hash gerado: " + hash);

	        System.out.println("Verificação (correta): " + SenhaUtil.verifyPassword("minhaSenha123", hash));
	        System.out.println("Verificação (errada): " + SenhaUtil.verifyPassword("outraSenha", hash));
	}
}
