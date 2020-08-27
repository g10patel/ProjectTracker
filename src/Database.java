import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {
    public static void createTable() throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS credentials(id int NOT NULL AUTO_INCREMENT, username varchar(255) , password varchar(255), PRIMARY KEY(id))");
            create.executeUpdate();
        }
        catch (Exception e){System.out.println(e);}
        finally {
            System.out.println("Function Complete");
            };
    }

    public static Connection getConnection() throws Exception {

        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/demo";
            String username="jiten";
            String password = "PASSWORD";
            Class.forName(driver);

            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return con;
        }
        catch(Exception e ){
            System.out.println(e);
        }
        return null;
    }

}
