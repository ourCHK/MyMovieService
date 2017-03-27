package dao;

public interface MovieDao {
	
	public boolean addMovie(String name,String main_performer,String introduce,boolean is_on_show,String path);
	
	public boolean queryMovie();
	
	public boolean deleteMovie();
	
	public boolean updateMovie();
	
	/**
	 * 查询指定间隔的数据库内容
	 * @param from	开始查询的条目
	 * @param to	结束查询到条目
	 * @return	返回查询后组成的json
	 */
	public String getQueryMovieJson(int from,int to);
}
