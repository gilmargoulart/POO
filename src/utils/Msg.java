package utils;

import javax.swing.JOptionPane;

import mains.MainProgram;

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
	
	public static void MsgStatusBar(String msg){
		setMsgStatusBar(msg, true);
	}
	
	public static void MsgStatusBar(String msg, boolean useTimeout){
		setMsgStatusBar(msg, useTimeout);
	}
	
	private static void setMsgStatusBar(String msg, boolean useTimeout){
		MainProgram.LBL_STATUSBAR.setText(msg);
		if (useTimeout) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(ConstantesSistema.WAIT_TIMEOUT_MSG_STATUSBAR);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					MainProgram.LBL_STATUSBAR.setText("");
				}
			}).start();
		}
	}
	
}