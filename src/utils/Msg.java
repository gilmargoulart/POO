package utils;

import javax.swing.JOptionPane;

public class Msg {
	
	public static void Aviso(String mensagem, String titulo) {
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void Erro(String mensagem, String titulo) {
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void Informacao(String mensagem, String titulo) {
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String Pergunta(String mensagem, String titulo) {
		return JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);
	}
	
	public static String PerguntaComOpcaoPadrao(String mensagem, Object opcaoPadrao) {
		return JOptionPane.showInputDialog(null, mensagem, opcaoPadrao);
	}
}