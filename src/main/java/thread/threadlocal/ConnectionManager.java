package thread.threadlocal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*
 * ThreadLocal 用于每一个线程都有一个对象的副本。“以空间换时间”
 * 内存泄漏？-->弱引用：WeakReference
 * String str = "xxx";
 * 
 */

public class ConnectionManager {

	
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	private ConnectionManager(){}
	
	public static Connection getInstance(){
		try {
			Connection conn = threadLocal.get();
			if(conn == null){
				conn = DriverManager.getConnection("","","");
				threadLocal.set(conn);
			}
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return null;
	}
	
}
