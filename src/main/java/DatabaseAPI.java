import java.sql.*;
import java.util.ArrayList;

public class DatabaseAPI {

    public Connection connection;

    DatabaseAPI(){
        // This will load the MySQL driver, each DB has its own driver

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Setup the connection with the DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/firesafetydb", "firesafetyuser", "cyvhy+Se*P4/F7zW");;
        }catch(Exception e){
            e.printStackTrace();
            connection = null;
            System.out.println("Error opening database connection");
        }
    }

    public ArrayList<Problem> getProblems() throws SQLException {

        ArrayList<Problem> problems = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("Select problem_id, response, problem_type from problem");
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int id = rs.getInt(1);
            String response = rs.getString(2);
            int type = rs.getInt(3);

            problems.add(getConditions(id, response, type));
        }

        return problems;
    }

    public Problem getConditions(int problem_id, String response, int type) throws SQLException{
        Problem prob = new Problem(response, type);

        PreparedStatement ps = connection.prepareStatement("Select concept, status from `condition` where problem_id = ?");
        ps.setInt(1, problem_id);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String concept = rs.getString(1);
            int status = rs.getInt(2);
            prob.addCondition(new Condition(concept, status));
        }

        return prob;
    }


    public static void main(String[] args) {
        try{
            DatabaseAPI dapi = new DatabaseAPI();
            System.out.println(dapi.getProblems());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
