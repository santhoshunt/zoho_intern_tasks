package servletclass;
 
import java.io.Serializable;
import java.security.Principal;

 public class JAASUserPrincipal implements Principal, Serializable {
 
 private String name;
 
 public JAASUserPrincipal(String name) {
   
 if (name == null) {
     throw new NullPointerException("NULL user name");
 }
     this.name = name;
 }
  
 @Override
 public String getName() {
     return name;
 }
 
 @Override
 public String toString() {
     return "UserPrincipal [name=" + name + "]";
 }
 
 @Override
 public int hashCode() {
     final int prime = 31;
     int result = 1;
     result = prime * result + ((name == null) ? 0 : name.hashCode());
     return result;
 }
 
 @Override
 public boolean equals(Object obj) {
     if (this == obj)
        return true;
     if (obj == null)
        return false;
     if (getClass() != obj.getClass())
        return false;
     JAASUserPrincipal other = (JAASUserPrincipal) obj;
     if (name == null) {
        if (other.name != null)
           return false;
     } else if (!name.equals(other.name))
        return false;
   
     return true;
 }
}