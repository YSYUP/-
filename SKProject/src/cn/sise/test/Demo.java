package cn.sise.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.sise.pojo.TurnOver;
import cn.sise.util.JDBCUtil;

public class Demo {
	public static void main(String[] args) throws SQLException, ParseException {
		String tmp = "2020-9-7";
		String tmp1 = tmp.substring(0, 5);
		String tmp2 = tmp.substring(0, tmp.length() - 1) + "30";
		System.out.println(tmp1);
		System.out.println(tmp2);
	}
}
