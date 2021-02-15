package eg.edu.alexu.csd.oop;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Run {

	public static void main(String[] args) throws SQLException {
		System.out.println("********************** Welcome To JDBS ***********************");
		System.out.print("Creating Driver....");
		try {
			Thread.sleep(2000);
			System.out.println(".. Driver Created");
			Thread.sleep(500);
			System.out.print("Creating Connection....");
			Thread.sleep(2000);
			System.out.println(".. Connection Created");
			Thread.sleep(500);
			System.out.print("Creating Statement....");
			Thread.sleep(2000);
			System.out.println(".. Statement Created ");
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Driver d = new Driver();
		Properties info = new Properties();
		File dbDir = new File("D:\\DBMS\\databases");
		info.put("path", dbDir.getAbsoluteFile());
		Connection c = d.connect("jdbc:xmldb://localhost", info);
		Statement s=c.createStatement();
		s.reload();
		while (true) {
		System.out.println("Enter Your Query TO execute it  or write (end) to exit :");
		Scanner sc = new Scanner(System.in);
		String sql = new String();
		java.sql.ResultSet r = new ResultSet();
		sql = sc.nextLine();
		if(sql.equalsIgnoreCase("end")) {
			System.out.println("Ending Program...");
			try {
				Thread.sleep(500);
				System.out.print("Closing Statement..");
				Thread.sleep(1000);
				System.out.println(".. Statement Closed");
				Thread.sleep(500);
				System.out.print("Closing Connection....");
				Thread.sleep(1000);
				System.out.println(".. Connection closed");
				Thread.sleep(500);
				System.out.println("..JDBC is Closed");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("********************* see you soon *************************");
			break;
		}
		
		
		try {
			
			if(sql.toLowerCase().contains("create") || sql.toLowerCase().contains("drop")) {
				s.addBatch(sql);
				long start = System.currentTimeMillis();
				s.execute(sql);
				 System.out.println("Execution time in ms = "+(System.currentTimeMillis()-start));
			}
			else if(sql.toLowerCase().contains("insert") || sql.toLowerCase().contains("update")|| sql.toLowerCase().contains("delete"))  {
				 s.addBatch(sql);
				 long start = System.currentTimeMillis();
				s.executeUpdate(sql);
				System.out.println("Execution time in ms = "+(System.currentTimeMillis()-start));
			}
			else if(sql.toLowerCase().contains("select") ) {
				 s.addBatch(sql);
				 long start = System.currentTimeMillis();
				r=s.executeQuery(sql);
				System.out.println("Execution time in ms = "+(System.currentTimeMillis()-start));
			}
			else {
				System.err.println("Invalid Input");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		}
			
	}

}
