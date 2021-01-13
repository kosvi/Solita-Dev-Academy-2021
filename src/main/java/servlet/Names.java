package servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
		long page = 0;
		try {
			page = Integer.valueOf(request.getParameter("page"));
		} catch (Exception e) {
			// some errornous parameter
			System.err.println(e.getMessage());
		}
		if (page == 2) {
			String[] names = nameList.getNamesAlphabetically();
			StringBuilder JsonResponse = new StringBuilder();
			JsonResponse.append("{\"names\": [");
			boolean first = true;
			for (String name : names) {
				if (first) {
					JsonResponse.append("\"" + name + "\"");
					first = false;
				} else {
					JsonResponse.append(", \"" + name + "\"");
				}
			}
			JsonResponse.append("]}");
			// https://stackoverflow.com/questions/2010990/how-do-you-return-a-json-object-from-a-java-servlet
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(JsonResponse);
			out.flush();
		} else {
			// go for the default action
			request.setAttribute("names", nameList.getNamesByAmount());
			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		}
	}

}
