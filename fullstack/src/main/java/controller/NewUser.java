package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import fullstack.User;
import fullstack.UserLogic;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class NewUser
 */
public class NewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserLogic userLogic;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUser() {
        this.userLogic = new UserLogic();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch(action) {
		case "/new":
			showNewForm(request, response);
			break;
		case "/insert":
			insertUser(request, response);
			break;
		case "/delete":
			deleteUser(request, response);
			break;
		case "/edit":
			showEditForm(request, response);
			break;
		case "/update":
			updateUser(request, response);
			break;
		default:
			allUser (request, response);
			break;
		}
	}
	
	private void allUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<User> listUser = userLogic.allUser();
		request.setAttribute("allUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("noneForNow");
		dispatcher.forward(request, response);
	}
	
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User updatedUser = new User(id, name, email, country);
		userLogic.updateUser(updatedUser);
		response.sendRedirect("home");
	}
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		userLogic.deleteUser(id);
		response.sendRedirect("home");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userLogic.getUserById(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("userForm.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
		
	}

	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("userForm.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User newUser = new User(name, email, country);
		userLogic.insertUser(newUser);
		response.sendRedirect("userList");
	}
	
}
