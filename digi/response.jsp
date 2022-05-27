<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import = " java.util.Base64" %>
<%@ page import = "java.util.Iterator, java.io.File, java.io.FileInputStream, java.util.Scanner, java.io.InputStream, java.util.Map, java.util.HashMap, java.io.PrintStream, javax.xml.XMLConstants" %>
<%@ page import = "org.opensaml.saml2.core.Assertion, org.opensaml.saml2.core.Response, org.opensaml.saml2.core.impl.IssuerBuilder, org.opensaml.Configuration, org.opensaml.common.SAMLObjectBuilder, org.opensaml.saml2.core.impl.ResponseBuilder, org.opensaml.saml2.core.impl.AssertionMarshaller, org.opensaml.DefaultBootstrap, org.opensaml.saml2.core.StatusCode, org.opensaml.saml2.core.impl.StatusBuilder, org.opensaml.saml2.core.impl.StatusCodeBuilder, org.opensaml.xml.ConfigurationException, org.opensaml.saml2.core.Conditions, org.opensaml.xml.XMLObjectBuilderFactory, org.opensaml.xml.XMLObjectBuilder, org.opensaml.saml2.core.Condition, org.opensaml.saml2.core.Issuer, org.opensaml.saml2.core.NameID, org.opensaml.saml2.core.impl.StatusMessageBuilder, org.opensaml.saml2.core.StatusMessage, org.opensaml.saml2.core.Status, org.opensaml.saml2.core.OneTimeUse, org.opensaml.saml2.core.Subject, org.opensaml.saml2.core.SubjectConfirmation, org.opensaml.saml2.core.SubjectConfirmationData, org.opensaml.saml2.core.impl.AssertionMarshaller, org.opensaml.saml2.core.Attribute, org.opensaml.saml2.core.AttributeStatement, org.opensaml.saml2.core.AttributeValue, org.opensaml.saml2.core.AuthnContext, org.opensaml.saml2.core.AuthnContextClassRef, org.opensaml.saml2.core.AuthnStatement, org.opensaml.xml.schema.XSString, org.opensaml.xml.security.credential.Credential, org.opensaml.xml.util.XMLHelper, org.w3c.dom.Element,org.w3c.dom.Document, java.security.KeyStore, org.opensaml.common.SAMLVersion, com.onelogin.saml2.util.Util, org.opensaml.xml.security.x509.BasicX509Credential, org.opensaml.saml2.core.Response, java.security.cert.CertificateFactory, java.security.PrivateKey,javax.xml.transform.stream.StreamResult,java.io.StringWriter,javax.xml.transform.TransformerFactory,javax.xml.transform.dom.DOMSource" %>
<%@ page import = "java.security.cert.X509Certificate" %>
<%@ page import = "servletclass.SAMLInputContainer" %>
<%@ page import = "org.joda.time.DateTime" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
</head>
<body>

	<%@ include file="navbar.jsp"%>
<%!
private XMLObjectBuilderFactory builderFactory;

public String signResponse(String saml) {
    try {
        Document document = Util.loadXML(saml);

        // loads certificate and private key from string
        InputStream certificate = new FileInputStream("/home/sandy/apache-tomcat-8.5.78/webapps/digi/certificate.crt");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate)cf.generateCertificate(certificate);
        certificate.close();

        File pKey = new File("/home/sandy/apache-tomcat-8.5.78/webapps/digi/privateKey.key");
        Scanner sc = new Scanner(pKey);
        StringBuilder sb = new StringBuilder(); 
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        String op = sb.toString();
        op = op.replace("-----BEGIN PRIVATE KEY-----", "");
        op = op.replace("-----END PRIVATE KEY-----", "");
        System.out.println(op);
        // String privateKeyString = 
        PrivateKey privateKey = Util.loadPrivateKey(op);
        sc.close();

        // signs the response
        String signedResponse = Util.addSign(document, privateKey, cert, null);
        System.out.println("Signed response is: " + signedResponse);
        return signedResponse;
    } catch (Exception e) {
        System.out.println(e);
    }
    return new String();
}

public XMLObjectBuilderFactory getSAMLBuilder() throws ConfigurationException{

    if(builderFactory == null){
        DefaultBootstrap.bootstrap();
        builderFactory = Configuration.getBuilderFactory();
    }

    return builderFactory;
}
public Attribute buildStringAttribute(String name, String value, XMLObjectBuilderFactory builderFactory) throws ConfigurationException{
    SAMLObjectBuilder attrBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
    Attribute attrFirstName = (Attribute) attrBuilder.buildObject();
    attrFirstName.setName(name);

    // Set custom Attributes
    XMLObjectBuilder stringBuilder = getSAMLBuilder().getBuilder(XSString.TYPE_NAME);
    XSString attrValueFirstName = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
    attrValueFirstName.setValue(value);

    attrFirstName.getAttributeValues().add(attrValueFirstName);
    return attrFirstName;
}

public Response wrapAssertionIntoResponse(Assertion assertion, String assertionIssuer) {
  Response response = new ResponseBuilder().buildObject();
  Issuer issuer = new IssuerBuilder().buildObject();
  issuer.setValue(assertionIssuer);
  response.setIssuer(issuer);
  response.setID("id-" + System.currentTimeMillis());
  Status stat = new StatusBuilder().buildObject();
  // Set the status code
  StatusCode statCode = new StatusCodeBuilder().buildObject();
  statCode.setValue("urn:oasis:names:tc:SAML:2.0:status:Success");
  stat.setStatusCode(statCode);
  response.setStatus(stat);
  response.setVersion(SAMLVersion.VERSION_20);
  response.setIssueInstant(new DateTime());
  response.getAssertions().add(assertion);
  return response;
}

public String buildDefaultAssertion(SAMLInputContainer input){
    try {
        // Create the NameIdentifier
        SAMLObjectBuilder nameIdBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(NameID.DEFAULT_ELEMENT_NAME);
        NameID nameId = (NameID) nameIdBuilder.buildObject();
        nameId.setValue(input.getStrNameID());
        nameId.setNameQualifier(input.getStrNameQualifier());
        nameId.setFormat(NameID.UNSPECIFIED);
        Status stat = new StatusBuilder().buildObject();
        // Set the status code
        StatusCode statCode = new StatusCodeBuilder().buildObject();
        statCode.setValue("urn:oasis:names:tc:SAML:2.0:status:Success");
        stat.setStatusCode(statCode);
        // Create the SubjectConfirmation

        SAMLObjectBuilder confirmationMethodBuilder = (SAMLObjectBuilder)  getSAMLBuilder().getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
        SubjectConfirmationData confirmationMethod = (SubjectConfirmationData) confirmationMethodBuilder.buildObject();
        DateTime now = new DateTime();
        confirmationMethod.setNotBefore(now);
        confirmationMethod.setNotOnOrAfter(now.plusMinutes(2));

        SAMLObjectBuilder subjectConfirmationBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
        SubjectConfirmation subjectConfirmation = (SubjectConfirmation) subjectConfirmationBuilder.buildObject();
        subjectConfirmation.setSubjectConfirmationData(confirmationMethod);

        // Create the Subject
        SAMLObjectBuilder subjectBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(Subject.DEFAULT_ELEMENT_NAME);
        Subject subject = (Subject) subjectBuilder.buildObject();

        subject.setNameID(nameId);
        subject.getSubjectConfirmations().add(subjectConfirmation);

        // Create Authentication Statement
        SAMLObjectBuilder authStatementBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
        AuthnStatement authnStatement = (AuthnStatement) authStatementBuilder.buildObject();
        //authnStatement.setSubject(subject);
        //authnStatement.setAuthenticationMethod(strAuthMethod);
        DateTime now2 = new DateTime();
        authnStatement.setAuthnInstant(now2);
        authnStatement.setSessionIndex(input.getSessionId());
        authnStatement.setSessionNotOnOrAfter(now2.plus(input.getMaxSessionTimeoutInMinutes()));

        SAMLObjectBuilder authContextBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(AuthnContext.DEFAULT_ELEMENT_NAME);
        AuthnContext authnContext = (AuthnContext) authContextBuilder.buildObject();

        SAMLObjectBuilder authContextClassRefBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
        AuthnContextClassRef authnContextClassRef = (AuthnContextClassRef) authContextClassRefBuilder.buildObject();
        authnContextClassRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:Password"); // TODO not sure exactly about this

        authnContext.setAuthnContextClassRef(authnContextClassRef);
        authnStatement.setAuthnContext(authnContext);

        // Builder Attributes
        SAMLObjectBuilder attrStatementBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
        AttributeStatement attrStatement = (AttributeStatement) attrStatementBuilder.buildObject();

        // Create the attribute statement
        Map attributes = input.getAttributes();
        if(attributes != null){
            Iterator keySet = attributes.keySet().iterator();
            while (keySet.hasNext()){
                String key = keySet.next().toString();
                String val = attributes.get(key).toString();
                Attribute attrFirstName = buildStringAttribute(key, val, getSAMLBuilder());
                attrStatement.getAttributes().add(attrFirstName);
            }
        }

        // Create the do-not-cache condition
        // SAMLObjectBuilder doNotCacheConditionBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(OneTimeUse.DEFAULT_ELEMENT_NAME);
        // Condition condition = (Condition) doNotCacheConditionBuilder.buildObject();

        // SAMLObjectBuilder conditionsBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(Conditions.DEFAULT_ELEMENT_NAME);
        // Conditions conditions = (Conditions) conditionsBuilder.buildObject();
        // conditions.getConditions().add(condition);

        // Create Issuer
        SAMLObjectBuilder issuerBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
        Issuer issuer = (Issuer) issuerBuilder.buildObject();
        issuer.setValue(input.getStrIssuer());

        // Create the assertion
        SAMLObjectBuilder assertionBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
        Assertion assertion = (Assertion) assertionBuilder.buildObject();
        assertion.setIssuer(issuer);
        assertion.setIssueInstant(now);
        assertion.setVersion(SAMLVersion.VERSION_20);

        assertion.getAuthnStatements().add(authnStatement);
        assertion.getAttributeStatements().add(attrStatement);
        // assertion.setConditions(conditions);

        Response res = wrapAssertionIntoResponse(assertion, "http:localhost:8085/digi/");
        Element xml = Configuration
               .getMarshallerFactory()
               .getMarshaller(res)
               .marshall(res);
        StreamResult result = new StreamResult(new StringWriter());
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(xml), result);
        String samlResponse = result.getWriter().toString();
        return samlResponse;
        // System.out.println("Response is : " + samlResponse);
        // return assertion;
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

%>
<%
    try {
        SAMLInputContainer input = new SAMLInputContainer();
        input.setStrIssuer("http:localhost:8085/digi/");
        input.setStrNameID("UserSanthosh");
        input.setStrNameQualifier("Digital vault");
        input.setSessionId("abcdedbljjljljf1234567");

        Map<String,String> customAttributes = new HashMap<String, String>();
        customAttributes.put("firstName", "Santhosh");
        customAttributes.put("lastName", "Kumar");
        customAttributes.put("email", "sec19cs130@sairamtap.edu.in");
        customAttributes.put("mobilePhone", "9500852560");
        customAttributes.put("locality", "India");
        customAttributes.put("userName", "santhosh.kumar");

        input.setAttributes(customAttributes);

        String assertion = buildDefaultAssertion(input);
        // AssertionMarshaller marshaller = new AssertionMarshaller();
        // Element plaintextElement = marshaller.marshall(assertion);
        // String originalAssertionString = XMLHelper.nodeToString(plaintextElement);

        // System.out.println("Assertion String: " + originalAssertionString);
        // out.println("Assertion String logged");
        String signedString = signResponse(assertion);
        if (signedString.trim().equals(new String())) {
            out.println("Signed string is empty");
        } else {
            out.println("Signed String logged");
        }
        String encodedString = Base64.getEncoder().encodeToString(signedString.getBytes());

        %>
        <form action="https://dev-93406085.okta.com/sso/saml2/0oa55wiuxmLHjSINH5d7	" method="post" class="text-center">
            <input name="SAMLResponse" type="hidden" value='<%=encodedString%>' />
            <input class="btn btn-primary" type="submit" value="submit" class="button" />
        </form>
        <%

    } catch (Exception e) {
        out.println(e);
    }
%>
</body>
</html>

