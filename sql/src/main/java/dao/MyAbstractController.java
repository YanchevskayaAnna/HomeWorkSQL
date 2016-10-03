package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sf on 03.10.16.
 */
public interface MyAbstractController <E,K> {

    List<E> getAll();
    E getEntityById( K id);
    boolean update(E entity);
    boolean create(E entity);

    default boolean delete(Connection connection, String tableName, int id){
        String sqlQuery = "DELETE FROM " + tableName + " WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
