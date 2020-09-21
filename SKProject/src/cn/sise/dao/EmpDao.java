package cn.sise.dao;

import java.util.ArrayList;

import cn.sise.pojo.Attend;
import cn.sise.pojo.Employee;
import cn.sise.pojo.Goods;
import cn.sise.pojo.Vip;

public interface EmpDao {

	Employee login(String num, String pwd);

	ArrayList<Employee> cashierList();

	int addCashier(Employee emp);

	int editName(String num, String name);

	int editSex(String num, String sex);

	int editPhone(String num, String phone);

	int takeOffice(String num, int tmp);

	int editPwd(String num, String pwd);

	ArrayList<Employee> buyerList();

	int addBuyer(Employee emp);

	Employee findByNum(String num);

	ArrayList<Vip> vipList();

	int addVip(Vip vip);

	int deleteVip(String pho);

	Vip findByPhone(String pho);

	int v_editName(String pho, String name);

	int v_editPhone(String pho, String t_pho);

	ArrayList<Employee> empList();

	int checkPoints(String pho);

	ArrayList<Goods> goodsList(int tmp);

	int purchase(String c_name, int inventory);

	int newProduct(Goods goods);

	int checkBill(String e_id, String str, String pho, int g_id, int amount);

	ArrayList<Attend> todayAttend();

	ArrayList<Attend> dateAttend(String date);

	String attendCount(String date2);
}
