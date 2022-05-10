package servletclass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class LoginCallbackHandler implements CallbackHandler {

	
	private String name = null;
	private String password = null;
	
	public LoginCallbackHandler(String name, String password) {
		this.name = name;
		this.password = password;
		System.out.println("In setter " + name + " " + password);
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		System.out.println("In handle...");
		NameCallback nameCallback = null;
		PasswordCallback passwordCallback = null;
		for (Callback callback: callbacks) {
			if (callback instanceof NameCallback) {
				nameCallback = (NameCallback) callback;
				nameCallback.setName(name);
				System.out.println("In handle  " + name);
			} else if(callback instanceof PasswordCallback) {
				passwordCallback = (PasswordCallback) callback;
				passwordCallback.setPassword(password.toCharArray());
				System.out.println("In handle  " + password);
			}
		}
	}

}