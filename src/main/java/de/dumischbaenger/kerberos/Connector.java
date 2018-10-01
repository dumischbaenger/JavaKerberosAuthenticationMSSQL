package de.dumischbaenger.kerberos;

import java.lang.invoke.MethodHandles;
import java.security.Security;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.sqlserver.jdbc.SQLServerDatabaseMetaData;
import com.microsoft.sqlserver.jdbc.SQLServerMetaData;

public class Connector {
  static final Logger LOGGER=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	static public void init() {
	        

	}
	
	static public void connectJavaKerberos() throws Exception {
	  
	  Properties s=System.getProperties();


    String connectionUrl=
       "jdbc:sqlserver://"+s.getProperty("dbhost")
      +":"+s.getProperty("dbport")+";"
      + "databaseName="+s.getProperty("dbname")+";"
      + "authenticationScheme=JavaKerberos;integratedSecurity=true;"
      + "serverSpn="+s.getProperty("serverSpn")+";"
      + "jaasConfigurationName="+s.getProperty("jaasConfigurationNameJavaKerberos")+";"
      + "user="+s.getProperty("username")+";"
      + "password="+s.getProperty("password")+";"; 

    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    Connection con;
    
    
    con = DriverManager.getConnection(connectionUrl);
    

    LOGGER.info(con.getMetaData().getDriverName());
	    
	  Statement stmt = null;
	  ResultSet rs = null;

	  // Create and execute an SQL statement that returns some data.
	  String [] sqlList = new String[] {
		  "select user,db_name()",
		  "select user,auth_scheme from sys.dm_exec_connections where session_id=@@spid"
	  };
	  
	  for(Integer i=0; i<sqlList.length;i++){
		String sql=sqlList[i];

		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);

		LOGGER.info("Statement: "+sql);
		LOGGER.info("Result: ");
		// Iterate through the data in the result set and display it.
		while (rs.next()) {
		  LOGGER.info("Row: " + rs.getString(1) + ", " + rs.getString(2));
		}   
	  }


	    
	  con.close();
	}

  static public void connectOsKerberos() throws Exception {
    
    Properties s=System.getProperties();
  
  
    String connectionUrl=
       "jdbc:sqlserver://"+s.getProperty("dbhost")
      +":"+s.getProperty("dbport")+";"
      + "databaseName="+s.getProperty("dbname")+";"
      + "authenticationScheme=JavaKerberos;integratedSecurity=true;"
      + "serverSpn="+s.getProperty("serverSpn")+";"
      + "jaasConfigurationName="+s.getProperty("jaasConfigurationNameOsKerberos")+";"
      + "user=;"
      + "password=;"; 
  
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
  
    Connection con;
    
    
    con = DriverManager.getConnection(connectionUrl);
    
  
    LOGGER.info(con.getMetaData().getDriverName());
      
    Statement stmt = null;
    ResultSet rs = null;
  
    // Create and execute an SQL statement that returns some data.
    String [] sqlList = new String[] {
  	  "select user,db_name()",
  	  "select user,auth_scheme from sys.dm_exec_connections where session_id=@@spid"
    };
    
    for(Integer i=0; i<sqlList.length;i++){
  	String sql=sqlList[i];
  
  	stmt = con.createStatement();
  	rs = stmt.executeQuery(sql);
  
  	LOGGER.info("Statement: "+sql);
  	LOGGER.info("Result: ");
  	// Iterate through the data in the result set and display it.
  	while (rs.next()) {
  	  LOGGER.info("Row: " + rs.getString(1) + ", " + rs.getString(2));
  	}   
    }
  
  
      
    con.close();
  }

}
