
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class ReadFile {
	static BufferedReader br = null;
	static int count = 1;
	static JSONObject jsonObject1 = new JSONObject();
	static JSONObject jsonObject2 = new JSONObject();
	static JSONObject jsonObject3 = new JSONObject();
	static JSONArray array = new JSONArray();
	public static void getDetails(Connection con) throws SQLException, IOException {
		PreparedStatement preparedStatement=con.prepareStatement("SELECT * FROM avrtable");
		ResultSet result=preparedStatement.executeQuery();
		while(result.next()) {
			String sequence=result.getString(1)+"s";
			String cputime=result.getString(2);
			
			jsonObject2.put(sequence,cputime);
		}
		jsonObject1.put("values",jsonObject2);
		PreparedStatement preparedStatement2=con.prepareStatement("SELECT max(value),avg(value) FROM avrtable");
		ResultSet result1=preparedStatement2.executeQuery();
		while(result1.next()) {
			float max=result1.getFloat(1);
			float avg=result1.getFloat(2);
			jsonObject1.put("max_cpu_value",max);
			jsonObject1.put("avg_cpu_value",avg);
			
		}
		jsonObject3.put("sampletransaction",jsonObject1);
		array.add(jsonObject3);
		
	}

    

	public static void main(String[] args) throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/avrdb?autoReconnect=true","root","root");
		Statement s= con.createStatement();
        //String sql1 = "CREATE TABLE mytable " +"(no VARCHAR(50) not NULL, " + " value  FLOAT)";
	    //s.executeUpdate(sql1);
		try {
			String line;
			br = new BufferedReader(new FileReader("C:\\Users\\vishalraj\\Downloads\\Reader\\sampleinput.txt"));
			while ((line = br.readLine()) != null) {
			StringTokenizer stringTokenizer = new StringTokenizer(line, " ");

				while (stringTokenizer.hasMoreElements()) {

					int x=0;
					while(x<8) {
						stringTokenizer.nextElement().toString();
						x++;
					}
					
//					required line
					Double reqCPU = Double.parseDouble(stringTokenizer.nextElement().toString());
					while(x<11) {
						stringTokenizer.nextElement().toString();
						x++;
					}
					 s.executeUpdate("insert into avrtable (no,value) values ("+count+","+reqCPU+")");
					count++;
					
				}
			}
			getDetails(con);
			
	
		}
			catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
