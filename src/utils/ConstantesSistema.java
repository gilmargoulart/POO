package utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import conexao.Conexao;

public class ConstantesSistema {
	public static String FORMATO_DATA = "dd/MM/yyyy";
	public static String VERSAO_SISTEMA = "1.0.4";
	public static Dimension RESOLUCAO = Toolkit.getDefaultToolkit().getScreenSize();
	public static int WAIT_TIMEOUT_MSG_STATUSBAR = 2000;
	
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
