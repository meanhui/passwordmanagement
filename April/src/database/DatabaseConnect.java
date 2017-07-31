package database;

/*���ݿ����Ӳ���
* 
1�������ӣ�
���� JDBC ������ ֻ��ӵ������������ſ���ע����������������ӵ��������衣
ע�� JDBC ����������һ���ᵼ�� JVM �����������������ʵ�ֵ��ڴ��У����������ʵ�� JDBC ����
���ݿ� URL �ƶ�������������ȷ��ʽ�ĵ�ַ��ָ��Ҫ���ӵ����ݿ⡣
��Ҫʹ�� DriverManager.getConnection() ��������һ��Connection ���������������ݿ����������

2��ִ�в�ѯ����Ҫʹ�����������Ķ��������ύһ�� SQL ��䵽���ݿ�

3���ӽ��������ȡ���ݣ�Ҫ��ʹ���ʵ��Ĺ��� ResultSet.getXXX() ���������������������

4�������������Եõ��Ľ����������صĲ���

5������������Ҫ��ȷ�عر����е����ݿ���Դ���ͷ��ڴ�
 * 
 * */

import java.sql.*;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import RSA.UseSHA256;


public class DatabaseConnect {
	//JDBC���������ƺ����ݿ��ַ
	static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
	//���ݿ�����
	static final String DB_URL = "jdbc:mysql://localhost/april";
	//���ݿ��û�������
	static final String USER = "root";
	static final String PASSWD = "123456" ; 
	
	public void changeLoginPassword(String username,String password){
//		if(!loginAuthentication(username, password)){
//			JOptionPane.showMessageDialog(null, "ԭ���벻��ȷ", "����", JOptionPane.WARNING_MESSAGE);
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
			System.out.println(rows+"����Ӱ��");
			if(rows>0){
				JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
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
		//������
		System.out.println("Connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
		//���Statement�ӿڶ���
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
			// Class.forname����
			e.printStackTrace();
		} catch (SQLException e) {
			// JDBC��������
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
		//������
		System.out.println("Connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
		//���Statement�ӿڶ���
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
			// Class.forname����
			e.printStackTrace();
		} catch (SQLException e) {
			// JDBC��������
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
			System.out.println(rows+"����Ӱ��");
			if(rows>0){
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
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
			JOptionPane.showMessageDialog(null, "�Ҳ�����", "û��", JOptionPane.WARNING_MESSAGE);
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
				System.out.println(rows+"����Ӱ��");
				if(rows>0){
					JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
				}
				stmt.close();
				conn.close();
				
			} catch (ClassNotFoundException e) {
				// Class.forname����
				e.printStackTrace();
			} catch (SQLException e) {
				// JDBC��������
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
			//ע��JDBC����
			Class.forName("com.mysql.jdbc.Driver");
			//������
			System.out.println("Connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASSWD);
			//���Statement�ӿڶ���
			System.out.println("Creating statement");
			stmt = conn.createStatement();
			String addToDatabase = "INSERT INTO homework (url,username,password) VALUES(?,?,?)";
			PreparedStatement s = conn.prepareStatement(addToDatabase);
			s.setString(1, url);
			s.setString(2, name);
			s.setString(3, pwd);
			int rows = s.executeUpdate();
			System.out.println(rows+"����Ӱ��");
			if(rows>0){
				JOptionPane.showMessageDialog(null, "��ӳɹ�");
			}

			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// Class.forname����
			e.printStackTrace();
		} catch (SQLException e) {
			// JDBC��������
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

