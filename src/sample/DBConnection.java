
package sample;

import java.sql.*;


// This class is connecting us to mySql DataBase

public class DBConnection {
    public Connection connection;

    //if you want to start the app on your system you must use your mySql data base. just change name, user name and password.
    public Connection getConnection() throws Exception {
        String dbName = "data_base"; //
        String userName = "root";
        String password = "password";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName+"?autoReconnect=true&useSSL=false", userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void CreateTable() throws Exception {
        try {
            Connection conn = getConnection();
            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS items(id INT NOT NULL AUTO_INCREMENT,code INT,name varchar(255), prize DOUBLE, PRIMARY KEY(id))");
            create.executeUpdate();
            create = conn.prepareStatement("INSERT INTO items (code, name, prize) VALUES " +
                    "(111,'milk',2.30)," +
                    "(222,'chips',3.40)," +
                    "(333,'roll',0.50)," +
                    "(444,'beer',2.20)," +
                    "(555,'bread',3.60)," +
                    "(666,'chocolate',7.30)," +
                    "(777,'water',2.40)," +
                    "(888,'ticket',1.20)");
            create.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Table has been created");
        }
    }

    public void DeleteTable() throws Exception {
        try {
            Connection conn = getConnection();
            PreparedStatement delete = conn.prepareStatement("DROP TABLE items");
            delete.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Table has been deleted");
        }


    }

    String SearchName(String barCode) throws Exception {

        String name="";
        try {
            Connection conn = getConnection();
            Statement search = conn.createStatement();
            ResultSet rs = search.executeQuery("SELECT * FROM items WHERE code=" + barCode);
            rs.next();
            name = rs.getString("name");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Item has been found");
        }
        return name;


    }

    double SearchPrize(String barCode) throws Exception {
        double prize=0.0;
        try {
            Connection conn = getConnection();
            Statement search = conn.createStatement();
            ResultSet rs = search.executeQuery("SELECT * FROM items WHERE code=" + barCode);
            rs.next();
            prize = rs.getDouble("prize");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Item has been found");
        }
        return prize;
    }

    boolean CodeISExists(String barCode) throws Exception{
        Connection conn = getConnection();
        Statement search = conn.createStatement();
        ResultSet rs = search.executeQuery("SELECT * FROM items WHERE code=" + barCode);
        if(!rs.isBeforeFirst()){
            return false;
        }
        else{
            return true;
        }
    }
}

