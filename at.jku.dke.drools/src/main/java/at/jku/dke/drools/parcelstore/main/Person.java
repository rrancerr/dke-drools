package at.jku.dke.drools.parcelstore.main;

public class Person {

	private String name;
	private String land;
	private String strasse;
	private long plz;
	private String ort;

	public Person(String name, String land, String strasse, String ort, long plz) {
		super();
		this.name = name;
		this.land = land;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public long getPlz() {
		return plz;
	}

	public void setPlz(long plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

}
