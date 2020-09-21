package cn.sise.service;

import java.util.ArrayList;

import cn.sise.dao.EmpDao;
import cn.sise.dao.EmpDaoImpl;
import cn.sise.dao.VipDao;
import cn.sise.dao.VipDaoImpl;
import cn.sise.pojo.Attend;
import cn.sise.pojo.Employee;
import cn.sise.pojo.Goods;
import cn.sise.pojo.Vip;
import cn.sise.service.EmpService;

public class EmpServiceImpl implements EmpService {
	private EmpDao ed = new EmpDaoImpl();

	@Override
	public Employee login(String num, String pwd) {
		return ed.login(num, pwd);
	}

	@Override
	public ArrayList<Employee> cashierList() {
		return ed.cashierList();
	}

	@Override
	public int addCashier(Employee emp) {
		return ed.addCashier(emp);
	}

	@Override
	public int editName(String num, String name) {
		return ed.editName(num, name);
	}

	@Override
	public int editSex(String num, String sex) {
		return ed.editSex(num, sex);
	}

	@Override
	public int editPhone(String num, String phone) {
		return ed.editPhone(num, phone);
	}

	@Override
	public int takeOffice(String num, int tmp) {
		return ed.takeOffice(num, tmp);
	}

	@Override
	public int editPwd(String num, String pwd) {
		return ed.editPwd(num, pwd);
	}

	@Override
	public ArrayList<Employee> buyerList() {
		return ed.buyerList();
	}

	@Override
	public int addBuyer(Employee emp) {
		return ed.addBuyer(emp);
	}

	@Override
	public Employee findByNum(String num) {
		return ed.findByNum(num);
	}

	@Override
	public ArrayList<Vip> vipList() {
		return ed.vipList();
	}

	@Override
	public int addVip(Vip vip) {
		return ed.addVip(vip);
	}

	@Override
	public int deleteVip(String pho) {
		return ed.deleteVip(pho);
	}

	@Override
	public Vip findByPhone(String pho) {
		return ed.findByPhone(pho);
	}

	@Override
	public int v_editName(String pho, String name) {
		return ed.v_editName(pho, name);
	}

	@Override
	public int v_editPhone(String pho, String t_pho) {
		return ed.v_editPhone(pho, t_pho);
	}

	@Override
	public ArrayList<Employee> empList() {
		return ed.empList();
	}

	@Override
	public int checkPoints(String pho) {
		return ed.checkPoints(pho);
	}

	@Override
	public ArrayList<Goods> goodsList(int tmp) {
		return ed.goodsList(tmp);
	}

	@Override
	public int purchase(String c_name, int inventory) {
		return ed.purchase(c_name, inventory);
	}

	@Override
	public int newProduct(Goods goods) {
		return ed.newProduct(goods);
	}

	@Override
	public int checkBill(String e_id, String str, String pho, int g_id, int amount) {
		return ed.checkBill(e_id, str, pho, g_id, amount);
	}

	@Override
	public ArrayList<Attend> todayAttend() {
		return ed.todayAttend();
	}

	@Override
	public ArrayList<Attend> dateAttend(String date) {
		return ed.dateAttend(date);
	}

	@Override
	public String attendCount(String date2) {
		return ed.attendCount(date2);
	}

}
