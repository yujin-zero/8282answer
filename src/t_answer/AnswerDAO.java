package t_answer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AnswerDAO {

	private Connection conn;
	private ResultSet rs;
	
	public AnswerDAO() {
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

	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류
	}
	
	public int getNext() {
		String SQL = "SELECT num_a from t_answer order by num_a desc";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; // 첫 번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int write(String content_a, int num_m, int num_q) {
		String SQL = "insert into t_answer values (?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setInt(2, num_q);
			pstmt.setInt(3, num_m);
			pstmt.setString(4, content_a);
			pstmt.setInt(5, 0);
			pstmt.setString(6, getDate());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	
	
	public ArrayList<Answer> getList(int num_q) {
		String SQL = "select * from t_answer where num_q = ? order by num_a";
		ArrayList<Answer> list = new ArrayList<Answer>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, num_q);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Answer answer = new Answer();
				answer.setNum_a(rs.getInt(1));
				answer.setNum_q(rs.getInt(2));
				answer.setNum_m(rs.getInt(3));
				answer.setContent_a(rs.getString(4));
				answer.setRecommend(rs.getInt(5));
				answer.setDate_a(rs.getString(6));
				list.add(answer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}