package dao;

import bean.MovieDetail;

public interface MovieDetailDao {
	
	public boolean queryMovieInLocal(int id);
	public boolean queryMovieInNetwork(int id);
	public boolean writeToLocalDatabase();
	public boolean parseJson(String json);
	
	/**
	 * 将movieDetail转为json
	 * @param movieDetail 查询到的MovieDetail
	 * @return
	 */
	public String toJson(MovieDetail movieDetail);
	
	/**
	 * 
	 * @param id	请求电影的id
	 * @return	返回客户端需要的json
	 */
	public String getMovieDetailJson(int id);
	
	public String saveImage(int id,String url);
	
}
