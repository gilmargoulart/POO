package mains;

import interfaceGrafica.CentralContato;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;

import utils.ConstantesSistema;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainProgram {

	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu mnCadastros;
	private JMenuItem mntmContato;
	private JSeparator separator;
	private JMenuItem mntmSair;
	private JMenu mnArquivo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConstantesSistema.inicioSistema();
					MainProgram window = new MainProgram();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainProgram() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				ConstantesSistema.fecharConexoes();
			}
		});
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		separator = new JSeparator();
		mnArquivo.add(separator);
		
		mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConstantesSistema.sairSistema();
			}
		});
		mnArquivo.add(mntmSair);
		
		mnCadastros = new JMenu("Cadastros");
		menuBar.add(mnCadastros);
		
		mntmContato = new JMenuItem("Contato");
		mntmContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CentralContato cc = new CentralContato();
				cc.setVisible(true);
			}
		});
		mnCadastros.add(mntmContato);
	}

}
