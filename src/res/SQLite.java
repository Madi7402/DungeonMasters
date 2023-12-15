package res;

import org.sqlite.SQLiteDataSource;
import java.sql.*;

/**
 * Handle the SQLite connection processes.
 */
public class SQLite implements AutoCloseable {
    private final Connection myConnection;
    private final ResultSet myResults;

    public SQLite(String theStatement) throws SQLException {
        SQLiteDataSource myDataSource = new SQLiteDataSource();
        myDataSource.setUrl("jdbc:sqlite:database.sqlite.db"); // TODO -JA: Make this a field? Constructor Parameter?
        this.myConnection = myDataSource.getConnection();
        this.myResults = makeQuery(theStatement); // TODO -JA: Avoid SQli?
    }

    /**
     * Make a query given the provided query.
     * @param theQuery the SQL query as a string
     * @return the ResultSet or null if the query failed.
     */
    private ResultSet makeQuery(String theQuery) throws SQLException {
        Statement statement = myConnection.createStatement();
        try {
            return statement.executeQuery(theQuery);
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    /**
     * Get the ResultSet of this SQLite Object's query.
     * @return the ResultSet of the query
     */
    public ResultSet getMyResults() {
        return myResults;
    }

    /**
     * Close the Database connections, AutoClosable implementation (try-with resources).
     */
    public void close() {
        try {
            if (myResults != null && !myResults.isClosed()) {
                myResults.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (myConnection != null && !myConnection.isClosed()) {
                myConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
