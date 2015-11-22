package mains;

import entidades.Contato;

public class GerarContatos {

	public static void main(String[] args) {
		int randomS = 50000;
		int randomT = 100000000;
		String nome, endereco, numeroTelefone, email;
		for (int i = 0; i < 5000; i++) {
			nome = "Contato Nome " + (i+1) + "_" + (int)(Math.random()*randomS);
			endereco = "Contato Endereco " + (i+1) + "_" + (int)(Math.random()*randomS);
			numeroTelefone = (i+1) + "." + (int)(Math.random()*randomT) + 100000;
			email = "email" + (i+1) + "_" + (int)(Math.random()*randomS) + "@empresashow.com";
			
			boolean isContatoInserido = new Contato(0, nome, endereco, numeroTelefone, email).Inserir();
			System.out.println("Contato inserido: " + isContatoInserido);
		}

	}

}
