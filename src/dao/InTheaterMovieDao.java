package dao;

import bean.InTheaterMovie;

public interface InTheaterMovieDao {
	
	public boolean addMovie();
	
	/**
	 * 获取服务器上有多少即将上映的影片
	 * @return 影片的数量
	 */
	public int getServerMovieCount();
	
	public int getMovieCount();
	
	public boolean getJson();
	
	public boolean getPic(String url,int id,InTheaterMovie itMovie);
	
	/**
	 * 查询指定间隔的数据库内容
	 * @param from	开始查询的条目
	 * @param to	结束查询到条目
	 * @return	返回查询后组成的json
	 */
	public String getQueryMovieJson(int from,int to);
}
