package conexao;

import java.sql.Connection; //Conexão com banco de dados
import java.sql.DriverManager; //Driver - Carregar drvifer MySQl, SQL Server, Oracle, etc...
import java.sql.SQLException; //Exceções

import utils.Msg;

public class Conexao {
	
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
	
	private static Connection connection;
	
	public void conect(){
		try {
			Msg.MsgStatusBar("Estabelecendo conexão com o servidor...", false);
			System.setProperty("jdbc.Drivers", this.driver);
			connection = DriverManager.getConnection(connectionString, this.user, this.password);
			System.out.println("Conexão efetuada com sucesso!");
			Msg.MsgStatusBar("Estabelecendo conexão com o servidor... OK");
		} catch (Exception e) {
			System.out.println("Não foi possível conectar. \nErro: " + e.getMessage());
		}
	}
	
	public void disconnect(){
		try {
			if (connection != null) {
				Msg.MsgStatusBar("Encerrando conexão com o servidor...", false);
				connection.close();
				Msg.MsgStatusBar("Encerrando conexão com o servidor... OK", true);
				System.out.println("Conexão encerrada com sucesso.");
			}
		} catch (SQLException e) {
			Msg.MsgStatusBar("Encerrando conexão com o servidor... Erro!", true);
			System.out.println("Não foi possível desconectar. \nErro: " + e.getMessage());
		}
	}
	
	public static Connection getConnection() throws SQLException{
		if (connection == null) {
			new Conexao().conect();
		} else {
			if (connection.isClosed()) {
				new Conexao().conect();
			}
		}
		return connection;
	}
}