package conexao;

import java.sql.Connection; //Conexão com banco de dados
import java.sql.DriverManager; //Driver - Carregar drvifer MySQl, SQL Server, Oracle, etc...
import java.sql.ResultSet; //Dados de retorno da query executada 
import java.sql.SQLException; //Exceções
import java.sql.Statement; //Query

public class Conexao {
	
	public Statement stmt;
	public ResultSet rs;
	private String driver = "com.mysq.jdbc.Driver";
	
	///*
	private String user = "b231614c0153ae";
	private String password = "e78733b8";
	private String host = "br-cdbr-azure-south-a.cloudapp.net";
	private String port = "3306";
	private String bd = "poo_contrato";
	//*/
	
	/*
	private String user = "root";
	private String password = "root";
	private String host = "localhost";
	private String port = "3306";
	private String bd = "bd_poo_contratos";
	*/
	
	private String connectionString = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.bd;
	
	public Connection connection;
	
	public void conect(){
		try {
			System.setProperty("jdbc.Drivers", this.driver);
			connection = DriverManager.getConnection(connectionString, this.user, this.password);
			System.out.println("Conexão efetuada com sucesso!");
		} catch (Exception e) {
			System.out.println("Não foi possível conectar. \nErro: " + e.getMessage());
		}
	}
	
	public void disconnect(){
		try {
			connection.close();
			System.out.println("Conexão encerrada com sucesso.");
		} catch (SQLException e) {
			System.out.println("Não foi possível desconectar. \nErro: " + e.getMessage());
		}
	}
}