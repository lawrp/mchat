package src.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SqlServerDbAccessor {

	private Connection con;
	private Statement stmt;
	private PreparedStatement prepStmt;

	private String connectionUrl;

	private String defaultConnUrl = "jdbc:sqlserver://;" +
			"servername=csdata.cd4sevot432y.us-east-1.rds.amazonaws.com;"
			+ "user=csc312cloud;password=c3s!c2Cld;";
	// + "databaseName=CSC312TeamProject;";
	/*
	 * // in WSC
	 * private String defaultConnUrl =
	 * "jdbc:sqlserver://;servername=cssql\\sqldata;"
	 * + "user=csc480dev;password=c4s*C0sWe;" +
	 * "databaseName=JLBookstore;";
	 */

	public SqlServerDbAccessor() {
		connectionUrl = defaultConnUrl;
	}

	public SqlServerDbAccessor(String serverName, String user, String pwd,
			String dbName) {
		connectionUrl = "jdbc:sqlserver://;";
		connectionUrl += "servername=" + serverName + ";";
		connectionUrl += "user=" + user + ";";
		connectionUrl += "password=" + pwd + ";";
		connectionUrl += "databaseName=" + dbName + ";";
	}

	public void setDbName(String dbName) {
		connectionUrl += "databaseName=" + dbName;
	}

	public void connectToDb() {
		try {
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Statement getStmt() {
		// TODO Auto-generated method stub
		return stmt;
	}

	public PreparedStatement getPrepStmt() {
		// TODO Auto-generated method stub
		return prepStmt;
	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		return con;
	}

	public String getUrl() {
		// TODO Auto-generated method stub
		return connectionUrl;
	}

}
