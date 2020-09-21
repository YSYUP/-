package cn.sise.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.sise.pojo.TurnOver;
import cn.sise.util.JDBCUtil;

public class TurnOverDaoImpl implements TurnOverDao {

	@Override
	public int thisWeek() {
		ArrayList<TurnOver> list = new ArrayList<TurnOver>();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String n_date = date.substring(0, date.length() - 2);
		String sql = "select s_quantity,c_price,vip_price, s_vip_number from sell_info inner join goods on s_c_number = c_number "
				+ "where s_c_number in (select s_c_number from sell_info where s_time between ? and ?)";
		String weekstart = n_date + (judgeDay(date) - judgeWeek(date) + 1);
		String weekend = n_date + (judgeDay(date) - judgeWeek(date) + 7);
		ResultSet rs = JDBCUtil.executeQuery(sql, weekstart, weekend);
		TurnOver to = null;
		try {
			while (rs.next()) {
				to = new TurnOver(rs.getInt("s_quantity"), rs.getInt("c_price"), rs.getInt("vip_price"),
						rs.getString("s_vip_number"));
				list.add(to);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			int amount = list.get(i).getS_quantity();
			if (list.get(i).getS_vip_number() != null) {
				int v = list.get(i).getVip_price();
				sum += v * amount;
			} else {
				int c = list.get(i).getC_price();
				sum += c * amount;
			}
		}
		return sum;
	}

	@Override
	public int thisMonth() {
		ArrayList<TurnOver> list = new ArrayList<TurnOver>();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String n_date = date.substring(0, date.length() - 2);
		String sql = "select s_quantity,c_price,vip_price, s_vip_number from sell_info inner join goods on s_c_number = c_number "
				+ "where s_c_number in (select s_c_number from sell_info where s_time between ? and ?)";
		ResultSet rs = JDBCUtil.executeQuery(sql, n_date + "1", n_date + "30");
		TurnOver to = null;
		try {
			while (rs.next()) {
				to = new TurnOver(rs.getInt("s_quantity"), rs.getInt("c_price"), rs.getInt("vip_price"),
						rs.getString("s_vip_number"));
				list.add(to);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			int amount = list.get(i).getS_quantity();
			if (list.get(i).getS_vip_number() != null) {
				int v = list.get(i).getVip_price();
				sum += v * amount;
			} else {
				int c = list.get(i).getC_price();
				sum += c * amount;
			}
		}
		return sum;
	}

	@Override
	public int thisSeason() {
		ArrayList<TurnOver> list = new ArrayList<TurnOver>();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int tmp = judgeSeason(date);
		date = date.substring(0, 5);
		String sql = "select s_quantity,c_price,vip_price, s_vip_number from sell_info inner join goods on s_c_number = c_number "
				+ "where s_c_number in (select s_c_number from sell_info where s_time between ? and ?)";
		String start = "";
		String end = "";
		if (tmp == 1) {
			start = date + "1-1";
			end = date + "3-31";
		} else if (tmp == 2) {
			start = date + "4-1";
			end = date + "6-30";
		} else if (tmp == 3) {
			start = date + "7-1";
			end = date + "9-30";
		} else {
			start = date + "10-1";
			end = date + "12-31";
		}
		ResultSet rs = JDBCUtil.executeQuery(sql, start, end);
		TurnOver to = null;
		try {
			while (rs.next()) {
				to = new TurnOver(rs.getInt("s_quantity"), rs.getInt("c_price"), rs.getInt("vip_price"),
						rs.getString("s_vip_number"));
				list.add(to);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			int amount = list.get(i).getS_quantity();
			if (list.get(i).getS_vip_number() != null) {
				int v = list.get(i).getVip_price();
				sum += v * amount;
			} else {
				int c = list.get(i).getC_price();
				sum += c * amount;
			}
		}
		return sum;
	}

	@Override
	public int whichYear(String year) {
		ArrayList<TurnOver> list = new ArrayList<TurnOver>();
		String sql = "select s_quantity,c_price,vip_price, s_vip_number from sell_info inner join goods on s_c_number = c_number "
				+ "where s_c_number in (select s_c_number from sell_info where s_time between ? and ?)";
		ResultSet rs = JDBCUtil.executeQuery(sql, (year + "-1-1"), (year + "-12-31"));
		TurnOver to = null;
		try {
			while (rs.next()) {
				to = new TurnOver(rs.getInt("s_quantity"), rs.getInt("c_price"), rs.getInt("vip_price"),
						rs.getString("s_vip_number"));
				list.add(to);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			int amount = list.get(i).getS_quantity();
			if (list.get(i).getS_vip_number() != null) {
				int v = list.get(i).getVip_price();
				sum += v * amount;
			} else {
				int c = list.get(i).getC_price();
				sum += c * amount;
			}
		}
		return sum;
	}

	public int judgeWeek(String time) {
		int w = 0;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			w = cal.get(Calendar.DAY_OF_WEEK);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (w == 1) {
			return 7;
		} else {
			return w - 1;
		}
	}

	public int judgeDay(String time) {
		int d = 0;
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			d = cal.get(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public int judgeSeason(String time) {
		int s = 0;
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			s = ((cal.get(Calendar.MONTH) + 3) / 3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return s;
	}
}
