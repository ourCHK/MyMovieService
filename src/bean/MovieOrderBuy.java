package bean;

/**
 * 该类为了解决日期格式不兼容问题
 * @author chk
 *
 */
public class MovieOrderBuy {
	int userId;
	int movieId;
	int choosed_row;
	int choosed_column;
	String ticket_date;
	String create_date;
	String title;
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public int getChoosed_row() {
		return choosed_row;
	}
	public void setChoosed_row(int choosed_row) {
		this.choosed_row = choosed_row;
	}
	public int getChoosed_column() {
		return choosed_column;
	}
	public void setChoosed_column(int choosed_column) {
		this.choosed_column = choosed_column;
	}
	public String getTicket_date() {
		return ticket_date;
	}
	public void setTicket_date(String ticket_date) {
		this.ticket_date = ticket_date;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
