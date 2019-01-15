package fdi.ucm.server.importparser.oda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Claser que define la conexion para un Oda1 en mySQL
 * @author Joaquin Gayoso-Cabada
 *
 */
public class MySQLConnectionOda {
	
	
	public enum DB {DBaseServer,DBaseLocal};
	
	private  MySQLConnectionOda instance;
	private Connection conexion;
	private String DBaseServerUnknow;
	
	private String DBSelected;
	
	private static final String DriverDatabase="com.mysql.jdbc.Driver";
	private static final String ErrorMySQLConnection="Error en driver de conexion al mySQL";
	private static final String ErrorCOnexionDB="Error en conexion a base de datos";
	private static final String ErrorUpdate="Error ejecutando Update Querry: ";
	private static final String ErrorSelect="Error ejecutando Querry: ";
	


	public MySQLConnectionOda(String dbNameIP,String database,int Port, String user, String password) {
		try {
			Class.forName(DriverDatabase);
			InicializacionAnonima(dbNameIP,database,Port,user,password); 
			instance=this;
		} catch (ClassNotFoundException e) {
			System.err.println(ErrorMySQLConnection);
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println(ErrorCOnexionDB);
			e.printStackTrace();

		}
	}

	private void InicializacionAnonima(String dbNameIP,String database, int port, String user, String password) throws SQLException {
		DBaseServerUnknow="jdbc:mysql://"+dbNameIP+":"+port+"/"+database;
		conexion = DriverManager.getConnection(DBaseServerUnknow, user, password);	
		if (conexion==null) throw new SQLException();
		DBSelected=DBaseServerUnknow;
		
	}
	

	
	public void RunQuerryUPDATE(String querry)
	{		
		try {
			Statement st = instance.conexion.createStatement();
			st.executeUpdate(querry);
		} catch (SQLException e) {
			System.err.println(ErrorUpdate + querry);
			e.printStackTrace();
		}
	}
	
	public ResultSet RunQuerrySELECT(String querry)
	{		
		try {
			Statement st = instance.conexion.createStatement();
			ResultSet rs = st.executeQuery(querry);
			return rs;
		} catch (SQLException e) {
			System.err.println(ErrorSelect + querry);
			e.printStackTrace();
			return null;
		}
	}
	 
public String getDBSelected() {
	return DBSelected;
}


}
