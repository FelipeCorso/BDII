package br.furb.jsondb.parser;

public class Date extends Value<String> {

	private int day, month, year;

	public Date(String date) {
		super(date);
		parseDate(date);
	}

	private void parseDate(String date) {
		day = Integer.parseInt(date.substring(0, 2));
		month = Integer.parseInt(date.substring(3, 5));
		year = Integer.parseInt(date.substring(6));
	}

	@Override
	public String toString() {
		return "DATE(".concat(getBaseValue()).concat(")");
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
