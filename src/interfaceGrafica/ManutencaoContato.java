package interfaceGrafica;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import entidades.Contato;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JProgressBar;

import utils.ConstantesSistema;
import utils.Msg;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import java.awt.Toolkit;

public class ManutencaoContato extends JFrame {
	
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
	public ManutencaoContato(int contatoCodigo) {
		
		this.contatoCodigo = contatoCodigo;
		
		initialize();
	}
	
	private void initialize() {
		getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				System.out.println(ke.getKeyCode());
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setVisible(false);
					dispose();
				}
			}
		});
		this.isUpdateMode = this.contatoCodigo > 0;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutencaoContato.class.getResource((isUpdateMode ? "/icones/edit_contact.png":"/icones/add_contact.png"))));
		setTitle((isUpdateMode ? "Contato":"Cadastrar contato"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		int resX = (ConstantesSistema.RESOLUCAO.width / 2) - (387 / 2);
		int resY = (ConstantesSistema.RESOLUCAO.height / 2) - (273 / 2);
		
		setBounds(resX, resY, 387, 273);
		getContentPane().setLayout(null);
		
		this.progressBar = new JProgressBar();
		this.progressBar.setBounds(10, 213, 351, 14);
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
		
		btnCadastrar = new JButton();
		this.btnCadastrar.setIcon(new ImageIcon(ManutencaoContato.class.getResource((isUpdateMode ? "/icones/save_as.png":"/icones/save.png"))));
		this.btnCadastrar.setToolTipText("Salvar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRecord();
			}
		});
		btnCadastrar.setBounds(219, 154, 48, 48);
		getContentPane().add(btnCadastrar);
		
		btnCancelar = new JButton("");
		this.btnCancelar.setIcon(new ImageIcon(ManutencaoContato.class.getResource("/icones/cancel.png")));
		this.btnCancelar.setToolTipText("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(278, 154, 48, 48);
		getContentPane().add(btnCancelar);
		
		loadRecord();
	}
	
	private void loadRecord(){
		if (isUpdateMode) {
			Msg.MsgStatusBar("Carregando dados do contato...", false);
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
						Msg.MsgStatusBar("Carregando dados do contato... ERRO!");
						e.printStackTrace();
					}
					progressBar.setIndeterminate(false);
					btnCadastrar.setEnabled(true);
					Msg.MsgStatusBar("Carregando dados do contato... OK");
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
					Msg.MsgStatusBar("Atualizando dados do contato...", false);
					try {
						if (contato == null){
							contato = new Contato(contatoCodigo);
						}
						contato.setNome(txtNome.getText());
						contato.setEndereco(txtEndereco.getText());
						contato.setNumeroTelefone(txtTelefone.getText());
						contato.setEmail(txtEmail.getText());
						if(contato.Alterar()){
							Msg.MsgStatusBar("Atualizando dados do contato... OK");
							dispose();
						} else {
							Msg.MsgStatusBar("Atualizando dados do contato... Nenhuma informação alterada!");
						}
					} catch (Exception e) {
						Msg.MsgStatusBar("Atualizando dados do contato... Erro!");
						e.printStackTrace();
					}
				} else {
					Msg.MsgStatusBar("Cadastrando contato...", false);
					String
						nome = txtNome.getText()
						,endereco = txtEndereco.getText()
						,numeroTelefone = txtTelefone.getText()
						,email = txtEmail.getText();
					
					contato = new Contato(0, nome, endereco, numeroTelefone, email);
					if (contato.Inserir()) {
						Msg.MsgStatusBar("Cadastrando contato... OK");
						dispose();
					} else {
						Msg.MsgStatusBar("Cadastrando contato... Contato não cadastrado!");
					}
				}
				progressBar.setIndeterminate(false);
				btnCadastrar.setEnabled(true);
				btnCancelar.setEnabled(true);
			}
		}).start();
	}
}