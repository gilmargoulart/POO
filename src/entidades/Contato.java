package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import utils.ConstantesSistema;
import utils.Msg;
import conexao.Conexao;


public class Contato {
	
	private int codigo;
	private String nome;
	private String endereco;
	private String numeroTelefone;
	private String email;
	
	public Contato() {
		
	}
	
	public Contato(int codigo){
		String sqlStmt = "SELECT "
				+ "`ContatoCodigo`, `ContatoNome`, `ContatoEndereco`, `ContatoNumeroTelefone`, `ContatoEmail` "
				+ "FROM `contato` "
				+ "WHERE `ContatoCodigo` = ?;";
		
		try {
			PreparedStatement p = Conexao.getConnection().prepareStatement(sqlStmt);
			p.setInt(1, codigo);
			
			ResultSet rs = p.executeQuery();
			
			if (rs.next()) {
				setCodigo(rs.getInt("ContatoCodigo"));
				setNome(rs.getString("ContatoNome"));
				setEndereco(rs.getString("ContatoEndereco"));
				setNumeroTelefone(rs.getString("ContatoNumeroTelefone"));
				setEmail(rs.getString("ContatoEmail"));
			} else {
				setCodigo(0);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean Inserir() {
		boolean isInserted = false;
		String sqlStmt = "INSERT INTO `contato` "
				+ "(`ContatoNome`, `ContatoEndereco`, `ContatoNumeroTelefone`, `ContatoEmail`) "
				+ "VALUES (?, ?, ?, ?);";
		
		try {
			PreparedStatement p = Conexao.getConnection().prepareStatement(sqlStmt);
			p.setString(1, getNome());
			p.setString(2, getEndereco());
			p.setString(3, getNumeroTelefone());
			p.setString(4, getEmail());
			
			int rowsAffected = p.executeUpdate();
			
			if (rowsAffected > 0) {
				isInserted = true;
				Msg.Informacao("Contato cadastrado com sucesso", "Contato cadastrado");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isInserted;
	}
	
	public Contato(int codigo, String nome, String endereco, String numeroTelefone, String email) {
		setCodigo(codigo);
		setNome(nome);
		setEndereco(endereco);
		setNumeroTelefone(numeroTelefone);
		setEmail(email);
	}
	
	public boolean Alterar(){
		boolean isUpdated = false;
		
		String sqlStmt = "UPDATE `contato` SET "
				+ "`ContatoNome` = ?,"
				+ " `ContatoEndereco` = ?,"
				+ " `ContatoNumeroTelefone` = ?,"
				+ " `ContatoEmail` = ?"
				+ " WHERE `ContatoCodigo` = ?;";
		
		try {
			PreparedStatement p = Conexao.getConnection().prepareStatement(sqlStmt);
			p.setString(1, getNome());
			p.setString(2, getEndereco());
			p.setString(3, getNumeroTelefone());
			p.setString(4, getEmail());
			p.setInt(5, getCodigo());
			
			int rowsAffected = p.executeUpdate();
			
			if (rowsAffected > 0) {
				isUpdated = true;
				Msg.Informacao("Contato alterado com sucesso.", "Contato alterado");
			} else {
				Msg.Aviso("Contato não foi alterado.", "Contato não alterado");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isUpdated;
	}

	public boolean Deletar(){
		
		boolean isDeleted = false;
		
		if (getCodigo() > 0) {
			Msg.MsgStatusBar("Deletando contato...", false);
			int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente deletar o contato selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirmacao == JOptionPane.YES_OPTION) {
				String sqlStmt = "DELETE FROM `contato` WHERE `ContatoCodigo` = ?;";
				
				try {
					PreparedStatement p = Conexao.getConnection().prepareStatement(sqlStmt);
					p.setInt(1, getCodigo());
					
					int rowsAffected = p.executeUpdate();
					
					if (rowsAffected > 0) {
						isDeleted = true;
						Msg.MsgStatusBar("Deletando contato... OK");
						Msg.Informacao("Contato deletado com sucesso.", "Contato deletado");
					} else {
						Msg.MsgStatusBar("Deletando contato... Contato não deletado!");
						Msg.Aviso("Contato não foi deletado.", "Contato não deletado");
					}
					
				} catch (SQLException e) {
					Msg.MsgStatusBar("Deletando contato... Erro!");
					e.printStackTrace();
				} catch (Exception e) {
					Msg.MsgStatusBar("Deletando contato... Erro!");
					e.printStackTrace();
				}
			} else {
				Msg.MsgStatusBar("Deletando contato... Contato não deletado! Ação cancelada pelo usuário.");
				Msg.Informacao("Contato não deletado.", "Contato não deletado");
			}
		} else {
			Msg.Aviso("Contato não deletado, pois não foi encontrado", "Contato não deletado");
		}
		
		return isDeleted;
	}
	
	public void exibirDados() {
		System.out.println(getCodigo() + " - " + getNome());
	}
	
	public static String getCsvLineTitle(){
		String csvLineTitle = "Código;Nome;Endereço;Telefone;E-Mail\n";
		return csvLineTitle;
	}
	
	public String getCsvLine(){
		String csvLine = "";
		String csvSeparator = ConstantesSistema.CSV_SEPARATOR;
		
		csvLine = getCodigo() + csvSeparator + getNome() + csvSeparator + getEndereco() + csvSeparator + getNumeroTelefone() + csvSeparator + getEmail() + "\n";
		
		return csvLine;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return this.endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getNumeroTelefone() {
		return this.numeroTelefone;
	}
	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}