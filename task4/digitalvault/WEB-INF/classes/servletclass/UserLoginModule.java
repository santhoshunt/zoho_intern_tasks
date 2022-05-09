package servletclass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.servlet.http.HttpServletRequest;

public class UserLoginModule implements LoginModule {

	String test_name = "user";
	String test_pasword = "pass";
	private CallbackHandler callbackHandler;
	private Subject subject;
	private UserPrincipal userPrincipal;
	private RolePrincipal rolePrincipal;
	private String login;
	private List<String> userGroups;
	boolean authsuccess = false;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {

		this.callbackHandler = callbackHandler;
		this.subject = subject;
		System.out.println("In initialize...");
	}

	@Override
	public boolean login() throws LoginException {

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("email");
		callbacks[1] = new PasswordCallback("password", false);

		try {
			callbackHandler.handle(callbacks);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedCallbackException e) {
			e.printStackTrace();
		}

		String name = ((NameCallback) callbacks[0]).getName();
		String password = new String(((PasswordCallback) callbacks[1]).getPassword());

		if (name != null && password != null && name.equals(test_name) && password.equals(test_pasword)) {
			System.out.println("Auth success!");
			login = name;
			userGroups = new ArrayList<String>();
			userGroups.add("admin");
			for (String group: userGroups) {
				System.out.println(group);
			}
			authsuccess = true;
			return true;
		} else {
			System.out.println("Auth failed");
			authsuccess = false;
			throw new FailedLoginException("Auth failed");
		}
	}

	@Override
	public boolean commit() throws LoginException {
		userPrincipal = new UserPrincipal(login);
	    subject.getPrincipals().add(userPrincipal);
	    System.out.println("In commit");

	    if (userGroups != null && userGroups.size() > 0) {
	      for (String groupName : userGroups) {
	        rolePrincipal = new RolePrincipal(groupName);
	        subject.getPrincipals().add(rolePrincipal);
	      }
	    } else {
	    	System.out.println("Empty usergroup");
	    }

	    return authsuccess;
	}

	@Override
	public boolean abort() throws LoginException {
		System.out.println("In abort...");
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().remove(userPrincipal);
	    subject.getPrincipals().remove(rolePrincipal);
	    return true;
	}

}