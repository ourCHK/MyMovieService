package dao;

import java.sql.Date;

public interface MovieOrderDao {
	public boolean buyTicket(int userId,int movieId,int[] choosed_rows,int[] choosed_columns);
	/**
	 *  用于返回一被订购的电影票的位置
	 * @param movieId 电影id
	 * @param create_date 购票时间
	 * @return
	 */
	public String getChoosedMovieOrder(int movieId,Date create_date);
}
