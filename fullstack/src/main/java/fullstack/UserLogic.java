package fullstack;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserLogic {
	private  String jdbcUrl = "jdbc:mysql://localhost:3306/democrud?useSSL=false";
	private  String jdbcUsername = "root";
	private  String jdbcPassword = "aserjames123";
	
	private static final String INSERT_USERS_SQL = "insert into users(name,email,country) values(?,?,?)";
	
	private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS = "delete from users where id =?";
	private static final String UPDATE_USERS = "update users set name = ?,email = ?, country = ? where id = ?;";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
 

	public void insertUser(User user) {
		
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	public boolean updateUser(User user) {
		boolean rowUpdated = false;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setInt(4,  user.getId());
			
			rowUpdated = preparedStatement.executeUpdate() > 0;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rowUpdated;
	}
	
	public User getUserById(int id) {
		User user = null;
		
		try {
			Connection connection  = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
			
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String country = resultSet.getString("country");
				user = new User(id, name, email, country);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	

	public List<User> allUser(){
		List<User> users = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String country = resultSet.getString("country");
				users.add(new User(id, name, email, country));
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public  boolean deleteUser(int id) {
		boolean rowDeleted = false;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS);
			preparedStatement.setInt(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}catch(Exception e) {	
			e.printStackTrace();
		}
		return rowDeleted;
	}
	
	
	
}
