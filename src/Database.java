
import java.sql.*;
import java.util.ArrayList;

public class Database {
    public static void createUserTable() {
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
        PreparedStatement checkExist = con.prepareStatement("select * from credentials where email = ?");
        try{

            checkExist.setString(1,email);
            ResultSet rs = checkExist.executeQuery();
            if(rs.next() == true){
                return false;
            }
        }
        catch(Exception e){
            con.close();
            return false;
        }

        PreparedStatement create = con.prepareStatement("INSERT INTO credentials(email, password, name) VALUES('" + email + "', '" + password + "','" + name + "')");
        try {
            create.executeUpdate();
            addProject(email);
        }
        catch(Exception e){
            con.close();
            System.out.println(e);
            return false;
        }
        finally {
            con.close();
            System.out.println("Insert Complete");
            checkExist.close();
            create.close();
            return true;
        }


    }

    public static boolean checkExists(String email) throws Exception {
        Connection con = getConnection();
        PreparedStatement checkExist = con.prepareStatement("select * from credentials where email = ?");
        try{

            checkExist.setString(1,email);
            ResultSet rs = checkExist.executeQuery();
            if(rs.next() == true){
                con.close();
                return true;
            }
        }
        catch(Exception e){
            con.close();
            return false;
        }
        finally
        {
            con.close();
            return true;
        }
    }


    public static boolean addProject(String email){
        int id = getID(email);
        try{
          Connection con = getConnection();
          String tablename = "p"+ id;
          String statement = "CREATE TABLE "+tablename+" (projectName varchar (255),taskOrder int , task varchar(255), description MEDIUMTEXT)";
          PreparedStatement addProject = con.prepareStatement(statement);
          addProject.executeUpdate();
          addProject.close();
        }
        catch (Exception e){

            System.out.println(e);
            return false;
        }

        return true;
    }

    public static ArrayList<Project> getUserData(String email){
        int id = getID(email);
        try{
            Connection con = getConnection();
            String query = "SELECT * FROM " +"p"+ id+" ORDER BY projectname DESC";
            PreparedStatement getProjects = con.prepareStatement(query);
            ResultSet result = getProjects.executeQuery();
            ArrayList<Task> tasks = new ArrayList<>();
            while(result.next()){

                Task newTask = new Task(result.getString("projectName"),result.getInt("taskOrder"),result.getString("task"),result.getString("description"));
                tasks.add(newTask);
            }
            ArrayList<Project> projects = Project.getProjects(tasks);
            return projects;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private static int getID(String email) {
        int id = 0;
        try {
            Connection con = getConnection();
            PreparedStatement find = con.prepareStatement("SELECT id FROM credentials WHERE email = ?");
            find.setString(1, email);
            ResultSet result = find.executeQuery();

            while (result.next()) {
                id = result.getInt("id");
            }
            find.close();
        }
        catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return id;
    }
}
