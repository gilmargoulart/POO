package interfaceGrafica;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import utils.Msg;
import conexao.Conexao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JProgressBar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;

public class CentralContato extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tblContatos;
	private JTextField txtFiltrobusca;
	private JButton btnBuscar;
	private JButton btnInserir;
	private DefaultTableModel modeloTabela;
	private JScrollPane scrollPane;
	private JProgressBar progressBar;
	private JCheckBox chckbxCodigo;
	private JCheckBox chckbxNome;
	private JCheckBox chckbxEndereco;
	private JCheckBox chckbxEmail;
	private JCheckBox chckbxTelefone;
	/**
	 * Create the frame.
	 */
	public CentralContato() {
		setTitle("Central de contatos");
		initialize();
		loadRecords();
	}
	
	@SuppressWarnings("serial")
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 643, 678);
		getContentPane().setLayout(null);
		modeloTabela = new DefaultTableModel(
				new Object[][] {
					},
					new String[] {
						"C\u00F3digo", "Nome", "Endere\u00E7o", "Email", "Telefone"
					}
				) {
					@SuppressWarnings("rawtypes")
					Class[] columnTypes = new Class[] {
						Integer.class, String.class, String.class, String.class, String.class
					};
					@SuppressWarnings({ "unchecked", "rawtypes" })
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
					boolean[] columnEditables = new boolean[] {
							false, false, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 607, 530);
		getContentPane().add(scrollPane);
		
		tblContatos = new JTable();
		this.tblContatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					openRecord();
				}
			}
		});
		scrollPane.setViewportView(tblContatos);
		tblContatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tblContatos.setModel(modeloTabela);
		this.tblContatos.getColumnModel().getColumn(0).setPreferredWidth(45);
		this.tblContatos.getColumnModel().getColumn(0).setMinWidth(20);
		this.tblContatos.getColumnModel().getColumn(0).setMaxWidth(100);
		this.tblContatos.getColumnModel().getColumn(1).setPreferredWidth(150);
		this.tblContatos.getColumnModel().getColumn(1).setMinWidth(30);
		this.tblContatos.getColumnModel().getColumn(1).setMaxWidth(500);
		this.tblContatos.getColumnModel().getColumn(2).setPreferredWidth(170);
		this.tblContatos.getColumnModel().getColumn(2).setMinWidth(50);
		this.tblContatos.getColumnModel().getColumn(2).setMaxWidth(700);
		this.tblContatos.getColumnModel().getColumn(3).setPreferredWidth(80);
		this.tblContatos.getColumnModel().getColumn(3).setMinWidth(40);
		this.tblContatos.getColumnModel().getColumn(3).setMaxWidth(800);
		this.tblContatos.getColumnModel().getColumn(4).setPreferredWidth(100);
		this.tblContatos.getColumnModel().getColumn(4).setMinWidth(20);
		this.tblContatos.getColumnModel().getColumn(4).setMaxWidth(200);
		
		txtFiltrobusca = new JTextField();
		this.txtFiltrobusca.setToolTipText("Buscar por c\u00F3digo, nome, endere\u00E7o, email ou telefone.");
		txtFiltrobusca.setBounds(10, 12, 457, 20);
		getContentPane().add(txtFiltrobusca);
		txtFiltrobusca.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadRecords();
			}
		});
		btnBuscar.setBounds(477, 12, 65, 23);
		getContentPane().add(btnBuscar);
		
		btnInserir = new JButton("Inserir");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManutencaoContrato cc = new ManutencaoContrato(0);
				cc.setVisible(true);
			}
		});
		btnInserir.setBounds(552, 12, 65, 23);
		getContentPane().add(btnInserir);
		
		this.progressBar = new JProgressBar();
		this.progressBar.setBounds(10, 614, 607, 14);
		getContentPane().add(this.progressBar);
		
		this.chckbxCodigo = new JCheckBox("C\u00F3digo");
		this.chckbxCodigo.setSelected(true);
		this.chckbxCodigo.setBounds(10, 34, 65, 20);
		getContentPane().add(this.chckbxCodigo);
		
		this.chckbxNome = new JCheckBox("Nome");
		this.chckbxNome.setSelected(true);
		this.chckbxNome.setBounds(76, 34, 53, 20);
		getContentPane().add(this.chckbxNome);
		
		this.chckbxEndereco = new JCheckBox("Endere\u00E7o");
		this.chckbxEndereco.setSelected(true);
		this.chckbxEndereco.setBounds(131, 34, 71, 20);
		getContentPane().add(this.chckbxEndereco);
		
		this.chckbxEmail = new JCheckBox("Email");
		this.chckbxEmail.setSelected(true);
		this.chckbxEmail.setBounds(204, 34, 53, 20);
		getContentPane().add(this.chckbxEmail);
		
		this.chckbxTelefone = new JCheckBox("Telefone");
		this.chckbxTelefone.setSelected(true);
		this.chckbxTelefone.setBounds(259, 34, 76, 20);
		getContentPane().add(this.chckbxTelefone);
	}
	
	private void loadRecords() {
		
		progressBar.setIndeterminate(true);
		txtFiltrobusca.setEditable(false);
		btnBuscar.setEnabled(false);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				boolean
					isFiltrarCodigo = chckbxCodigo.isSelected()
					,isFiltrarNome = chckbxNome.isSelected()
					,isFiltrarEndereco = chckbxEndereco.isSelected()
					,isFiltrarTelefone = chckbxTelefone.isSelected()
					,isFiltrarEmail = chckbxEmail.isSelected()
					,isIncluirCondOR = false;
				
				int codigoCliente = 0;
				if (isFiltrarCodigo) {
					try {
						//Verificar se é um número
						codigoCliente = Integer.parseInt(txtFiltrobusca.getText());
					} catch (Exception e) {
						isFiltrarCodigo = false;
					}
				}
				
				String txtBusca = txtFiltrobusca.getText();
				boolean isFiltroSeleccionado = (isFiltrarCodigo | isFiltrarNome | isFiltrarEndereco | isFiltrarTelefone | isFiltrarEmail) & txtBusca.length() > 0;
				
				String sqlQuery = "SELECT"
						+ " `contato`.`ContatoCodigo`,"
						+ "`contato`.`ContatoNome`,"
						+ "`contato`.`ContatoEndereco`,"
						+ "`contato`.`ContatoNumeroTelefone`,"
						+ "`contato`.`ContatoEmail`"
						+ " FROM `contato`";
				
				if (isFiltroSeleccionado) {
					sqlQuery += " WHERE";
					sqlQuery += (isFiltrarCodigo ? (" `ContatoCodigo` = ?"):"");
					
					isIncluirCondOR = isFiltrarCodigo;
					sqlQuery += (isFiltrarNome ? ((isIncluirCondOR ? " OR ":" ") + "`ContatoNome` LIKE \"%\"?\"%\""):"");
					
					isIncluirCondOR = isFiltrarCodigo | isFiltrarNome;
					sqlQuery += (isFiltrarEndereco ? ((isIncluirCondOR ? " OR ":" ") + "`ContatoEndereco` LIKE \"%\"?\"%\""):"");
					
					isIncluirCondOR = isFiltrarCodigo | isFiltrarNome | isFiltrarEndereco;
					sqlQuery += (isFiltrarTelefone ? ((isIncluirCondOR ? " OR ":" ") + "`ContatoNumeroTelefone` LIKE \"%\"?\"%\""):"");
					
					isIncluirCondOR = isFiltrarCodigo | isFiltrarNome | isFiltrarEndereco | isFiltrarTelefone;
					sqlQuery += (isFiltrarEmail ? ((isIncluirCondOR ? " OR ":" ") + "`ContatoEmail` LIKE \"%\"?\"%\""):"");
					
				}
				
				sqlQuery += ";";
				
				try {
					
					PreparedStatement prep = Conexao.getConnection().prepareStatement(sqlQuery);
					if (isFiltroSeleccionado) {
						int indexPrepString = 1;
						if (isFiltrarCodigo) {
							prep.setInt(indexPrepString, codigoCliente);
							indexPrepString += 1;
						}
						if (isFiltrarNome) {
							prep.setString(indexPrepString, txtBusca);
							indexPrepString += 1;
						}
						if (isFiltrarEndereco) {
							prep.setString(indexPrepString, txtBusca);
							indexPrepString += 1;
						}
						if (isFiltrarTelefone) {
							prep.setString(indexPrepString, txtBusca);
							indexPrepString += 1;
						}
						if (isFiltrarEmail) {
							prep.setString(indexPrepString, txtBusca);
						}
					}
					
					
					ResultSet rs = prep.executeQuery();
					modeloTabela.setNumRows(0);
					
					while (rs.next()) {
						modeloTabela.addRow(new Object[]{
							rs.getInt("ContatoCodigo"),
							rs.getString("ContatoNome"),
							rs.getString("ContatoEndereco"),
							rs.getString("ContatoNumeroTelefone"),
							rs.getString("ContatoEmail"),
						});
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					Msg.Erro(e.getMessage(), "Erro");
				} catch (Exception e) {
					e.printStackTrace();
					Msg.Erro(e.getMessage(), "Erro");
				}
				progressBar.setIndeterminate(false);
				txtFiltrobusca.setEditable(true);
				btnBuscar.setEnabled(true);
			}
		}).start();	
		
		
	}
	
	private void openRecord(){
		int row = tblContatos.getSelectedRow();
		if (row >= 0) {
			int codigoCliente = (Integer)modeloTabela.getValueAt(row, 0);
			new ManutencaoContrato(codigoCliente).setVisible(true);
		} else {
			Msg.Erro("Selecione algum registro.", "Nenhum registro selecionado");
		}
	}
}
