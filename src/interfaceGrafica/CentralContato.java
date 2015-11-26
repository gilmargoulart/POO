package interfaceGrafica;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import utils.ConstantesSistema;
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
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import entidades.Contato;

import java.awt.Component;

import javax.swing.ImageIcon;

import java.awt.Toolkit;

public class CentralContato extends JFrame {

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
	private JPopupMenu popupMenu;
	private JMenuItem mntmExluir;
	private JMenuItem mntmAlterar;
	private JButton btnExportarCSV;
	/**
	 * Create the frame.
	 */
	public CentralContato() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CentralContato.class.getResource("/icones/contacts.png")));
		setResizable(false);
		setTitle("Central de contatos");
		initialize();
		loadRecords();
	}
	
	@SuppressWarnings("serial")
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		int resX = (ConstantesSistema.RESOLUCAO.width / 2) - (643 / 2);
		int resY = (ConstantesSistema.RESOLUCAO.height / 2) - (678 / 2);
				
		setBounds(resX, resY, 643, 678);
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
		scrollPane.setBounds(10, 75, 607, 544);
		getContentPane().add(scrollPane);
		
		tblContatos = new JTable();
		this.tblContatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2 & me.getButton() == MouseEvent.BUTTON1) {
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
		
		this.popupMenu = new JPopupMenu();
		addPopup(this.tblContatos, this.popupMenu);
		
		this.mntmAlterar = new JMenuItem("Alterar");
		this.mntmAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openRecord();
			}
		});
		this.mntmAlterar.setActionCommand("Alterar");
		this.popupMenu.add(this.mntmAlterar);
		
		this.mntmExluir = new JMenuItem("Exluir");
		this.mntmExluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteRecord();
			}
		});
		this.popupMenu.add(this.mntmExluir);
		
		txtFiltrobusca = new JTextField();
		this.txtFiltrobusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadRecords();
			}
		});
		this.txtFiltrobusca.setToolTipText("Buscar por c\u00F3digo, nome, endere\u00E7o, email ou telefone.");
		txtFiltrobusca.setBounds(10, 12, 457, 20);
		getContentPane().add(txtFiltrobusca);
		txtFiltrobusca.setColumns(10);
		
		btnBuscar = new JButton();
		this.btnBuscar.setIcon(new ImageIcon(CentralContato.class.getResource("/icones/refresh.png")));
		this.btnBuscar.setToolTipText("Atualizar dados da consulta");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadRecords();
			}
		});
		btnBuscar.setBounds(477, 12, 48, 48);
		getContentPane().add(btnBuscar);
		
		btnInserir = new JButton();
		this.btnInserir.setIcon(new ImageIcon(CentralContato.class.getResource("/icones/add_contact.png")));
		this.btnInserir.setToolTipText("Inserir");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManutencaoContato cc = new ManutencaoContato(0);
				cc.setVisible(true);
			}
		});
		btnInserir.setBounds(528, 12, 48, 48);
		getContentPane().add(btnInserir);
		
		this.btnExportarCSV = new JButton();
		this.btnExportarCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exportContatosToCSV();
			}
		});
		this.btnExportarCSV.setIcon(new ImageIcon(CentralContato.class.getResource("/icones/export_csv.png")));
		this.btnExportarCSV.setToolTipText("Exportar contatos para arquivo CSV");
		this.btnExportarCSV.setBounds(579, 12, 48, 48);
		getContentPane().add(this.btnExportarCSV);
		
		this.progressBar = new JProgressBar();
		this.progressBar.setBounds(10, 624, 607, 14);
		getContentPane().add(this.progressBar);
		
		this.chckbxCodigo = new JCheckBox("C\u00F3digo");
		this.chckbxCodigo.setToolTipText("Indicar se filtra o c\u00F3digo do contato durante a busca.");
		this.chckbxCodigo.setSelected(true);
		this.chckbxCodigo.setBounds(10, 34, 65, 20);
		getContentPane().add(this.chckbxCodigo);
		
		this.chckbxNome = new JCheckBox("Nome");
		this.chckbxNome.setToolTipText("Indicar se filtra o nome do contato durante a busca.");
		this.chckbxNome.setSelected(true);
		this.chckbxNome.setBounds(76, 34, 53, 20);
		getContentPane().add(this.chckbxNome);
		
		this.chckbxEndereco = new JCheckBox("Endere\u00E7o");
		this.chckbxEndereco.setToolTipText("Indicar se filtra o endere\u00E7o do contato durante a busca.");
		this.chckbxEndereco.setSelected(true);
		this.chckbxEndereco.setBounds(131, 34, 71, 20);
		getContentPane().add(this.chckbxEndereco);
		
		this.chckbxEmail = new JCheckBox("Email");
		this.chckbxEmail.setToolTipText("Indicar se filtra o email do contato durante a busca.");
		this.chckbxEmail.setSelected(true);
		this.chckbxEmail.setBounds(204, 34, 53, 20);
		getContentPane().add(this.chckbxEmail);
		
		this.chckbxTelefone = new JCheckBox("Telefone");
		this.chckbxTelefone.setToolTipText("Indicar se filtra o telefone do contato durante a busca.");
		this.chckbxTelefone.setSelected(true);
		this.chckbxTelefone.setBounds(259, 34, 76, 20);
		getContentPane().add(this.chckbxTelefone);
	}
	
	private void deleteRecord() {
		int row = tblContatos.getSelectedRow();
		if (row >= 0) {
			int codigoContato = (Integer)modeloTabela.getValueAt(row, 0);
			
			if(new Contato(codigoContato, null, null, null, null).Deletar()){
				modeloTabela.removeRow(row);
			}
		} else {
			Msg.Erro("Selecione algum registro.", "Nenhum registro selecionado");
		}
	}

	private void loadRecords() {
		
		progressBar.setIndeterminate(true);
		txtFiltrobusca.setEditable(false);
		btnBuscar.setEnabled(false);
		tblContatos.setEnabled(false);
		
		Msg.MsgStatusBar("Carregando contatos...", false);
		
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
						codigoCliente = 0;
						//isFiltrarCodigo = false;
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
							rs.getString("ContatoEmail"),
							rs.getString("ContatoNumeroTelefone"),
						});
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					Msg.MsgStatusBar("Carregando contatos... Erro!");
					Msg.Erro(e.getMessage() + "\n" + e.getStackTrace(), "Erro");
				} catch (Exception e) {
					e.printStackTrace();
					Msg.MsgStatusBar("Carregando contatos... Erro!");
					Msg.Erro(e.getMessage() + "\n" + e.getStackTrace(), "Erro");
				}
				progressBar.setIndeterminate(false);
				txtFiltrobusca.setEditable(true);
				btnBuscar.setEnabled(true);
				tblContatos.setEnabled(true);
				Msg.MsgStatusBar("Carregando contatos... OK");
			}
		}).start();	
		
		
	}
	
	private void openRecord(){
		int row = tblContatos.getSelectedRow();
		if (row >= 0) {
			int codigoContato = (Integer)modeloTabela.getValueAt(row, 0);
			new ManutencaoContato(codigoContato).setVisible(true);
		} else {
			Msg.Erro("Selecione algum registro.", "Nenhum registro selecionado");
		}
	}
	
	private void exportContatosToCSV() {
		
		Msg.Aviso("Implementação pendente... :(", "Recurso indispoível :(");
		
		if (modeloTabela.getRowCount() <= 0) {
			Msg.Aviso("Nenhum contato para exportar. :(", "Sem contatos");
		} else {
			/*
			String filePath = "";
			JFileChooser escolheArq = new JFileChooser();
					
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo csv", "csv");
		    escolheArq.setFileFilter(filter);
		    
		    int returnVal = escolheArq.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	filePath = escolheArq.getSelectedFile().getAbsolutePath();
		    	System.out.println("Caminho arquivo selecionado: " + filePath);
		    	
		    	Msg.MsgStatusBar("Exportando arquivo para \"" + filePath + "\"...");
		    	try {
					
		    		FileWriter arquivo = new FileWriter(filePath);
					PrintWriter pWriter = new PrintWriter(filePath); //Gravar no arquivo
					
					pWriter.printf(Contato.getCsvLineTitle());
					Contato c;
					
					Vector <Object> dados = modeloTabela.getDataVector();
					//Vector v1 = tblContatos.getColumnModel().getColumns();
					//tblContatos.
					
					for(Object v : dados) {
						//c = new Contato(codigo, nome, endereco, numeroTelefone, email);
						//pWriter.printf(c.getCsvLine());
						System.out.println(v[0]);
						System.out.println(v[1]);
						System.out.println(v[2]);
						System.out.println(v[3]);
						System.out.println(v[4]);
					}
					pWriter.close();
					arquivo.close();
		    			
	    			Msg.MsgStatusBar("Exportando arquivo para \"" + filePath + "\"... OK", false);
				} catch (IOException e) {
					e.printStackTrace();
					Msg.MsgStatusBar("Exportando arquivo para \"" + filePath + "\"... ERRO", false);
					Msg.Erro(e.getMessage(), "Erro exportando arquivo");
				}
				filePath = null;
				
		    }
			 */
		}
		
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
