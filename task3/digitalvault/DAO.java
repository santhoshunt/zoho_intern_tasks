package digitalvault;

import java.sql.Connection;

public interface DAO {

    void insertData(Connection con, String... args);

    void deleteData(Connection con, int delId);

    boolean selectSingleRow(Connection con, int id);

    void updateData(Connection con, int updateId, String... args);

    void showData(Connection con);

    boolean filterData(Connection con, String key, String field);

}