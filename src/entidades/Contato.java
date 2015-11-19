package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
		Conexao c = new Conexao();
		String sqlStmt = "SELECT "
				+ "`ContatoCodigo`, `ContatoNome`, `ContatoEndereco`, `ContatoNumeroTelefone`, `ContatoEmail` "
				+ "FROM `contato` "
				+ "WHERE `ContatoCodigo` = ?;";
		
		try {
			PreparedStatement p;
			c.conect();
			p = c.connection.prepareStatement(sqlStmt);
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
		} finally {
			c.disconnect();
		}
	}
	
	public void Inserir() {
		Conexao c = new Conexao();
		String sqlStmt = "INSERT INTO `contato` "
				+ "(`ContatoNome`, `ContatoEndereco`, `ContatoNumeroTelefone`, `ContatoEmail`) "
				+ "VALUES (?, ?, ?, ?);";
		
		try {
			PreparedStatement p;
			c.conect();
			p = c.connection.prepareStatement(sqlStmt);
			p.setString(1, getNome());
			p.setString(2, getEndereco());
			p.setString(3, getNumeroTelefone());
			p.setString(4, getEmail());
			
			int rowsAffected = p.executeUpdate();
			
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Contato cadastrado com sucesso.", "Contato cadastrado", JOptionPane.INFORMATION_MESSAGE);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.disconnect();
		}
	}
	
	public static ArrayList<Contato> consultarContatoPorNome(String nome) {
		ArrayList<Contato> contatos = new ArrayList<Contato>();
		Conexao c = new Conexao();
		String sqlStmt = "SELECT "
				+ "`ContatoCodigo`, `ContatoNome`, `ContatoEndereco`, `ContatoNumeroTelefone`, `ContatoEmail`"
				+ "FROM `contato` ";
		if (!sqlStmt.isEmpty()){
			sqlStmt += "WHERE `ContatoNome` LIKE \"%\"?\"%\";";
		}
		
		try {
			PreparedStatement p;
			c.conect();
			p = c.connection.prepareStatement(sqlStmt);
			if (!sqlStmt.isEmpty()){
				p.setString(1, nome);
			}
			
			ResultSet rs = p.executeQuery();
			
			while (rs.next()) {
				contatos.add(new Contato(rs.getInt("ContatoCodigo"), rs.getString("ContatoNome"), rs.getString("ContatoEndereco"), rs.getString("ContatoNumeroTelefone"), rs.getString("ContatoEmail")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.disconnect();
		}
		return contatos;
	}
	
	public Contato(int codigo, String nome, String endereco, String numeroTelefone, String email) {
		setCodigo(codigo);
		setNome(nome);
		setEndereco(endereco);
		setNumeroTelefone(numeroTelefone);
		setEmail(email);
	}
	
	public void Alterar(){
		Conexao c = new Conexao();
		String sqlStmt = "UPDATE `contato` SET "
				+ "`ContatoNome` = ?,"
				+ " `ContatoEndereco` = ?,"
				+ " `ContatoNumeroTelefone` = ?,"
				+ " `ContatoEmail` = ?"
				+ " WHERE `ContatoCodigo` = ?;";
		
		try {
			PreparedStatement p;
			c.conect();
			p = c.connection.prepareStatement(sqlStmt);
			p.setString(1, getNome());
			p.setString(2, getEndereco());
			p.setString(3, getNumeroTelefone());
			p.setString(4, getEmail());
			p.setInt(5, getCodigo());
			
			int rowsAffected = p.executeUpdate();
			
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Contato alterado com sucesso.", "Contato alterado", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Contato não foi alterado.", "Contato não alterado", JOptionPane.WARNING_MESSAGE);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.disconnect();
		}
	}

	public void Deletar(){
		
		if (getCodigo() > 0) {
			int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente deletar o contato selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (confirmacao == JOptionPane.YES_OPTION) {
				Conexao c = new Conexao();
				String sqlStmt = "DELETE FROM `contato` WHERE `ContatoCodigo` = ?;";
				
				try {
					PreparedStatement p;
					c.conect();
					p = c.connection.prepareStatement(sqlStmt);
					p.setInt(1, getCodigo());
					
					int rowsAffected = p.executeUpdate();
					
					if (rowsAffected > 0) {
						JOptionPane.showMessageDialog(null, "Contato deletado com sucesso.", "Contato deletado", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Contato não foi deletado.", "Contato não deletado", JOptionPane.WARNING_MESSAGE);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					c.disconnect();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Contato não deletado.", "Contato não deletado.", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Contato não deletado, pois não foi encontrado", "Contato não deletado.", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void exibirDados() {
		System.out.println(getCodigo() + " - " + getNome());
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