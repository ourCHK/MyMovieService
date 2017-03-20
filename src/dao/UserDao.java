package dao;

public interface UserDao {
	/**
	 * 查询用户是否存在用户，用于登录使用
	 * @return
	 */
	public boolean queryUser(String userAccount,String userPassword);
	
	public boolean insertUser();
	
	public boolean deleteUser();
	
	public boolean updateUser();
}
