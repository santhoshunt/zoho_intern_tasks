package servletclass;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class JAASCallbackHandler implements CallbackHandler {

   private String username = null;
   private String password = null;

   public JAASCallbackHandler(String username, String password) {
      this.username = username;
      this.password = password;
   }

   @Override
   public void handle(Callback[] callbacks) throws IOException,
         UnsupportedCallbackException {

      System.out.println("=======================Callback handler invoked====================");

      for (int i = 0; i < callbacks.length; i++) {
         if (callbacks[i] instanceof NameCallback) {
            NameCallback nameCallback = (NameCallback) callbacks[i];
            nameCallback.setName(username);
         } else if (callbacks[i] instanceof PasswordCallback) {
            PasswordCallback passwordCallback = (PasswordCallback) callbacks[i];
            passwordCallback.setPassword(password.toCharArray());
         } else {
            throw new UnsupportedCallbackException(callbacks[i], "The submitted Callback is unsupported");
         }
      }
   }
}