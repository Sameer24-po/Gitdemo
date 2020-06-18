package databaseOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class LOBDataBaseOperation {
	String result;
	Statement st;
	int executedquerycount;
	ResultSet resultset;
	Connection con;
	String query;
	public Connection connectDataBase()
	{
		result="connectionfailed";
		  con=null;
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			  con=DriverManager.getConnection("jdbc:mysql://localhost:3308/seleniumdb","root","admin");  
			 result="connected";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("exception caught : "+e);
		}  	
		
		return con;
		
	}

	public int addLOB(String lobname,String lobdescription)
	{
		System.out.println("in method addLOB** ");
		con=connectDataBase();
	//	LOBDataBaseOperation processobj=new LOBDataBaseOperation();
		// con=processobj.connectDataBase();
		 System.out.println("connection object is = "+con);
		 if(con!=null)
			{
		//System.out.println("calling method addLOB");
		try {
			 query="insert into lob(LOB_Name,LOB_Description) values('"+ lobname +"', '"+lobdescription+"')";
			 System.out.println("query is "+ query);
			st=con.createStatement();	
			executedquerycount=st.executeUpdate(query);
			System.out.println("result set is :="+executedquerycount);
			
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
		 else
			{
				System.out.println("Database is not connected ");
			}
		return executedquerycount;
	
		
		//return result;
	}
	public int getNewLOBId(String lobname)
	{
		con=connectDataBase();
		int logid=0;
		if(con!=null)
		{
			try {
				query="select LOB_Id from lob where LOB_Name='"+lobname+"'";
				System.out.println("query is "+ query);
				st=con.createStatement();	
				
				resultset=st.executeQuery(query);
				while(resultset.next())
				{
					logid=resultset.getInt(1);
					
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return logid;
	}
	public int deleteLOB(String lobid,String lobname)
	{
		//LOBDataBaseOperation processobj=new LOBDataBaseOperation();
		// con=processobj.connectDataBase();
		int lob_id=Integer.parseInt(lobid);
		System.out.println("in delete lob method");
		con=connectDataBase();
		 System.out.println("connection object is = "+con);
		 if(con!=null)
			{
		//System.out.println("calling method addLOB");
		try {
			 query="delete from  lob where LOB_Id='"+ lob_id+"' and LOB_Name='"+lobname +"'";
			 System.out.println("delete query is == "+query);
			st=con.createStatement();	
			executedquerycount=st.executeUpdate(query);
			System.out.println("result set is :="+executedquerycount);
			
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
		 else
			{
				System.out.println("Database is not connected ");
			}
		return executedquerycount;
		
		
	}
	public int updtaeLOBTable(String lobid,String lob_name,String lob_description)
	{
		/*Set editlob_set=editlobdata.keySet();
		Iterator editlob_itr=editlob_set.iterator();
		LinkedList valuelist=new LinkedList();
		String values="";
		while(editlob_itr.hasNext())
		{
			String key=editlob_itr.next().toString();
			values=editlobdata.get(key).toString();
			valuelist.add(values);
			
		}
		System.out.println("values list are = "+valuelist);*/
		/*String lobid=valuelist.get(1).toString();
		String lobname=valuelist.get(2).toString();
		String lobdisc=valuelist.get(3).toString();*/
		int lobid_int=Integer.parseInt(lobid);
		System.out.println("in update database lob method");
		con=connectDataBase();
		System.out.println("connection object is = "+con);
		 if(con!=null)
			{
			 try {
				 query="update LOB set LOB_Name='"+lob_name+"',LOB_Description='"+lob_description +"'WHERE LOB_Id='"+lobid_int+"'";
				 System.out.println("update query is = "+query);
				st=con.createStatement();	
				executedquerycount=st.executeUpdate(query);
				System.out.println("result set is :="+executedquerycount);
				
						
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 else
			{
				System.out.println("Database is not connected ");
			}
		return executedquerycount;
	}
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String result;
		// Connection con;
		String lobname="Helloedit";
		String lobdisc="new disc";
		int lobid=8;
		LOBDataBaseOperation processobj=new LOBDataBaseOperation();
	//	processobj.addLOB();
		int lodid=processobj.getNewLOBId(lobname);
		System.out.println("add result is : "+lodid);

		
	}*/

}
