package digitalvault;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServiceDAOImpl implements DAO {

    static final String INSERTSERVICE = "INSERT INTO service (name, username, password) VALUES (?, ?, ?)";
    static final String INSERTURL = "INSERT INTO url(name, url) VALUES (?, ?) ON CONFLICT (name) DO NOTHING";
    static final String DELETE = "DELETE FROM service where id=?";
    static final String SELECTROW = "SELECT * FROM service JOIN url ON service.name = url.name WHERE service.id=?";
    static final String SELECTALL = "SELECT * FROM service JOIN url ON service.name = url.name";

    @Override
    public void insertData(Connection con, String... args) {

        String serviceName = args[0];
        String userName = args[1];
        String password = args[2];
        String url = args[3];

        try {
            PreparedStatement pstm1 = con.prepareStatement(INSERTSERVICE);
            PreparedStatement pstm2 = con.prepareStatement(INSERTURL);

            pstm1.setString(1, serviceName);
            pstm1.setString(2, userName);
            pstm1.setString(3, password);

            pstm2.setString(1, serviceName);
            pstm2.setString(2, url);

            if (pstm2.executeUpdate() == 1 && pstm1.executeUpdate() == 1) {
                System.out.println("\nInsertion successfull!\n");
            } else {
                System.err.println("\nInsertion not successfull!\n");
            }

            pstm1.close();
            pstm2.close();
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
                System.out.println("\nDeletion successfull!\n");
            } else {
                System.err.println("\nDeletion not successfull!\n");
            }
            pstm.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public boolean selectSingleRow(Connection con, int id) {
        boolean resultFound = false;
        try {
            PreparedStatement pstm = con.prepareStatement(SELECTROW);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int serviceId = rs.getInt(1);
                String serviceName = rs.getString(2);
                String userName = rs.getString(3);
                String password = rs.getString(4);
                String url = rs.getString(7);
                resultFound = true;
                System.out.println(serviceId + " " + serviceName + " " + userName + " " + password + " " + url);
            }
            rs.close();
            pstm.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return resultFound;
    }

    @Override
    public void updateData(Connection con, int updateId, String... args) {
        // String updateService = "UPDATE "
        try {
            Statement st = con.createStatement();
            String serviceName = args[0];
            String userName = args[1];
            String password = args[2];
            String url = args[3];
            if (!serviceName.equals("") || !url.equals("")) {
                StringBuilder res = new StringBuilder("UPDATE url SET");
                if (!serviceName.equals("")) {
                    res.append(" name='");
                    res.append(serviceName + "'");
                }

                if (!url.equals("")) {
                    res.append(" url='");
                    res.append(url + "'");
                }

                if (!res.toString().endsWith("SET")) {
                    res.append(" where id=" + updateId);
                    if (st.executeUpdate(res.toString()) == 1) {
                        System.out.println("\nUpdation successfull!");
                    } else {
                        System.out.println("\nUpdation not successfull!");
                    }
                }
            }

            if (!serviceName.equals("") || !userName.equals("") || !password.equals("")) {
                StringBuilder res = new StringBuilder("UPDATE service SET");
                if (!serviceName.equals("")) {
                    res.append(" name='");
                    res.append(serviceName + "'");
                }

                if (!password.equals("")) {
                    res.append(" password='");
                    res.append(password + "'");
                }

                if (!userName.equals("")) {
                    res.append(" username='");
                    res.append(userName + "'");
                }

                if (!res.toString().endsWith("SET")) {
                    res.append(" where id=" + updateId);
                    if (st.executeUpdate(res.toString()) == 1) {
                        System.out.println("\nUpdation successfull!");
                    } else {
                        System.out.println("\nUpdation not successfull!");
                    }
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @Override
    public void showData(Connection con) {
        try {
            PreparedStatement pstm = con.prepareStatement(SELECTALL);
            ResultSet rs = pstm.executeQuery();
            System.out.printf("%10s %20s %25s %20s %30s\n",
                    "serviceId", "serviceName", "userName", "password", "url");
            while (rs.next()) {
                int serviceId = rs.getInt(1);
                String serviceName = rs.getString(2);
                String userName = rs.getString(3);
                String password = rs.getString(4);
                String url = rs.getString(7);
                System.out.printf("%10d %20s %25s %20s %30s\n",
                        serviceId, serviceName, userName, password, url);
            }
            rs.close();
            pstm.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public boolean filterData(Connection con, String key, String field) {
        key = key.toLowerCase();
        field = field.toLowerCase();
        StringBuilder filterSQL = new StringBuilder("SELECT * FROM service JOIN url ON service.name=url.name where ");
        if (field.equals("url")) {
            filterSQL.append("url.url LIKE '%");
            filterSQL.append(key + "%'");
        } else {
            filterSQL.append("service.");
            filterSQL.append(field + " LIKE '%");
            filterSQL.append(key + "%'");
        }
        boolean resulFound = false;
        try {
            PreparedStatement pstm = con.prepareStatement(filterSQL.toString());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int serviceId = rs.getInt(1);
                String serviceName = rs.getString(2);
                String userName = rs.getString(3);
                String password = rs.getString(4);
                String url = rs.getString(7);
                System.out.printf("%5d %20s %25s %20s %30s\n",
                        serviceId, serviceName, userName, password, url);
                resulFound = true;
            }
            rs.close();
            pstm.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return resulFound;
    }

}
