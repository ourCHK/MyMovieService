package dao;

import java.sql.Date;
import java.util.ArrayList;

import bean.Ticket;

public interface TicketDao {
	/**
	 * 检查电影余票
	 * @param movieId 电影id
	 */
	public int checkCount(int movieId);
	
	/**
	 * 添加电影票
	 * @param movieId 电影id
	 * @param count 电影票数量
	 * @param price 电影票价格
	 * @param create_date 创建日期
	 * @return true or false
	 */
	public boolean addTicket(int movieId,int count,int price,Date create_date);
	
	/**
	 * ;批量添加电影
	 * @param ticketList	电影集合
	 * @return
	 */
	public boolean addAllTickets(ArrayList<Ticket> ticketList);
	
	/**
	 * 获取电影票价格
	 * @param movieId
	 * @return
	 */
	public int getPrice(int movieId);
}
