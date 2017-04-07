package bean;

public class ComingSoonMovieDetail {
	int id;
	String countries;
	String[] casts;
	String[] directors;
	String summary;
	String mobile_url;
	
	public String getCountries() {
		return countries;
	}
	public void setCountries(String countries) {
		this.countries = countries;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String[] getCasts() {
		return casts;
	}
	public void setCasts(String[] casts) {
		this.casts = casts;
	}
	public String[] getDirectors() {
		return directors;
	}
	public void setDirectors(String[] directors) {
		this.directors = directors;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getMobile_url() {
		return mobile_url;
	}
	public void setMobile_url(String mobile_url) {
		this.mobile_url = mobile_url;
	}
	
	

}
