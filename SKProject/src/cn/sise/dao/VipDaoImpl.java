package cn.sise.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.sise.pojo.Vip;
import cn.sise.util.JDBCUtil;

public class VipDaoImpl implements VipDao {

	@Override
	public Vip findByNuber(String number) {

		// 直接調用工具类完成数据库操作就可以
		String sql = "select * from vip where v_phone=?";
		ResultSet re = JDBCUtil.executeQuery(sql, number);
		Vip vip = null;
		try {
			if (re.next()) {

				String number1 = re.getString("v_number");
				String name = re.getString("v_name");
				int score = re.getInt("v_score");
				String phone = re.getString("v_phone");
				String date = re.getString("v_date");
				vip = new Vip(number1, name, score, phone, date);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vip;
	}

}
