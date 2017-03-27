package bean;

public class Movie {
	int id;
	String name;
	String main_performer;
	String introduce;
	boolean is_on_show;
	String path;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMain_performer() {
		return main_performer;
	}
	public void setMain_performer(String main_performer) {
		this.main_performer = main_performer;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public boolean isIs_on_show() {
		return is_on_show;
	}
	public void setIs_on_show(boolean is_on_show) {
		this.is_on_show = is_on_show;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
