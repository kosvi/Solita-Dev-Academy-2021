package logic;

import model.Name;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NameList {

	private final String FILENAME = System.getenv("NAMES_JSON_FILE");
//	private final String FILENAME = "/home/ldap/ville/Solita/solita2021/names.json";

	public List<Name> getNamesByAmount() {
		List<Name> names;
		try {
			names = this.readDataFromFile();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		Collections.sort(names);
		return names;
	}

	public String[] getNamesAlphabetically() {
		List<Name> names;
		try {
			names = this.readDataFromFile();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		// get the number of names and create String array of that size
		int totalAmount = names.size();
		String[] nameArray = new String[totalAmount];
		// ... and add all names to that array
		for (int i = 0; i < totalAmount; i++) {
			nameArray[i] = names.get(i).getName();
		}
		// ... sort & return
		Arrays.sort(nameArray);
		return nameArray;
	}

	public int getNumberOfNames() {
		try {
			List<Name> names = this.readDataFromFile();
			int amount = 0;
			for (Name n : names) {
				amount += n.getAmount();
			}
			return amount;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return -1;
		}
	}

	public int getAmount(String name) {
		int amount = -1;
		List<Name> names;
		try {
			names = this.readDataFromFile();
		} catch (Exception e) {
			// Errors could be handled better. Maybe a logger?
			System.err.println(e.getMessage());
			names = null;
		}
		if (names != null) {
			// let's find the index for the name given as a parameter
			int index = names.indexOf(new Name(name, 0));
			// if index < 0 -> name wasn't on the list
			if (index >= 0) {
				amount = names.get(index).getAmount();
			}
		}

		// returns -1 on error, otherwise the amount for the name
		return amount;
	}

	// I have never read JSON-file with Java, so had to google:
	// https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library

	private List<Name> readDataFromFile() throws IOException, ParseException {
		List<Name> names = new ArrayList<>();
		JSONParser parser = new JSONParser();
		try {
			// Read names & amount from FILENAME
			JSONObject namesObject = (JSONObject) parser.parse(new FileReader(FILENAME));
			// We know the file format, so 'names' contains all the objects we actually want
			JSONArray namesArray = (JSONArray) namesObject.get("names");
			for (Object o : namesArray) {
				// let's get the name & amount from each and every object in the array
				JSONObject name = (JSONObject) o;
				String n = name.get("name").toString();
				String a = name.get("amount").toString();
				// ... and add them to our List
				names.add(new Name(n, Integer.valueOf(a)));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		// finally return the List
		return names;
	}
}
