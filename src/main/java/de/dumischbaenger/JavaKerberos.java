package de.dumischbaenger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dumischbaenger.kerberos.KerberosCallBackHandler;
import de.dumischbaenger.kerberos.Connector;

public class JavaKerberos {
  static private Logger LOG=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public static void main(String[] args) {
    
      LOG.info("program started");

      Security.setProperty("auth.login.defaultCallbackHandler", "de.dumischbaenger.kerberos.KerberosCallBackHandler");
      Security.setProperty("login.configuration.provider", "de.dumischbaenger.kerberos.KerberosConfig");    


      try {
          //load a properties file from class path, inside static method
          InputStream is=JavaKerberos.class.getResourceAsStream("/config.properties");
          System.getProperties().load(is);
      } 
      catch (IOException ex) {
          ex.printStackTrace();
      }
      
      String props[]= {
          "javax.security.auth.useSubjectCredsOnly",
          "java.security.krb5.realm",
          "java.security.krb5.kdc",
          "auth.login.defaultCallbackHandler",
          "login.configuration.provider",
          "java.security.auth.login.config",
          "login.config.url.1",
          "login.config.url.2",
          "login.config.url.3",
      };
      
      for(int i=0; i< props.length;i++) {
        LOG.info(props[i] + ": " + System.getProperty(props[i]));
      }
      
      try {
        Connector.connectJavaKerberos();
      } catch (Exception e) {
        e.printStackTrace();
      }
      
      
      LOG.info("program stopped");
  }

}
