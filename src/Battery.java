import java.io.*;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.nio.file.*;
public class Battery {
	public static void main(String[] args)throws Exception 
	  { 
		String Uid="Uid u0a202";
		String Foreground_activities="Foreground activities";
		float Battery_percentage=0,Battery_drain=0;
		File file = new File("C:\\Users\\vishalraj\\eclipse-workspace\\battery\\Battery.txt"); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String str,str2,str3,Foreground=""; 
	//parsing
		while ((str = br.readLine()) != null) 
		{
			str2=str;
			String[] array = str2.split(":");
			if(array.length>1)
			{ 
				array[0]=array[0].trim();
				if(array[0].equals(Uid)  )
				{
		         str3=array[1];
		         String[] array1 = str3.split("\\(");
		         Battery_drain=Float.parseFloat(array1[0]); 
		         
				}
				if(array[0].equals(Foreground_activities))
				{
                 str3=array[1];
		         String[] array1 = str3.split("\\(r");
		         Foreground=array1[0].trim();
		         
				} 
			}
	   
			  
		}
		Battery_percentage=(Battery_drain/1000);// battery percentage
		
	//writing to json file
		JSONParser parser=new JSONParser();
		JSONObject obj=new JSONObject();
		obj.put("Foreground_time",Foreground);
		obj.put("Battery_drain" ,Battery_drain);
		obj.put("Battery_percentage" ,Battery_percentage);
		FileWriter file1=new FileWriter("C:\\\\Users\\\\vishalraj\\\\eclipse-workspace\\\\battery\\\\Battery.json");
		file1.write(obj.toString());
		file1.flush();
		System.out.println(obj);
		
	  } 
	
}
