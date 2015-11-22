package utils;

import conexao.Conexao;

public class ConstantesSistema {
	public static String FORMATO_DATA = "dd/MM/yyyy";
	public static String VERSAO_SISTEMA = "1.0.3";
	
	public static void inicioSistema(){
		//new Conexao().conect();
	}
	
	public static void fecharConexoes(){
		new Conexao().disconnect();
	}
	
	public static void sairSistema(){
		fecharConexoes();
		System.exit(0);
	}
	
}
