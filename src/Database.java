import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Database {
    public static void createUserTable() throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS credentials(id int NOT NULL AUTO_INCREMENT, email varchar(255) , password varchar(255), name varchar (255), PRIMARY KEY(id))");
            create.executeUpdate();
            create.close();
        }
        catch (Exception e){System.out.println(e);}
        finally {
            System.out.println("Function Complete");
            }
    }

    public static Connection getConnection() throws Exception {

        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/ProjectManager";
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


    public static boolean addUser(String email, String password, String name) throws Exception {

        Connection con = getConnection();
        try{
            PreparedStatement checkExist = con.prepareStatement("select * from credentials where email = ?");
            checkExist.setString(1,email);
            ResultSet rs = checkExist.executeQuery();
            if(rs.next() == true){
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
        try {
            PreparedStatement create = con.prepareStatement("INSERT INTO credentials(email, password, name) VALUES('" + email + "', '" + password + "','" + name + "')");
            create.executeUpdate();

        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("Insert Complete");
            return true;
        }


    }

    public static ArrayList<Project> getUserData(String email){
        return null;
    }
}
