package t_member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bbs.Bbs;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL="jdbc:mysql://localhost:3306/webple?characterEncoding=utf8&useUnicode=true&mysqlEncoding=utf8";
			String dbID="root";
			String dbPassword="1234";
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(dbURL,dbID,dbPassword);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String id, String password) {
		String SQL="select password from t_member where id=?";
		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				if(rs.getString(1).equals(password)) {
					return 1; //로그인 성공
				}
				else
					return 0; //비밀번호 불일치
			}
			return -1; //아이디가 없음
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "insert into t_member(name,id,password) values (?,?,?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getId());
			pstmt.setString(3, user.getPassword());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public ArrayList<User> getList() {
		String SQL = "select * from t_member order by point desc limit 16";
		ArrayList<User> list1 = new ArrayList<User>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setName(rs.getString(2));
				user.setId(rs.getString(3));
				user.setPoint(rs.getInt(5));
				list1.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
		
	}

	public User getInfo(String id) {
	      //name, point, grade
	      String SQL = "select name, point, grade from t_member where id =?";
	      //select name, point, grade, (select count(*)+1 from t_member where point>t.point) as rank from t_member as t where id = ? order by rank asc;
	      User info = null;
	      
	      try {         
	         pstmt = conn.prepareStatement(SQL);
	         pstmt.setString(1, id);
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            info = new User();
	            info.setName(rs.getString("name"));
	            info.setPoint(rs.getInt("point"));
	            info.setGrade(rs.getInt("grade"));
	         }
	      }catch(Exception e) {
	         e.printStackTrace();
	      }
	      return info;
	   }
	
	
}
