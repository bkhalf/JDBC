package eg.edu.alexu.csd.oop;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public  class ConnectionManager {
	private Hashtable<Connection, Long> lock = new Hashtable<Connection, Long>(),unlock = new Hashtable<Connection, Long>();
	private final long deadTime = 5000;
	/***********************************Singletoon Design Pattern************************************/
	private static ConnectionManager instance = new ConnectionManager();
	
	private ConnectionManager() {}
	
	public static ConnectionManager get_instance() {
		return instance;
	}
	/*******************************************************************************************/
		public Connection getConnection (String path) {
		
			long now = System.currentTimeMillis(); 
	        Connection c;
			if (unlock.size() > 0) { 
	            Enumeration<Connection> e = unlock.keys(); 
	            while (e.hasMoreElements()) { 
	                c  = e.nextElement(); 
	                if ((now - unlock.get(c)) > deadTime) { 
	                    unlock.remove(c); 
	                    try {
							c.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
	                    c = null; 
	                } 
	                else { 
	                  
	                        unlock.remove(c); 
	                        lock.put(c, now); 
	                        return c; 
	                } 
	            } 
	        } 
	        // no objects available, create a new one 
	        c = new Connection(); 
	        lock.put(c, now); 
	        return c; 
	    
			
		}
		
		
		
		
		
		
	
		
		
		
		
		
		
	}