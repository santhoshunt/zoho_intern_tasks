package digitalvault;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ContactDAOImpl implements DAO {

    static final String SHOW = "SELECT * FROM contact";
    static final String INSERT = "INSERT INTO contact (name, phno, email, job, age, dob) VALUES (?, ?, ?, ?, ?, ?)";
    static final String DELETE = "DELETE FROM contact WHERE id=?";
    static final String SELECTSINGLE = "SELECT * FROM  contact where id=?";

    @Override
    public void insertData(Connection con, String... args) {
        try {
            String name = args[0];
            String phoneNumber = args[1];
            String email = args[2];
            String job = args[3];
            int age = Integer.parseInt(args[4]);
            String dob = args[5];
            PreparedStatement pstm = con.prepareStatement(INSERT);
            pstm.setString(1, name);
            pstm.setString(2, phoneNumber);
            pstm.setString(3, email);
            pstm.setString(4, job);
            pstm.setInt(5, age);
            pstm.setString(6, dob);
            if (pstm.executeUpdate() == 1) {
                System.out.println("Insertion successfull!\n");
                pstm.close();
                return;
            }
            System.err.println("Instertion not successfull. Please try again\n");
            pstm.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void deleteData(Connection con, int delId) {
        try {
            PreparedStatement pstm = con.prepareStatement(DELETE);
            pstm.setInt(1, delId);
            if (pstm.executeUpdate() == 1) {
                System.out.println("Delete Success!");
            } else {
                System.out.println("Deletion failed");
            }
            pstm.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @Override
    public boolean selectSingleRow(Connection con, int searchID) {
        try {
            PreparedStatement pstm = con.prepareStatement(SELECTSINGLE);
            pstm.setInt(1, searchID);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String phoneNumber = rs.getString(3);
                String email = rs.getString(4);
                String job = rs.getString(5);
                int age = rs.getInt(6);
                String dob = rs.getString(7);
                System.out.printf(
                        "%5s %20s %15s %25s %30s %5s %10s\n",
                        "" + id, name, phoneNumber, email, job, "" + age, dob);
                pstm.close();
                return true;
            }
            pstm.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return false;
    }

    @Override
    public void updateData(Connection con, int updateId, String... args) {
        try {
            String name = args[0];
            String phoneNumber = args[1];
            String email = args[2];
            String job = args[3];
            int age = Integer.parseInt(args[4] == "" ? "-1" : args[4]);
            String dob = args[5];
            StringBuilder updateSQL = new StringBuilder("UPDATE contact SET");

            if (!name.equals("")) {
                updateSQL.append(" name='");
                updateSQL.append(name);
                updateSQL.append("'");
            }

            if (!phoneNumber.equals("")) {
                updateSQL.append(" phno='");
                updateSQL.append(phoneNumber);
                updateSQL.append("'");
            }

            if (!email.equals("")) {
                updateSQL.append(" email='");
                updateSQL.append(email);
                updateSQL.append("'");
            }

            if (!job.equals("")) {
                updateSQL.append(" job='");
                updateSQL.append(job);
                updateSQL.append("'");
            }

            if (age != -1) {
                updateSQL.append(" age=");
                updateSQL.append("" + age);
                updateSQL.append("");
            }

            if (!dob.equals("")) {
                updateSQL.append(" dob='");
                updateSQL.append(dob);
                updateSQL.append("'");
            }

            if (updateSQL.toString().endsWith("SET")) {
                System.out.println("\nNo values to update!\n");
            } else {
                updateSQL.append(" WHERE id=");
                updateSQL.append("" + updateId);
                Statement statement = con.createStatement();
                if (statement.executeUpdate(updateSQL.toString()) == 1) {
                    System.out.println("Updation Successfull!\n");
                } else {
                    System.err.println("Updation failed! Please try again\n");
                }
                statement.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void showData(Connection con) {
        try {
            PreparedStatement pstm = con.prepareStatement(SHOW);
            ResultSet rs = pstm.executeQuery();

            System.out.printf(
                    "%5s %20s %15s %30s %35s %5s %10s\n",
                    "id", "name", "phoneNumber", "email", "job", "age", "dob");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String phoneNumber = rs.getString(3);
                String email = rs.getString(4);
                String job = rs.getString(5);
                int age = rs.getInt(6);
                String dob = rs.getString(7);
                System.out.printf(
                        "%5s %20s %15s %25s %30s %5s %10s\n",
                        "" + id, name, phoneNumber, email, job, "" + age, dob);
            }
            pstm.close();
            System.out.println();
        } catch (Exception e) {
            System.err.println(e);
            System.out.println(e.getStackTrace().toString());
        }

    }

    @Override
    public boolean filterData(Connection con, String key, String field) {
        try {
            String filter = "SELECT * FROM contact WHERE " + field + " LIKE CONCAT( '%',?,'%')";
            PreparedStatement pstm = con.prepareStatement(filter);
            pstm.setString(1, key);
            // System.out.println(pstm);
            boolean resultFound = false;
            ResultSet rs = pstm.executeQuery();

            // System.out.printf(
            // "%5s %20s %15s %25s %30s %5s %10s\n",
            // "id", "name", "phoneNumber", "email", "job", "age", "dob");

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String phoneNumber = rs.getString(3);
                String email = rs.getString(4);
                String job = rs.getString(5);
                int age = rs.getInt(6);
                String dob = rs.getString(7);
                resultFound = true;
                System.out.printf(
                        "%5d %20s %15s %25s %30s %5d %10s\n",
                        id, name, phoneNumber, email, job, age, dob);
            }
            if (!resultFound) {
                System.out.println("\nYour search yielded no results!\nplease try with different keywords\n");
                pstm.close();
            }
            System.out.println();
            pstm.close();
            return resultFound;
        } catch (Exception e) {
            System.err.println(e);
        }

        return false;
    }

}
