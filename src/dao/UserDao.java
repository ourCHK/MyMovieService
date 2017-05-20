package dao;

public interface UserDao {
	/**
	 * 查询用户是否存在用户，用于登录使用
	 * @return
	 */
	public boolean loginUser(String userAccount,String userPassword);
	
	public boolean registerUser(String name, String sex, String account, String password, String phone);
	
	public boolean deleteUser();
	
	public boolean updateUser(String name, String sex, String account, String password, String phone);
}
