import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpDao {
	public static Connection getConnection() {
		Connection conn = null;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/test";
		String user = "root";
		String pass = "root";
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, user, pass);
			if (conn != null) {
				System.out.println("DBConnected....");
			} else {
				System.out.println("Connection is not done ..!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static int save(Emp e) {
		int status = 0;
		Connection con = EmpDao.getConnection();
		try {
			String query = "insert into user905(name,password,email,country) values (?,?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setString(1, e.getName());
			pstmt.setString(2, e.getPassword());
			pstmt.setString(3, e.getEmail());
			pstmt.setString(4, e.getCountry());
			status = pstmt.executeUpdate();
			con.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return status;
	}

	public static int update(Emp e) {
		int status = 0;
		Connection con = EmpDao.getConnection();
		try {
			PreparedStatement pstmt = (PreparedStatement) con
					.prepareStatement("update user905 set name=?,password=?,email=?,country=? where id=?");
			pstmt.setString(1, e.getName());
			pstmt.setString(2, e.getPassword());
			pstmt.setString(3, e.getEmail());
			pstmt.setString(4, e.getCountry());
			pstmt.setInt(5, e.getId());

			status = pstmt.executeUpdate();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return status;
	}

	public static int delete(int id) {
		int status = 0;
		Connection con = EmpDao.getConnection();
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("delete from user905 where id=?");
			pstmt.setInt(1, id);
			status = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public static Emp getEmployeeById(int id) {
		Emp e = new Emp();
		Connection con = EmpDao.getConnection();
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("select * from user905 where id=?");
			pstmt.setInt(1, id);
			ResultSet rs = (ResultSet) pstmt.executeQuery();
			if (rs.next()) {
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setPassword(rs.getString(3));
				e.setEmail(rs.getString(4));
				e.setCountry(rs.getString(5));
			}
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return e;
	}

	public static List<Emp> getAllEmployees() {
		List<Emp> list = new ArrayList<Emp>();
		Connection con = EmpDao.getConnection();

		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("select * from user905");
			ResultSet rs = (ResultSet) pstmt.executeQuery();
			while (rs.next()) {
				Emp e = new Emp();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setPassword(rs.getString(3));
				e.setEmail(rs.getString(4));
				e.setCountry(rs.getString(5));
				list.add(e);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
}
