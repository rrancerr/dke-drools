package at.jku.dke.drools.parcelstore.main;

import java.util.Date;

public class Paket {
	private int id;
	private double laenge;
	private double breite;
	private double hoehe;
	private double gewicht;
	private double versandkosten;
	private Person empfaenger;
	private Person sender;
	private double wert;
	private boolean versichert;
	private boolean rabatt;
	private double versicherungskosten;
	private Date datum;

	public Paket(int id, double laenge, double breite, double hoehe, double gewicht, double versandkosten,
			Person empfaenger, Person sender, double wert, boolean versichert, boolean rabatt,
			double versicherungskosten, Date datum) {
		super();
		this.id = id;
		this.laenge = laenge;
		this.breite = breite;
		this.hoehe = hoehe;
		this.gewicht = gewicht;
		this.versandkosten = versandkosten;
		this.empfaenger = empfaenger;
		this.sender = sender;
		this.wert = wert;
		this.versichert = versichert;
		this.rabatt = rabatt;
		this.versicherungskosten = versicherungskosten;
		this.datum = datum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLaenge() {
		return laenge;
	}

	public void setLaenge(double laenge) {
		this.laenge = laenge;
	}

	public double getBreite() {
		return breite;
	}

	public void setBreite(double breite) {
		this.breite = breite;
	}

	public double getHoehe() {
		return hoehe;
	}

	public void setHoehe(double hoehe) {
		this.hoehe = hoehe;
	}

	public double getGewicht() {
		return gewicht;
	}

	public void setGewicht(double gewicht) {
		this.gewicht = gewicht;
	}

	public double getVersandkosten() {
		return versandkosten;
	}

	public void setVersandkosten(double versandkosten) {
		this.versandkosten = versandkosten;
	}

	public Person getEmpfaenger() {
		return empfaenger;
	}

	public void setEmpfaenger(Person empfaenger) {
		this.empfaenger = empfaenger;
	}

	public Person getSender() {
		return sender;
	}

	public void setSender(Person sender) {
		this.sender = sender;
	}

	public double getWert() {
		return wert;
	}

	public void setWert(double wert) {
		this.wert = wert;
	}

	public boolean isVersichert() {
		return versichert;
	}

	public void setVersichert(boolean versichert) {
		this.versichert = versichert;
	}

	public boolean isRabatt() {
		return rabatt;
	}

	public void setRabatt(boolean rabatt) {
		this.rabatt = rabatt;
	}

	public double getVersicherungskosten() {
		return versicherungskosten;
	}

	public void setVersicherungskosten(double versicherungskosten) {
		this.versicherungskosten = versicherungskosten;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

}
