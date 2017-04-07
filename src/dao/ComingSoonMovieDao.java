package dao;

import bean.ComingSoonMovie;

public interface ComingSoonMovieDao {
	
	public boolean addMovie();
	
	/**
	 * 获取服务器上有多少即将上映的影片
	 * @return 影片的数量
	 */
	public int getServerMovieCount();
	
	public int getMovieCount();
	
	public boolean getJson();
	
	public boolean getPic(String url,int id,ComingSoonMovie csMovie);
}
