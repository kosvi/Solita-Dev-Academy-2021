package model;

public class Name implements Comparable<Name> {

	private String name;
	private int amount;

	public Name(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Name o) {
		if (this.amount != o.getAmount()) {
			// bigger number first!
			return Integer.compare(o.getAmount(), this.amount);
		} else {
			return this.name.compareTo(o.getName());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Name other = (Name) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
