package digitalvault;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Digital Vault!!\n");

        boolean userWantsToContinue = true;

        ConnectionManager conMan = new ConnectionManager();
        Connection connection = conMan.getConnection();

        ContactDAOImpl contact = new ContactDAOImpl();

        ServiceDAOImpl service = new ServiceDAOImpl();

        do {
            System.out.println("\nEnter \"CONTACT\" / \"SERVICE\"");
            System.out.println("Enter EXIT to exit\n");
            String choice = sc.nextLine().strip();
            if (choice.toLowerCase().equals("contact")) {
                // add / delete / view / update / filter
                boolean isNotValidChoice = false;
                do {
                    System.out.println(
                            "Please Enter any one of the choices given below\nEither the choice number or the choice itself!");
                    System.out.println("1. ADD");
                    System.out.println("2. DELETE");
                    System.out.println("3. VIEW");
                    System.out.println("4. UPDATE");
                    System.out.println("5. FILTER");
                    System.out.println("6. MENU\n");
                    String contactChoice = sc.nextLine().strip().toLowerCase();

                    if (contactChoice.equals("add") || contactChoice.equals("1")) {
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine().strip();
                        System.out.print("Enter Phone Number: ");
                        String phoneNumber = sc.nextLine().strip();
                        System.out.print("Enter email: ");
                        String email = sc.nextLine().strip();
                        System.out.print("Enter job: ");
                        String job = sc.nextLine().strip();
                        System.out.print("Enter Age: ");
                        String age = sc.nextLine().strip();
                        System.out.print("Enter Date Of Birth: ");
                        String dob = sc.nextLine().strip();
                        contact.insertData(
                                connection, name, phoneNumber, email, job, age, dob);

                    } else if (contactChoice.equals("delete") || contactChoice.equals("2")) {

                        System.out.print("Enter the keyword: ");
                        String key = sc.nextLine().strip();
                        System.out.println("Search results for " + key + " in name field:\n");
                        contact.filterData(connection, key, "name");

                        System.out.println("Search results for " + key + " in phoneNumber field:\n");
                        contact.filterData(connection, key, "phno");

                        System.out.println("Search results for " + key + " in email field:\n");
                        contact.filterData(connection, key, "email");

                        System.out.println("Search results for " + key + " in job field:\n");
                        contact.filterData(connection, key, "job");

                        System.out.println("Search results for " + key + " in Date of birth field:\n");
                        contact.filterData(connection, key, "dob");

                        System.out.println("\nEnter the ID of the CONTACT you wanna delete");
                        int delId = sc.nextInt();

                        contact.deleteData(connection, delId);

                        if (contact.selectSingleRow(connection, delId)) {
                            System.out.println("Are you sure you wanna delete the above contact? (y / n)");
                            String yesOrNo = sc.nextLine().strip().toLowerCase();
                            if (yesOrNo.equals("y") || yesOrNo.equals("yes")) {
                                contact.deleteData(connection, delId);
                            } else {
                                System.out.println("Deletion cancelled!");
                            }
                        } else {
                            System.out.println("\nPlease enter correct ID\n");
                        }

                    } else if (contactChoice.equals("view") || contactChoice.equals("3")) {

                        contact.showData(connection);

                    } else if (contactChoice.equals("update") || contactChoice.equals("4")) {
                        System.out.print("\nEnter the keyword: ");
                        String key = sc.nextLine();

                        boolean resultFound = contact.filterData(connection, key, "name") ||
                                contact.filterData(connection, key, "phno") ||
                                contact.filterData(connection, key, "email") ||
                                contact.filterData(connection, key, "job") ||
                                contact.filterData(connection, key, "dob");

                        if (resultFound) {
                            System.out.println("Enter the id you wanna update");
                            String updateId = sc.nextLine().strip();

                            String name = "";
                            String email = "";
                            String phoneNumber = "";
                            String job = "";
                            String age = "";
                            String dob = "";
                            System.out.println("\nEnter the field you only want to update, else leave it blank\n");

                            System.out.print("Enter Name: ");
                            name = sc.nextLine().strip();
                            System.out.print("Enter Phone Number: ");
                            phoneNumber = sc.nextLine().strip();
                            System.out.print("Enter email: ");
                            email = sc.nextLine().strip();
                            System.out.print("Enter job: ");
                            job = sc.nextLine().strip();
                            System.out.print("Enter Age: ");
                            age = sc.nextLine().strip();
                            System.out.print("Enter Date Of Birth: ");
                            dob = sc.nextLine().strip();
                            int upId;

                            try {
                                upId = Integer.parseInt(updateId);
                            } catch (Exception e) {
                                System.err.println(e);
                                System.out.println("please enter valid ID");
                                continue;
                            }

                            contact.updateData(
                                    connection, upId, name, phoneNumber, email, job, age, dob);
                        } else {
                            System.out.println("\n Your search yeilded no result, so can't update!");
                        }

                    } else if (contactChoice.equals("filter") || contactChoice.equals("5")) {

                        System.out.print("Enter the keyword to filter: ");
                        String key = sc.nextLine().strip();
                        System.out.println("Search results for " + key + " in name field:\n");
                        contact.filterData(connection, key, "name");

                        System.out.println("Search results for " + key + " in phoneNumber field:\n");
                        contact.filterData(connection, key, "phno");

                        System.out.println("Search results for " + key + " in email field:\n");
                        contact.filterData(connection, key, "email");

                        System.out.println("Search results for " + key + " in job field:\n");
                        contact.filterData(connection, key, "job");

                        System.out.println("Search results for " + key + " in Date of birth field:\n");
                        contact.filterData(connection, key, "dob");

                    } else if (contactChoice.equals("menu") || contactChoice.equals("6")) {

                        System.out.println("Redirecting to MAIN MENU ...\n");
                        break;

                    } else {

                        System.out.println("Please Enter a valid choice!\n");
                        isNotValidChoice = true;

                    }

                } while (isNotValidChoice);

            } else if (choice.toLowerCase().equals("service")) {
                boolean isNotValidChoice = false;
                do {
                    System.out.println("Please Enter any one of the choices");
                    System.out.println("1. ADD");
                    System.out.println("2. DELETE");
                    System.out.println("3. VIEW");
                    System.out.println("4. UPDATE");
                    System.out.println("5. FILTER");
                    System.out.println("6. MENU\n");
                    String serviceChoice = sc.nextLine().strip().toLowerCase();

                    if (serviceChoice.equals("add") || serviceChoice.equals("1")) {

                        System.out.print("Enter Service Name: ");
                        String serviceName = sc.nextLine();

                        System.out.print("Enter UserName: ");
                        String userName = sc.nextLine();

                        System.out.print("Enter Password: ");
                        String password = sc.nextLine();

                        System.out.print("Enter Link / URL: ");
                        String url = sc.nextLine();

                        service.insertData(connection, serviceName, userName, password, url);

                    } else if (serviceChoice.equals("delete") || serviceChoice.equals("2")) {

                        System.out.print("\nEnter the Keyword: ");
                        String key = sc.nextLine().strip();

                        boolean found = service.filterData(connection, key, "name") ||
                                service.filterData(connection, key, "username") ||
                                service.filterData(connection, key, "url");

                        if (!found) {
                            System.err.println("Please Enter valid search keyword");
                            continue;
                        }

                        System.out.print("\nEnter the id you wanna delete: ");
                        String id = sc.nextLine().strip();
                        Integer delId;

                        try {
                            delId = Integer.parseInt(id);
                        } catch (Exception e) {
                            System.err.println(e);
                            continue;
                        }

                        service.selectSingleRow(connection, delId);
                        System.out.println("Are you sure you wanna delete the above service? (y / n)");

                        String yesOrNo = sc.nextLine().toLowerCase();

                        if (yesOrNo.equals("y") || yesOrNo.equals("yes")) {
                            service.deleteData(connection, delId);
                        } else {
                            System.out.println("\nDeletion unsuccessfull!");
                        }

                    } else if (serviceChoice.equals("view") || serviceChoice.equals("3")) {

                        service.showData(connection);

                    } else if (serviceChoice.equals("update") || serviceChoice.equals("4")) {

                        System.out.print("\nEnter the Keyword: ");
                        String key = sc.nextLine().strip();

                        boolean found = service.filterData(connection, key, "name") ||
                                service.filterData(connection, key, "username") ||
                                service.filterData(connection, key, "url");

                        if (!found) {
                            System.err.println("Please Enter valid search keyword");
                            continue;
                        }

                        System.out.print("\nEnter the id you wanna Update: ");
                        String id = sc.nextLine().strip();
                        Integer updateId;

                        try {
                            updateId = Integer.parseInt(id);
                        } catch (Exception e) {
                            System.err.println(e);
                            continue;
                        }

                        service.selectSingleRow(connection, updateId);
                        System.out.println("Are you sure you wanna Update the above service? (y / n)");

                        String yesOrNo = sc.nextLine().toLowerCase();

                        if (yesOrNo.equals("y") || yesOrNo.equals("yes")) {
                            System.out.println("Fill only the feilds which needs to be updated!, else leave empty");

                            System.out.print("Enter Service Name: ");
                            String serviceName = sc.nextLine().strip();

                            System.out.print("Enter UserName: ");
                            String userName = sc.nextLine().strip();

                            System.out.print("Enter Password: ");
                            String password = sc.nextLine().strip();

                            System.out.print("Enter Link / URL: ");
                            String url = sc.nextLine().strip();

                            service.updateData(connection, updateId, serviceName, userName, password, url);
                        } else {
                            System.out.println("\nUpdation unsuccessfull!");
                        }

                    } else if (serviceChoice.equals("filter") || serviceChoice.equals("5")) {

                        System.out.print("Enter the keyword: ");
                        String key = sc.nextLine().strip();

                        System.out.println("Filter by Name: ");
                        service.filterData(connection, key, "name");

                        System.out.println("Filter by username: ");
                        service.filterData(connection, key, "username");

                        System.out.println("Filter by url: ");
                        service.filterData(connection, key, "url");

                    } else if (serviceChoice.equals("menu") || serviceChoice.equals("6")) {

                        System.out.println("\nRedirecting to MAIN MENU...\n");
                        break;

                    } else {

                        System.out.println("Please Enter a valid choice");
                        isNotValidChoice = true;

                    }

                } while (isNotValidChoice);
            } else if (choice.toLowerCase().equals("exit")) {
                userWantsToContinue = false;
                try {
                    connection.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
                System.out.println("Exited application!");
            } else {
                System.out.println("Please Enter a valid choice.");
                System.out.println("\"CONTACT\" / \"SERVICE\" / \"EXIT\"");
            }
        } while (userWantsToContinue);
        sc.close();
    }
}
