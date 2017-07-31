package database;

/*数据库连接步骤
* 
1、打开连接：
导入 JDBC 驱动： 只有拥有了驱动程序才可以注册驱动程序完成连接的其他步骤。
注册 JDBC 驱动程序：这一步会导致 JVM 加载所需的驱动程序实现到内存中，因此它可以实现 JDBC 请求。
数据库 URL 制定：创建具有正确格式的地址，指向到要连接的数据库。
需要使用 DriverManager.getConnection() 方法创建一个Connection 对象，它代表与数据库的物理连接

2、执行查询：需要使用类型声明的对象建立并提交一个 SQL 语句到数据库

3、从结果集中提取数据：要求使用适当的关于 ResultSet.getXXX() 方法来检索结果集的数据

4、处理结果集：对得到的结果集进行相关的操作

5、清理环境：需要明确地关闭所有的数据库资源，释放内存
 * 
 * */

import java.sql.*;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import RSA.UseSHA256;


public class DatabaseConnect {
	//JDBC驱动器名称和数据库地址
	static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
	//数据库名称
	static final String DB_URL = "jdbc:mysql://localhost/april";
	//数据库用户和密码
	static final String USER = "root";
	static final String PASSWD = "123456" ; 
	
	public void changeLoginPassword(String username,String password){
//		if(!loginAuthentication(username, password)){
//			JOptionPane.showMessageDialog(null, "原密码不正确", "错误", JOptionPane.WARNING_MESSAGE);
//			return ;
//		}
		Connection conn = null;
		Statement stmt = null;
		String sql = "UPDATE user SET password=? where username=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
			stmt = conn.createStatement();
			PreparedStatement s = conn.prepareStatement(sql);
			UseSHA256 sha = new UseSHA256();
			s.setString(1, password);
			s.setString(2, "admin");
			int rows = s.executeUpdate();
			System.out.println(rows+"行受影响");
			if(rows>0){
				JOptionPane.showMessageDialog(null, "修改成功");
			}
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
		}
		
		
	}
	
	public boolean loginAuthentication(String username,String password){
		System.out.println(password);
		Connection conn = null;
		Statement stmt = null;
		username= "admin";
		String sql = "SELECT * FROM user where username=? and password=?";
		try {
			System.out.println("loginAuthentication");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
			stmt = conn.createStatement();
			PreparedStatement s = conn.prepareStatement(sql);
			s.setString(1, username);
			s.setString(2, password);
			int i = 0;
			ResultSet rs = s.executeQuery();
			while(rs.next()){
				i++;
			}
			
			if(i>0)return true;
			else return false;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
					
			}catch(SQLException e2){
				e2.printStackTrace();
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
		}
		
		return false;		
	}
	
	@SuppressWarnings("unchecked")
	public IdentityHashMap findRecordByURL(String url){
		IdentityHashMap result = new IdentityHashMap();
		Connection conn = null;
		Statement stmt = null;
		String sql = "SELECT * FROM homework where url=?";
		try{
		Class.forName("com.mysql.jdbc.Driver");
		//打开连接
		System.out.println("Connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
		//获得Statement接口对象
		System.out.println("findRecordByURL");
		stmt = conn.createStatement();
		PreparedStatement s = conn.prepareStatement(sql);
		s.setString(1,url);
		//int row = s.executeUpdate();
		int i=0;
		ResultSet rs = s.executeQuery();
		while(rs.next()){
			int id = rs.getInt("id");
			String urlRecord = rs.getString("url");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String usernameANDpwd[] = new String[3];
			usernameANDpwd[0] = urlRecord;
			usernameANDpwd[1] = username;
			usernameANDpwd[2] = password;
			//System.out.println(url+usernameANDpwd[0]+usernameANDpwd[1]);
			result.put(id,usernameANDpwd);
			i++;
		}
		if(i==0)return null;
		stmt.close();
		conn.close();
		
		return result;
		}
		catch (ClassNotFoundException e) {
			// Class.forname错误
			e.printStackTrace();
		} catch (SQLException e) {
			// JDBC操作错误
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
					
			}catch(SQLException e2){
				e2.printStackTrace();
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
		}
		return null;
			
	}
	
	public IdentityHashMap findAllRecord(){
		IdentityHashMap result = new IdentityHashMap();
		Connection conn = null;
		Statement stmt = null;
		String sql = "SELECT * FROM homework";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
			stmt = conn.createStatement();
			PreparedStatement s = conn.prepareStatement(sql);
			
			int i=0;
			ResultSet rs = s.executeQuery();
			while(rs.next()){
				int num = rs.getInt("id");
				String urlRecord = rs.getString("url");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String usernameANDpwd[] = new String[3];
				usernameANDpwd[0] = urlRecord;
				usernameANDpwd[1] = username;
				usernameANDpwd[2] = password;
				//System.out.println(url+usernameANDpwd[0]+usernameANDpwd[1]);
				result.put(num,usernameANDpwd);
				i++;
			}
			if(i==0)return null;
			stmt.close();
			conn.close();
			return result;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
		}
		return null;
	}
	
	public IdentityHashMap findRecordByid(int id){
		IdentityHashMap result = new IdentityHashMap();
		Connection conn = null;
		Statement stmt = null;
		String sql = "SELECT * FROM homework where id=?";
		try{
		Class.forName("com.mysql.jdbc.Driver");
		//打开连接
		System.out.println("Connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
		//获得Statement接口对象
		System.out.println("findRecordByid");
		stmt = conn.createStatement();
		PreparedStatement s = conn.prepareStatement(sql);
		s.setInt(1,id);
		//int row = s.executeUpdate();
		int i=0;
		ResultSet rs = s.executeQuery();
		while(rs.next()){
			int num = rs.getInt("id");
			String urlRecord = rs.getString("url");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String usernameANDpwd[] = new String[3];
			usernameANDpwd[0] = urlRecord;
			usernameANDpwd[1] = username;
			usernameANDpwd[2] = password;
			//System.out.println(url+usernameANDpwd[0]+usernameANDpwd[1]);
			result.put(num,usernameANDpwd);
			i++;
		}
		if(i==0)return null;
		stmt.close();
		conn.close();
		return result;
		}
		catch (ClassNotFoundException e) {
			// Class.forname错误
			e.printStackTrace();
		} catch (SQLException e) {
			// JDBC操作错误
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
		}
		return null;
			
	}
	
	public void dropRecord(int id){
		Connection conn = null;
		Statement stmt = null;
		String sql = "DELETE FROM homework WHERE id=?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("dropRecord");
			conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
			stmt = conn.createStatement();
			PreparedStatement s = conn.prepareStatement(sql);
			s.setInt(1, id);
			
			int rows = s.executeUpdate();
			System.out.println(rows+"行受影响");
			if(rows>0){
				JOptionPane.showMessageDialog(null, "删除成功");
			}

			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
		}
	}
	
	public void updateRecord(int id,String name,String pwd){
		IdentityHashMap result;
		Connection conn = null;
		Statement stmt = null;
		result = findRecordByid(id);
		String sql = "UPDATE homework SET username=?,password=? where id = ?";
		if(result == null){
			JOptionPane.showMessageDialog(null, "找不到噢", "没有", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		else{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
				stmt = conn.createStatement();
				PreparedStatement s = conn.prepareStatement(sql);
				s.setString(1, name);
				//s.setString(2, name);
				s.setString(2, pwd);
				s.setInt(3, id);
				int rows = s.executeUpdate();
				System.out.println(rows+"行受影响");
				if(rows>0){
					JOptionPane.showMessageDialog(null, "修改成功");
				}
				stmt.close();
				conn.close();
				
			} catch (ClassNotFoundException e) {
				// Class.forname错误
				e.printStackTrace();
			} catch (SQLException e) {
				// JDBC操作错误
				e.printStackTrace();
			}finally{
				try{
					if(stmt!=null)
						stmt.close();
				}catch(SQLException e2){
					e2.printStackTrace();
				}
				try{
					if(conn!=null)
						conn.close();
				}catch(SQLException e2){
					e2.printStackTrace();
				}
			}
		}
		
	}
	
	public void addRecord(String url,String name,String pwd) {
		Connection conn = null;
		Statement stmt = null;
		try {
			//注册JDBC驱动
			Class.forName("com.mysql.jdbc.Driver");
			//打开连接
			System.out.println("Connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
			//获得Statement接口对象
			System.out.println("Creating statement");
			stmt = conn.createStatement();
			String addToDatabase = "INSERT INTO homework (url,username,password) VALUES(?,?,?)";
			PreparedStatement s = conn.prepareStatement(addToDatabase);
			s.setString(1, url);
			s.setString(2, name);
			s.setString(3, pwd);
			int rows = s.executeUpdate();
			System.out.println(rows+"行受影响");
			if(rows>0){
				JOptionPane.showMessageDialog(null, "添加成功");
			}

			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// Class.forname错误
			e.printStackTrace();
		} catch (SQLException e) {
			// JDBC操作错误
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException e2){
				e2.printStackTrace();
			}
		}
		
	}

}

