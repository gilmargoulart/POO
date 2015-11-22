package interfaceGrafica;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import entidades.Contato;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JProgressBar;

public class ManutencaoContrato extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblNome;
	private JLabel lblEndereo;
	private JLabel lblTelefone;
	private JLabel lblEmail;
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JButton btnCadastrar;
	private JButton btnCancelar;
	private int contatoCodigo;
	private Contato contato;
	private boolean isUpdateMode;
	private JProgressBar progressBar;
	
	/**
	 * Create the frame.
	 */
	public ManutencaoContrato(int contatoCodigo) {
		
		this.contatoCodigo = contatoCodigo;
		
		initialize();
	}
	
	private void initialize() {
		this.isUpdateMode = this.contatoCodigo > 0;
		
		setTitle((isUpdateMode ? "Contato":"Cadastrar contato"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 387, 273);
		getContentPane().setLayout(null);
		
		this.progressBar = new JProgressBar();
		this.progressBar.setBounds(10, 209, 351, 14);
		getContentPane().add(this.progressBar);
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(33, 51, 46, 14);
		getContentPane().add(lblNome);
		
		lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(33, 76, 46, 14);
		getContentPane().add(lblEndereo);
		
		lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(33, 101, 46, 14);
		getContentPane().add(lblTelefone);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(33, 126, 46, 14);
		getContentPane().add(lblEmail);
		
		txtNome = new JTextField();
		txtNome.setToolTipText("Nome");
		txtNome.setBounds(106, 48, 220, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		
		txtEndereco = new JTextField();
		txtEndereco.setBounds(106, 73, 220, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);
		
		txtTelefone = new JTextField();
		txtTelefone.setBounds(106, 98, 220, 20);
		getContentPane().add(txtTelefone);
		txtTelefone.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(106, 123, 220, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		btnCadastrar = new JButton((isUpdateMode ? "Salvar":"Cadastrar"));
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRecord();
			}
		});
		btnCadastrar.setBounds(144, 166, 89, 23);
		getContentPane().add(btnCadastrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(237, 166, 89, 23);
		getContentPane().add(btnCancelar);
		
		loadRecord();
		
	}
	
	private void loadRecord(){
		if (isUpdateMode) {
			progressBar.setIndeterminate(true);
			btnCadastrar.setEnabled(false);
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						if (contato == null){
							contato = new Contato(contatoCodigo);
						}
						txtNome.setText(contato.getNome());
						txtEndereco.setText(contato.getEndereco());
						txtTelefone.setText(contato.getNumeroTelefone());
						txtEmail.setText(contato.getEmail());
					} catch (Exception e) {
						e.printStackTrace();
					}
					progressBar.setIndeterminate(false);
					btnCadastrar.setEnabled(true);
			}}).start();
		}
	}
	
	private void saveRecord() {
		progressBar.setIndeterminate(true);
		btnCadastrar.setEnabled(false);
		btnCancelar.setEnabled(false);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (isUpdateMode) {
					try {
						if (contato == null){
							contato = new Contato(contatoCodigo);
						}
						contato.setNome(txtNome.getText());
						contato.setEndereco(txtEndereco.getText());
						contato.setNumeroTelefone(txtTelefone.getText());
						contato.setEmail(txtEmail.getText());
						contato.Alterar();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					String
						nome = txtNome.getText()
						,endereco = txtEndereco.getText()
						,numeroTelefone = txtTelefone.getText()
						,email = txtEmail.getText();
					
					contato = new Contato(0, nome, endereco, numeroTelefone, email);
					contato.Inserir();
				}
				progressBar.setIndeterminate(false);
				btnCadastrar.setEnabled(true);
				btnCancelar.setEnabled(true);
			}
		}).start();
	}
}