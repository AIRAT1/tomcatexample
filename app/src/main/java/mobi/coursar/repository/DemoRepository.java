package mobi.coursar.repository;

import mobi.coursar.exception.DemoException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DemoRepository {
    private final DataSource dataSource;

    public DemoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        try (
                final Connection connection = dataSource.getConnection();
                final Statement tblStatement = connection.createStatement();
                final Statement dataStatement = connection.createStatement();
                ){
            tblStatement.execute("CREATE TABLE IF NOT EXISTS records (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL );");
            dataStatement.execute("INSERT INTO records(name) VALUES ('first');");
        }catch (SQLException e) {
            throw new DemoException(e);
        }
    }

    public List<String> getAll() {
        try (
                final Connection connection = dataSource.getConnection();
                final Statement statement = connection.createStatement();
                final ResultSet resultSet = statement.executeQuery("SELECT * FROM records;");
                ){
                List<String> result = new LinkedList<>();
                while (resultSet.next()) {
                    result.add(resultSet.getString("name"));
                }
                return result;
        }catch (SQLException e) {
            throw new DemoException(e);
        }
    }
}
