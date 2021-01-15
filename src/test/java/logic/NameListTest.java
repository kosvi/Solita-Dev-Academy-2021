package logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Name;

class NameListTest {

	private NameList nameList;

	@BeforeEach
	void initList() {
		this.nameList = new NameList();
	}

	@Test
	void getNamesByAmountTest() {
		List<Name> names = nameList.getNamesByAmount();
		int currentAmount = names.get(0).getAmount();
		// We should always find smaller and smaller number (or equal), but never
		// bigger!
		for (Name n : names) {
			assertFalse(currentAmount < n.getAmount());
			currentAmount = n.getAmount();
		}
	}

	@Test
	void getNamesAlphabeticallyTest() {
		String[] names = nameList.getNamesAlphabetically();
		assertTrue(names[0].equals("Anna"));
		assertTrue(names[19].equals("Ville"));
	}

	@Test
	void getNumberOfNamesTest() {
		assertEquals(211, nameList.getNumberOfNames());
	}

	@Test
	void getAmountTest() {
		assertEquals(24, nameList.getAmount("Ville"));
		assertEquals(4, nameList.getAmount("Suvi"));
		// -1 for names not found (or other failure)
		assertEquals(-1, nameList.getAmount("Nonexistent"));
	}

}
