import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    public static void createUserTable() throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS credentials(email varchar(255) , password varchar(255), name varchar (255), PRIMARY KEY(email))");
            create.executeUpdate();
            create.close();
        }
        catch (Exception e){System.out.println(e);}
        finally {
            System.out.println("Function Complete");
            };
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
        String var1= email;
        String var2 = password;
        String var3 = name;
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
            PreparedStatement create = con.prepareStatement("INSERT INTO credentials(email, password, name) VALUES('" + var1 + "', '" + var2 + "','" + var3 + "')");
            create.executeUpdate();
        }
        catch(Exception e){
            return false;
        }
        finally {
            System.out.println("Insert Complete");
            return true;
        }


    }
}
