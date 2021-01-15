package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.NameList;
import model.Name;

@WebServlet("/api")
public class NameApi extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NameList nameList = new NameList();
		int page = 0;
		try {
			page = Integer.valueOf(request.getParameter("page"));
		} catch (Exception e) {
			// some errornous parameter
			System.err.println(e.getMessage());
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		switch (page) {
		case 1:
			out.print(this.listNamesWithAmounts(nameList));
			out.flush();
			break;
		case 2:
			out.print(this.listNamesAlphabetically(nameList));
			out.flush();
			break;
		case 3:
			out.print(this.totalAmountOfNames(nameList));
			out.flush();
			break;
		case 4:
			String name = "";
			try {
				name = request.getParameter("name");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			out.print(this.amountOfNameGiven(name, nameList));
			out.flush();
			break;
		default:
			break;
		}
	}

	private String listNamesWithAmounts(NameList nameList) {
		List<Name> names = nameList.getNamesByAmount();
		StringBuilder JsonString = new StringBuilder();
		JsonString.append("{\"names\": [");
		boolean first = true;
		for (Name n : names) {
			if (!first) {
				JsonString.append(", ");
			} else {
				first = false;
			}
			JsonString.append("{\"name\": \"" + n.getName() + "\", \"amount\": \"" + n.getAmount() + "\"}");
		}
		JsonString.append("]}");
		return JsonString.toString();
	}

	// Method will return those names in Json-format, ready for a response!
	private String listNamesAlphabetically(NameList nameList) {
		// https://stackoverflow.com/questions/2010990/how-do-you-return-a-json-object-from-a-java-servlet
		String[] names = nameList.getNamesAlphabetically();
		StringBuilder JsonString = new StringBuilder();
		JsonString.append("{\"names\": [");
		boolean first = true;
		for (String name : names) {
			if (first) {
				JsonString.append("\"" + name + "\"");
				first = false;
			} else {
				JsonString.append(", \"" + name + "\"");
			}
		}
		JsonString.append("]}");
		return JsonString.toString();
	}

	// return total amount of names
	private String totalAmountOfNames(NameList nameList) {
		return "{\"amount\": \"" + nameList.getNumberOfNames() + "\" }";
	}

	// return the amount of name given as param
	private String amountOfNameGiven(String name, NameList nameList) {
		int amount = nameList.getAmount(name);
		return "{\"name\": \"" + name + "\", \"amount\": \"" + amount + "\"}";
	}
}
