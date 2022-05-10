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
 
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
 
import org.apache.log4j.Logger;
 
/**
 * @author semika
 *
 */
public class JAASLoginModule implements LoginModule { 
  

    private static Logger LOGGER = Logger.getLogger(JAASLoginModule.class); 
  
    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map sharedState;
    private Map options;
 
    // configurable option
    private boolean debug = false;
     
    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;
     
    //user credentials
    private String username = null;
    private char[] password = null;
     
    //user principle
    private JAASUserPrincipal userPrincipal = null;
    private JAASPasswordPrincipal passwordPrincipal = null;
     
    public JAASLoginModule() {
         super();
    }
 
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
   
        debug = "true".equalsIgnoreCase((String)options.get("debug")); 


    }
 
    @Override
    public boolean login() throws LoginException {
   
        if (callbackHandler == null){
            throw new LoginException("Error: no CallbackHandler available " +
            "to garner authentication information from the user");
        }
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("username");
        callbacks[1] = new PasswordCallback("password: ", false);
   
        try {
    
            callbackHandler.handle(callbacks);
            username = ((NameCallback)callbacks[0]).getName();
            password = ((PasswordCallback)callbacks[1]).getPassword();
    
            if (debug) {
                LOGGER.debug("Username :" + username);
                LOGGER.debug("Password : " + password);
        System.out.println("Username :" + username + "\nPassword : " + password);

            }
    
            if (username == null || password == null) {
                LOGGER.error("Callback handler does not return login data properly");
        System.out.println();
                throw new LoginException("Callback handler does not return login data properly"); 
            }
    
            if (isValidUser()) { //validate user.
                succeeded = true;
                return true;
            } 
    
        } catch (IOException e) { 
             e.printStackTrace();
        } catch (UnsupportedCallbackException e) {
             e.printStackTrace();
        }
   
        return false;
    }
 
    @Override
    public boolean commit() throws LoginException {
        System.out.println("in commit");
        if (succeeded == false) {
            System.out.println("succeeded is " + succeeded);
            return false;
        } else { 
            userPrincipal = new JAASUserPrincipal(username);
            System.out.println("In commit " + username);
            
            passwordPrincipal = new JAASPasswordPrincipal(new String(password)); 
            if (!subject.getPrincipals().contains(passwordPrincipal)) {
                subject.getPrincipals().add(passwordPrincipal);
        System.out.println("Password principal added: " + passwordPrincipal);
                LOGGER.debug("Password principal added: " + passwordPrincipal);
            } else {
        System.out.println("Password principal exsiting: " + passwordPrincipal);
            }
       
            //populate subject with roles.
            List<String> roles = getRoles();
            for (String role: roles) {
                JAASRolePrincipal rolePrincipal = new JAASRolePrincipal(role);
                if (!subject.getPrincipals().contains(rolePrincipal)) {
                    subject.getPrincipals().add(rolePrincipal); 
        System.out.println("Role principal added: " + rolePrincipal);
                    LOGGER.debug("Role principal added: " + rolePrincipal);
                }
            }
            if (!subject.getPrincipals().contains(userPrincipal)) {
                subject.getPrincipals().add(userPrincipal);
                        System.out.println("User principal existing:" + userPrincipal);
                LOGGER.debug("User principal added:" + userPrincipal);
            } else {
                        System.out.println("User principal existing:" + userPrincipal);
            }
       
            commitSucceeded = true;
            System.out.println("Login subject were successfully populated with principals and roles");
            LOGGER.info("Login subject were successfully populated with principals and roles"); 
       
            return true;
       }
   }
 
   @Override
   public boolean abort() throws LoginException {
    System.out.println("In abortt=========");
      if (succeeded == false) {
          return false;
      } else if (succeeded == true && commitSucceeded == false) {
          succeeded = false;
          username = null;
          if (password != null) {
              password = null;
          }
          userPrincipal = null;    
      } else {
          logout();
      }
      return true;
   }
 
    @Override
    public boolean logout() throws LoginException {
        System.out.println("In logout======");
        subject.getPrincipals().remove(userPrincipal);
        succeeded = false;
        succeeded = commitSucceeded;
        username = null;
        if (password != null) {
            for (int i = 0; i < password.length; i++){
                password[i] = ' ';
                password = null;
            }
        }
        userPrincipal = null;
        return true;
   }
  
   private boolean isValidUser() throws LoginException {
 
    System.out.println("In validate user....................");
      // String sql = (String)options.get("userQuery");
        String userQuery="select email from users where users.email=? and users.password=?";
        Connection con = null;
      ResultSet rs = null;
      PreparedStatement stmt = null;
   
      try {
          con = getConnection();
          stmt = con.prepareStatement(userQuery);
          stmt.setString(1, username);
          stmt.setString(2, new String(password));
          // System.out.println(stmt);
    
          rs = stmt.executeQuery();
    
          if (rs.next()) { //User exist with the given user name and password.
            System.out.println("Login successfull");
              return true;
          }
       } catch (Exception e) {
        System.out.println("Error when loading user from the database " + e);
           LOGGER.error("Error when loading user from the database " + e);
           e.printStackTrace();
       } finally {
           try {
               rs.close();
           } catch (SQLException e) {
        System.out.println("Error when closing result set." + e);
               LOGGER.error("Error when closing result set." + e);
           }
           try {
               stmt.close();
           } catch (SQLException e) {
        System.out.println("Error when closing statement." + e);
               LOGGER.error("Error when closing statement." + e);
           }
           try {
               con.close();
           } catch (SQLException e) {
        System.out.println("Error when closing connection." + e);
               LOGGER.error("Error when closing connection." + e);
           }
       }
       return false;
   }
 
 /**
  * Returns list of roles assigned to authenticated user.
  * @return
  */
  private List<String> getRoles() { 
   
      Connection con = null;
      ResultSet rs = null;
      PreparedStatement stmt = null;
       String roleQuery="select userrole.role from users, userrole where users.email=userrole.email and users.email=?";
     
   
      List<String> roleList = new ArrayList<String>(); 
   
      try {
          con = getConnection();
          stmt = con.prepareStatement(roleQuery);
          stmt.setString(1, username);
    
          rs = stmt.executeQuery();
    
          if (rs.next()) { 
              roleList.add(rs.getString("role")); 
          }
      } catch (Exception e) {
        System.out.println("Error when loading user from the database " + e);
          LOGGER.error("Error when loading user from the database " + e);
          e.printStackTrace();
      } finally {
           try {
               rs.close();
           } catch (SQLException e) {
        System.out.println("Error when closing result set." + e);
               LOGGER.error("Error when closing result set." + e);
           }
           try {
               stmt.close();
           } catch (SQLException e) {
        System.out.println("Error when closing statement." + e);
               LOGGER.error("Error when closing statement." + e);
           }
           try {
               con.close();
           } catch (SQLException e) {
        System.out.println("Error when closing connection." + e);
               LOGGER.error("Error when closing connection." + e);
           }
       }
       return roleList;
 }
  
 /**
  * Returns JDBC connection
  * @return
  * @throws LoginException
  */
  private Connection getConnection() throws LoginException {
   
      String dBUser = (String)options.get("dbUser");
      String dBPassword = (String)options.get("dbPassword");
      String dBUrl = (String)options.get("dbURL");
      String dBDriver = (String)options.get("dbDriver");
 
      Connection con = null;
      try {
         //loading driver
         Class.forName (dBDriver).newInstance();
         con = DriverManager.getConnection (dBUrl, dBUser, dBPassword);
      } 
      catch (Exception e) {
            System.out.println("Error when creating database connection" + e);
         LOGGER.error("Error when creating database connection" + e);
         e.printStackTrace();
      } finally {
      }
      return con;
   }
}