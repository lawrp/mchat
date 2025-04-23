package mchat.dao;

import java.sql.*;

public class MChatAccountMgr {
	SqlServerDbAccessor dao;
	
	public MChatAccountMgr() {
		dao = new SqlServerDbAccessor();
		
		dao.setDbName("CSC312TeamProject");
		dao.connectToDb();
	}

	public static void main(String[] args) throws SQLException {
		MChatAccountMgr mgr = new MChatAccountMgr();
		Statement stmt = mgr.dao.getStmt(); //getConnection().createStatement();
		ResultSet rset = stmt.executeQuery("SELECT * FROM ValidUserIds;");
		ResultSetMetaData meta = rset.getMetaData();
		System.out.println(meta.getColumnCount());
		
		String line = "MB-956 KF-507 DG-301 BH-328 LH-385 II-873 MJ-122 HJ-089 JK-015 TK-376 DK-666 MK-325 RL-224 RM-601 MP-865 HS-701 SS-349 BT-952 AV-741"; 
		String qInsertValidIds = "INSERT INTO ValidUserIds (UserId) VALUES (?)";
		PreparedStatement pStmt = mgr.dao.getConnection().prepareStatement(qInsertValidIds);
		
		String[] ids = line.split(" ");
		for (String id : ids) {
			System.out.println(id);
			pStmt.setString(1, id);
			pStmt.executeUpdate();
		}
	}

}
