package wipro.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBUtil {

	String result;
	Statement st;
	int executedquerycount;
	ResultSet resultset;
	public static Connection con = null;
	String query;

    public static Connection getDBConnection() {
        Connection con = null;
        try {
                        Class.forName("com.mysql.jdbc.Driver");
   
                        /*--------For Live-----------*/
//                        con = DriverManager.getConnection("jdbc:mysql://10.1.231.110:5500/rpauidb", "rpadb_user", "Rpadb_user@123");
                        
                        /*-----For UAT--------*/
                        con = DriverManager.getConnection("jdbc:mysql://10.1.62.200:3306/rpauidb", "root", "password");
                        
   
        } catch (ClassNotFoundException e) {
                        e.printStackTrace();
        } catch (SQLException e) {
                        e.printStackTrace();
        }
        return con;
    }

		
	/**
     * close DB connection 
     * @param con DB connection 
     */ 
   public static void closeConn() { 
        try { 
            if (con != null) { 
                con.close(); 
            } 
            con = null; 
        } catch (Exception e) { 
        	System.out.println("exception caught : " + e);
        } 
    }

	
   public ResultSet getResultSetQueryData(String query) {
		try {
			resultset = null;
			Connection conn = DBUtil.getDBConnection();
			Statement st1 = conn.createStatement();
			resultset = st1.executeQuery(query);
			conn.close();
			st1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return resultset;
	}

	/**
	 * Method to execute add/update/delete query
	 * 
	 * @param query
	 * @return
	 */
	public int executeQuery(String query) {
		try {
			//System.out.println("query is " + query);
			
			Connection conn = DBUtil.getDBConnection();
			Statement st1 = conn.createStatement();
			int i = st1.executeUpdate(query);
			st1.close();
			conn.close();
			return i;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int getID(String name, String tablename) {
		int i = 0;
		try {
			
			resultset = null;
			
			Connection conn = DBUtil.getDBConnection();
		    String query1 = "select id from " + tablename + " where name='" + name + "'";
		    Statement st1 = conn.createStatement();
			resultset = st1.executeQuery(query1);
			if (resultset.next()) {
				 i = resultset.getInt(1);
			}
			conn.close();
			st1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return i;
	}
	
	public int getAutoId(String tablename) {
		int i = 0;
		try {
			
			resultset = null;
		    String query1 = "select max(id) from "+tablename;
		    Connection conn = DBUtil.getDBConnection();
		    Statement st1 = conn.createStatement();

			resultset = st1.executeQuery(query1);
			if (resultset.next()) {
				i = resultset.getInt(1);
			}
			conn.close();
			st1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return i;
	}
	
	public int isNamePresent(String name, String tablename) {
		int i = 0;
		try {
			resultset = null;
			String query1 = "select name from " + tablename + " where name='" + name + "'";
			Connection conn = DBUtil.getDBConnection();
			Statement st1 = conn.createStatement();

			resultset = st1.executeQuery(query1);
			if (resultset.next()) {
				i = 1;

			}
			conn.close();
			st1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return i;
	}
	
	public ArrayList<String> getLatestExecDetails(String userid,String jobname) {
		ArrayList<String> alResult = new ArrayList<String>();
		
		try {
			resultset = null;
			String query1="Select id,status,createdon,completedon,time from executionhistory where id=(select max(id) from executionhistory where userid='"+userid+"' and name='"+jobname+"')";
			//System.out.println("query is: "+ query);
			Connection conn = DBUtil.getDBConnection();
			Statement st1 = conn.createStatement();

			resultset = st1.executeQuery(query1);
			if (resultset.next()) {
				alResult.add(0,resultset.getObject(1)!=null?resultset.getObject(1).toString():null);
				alResult.add(1,resultset.getObject(2)!=null?resultset.getObject(2).toString():null);
				alResult.add(2,resultset.getObject(3)!=null?resultset.getObject(3).toString():null);
				alResult.add(3,resultset.getObject(4)!=null?resultset.getObject(4).toString():null);
				alResult.add(4,resultset.getObject(5)!=null?resultset.getObject(5).toString():null);
			}
			conn.close();
			st1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return alResult;
	}
}
