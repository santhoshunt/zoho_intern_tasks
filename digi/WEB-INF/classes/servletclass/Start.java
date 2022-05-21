package servletclass;
 
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import javax.security.auth.*;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.*;
import javax.security.auth.spi.LoginModule;

import org.apache.log4j.Logger;


public class Start {


	public static void main(String[] args) {


    Logger LOGGER = Logger.getLogger(JAASLoginModule.class); 
		  LoginContext lc = null;
try {
    lc = new LoginContext("digi", new JAASCallbackHandler("test@gmail.com", "test"));
    lc.login();
    //get the subject.
    Subject subject = lc.getSubject();
    //get principals
    subject.getPrincipals();
    LOGGER.info("established new logincontext");
} catch (LoginException e) {
    LOGGER.error("Authentication failed " + e);
} 

	}
}