package sample.util;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;


public class DBUtil {

    //Declare JDBC Driver
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    //Connection
    private static Connection con = null;
    //step2 create  the connection object
    //Connect to DB
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        //Setting Oracle JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            throw e;
        }
        System.out.println("Oracle JDBC Driver Registered!");
        //Establish the Oracle Connection using Connection String
        try {
            con = DriverManager.getConnection(





                    "jdbc:oracle:thin:@localhost:1521:orcl","system","Oracle_1");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console  \n" + e);
            e.printStackTrace();
            throw e;
        }
    }
    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Disconnected Database!");
            }
        } catch (Exception e){
            throw e;
        }
    }

    //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            System.out.println(queryStmt );
            //Create statement
            stmt = con.createStatement();

            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);

            //        while(resultSet.next())
            //         System.out.println(resultSet.getString(1)+"  "+resultSet.getString(2)+
            //               "  "+resultSet.getString(3)+"  "+resultSet.getString(4)
            //             +"  "+resultSet.getString(5)+"  "+resultSet.getString(6));

            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return crs;
    }

    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = con.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {

                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }

    public static ResultSet dbExecuteStatement(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;

        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            System.out.println(queryStmt );
            //Create statement
            stmt = con.createStatement();

            //Execute select (query) operation
            stmt.execute(queryStmt);

            //        while(resultSet.next())
            //         System.out.println(resultSet.getString(1)+"  "+resultSet.getString(2)+
            //               "  "+resultSet.getString(3)+"  "+resultSet.getString(4)
            //             +"  "+resultSet.getString(5)+"  "+resultSet.getString(6));

            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return crs;
    }

}





