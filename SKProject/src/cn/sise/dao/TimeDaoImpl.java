package cn.sise.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.sise.util.JDBCUtil;

public class TimeDaoImpl implements TimeDao {

	@Override
	// 系统启动录入日期
	public void workDate() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String sql1 = "select work_date from work_date where work_date = ?";
		String sql2 = "insert into work_date(work_date) values(?)";
		ResultSet rs = JDBCUtil.executeQuery(sql1, date);
		try {
			if (!rs.next()) {
				JDBCUtil.executeUpdate(sql2, date);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	// 员工登录系统会预先录入时间和工号
	public void empOnline(String e_id) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String sql1 = "select * from clock_info where employee_no = ? and clock_date = ?";
		String sql2 = "insert into clock_info (employee_no,clock_date) values (?,?)";
		ResultSet rs = JDBCUtil.executeQuery(sql1, e_id, date);
		try {
			if (!rs.next()) {
				JDBCUtil.executeUpdate(sql2, e_id, date);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 记录上班打卡时间
	@Override
	public int goWork(String e_id) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		int res = 0;
		String[] split = time.split(" ");
		if (judgeWeek(time)) {
			String sql1 = "select clock_in_time from clock_info where employee_no = ? and clock_date = ?";
			String sql2 = "update clock_info set clock_in_time = ? where employee_no = ? and clock_date = ?";
			ResultSet rs = JDBCUtil.executeQuery(sql1, e_id, split[0]);
			try {
				if (rs.next()) {
					String tmp = rs.getString("clock_in_time");
					if (tmp == null) {
						res = JDBCUtil.executeUpdate(sql2, time, e_id, split[0]);
					} else {
						return 2;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return 3;
		}
		return res;
	}

	// 记录下班打卡时间
	@Override
	public int offWork(String e_id) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		int res = 0;
		String[] split = time.split(" ");
		if (judgeWeek(time)) {
			String sql1 = "select clock_off_time from clock_info where employee_no = ? and clock_date = ?";
			String sql2 = "update clock_info set clock_off_time = ? where employee_no = ? and clock_date = ?";
			ResultSet rs = JDBCUtil.executeQuery(sql1, e_id, split[0]);
			try {
				if (rs.next()) {
					String tmp = rs.getString("clock_off_time");
					if (tmp == null) {
						res = JDBCUtil.executeUpdate(sql2, time, e_id, split[0]);
					} else {
						return 2;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return 3;
		}
		return res;
	}

	public boolean judgeWeek(String time) {
		String[] split = time.split(" ");
		int w = 0;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(split[0]);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			w = cal.get(Calendar.DAY_OF_WEEK);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (w == 1 || w == 7) {
			return false;
		} else {
			return true;
		}
	}
}
