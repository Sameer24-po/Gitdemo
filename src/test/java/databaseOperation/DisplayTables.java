package databaseOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DisplayTables {
Connection con=null;
Statement st;
ResultSet resultset=null;
String query;
int totalcolumn=0;
ArrayList columnlist;
	public Connection connectDatabase()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3308/seleniumdb","root","admin");  
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("exception caught : "+e);
		}
		return con;
	}
	public ResultSet displayLOBTable() throws SQLException
	{
		con=connectDatabase();
		if(con!=null)
		{
			query="select * from LOB";
			System.out.println("query is = "+query);
			st=con.createStatement();
			resultset=st.executeQuery(query);
			System.out.println("resultset is = "+resultset);
			
		}
		else
		{
			System.out.println("connection database failed ");
		}
		return resultset;
		
	}
	public ArrayList getTotalLOBColumn(String dbname,String tablename)
	{
		columnlist=new ArrayList();
		System.out.println("in get total column count");
		con=connectDatabase();
		if(con!=null)
		{
			
			try {
				query="SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+dbname+"' AND TABLE_NAME = '"+tablename+"';";
				System.out.println("query is = "+query);
				st=con.createStatement();
				resultset=st.executeQuery(query);
				while(resultset.next())
				{
					System.out.println("1.= "+resultset.getString(1));
					columnlist.add(resultset.getString(1));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("connection database failed ");
		}
		return columnlist;
	}
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			ArrayList list=new ArrayList();
			String dbname="seleniumdb";
			String tablename="lob";
			DisplayTables display=new DisplayTables();
			ResultSet rs=display.getTotalLOBColumn(dbname,tablename);
			System.out.println("rs is = "+rs);
			while(rs.next())
			{
				System.out.println("1.= "+rs.getString(1));
				list.add(rs.getString(1));
				
			}
			System.out.println("length of array list is ="+list.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}

