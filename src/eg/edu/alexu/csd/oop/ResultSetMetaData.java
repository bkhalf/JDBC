package eg.edu.alexu.csd.oop;

import java.sql.SQLException;
import java.sql.Types;
import java.sql.Types.*;

public class ResultSetMetaData implements java.sql.ResultSetMetaData{

	
	private static ResultSetMetaData instance = new ResultSetMetaData();
	private Object[][] Result = null;
	private String[] cols_names = null;
	private String table_name=null;
	/**********************************Singleton Design Pattern*****************************************/
	
	private ResultSetMetaData() {}
	
	public static ResultSetMetaData get_instance() {
		return instance;
	}
	
	/************************************************************************************/
	public void set_Result(Object[][] x,String[] cols_names,Statement y) {
		Result = x;
		this.cols_names = cols_names; 
		table_name = y.get_table_name();
	}
	/**********************************************************************************************************/
	
	
	@Override
	public int getColumnCount() throws SQLException {
		return Result[0].length;
	}


	@Override
	public String getColumnLabel(int column) throws SQLException {
		return (String) Result[0][column];
	}


	@Override
	public String getColumnName(int column) throws SQLException {
		return (String) Result[0][column];
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		if(Result[1][column] instanceof Integer) {
			return Types.INTEGER;
		}
		return Types.LONGNVARCHAR;
	}

	@Override
	public String getTableName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return table_name;
	}

	
	
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
	public String getCatalogName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public String getColumnTypeName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScale(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int isNullable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
