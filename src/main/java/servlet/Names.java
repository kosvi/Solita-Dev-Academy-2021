package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.NameList;

@WebServlet("")
public class Names extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NameList nameList = new NameList();

		// go for the default action
		request.setAttribute("names", nameList.getNamesByAmount());
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

}
