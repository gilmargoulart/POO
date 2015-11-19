package interfaceGrafica;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;

public class CentralContato extends JFrame {
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

	/**
	 * Create the frame.
	 */
	public CentralContato() {
		setTitle("Cadastrar contato");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 387, 273);
		getContentPane().setLayout(null);
		
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
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(237, 166, 89, 23);
		getContentPane().add(btnCadastrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(144, 166, 89, 23);
		getContentPane().add(btnCancelar);
	}
}
