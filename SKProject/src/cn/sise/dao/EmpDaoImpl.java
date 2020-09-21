package cn.sise.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.sise.pojo.Attend;
import cn.sise.pojo.Employee;
import cn.sise.pojo.Goods;
import cn.sise.pojo.Vip;
import cn.sise.util.JDBCUtil;

public class EmpDaoImpl implements EmpDao {

	// 管理员登录
	public Employee findByNum(String num) {
		String sql = "select * from employee where number = ?";
		ResultSet rs = JDBCUtil.executeQuery(sql, num);
		Employee emp = null;
		try {
			if (rs.next()) {
				emp = new Employee(rs.getString("number"), rs.getString("username"), rs.getString("password"),
						rs.getString("sex"), rs.getString("phone"), rs.getInt("role"), rs.getInt("remark"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	public Employee login(String num, String pwd) {
		Employee emp = findByNum(num);
		if (emp != null) {
			if (pwd.equals(emp.getPassword())) {
				return emp;
			}
			return null;
		}
		return null;
	}

	// 查询收银员
	@Override
	public ArrayList<Employee> cashierList() {
		ArrayList<Employee> list = new ArrayList<Employee>();
		String sql = "select * from employee where role = 2";
		ResultSet rs = JDBCUtil.executeQuery(sql);
		Employee emp = null;
		try {
			while (rs.next()) {
				emp = new Employee(rs.getString("number"), rs.getString("username"), rs.getString("password"),
						rs.getString("sex"), rs.getString("phone"), rs.getInt("role"), rs.getInt("remark"));
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 添加收银员

	@Override
	public int addCashier(Employee emp) {
		String sql = "insert into employee(number, username, password, sex, phone, role, remark) values (?,?,?,?,?,?,?)";
		int res = JDBCUtil.executeUpdate(sql, emp.getNumber(), emp.getUsername(), emp.getPassword(), emp.getSex(),
				emp.getPhone(), 2, 1);
		return res;
	}

	// 员工修改模块
	@Override
	public int editName(String num, String name) {
		String sql = "update employee set username = ? where number = ?";
		int res = JDBCUtil.executeUpdate(sql, name, num);
		return res;
	}

	@Override
	public int editSex(String num, String sex) {
		String sql = "update employee set sex = ? where number = ?";
		int res = JDBCUtil.executeUpdate(sql, sex, num);
		return res;
	}

	@Override
	public int editPhone(String num, String phone) {
		String sql = "update employee set phone = ? where number = ?";
		int res = JDBCUtil.executeUpdate(sql, phone, num);
		return res;
	}

	@Override
	public int takeOffice(String num, int tmp) {
		String sql = "update employee set remark = ? where number = ?";
		int res = JDBCUtil.executeUpdate(sql, tmp, num);
		return res;
	}

	@Override
	public int editPwd(String num, String pwd) {
		String sql = "update employee set password = ? where number = ?";
		int res = JDBCUtil.executeUpdate(sql, pwd, num);
		return res;
	}

	// 采购员模块
	@Override
	public ArrayList<Employee> buyerList() {
		ArrayList<Employee> list = new ArrayList<Employee>();
		String sql = "select * from employee where role = 3";
		ResultSet rs = JDBCUtil.executeQuery(sql);
		Employee emp = null;
		try {
			while (rs.next()) {
				emp = new Employee(rs.getString("number"), rs.getString("username"), rs.getString("password"),
						rs.getString("sex"), rs.getString("phone"), rs.getInt("role"), rs.getInt("remark"));
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int addBuyer(Employee emp) {
		String sql = "insert into employee(number, username, password, sex, phone, role, remark) values (?,?,?,?,?,?,?)";
		int res = JDBCUtil.executeUpdate(sql, emp.getNumber(), emp.getUsername(), emp.getPassword(), emp.getSex(),
				emp.getPhone(), 3, 1);
		return res;
	}

	// Vip 模块
	@Override
	public ArrayList<Vip> vipList() {
		ArrayList<Vip> list = new ArrayList<Vip>();
		String sql = "select * from vip";
		ResultSet rs = JDBCUtil.executeQuery(sql);
		Vip vip = null;
		try {
			while (rs.next()) {
				vip = new Vip(rs.getString("v_number"), rs.getString("v_name"), rs.getInt("v_score"),
						rs.getString("v_phone"), rs.getString("v_date"));
				list.add(vip);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int addVip(Vip vip) {
		String sql = "insert into vip(v_number, v_name, v_score, v_phone, v_date) values (?,?,?,?,?)";
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int res = JDBCUtil.executeUpdate(sql, vip.getNumber(), vip.getName(), 0, vip.getPhone(), date);
		return res;
	}

	@Override
	public int deleteVip(String pho) {
		String sql = "delete from vip where v_phone=?";
		int res = JDBCUtil.executeUpdate(sql, pho);
		return res;
	}

	@Override
	public Vip findByPhone(String pho) {
		String sql = "select * from vip where v_phone = ?";
		ResultSet rs = JDBCUtil.executeQuery(sql, pho);
		Vip vip = null;
		try {
			if (rs.next()) {
				vip = new Vip(rs.getString("v_number"), rs.getString("v_name"), rs.getInt("v_score"),
						rs.getString("v_phone"), rs.getString("v_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vip;
	}

	@Override
	public int v_editName(String pho, String name) {
		String sql = "update vip set v_name = ? where v_phone = ?";
		int res = JDBCUtil.executeUpdate(sql, name, pho);
		return res;
	}

	@Override
	public int v_editPhone(String pho, String t_pho) {
		String sql = "update vip set v_phone = ? where v_phone = ?";
		int res = JDBCUtil.executeUpdate(sql, t_pho, pho);
		return res;
	}

	// 查询所有人员信息
	@Override
	public ArrayList<Employee> empList() {
		ArrayList<Employee> list = new ArrayList<Employee>();
		String sql = "select * from employee";
		ResultSet rs = JDBCUtil.executeQuery(sql);
		Employee emp = null;
		try {
			while (rs.next()) {
				emp = new Employee(rs.getString("number"), rs.getString("username"), rs.getString("password"),
						rs.getString("sex"), rs.getString("phone"), rs.getInt("role"), rs.getInt("remark"));
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int checkPoints(String pho) {
		String sql = "select v_score from vip where v_phone = ?";
		ResultSet rs = JDBCUtil.executeQuery(sql, pho);
		int tmp = 0;
		try {
			if (rs.next()) {
				tmp = rs.getInt("v_score");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	@Override
	public ArrayList<Goods> goodsList(int tmp) {
		ArrayList<Goods> list = new ArrayList<Goods>();
		String sql1 = "select * from goods where inventory < 100";
		String sql2 = "select * from goods";
		Goods g = null;
		ResultSet rs = null;
		if (tmp == 0) {
			rs = JDBCUtil.executeQuery(sql2);
		}
		if (tmp == 1) {
			rs = JDBCUtil.executeQuery(sql1);
		}
		try {
			while (rs.next()) {
				g = new Goods(rs.getInt("c_number"), rs.getString("c_name"), rs.getDouble("c_price"),
						rs.getDouble("vip_price"), rs.getInt("inventory"));
				list.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int purchase(String c_name, int inventory) {
		String sql = "update goods set inventory = (? + inventory) where c_name=?";
		int res = JDBCUtil.executeUpdate(sql, inventory, c_name);
		return res;
	}

	@Override
	public int newProduct(Goods g) {
		String sql = "insert into goods(c_name, c_price, vip_price, inventory) values (?,?,?,?)";
		int res = JDBCUtil.executeUpdate(sql, g.getC_name(), g.getC_price(), g.getVip_price(), g.getInventory());
		return res;
	}

	// 添加账单结算信息
	@Override
	public int checkBill(String e_id, String str, String pho, int g_id, int amount) {
		String tmp = null;
		int res = 0;
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		// 有会员卡
		if (str.equals("Y")) {
			String sql1 = "select v_number from vip where v_phone = ?";
			String sql2 = "insert into sell_info(s_c_number, s_quantity, s_time, s_e_number, s_vip_number) values(?,?,?,?,?)";
			ResultSet rs = JDBCUtil.executeQuery(sql1, pho);
			try {
				if (rs.next()) {
					tmp = rs.getString("v_number");
				}
				res = JDBCUtil.executeUpdate(sql2, g_id, amount, date, e_id, tmp);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (str.equals("N")) {
			String sql3 = "insert into sell_info(s_c_number, s_quantity, s_time, s_e_number) values(?,?,?,?)";
			res = JDBCUtil.executeUpdate(sql3, g_id, amount, date, e_id);
		}
		return res;
	}

	@Override
	public ArrayList<Attend> todayAttend() {
		ArrayList<Attend> list = new ArrayList<Attend>();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String sql = "select * from check_info where work_date = ?";
		ResultSet rs = JDBCUtil.executeQuery(sql, date);
		Attend a = null;
		try {
			while (rs.next()) {
				a = new Attend(rs.getString("work_date"), rs.getString("employee_no"), rs.getString("clock_in_time"),
						rs.getString("clock_off_time"), rs.getString("diff_in_status"), rs.getString("diff_off_time"));
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList<Attend> dateAttend(String date) {
		ArrayList<Attend> list = new ArrayList<Attend>();
		String sql = "select * from check_info where work_date = ?";
		ResultSet rs = JDBCUtil.executeQuery(sql, date);
		Attend a = null;
		try {
			while (rs.next()) {
				a = new Attend(rs.getString("work_date"), rs.getString("employee_no"), rs.getString("clock_in_time"),
						rs.getString("clock_off_time"), rs.getString("diff_in_status"), rs.getString("diff_off_time"));
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String attendCount(String date2) {
		// 出勤异常的员工工号
		String sql1 = "select employee_no from check_info where not diff_in_status='正常' and not diff_off_time = '正常' and work_date = ?";
		// 旷工人数
		String sql2 = "select count(*) from check_info where diff_in_status='旷工' and work_date = ?";
		// 迟到人数
		String sql3 = "select count(*) from check_info where diff_in_status='迟到' and work_date = ?";
		// 早退人数
		String sql4 = "select count(*) from check_info where diff_off_time='早退' and work_date = ?";
		String tmp = "";
		ResultSet rs1 = JDBCUtil.executeQuery(sql1, date2);
		ResultSet rs2 = JDBCUtil.executeQuery(sql2, date2);
		ResultSet rs3 = JDBCUtil.executeQuery(sql3, date2);
		ResultSet rs4 = JDBCUtil.executeQuery(sql4, date2);
		try {
			if (rs2.next()) {
				tmp += rs2.getInt("count(*)") + "/";
			}
			if (rs3.next()) {
				tmp += rs3.getInt("count(*)") + "/";
			}
			if (rs4.next()) {
				tmp += rs4.getInt("count(*)") + "/";
			}
			while (rs1.next()) {
				tmp += rs1.getString("employee_no") + ",";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmp;
	}
}
