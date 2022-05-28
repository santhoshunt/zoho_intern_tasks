package servletclass;

import java.util.Map;

public class SAMLInputContainer {

    private String strIssuer;
    private String strNameID;
    private String strNameQualifier;
    private String sessionId;
    private int maxSessionTimeoutInMinutes = 15;

    private Map<String, String> attributes;

    public String getStrIssuer() {
        return strIssuer;
    }

    public void setStrIssuer(String strIssuer) {
        this.strIssuer = strIssuer;
    }

    public String getStrNameID() {
        return strNameID;
    }

    public void setStrNameID(String strNameID) {
        this.strNameID = strNameID;
    }

    public String getStrNameQualifier() {
        return strNameQualifier;
    }

    public void setStrNameQualifier(String strNameQualifier) {
        this.strNameQualifier = strNameQualifier;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setMaxSessionTimeoutInMinutes(int maxSessionTimeoutInMinutes) {
        this.maxSessionTimeoutInMinutes = maxSessionTimeoutInMinutes;
    }

    public int getMaxSessionTimeoutInMinutes() {
        return maxSessionTimeoutInMinutes;
    }

}