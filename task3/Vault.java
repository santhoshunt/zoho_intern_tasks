import java.util.*;
import java.sql.*;

public class Vault {
    private final String url = "jdbc:postgresql://localhost:5432/sandy";
    private final String user = "sandy";
    private final String password = "santhosh";
    Connection connection = null;
    Statement st = null;

    Vault() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connection successfull");
            } else {
                System.out.println("Connection failed");
            }
            st = connection.createStatement();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // ***************************** Other helpers ***********************

    // ******************************* service ********************************

    void addService() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the serive name: ");
        String name = sc.nextLine().strip().toLowerCase();
        System.out.println("Enter the URL / Link of the service: ");
        String url = sc.nextLine().strip().toLowerCase();
        System.out.println("Enter the user name: ");
        String userName = sc.nextLine().strip();
        System.out.println("Enter the password: ");
        String password = sc.nextLine().strip();
        String query1 = "insert into service(name, username, password) values('" + name + "', '" + userName + "','"
                + password + "')";
        String query2 = "insert into url(name, url) values('" + name + "','" + url + "') on conflict (name) do nothing";
        try {
            st.executeUpdate(query2);
            st.executeUpdate(query1);
            System.out.println(query1);
            System.out.println(query2);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    void viewService() {
        String query = "select * from service join url on service.name = url.name";
        ResultSet rs;
        System.out.printf("%5s %20s %20s %20s %20s\n", "id", "name", "url", "user name", "password");
        try {
            // id | name | username | password | id | name | url
            rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String userName = rs.getString(3);
                String password = rs.getString(4);
                String url = rs.getString(7);
                Formatter fmt = new Formatter();
                fmt.format("%5d %20s %20s %20s %20s", id, name, url, userName, password);
                System.out.println(fmt);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    void deleteService() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the service name: ");
        String key = sc.nextLine().strip().toLowerCase();
        if (filterService(key)) {
            System.out.println("Enter the id of the serive you wanna delete");
            int enteredId = sc.nextInt();
            sc.nextLine();
            String query = "select * from service join url on service.name = url.name where service.id=" + enteredId;
            ResultSet rs;
            try {
                rs = st.executeQuery(query);
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String userName = rs.getString(3);
                    String password = rs.getString(4);
                    String url = rs.getString(7);
                    Formatter fmt = new Formatter();
                    fmt.format("%5d %20s %20s %20s %20s", id, name, url, userName, password);
                    System.out.println(fmt);
                }
                System.out.println("Are you sure you want to delete the above service account? (y/n)");
                String responce = sc.nextLine().strip();
                if (responce.toLowerCase().equals("y")) {
                    String sql = "delete from service where service.id=" + enteredId;
                    st.executeUpdate(sql);
                    System.out.println("Deletion successfull!");
                    return;
                }
                System.out.println("Deletion not successfull!");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    void updateService() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the service name you want to update: ");
        String key = sc.nextLine().toLowerCase();
        if (filterService(key)) {
            System.out.println("Enter the id of the serive you wanna update: ");
            int enteredId = sc.nextInt();
            sc.nextLine();
            String query = "select * from service join url on service.name = url.name where service.id=" + enteredId;
            ResultSet rs;
            try {
                rs = st.executeQuery(query);
                String name = "";
                String userName = "";
                String password = "";
                String url = "";
                while (rs.next()) {
                    int id = rs.getInt(1);
                    name = rs.getString(2);
                    userName = rs.getString(3);
                    password = rs.getString(4);
                    url = rs.getString(7);
                    Formatter fmt = new Formatter();
                    fmt.format("%5d %20s %20s %20s %20s", id, name, url, userName, password);
                    System.out.println(fmt);
                }
                System.out.println("Are you sure you want to delete the above service account? (y/n)");
                String responce = sc.nextLine().strip();
                if (responce.toLowerCase().equals("y")) {
                    System.out.println(
                            "Enter the fields you wanna update\nIF YOU DON'T WANT TO UPDATE CERTAIN FEILD LEAVE IT BLANK AND PRESS ENTER");
                    System.out.println("Enter the service name");
                    String newName = sc.nextLine().strip().toLowerCase();
                    System.out.println("Enter the service URL / link");
                    String newUrl = sc.nextLine().strip().toLowerCase();
                    System.out.println("Enter the username");
                    String newUserName = sc.nextLine().strip();
                    System.out.println("Enter the password");
                    String newPassword = sc.nextLine().strip();
                    newUrl = newUrl.equals("") ? url : newUrl;
                    String sql2 = "update url set name='" + (newName.equals("") ? name
                            : newName) + "', url='" + newUrl
                            + "' where url.name='" + name + "'";
                    st.executeUpdate(sql2);
                    newName = newName.equals("") ? name : newName;
                    newUserName = newUserName.equals("") ? userName : newUserName;
                    newPassword = newPassword.equals("") ? password : newPassword;
                    String sql = "update service set name='" + newName + "', username='" + newUserName
                            + "', password=' "
                            + newPassword + "' where id=" + enteredId;
                    st.executeUpdate(sql);
                    System.out.println("Updation successfull!");
                    return;
                }
                System.out.println("Updation not successfull!");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    boolean filterService(String key) {
        ResultSet rs;
        String query = "select * from service join url on service.name = url.name where service.name like '%" + key
                + "%'";
        try {
            System.out.printf("%5s %20s %20s %20s %20s\n", "id", "name", "url", "user name", "password");
            rs = st.executeQuery(query);
            boolean valueFound = false;
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String userName = rs.getString(3);
                String password = rs.getString(4);
                String url = rs.getString(7);
                Formatter fmt = new Formatter();
                fmt.format("%5d %20s %20s %20s %20s", id, name, url, userName, password);
                System.out.println(fmt);
                valueFound = true;
            }
            if (!valueFound) {
                System.out.println(
                        "Your search with key \"" + key + "\" yeilds no result\nplease try gain with different key");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    // ******************************* CONTACT *******************************

    void viewAccount() {
        String query = "select * from contact";
        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);

            Formatter fmt = new Formatter();
            fmt.format("%5s %20s %15s %30s %35s %5s %12s", "id", "name", "phno", "email", "job", "age",
                    "dob");
            System.out.println(fmt);
            // Name, mobile numbers, email addresses, job title, age, Date of birth
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String phno = rs.getString(3);
                String email = rs.getString(4);
                String job = rs.getString(5);
                int age = rs.getInt(6);
                String dob = rs.getString(7);
                Formatter fmt1 = new Formatter();
                fmt1.format("%5d %20s %15s %30s %35s %5d %12s", id, name, phno, email, job, age, dob);
                System.out.println(fmt1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteContact() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the keyword: ");
        String key = sc.nextLine().strip();
        if (!filterContact(key)) {
            return;
        }
        System.out.println("please enter the id number of the name you want to delete");
        int deleteId = sc.nextInt();
        sc.nextLine();
        String query = "delete from contact where id=" + deleteId;
        String verify = "select * from contact where id=" + deleteId;
        ResultSet rs = st.executeQuery(verify);
        boolean found = false;
        if (rs.next()) {
            found = true;
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String phno = rs.getString(3);
            String email = rs.getString(4);
            String job = rs.getString(5);
            int age = rs.getInt(6);
            String dob = rs.getString(7);
            Formatter fmt1 = new Formatter();
            fmt1.format("%5d %20s %15s %30s %35s %5d %12s", id, name, phno, email, job, age, dob);
            System.out.println(fmt1);
        }
        if (!found) {
            System.err.println("Please Enter the correct ID");
            return;
        }
        System.out.println("Are you sure you want to delete the above contact? (y/n)");
        String choice = sc.nextLine().strip().toLowerCase();
        if (choice.equals("y")) {
            st.executeUpdate(query);
            System.out.println("Contact deleted successfully!");
            return;
        }
        System.out.println("deletion cancelled");
    }

    // *********************************************************************************************
    void updateContact() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the keyword: ");
        String key = sc.nextLine().trim();
        if (!filterContact(key)) {
            return;
        }
        System.out.println("Please select the id you want to update");
        int updateId = sc.nextInt();
        sc.nextLine();
        String verify = "select * from contact where id=" + updateId;
        ResultSet rs = st.executeQuery(verify);
        boolean found = false;
        String oldName = "";
        String oldPhno = "";
        String oldEmail = "";
        String oldJob = "";
        int oldAge = -1;
        String oldDob = "";

        if (rs.next()) {
            found = true;
            int id = rs.getInt(1);
            oldName = rs.getString(2);
            oldPhno = rs.getString(3);
            oldEmail = rs.getString(4);
            oldJob = rs.getString(5);
            oldAge = rs.getInt(6);
            oldDob = rs.getString(7);
            Formatter fmt1 = new Formatter();
            fmt1.format("%5d %20s %15s %30s %35s %5d %12s", id, oldName, oldPhno, oldEmail, oldJob, oldAge, oldDob);
            System.out.println(fmt1);
        }
        if (!found) {
            System.err.println("Please Enter the correct ID");
            return;
        }
        System.out.println("Are you sure you want to Update the above contact? (y/n)");
        String choice = sc.nextLine().strip().toLowerCase();
        if (choice.equals("y")) {
            System.out.println("PLEASE ENTER THE FIELDS ONLY YOU WANT TO UPDATE");
            System.out.println("IF YOU DONT WANT TO CHANGE ANY DETAILS PLEASE LEAVE IT BLANK AND PRESS ENTER");
            System.out.println("Enter Name: ");
            String name = sc.nextLine().strip();
            System.out.println("Mobile Number: ");
            String phno = sc.nextLine().strip();
            System.out.println("e-mail: ");
            String email = sc.nextLine().strip();
            System.out.println("Job-title: ");
            String job = sc.nextLine().strip();
            System.out.println("Age: ");
            String age = sc.nextLine().strip();
            System.out.println("Date of birth: ");
            String dob = sc.nextLine().strip();
            name = name.equals("") ? oldName : name;
            phno = phno.equals("") ? oldPhno : phno;
            email = email.equals("") ? oldEmail : email;
            job = job.equals("") ? oldJob : job;
            int upAge = age.equals("") ? oldAge : Integer.parseInt(age);
            dob = dob.equals("") ? oldDob : dob;

            String query = "update contact set name='" + name + "',phno='" + phno + "',email='" + email + "',job='"
                    + job + "',age=" + upAge + ",dob='" + dob + "' where id=" + updateId;
            // System.out.println(query);
            st.executeUpdate(query);
            System.out.println("Contact Updated successfully!");
            return;
        }
        System.out.println("Updation cancelled");
    }

    void addContact() throws SQLException {
        Scanner inp = new Scanner(System.in);
        System.out.println("Enter Name: ");
        String name = inp.nextLine().strip();
        System.out.println("Mobile Number: ");
        String phno = inp.nextLine().strip();
        System.out.println("e-mail: ");
        String email = inp.nextLine().strip();
        System.out.println("Job-title: ");
        String job = inp.nextLine().strip();
        System.out.println("Age: ");
        int age = inp.nextInt();
        inp.nextLine();
        System.out.println("Date of birth: ");
        String dob = inp.nextLine().strip();
        String query = "insert into contact(name, phno, email, job, age, dob) values('" + name + "','" + phno
                + "','" + email + "','" + job + "'," + age + ",'" + dob + "');";
        System.out.println(query);
        st.executeUpdate(query);
        System.out.println("Contact added successfully");
        // inp.close();
    }

    boolean filterContact(String searchName) {
        searchName = searchName.toLowerCase();
        String query = "select * from contact where name like '%" + searchName + "%'";
        try {
            ResultSet rs = st.executeQuery(query);
            System.out.println(
                    "Your search result with the keyword \"" + searchName + "\" yielded the following result: ");
            Formatter fmt = new Formatter();
            fmt.format("%5s %20s %15s %30s %35s %5s %12s", "id", "name", "phno", "email", "job", "age",
                    "dob");
            System.out.println(fmt);
            boolean resultQueried = false;
            while (rs.next()) {
                resultQueried = true;
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String phno = rs.getString(3);
                String email = rs.getString(4);
                String job = rs.getString(5);
                int age = rs.getInt(6);
                String dob = rs.getString(7);
                Formatter fmt1 = new Formatter();
                fmt1.format("%5d %20s %15s %30s %35s %5d %12s", id, name, phno, email, job, age, dob);
                System.out.println(fmt1);
            }
            if (!resultQueried) {
                System.out.println("Your search result with the keyword \"" + searchName
                        + "\" yielded no results please try again with different keywords");
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return true;

    }

    void start() {
        try {

            Scanner sc = new Scanner(System.in);

            boolean userWantsToContinue = true;

            // ********************************* menu *********************************
            do {
                System.out.println(
                        "\nEnter ACCOUNT\nEnter CONTACT\nEnter exit to exit application\n");
                String line = sc.nextLine().strip();
                String validOptions = "add delete view update";
                if (line.toLowerCase().equals("account")) {
                    System.out.println("Enter add / delete / view / update / filter");
                    String operation = sc.nextLine().strip();
                    while (validOptions.indexOf(operation.toLowerCase()) == -1) {
                        System.out.println("Please Enter a valid option");
                        System.out.println("Enter add / delete / view / update / filter");
                        operation = sc.nextLine().strip();
                    }
                    if (operation.toLowerCase().equals("add")) {
                        addService();
                    } else if (operation.toLowerCase().equals("delete")) {
                        deleteService();
                    } else if (operation.toLowerCase().equals("view")) {
                        viewService();
                    } else if (operation.toLowerCase().equals("update")) {
                        updateService();
                    } else if (operation.toLowerCase().equals("filter")) {
                        System.out.println("Enter the keyword to search");
                        String key = sc.nextLine().strip();
                        filterService(key);
                    }
                } else if (line.toLowerCase().equals("contact")) {
                    System.out.println("Enter add / delete / view / update / filter");
                    String operation = sc.nextLine().strip();
                    if (operation.toLowerCase().equals("add")) {
                        addContact();
                    } else if (operation.toLowerCase().equals("delete")) {
                        deleteContact();
                    } else if (operation.toLowerCase().equals("view")) {
                        viewAccount();
                    } else if (operation.toLowerCase().equals("update")) {
                        updateContact();
                    } else if (operation.toLowerCase().equals("filter")) {
                        System.out.println("Please Enter the keyword you wanna search");
                        String searchName = sc.nextLine().strip();
                        filterContact(searchName);
                    } else {
                        System.out.println("Please Enter a valid option");
                    }
                } else if (line.toLowerCase().equals("exit")) {
                    st.close();
                    connection.close();
                    userWantsToContinue = false;
                } else {
                    System.err.println("Please enter \"Account\" /  \"Contact\" / \"Exit\"");
                }
            } while (userWantsToContinue);

            sc.close();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Vault vault = new Vault();
        vault.start();
    }
}