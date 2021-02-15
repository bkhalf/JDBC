package eg.edu.alexu.csd.oop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

import eg.edu.alexu.csd.oop.db.Check;
import eg.edu.alexu.csd.oop.db.DB;
import eg.edu.alexu.csd.oop.db.Facade;
import eg.edu.alexu.csd.oop.db.Reload_DATA;

public class Statement implements java.sql.Statement {
	private Timer timer = new Timer(true);
	private boolean timeout =false;
	private int waitTimeout =0;
	private InterruptTimerTask interruptTimerTask = new InterruptTimerTask(Thread.currentThread());
	private String table_name = null ;
	private DB db=DB.get_instance();
	private Stack<String> sql_list=new Stack<String>();
	public void reload() {
		Reload_DATA ourinput = new Reload_DATA();
		try {
			ourinput.reload();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public String get_table_name() {
		return table_name;
	}
	public void set_table_name(String r) {
		table_name=r;
	} 
	@Override
	public void addBatch(String sql) throws SQLException {
		// TODO Auto-generated method stub
		
	 Check ch = Check.get_instance();
	 String First_Word = sql.substring(0, sql.indexOf(" "));
		String [] s = new String [100] ;
		switch(First_Word) {
		case "drop": s[0]=ch.dropscheck(sql);
			break;
		case "select":s = ch.selectcheck(sql);
			set_table_name(s[0]);
			break;
		case "delete": s= ch.deletecheck(sql);
			break;
		case "insert": s = ch.insertcheck(sql);
			break;
		case "update":s = ch.updatecheck(sql);
			break;
		case "create": s=ch.createcheck(sql);
			break;	
		default: s=null;
		    break;
		}	
		if ( s != null) {
			sql_list.push(sql);
		}
		
	}


	@Override
	public void clearBatch() throws SQLException {
		// TODO Auto-generated method stub
		sql_list.clear();
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		Facade f=new Facade();
		if(waitTimeout==0) {
			try {
				if(sql.toLowerCase().contains("create")||sql.toLowerCase().contains("drop")) {
					boolean n =db.executeStructureQuery(sql);
					//System.out.println("mmm"+n);
					return n;
				}
				f.do_query(sql);
				timeout = false ;
				return true;
			}catch (SQLException e) {
				throw new SQLException();
			}
		}
		else {
		timer.schedule(interruptTimerTask, waitTimeout);
		
		try {
			f.do_query(sql);
			timeout = false ;
			return true;
		}catch (SQLException e) {
			return false;
		}
		catch (Exception e) {
			timeout = true;
			
		}
		finally {
		    timer.cancel();
		}
		return false;
		}
	}

	

	@Override
	public int[] executeBatch() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		
		if(waitTimeout==0) {
			
			eg.edu.alexu.csd.oop.ResultSet r =  new eg.edu.alexu.csd.oop.ResultSet();
			Object[][] b= db.executeQuery(sql);
				r.set_Result(b,db.get_cols_names(),this);
			//	System.out.println("array without islam yousry");
				for(int i = 0;i < b.length ; i++) {
					for(int j = 0 ; j < b[0].length;j++) {
					//	System.out.print(b[i][j]+"                  ");
					}
					//System.out.println(" ");

				}
			return r;
		}
		else {
			timer.schedule(interruptTimerTask, waitTimeout);
			try {
				
				eg.edu.alexu.csd.oop.ResultSet r =  new eg.edu.alexu.csd.oop.ResultSet();
			r.set_Result(db.executeQuery(sql),db.get_cols_names(),this);
			timeout = false ;
		
				return r;
			} catch(Exception e) {
				timeout = true;
			}finally {
			    timer.cancel();
			}
			return null;
		}
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		if(waitTimeout==0) {
			timeout = false ;
			int h=db.executeUpdateQuery(sql);
//			System.out.println("here is our h"+h);
			return h;
		}
		else {
		timer.schedule(interruptTimerTask, waitTimeout);
		// TODO Auto-generated method stub
		try {
			timeout = false ;
			return db.executeUpdateQuery(sql);
			
		} catch (Exception e) {
			timeout = true;
		}finally {
		    timer.cancel();
		}
		return 0;
		}
		
	}

	

	@Override
	public int getQueryTimeout() throws SQLException {
		return waitTimeout;
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		waitTimeout =seconds;
		if (timeout == true) {
			SQLException s = new SQLException();
			throw s;
		}
		
	}
	/**************************Unimplemented *****************************************************************/


	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void cancel() throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getMaxFieldSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean getMoreResults() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getResultSetConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getResultSetType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setCursorName(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setMaxRows(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
