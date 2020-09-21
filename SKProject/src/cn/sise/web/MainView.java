package cn.sise.web;

import java.util.ArrayList;
import java.util.Scanner;

import cn.sise.pojo.Attend;
import cn.sise.pojo.Employee;
import cn.sise.pojo.Goods;
import cn.sise.pojo.Vip;
import cn.sise.service.EmpService;
import cn.sise.service.EmpServiceImpl;
import cn.sise.service.TimeService;
import cn.sise.service.TimeServiceImpl;
import cn.sise.service.TurnOverService;
import cn.sise.service.TurnOverServiceImpl;
import cn.sise.service.VipService;
import cn.sise.service.VipServiceImpl;

public class MainView {
	private Scanner sc = new Scanner(System.in);
	private VipService vs = new VipServiceImpl();
	private EmpService es = new EmpServiceImpl();
	private TimeService ts = new TimeServiceImpl();
	private TurnOverService tos = new TurnOverServiceImpl();

	// 系统主界面
	public void login() {
		ts.workDate();
		while (true) {
			System.out.println(">>>>>>>>>欢迎使用超市收银管理系统<<<<<<<");
			System.out.println("请选择登录方式:");
			System.out.println("-> 1.管理员登录");
			System.out.println("-> 2.收银员登录");
			System.out.println("-> 3.采购员登录");
			System.out.println("-> 4.会员登录");
			System.out.println("-> 0.退出登录");
			String next = sc.next();
			switch (next) {
			case "1":
				adminLogin();
				break;
			case "2":
				cashierLogin();
				break;
			case "3":
				buyerLogin();
				break;
			case "4":
				vipLogin();
				break;
			case "0":
				System.out.println("成功退出系统");
				System.exit(0);
				break;
			default:
				System.out.println("输入的查询选项有误，请重新输入！\n");
				break;
			}
		}
	}

	// 采购员界面
	public void buyerLogin() {
		System.out.println("请输入采购员的账号：");
		String num = sc.next();
		System.out.println("请输入采购员的密码：");
		String pwd = sc.next();
		boolean flag = true;
		while (flag) {
			Employee emp = es.login(num, pwd);
			if (emp != null) {
				System.out.println(">>>>>>>>>欢迎采购员：" + emp.getUsername() + "<<<<<<<");
				System.out.println("-> 1.商品信息清单");
				System.out.println("-> 2.进行商品补货");
				System.out.println("-> 3.查询进货单");
				System.out.println("-> 4.上班打卡");
				System.out.println("-> 5.下班打卡");
				System.out.println("-> 0.退出");
				int in = sc.nextInt();
				switch (in) {
				case 1:
					ArrayList<Goods> g1 = es.goodsList(0);
					g1.forEach(li -> System.out.println(li));
					System.out.println();
					break;
				case 2:
					addGoods();
					break;
				case 3:
					System.out.println("提示：以下商品库存少于100！");
					ArrayList<Goods> g2 = es.goodsList(1);
					g2.forEach(li -> System.out.println(li));
					System.out.println();
					break;
				case 4:
					int res1 = ts.goWork(emp.getNumber());
					if (res1 == 1) {
						System.out.println("上班打卡成功！");
					} else if (res1 == 2) {
						System.out.println("请勿重复打卡！");
					} else if (res1 == 3) {
						System.out.println("非工作日无需打卡！");
					} else {
						System.out.println("上班打卡失败，请重试！");
					}
					System.out.println();
					break;
				case 5:
					int res2 = ts.offWork(emp.getNumber());
					if (res2 == 1) {
						System.out.println("下班打卡成功！");
					} else if (res2 == 2) {
						System.out.println("请勿重复打卡！");
					} else if (res2 == 3) {
						System.out.println("非工作日无需打卡！");
					} else {
						System.out.println("下班打卡失败，请重试！");
					}
					System.out.println();
					break;
				case 0:
					flag = false;
					break;
				default:
					System.out.println("输入的查询选项有误，请重新输入！\n");
					break;
				}
			} else {
				System.out.println("账号或密码错误！\n");
				flag = false;
			}
		}
	}

	// 商品补货界面
	public void addGoods() {
		boolean flag = true;
		while (flag) {
			System.out.println(">>>>>>>>>请选择操作<<<<<<<<");
			System.out.println("-> 1.商品补货");
			System.out.println("-> 2.添加新的商品");
			System.out.println("-> 0.退出");
			int in = sc.nextInt();
			switch (in) {
			case 1:
				System.out.println("请输入要补货的商品名：");
				String c_name = sc.next();
				System.out.println("请输入要进货的数量：");
				int inventory = sc.nextInt();
				int res = es.purchase(c_name, inventory);
				if (res == 1) {
					System.out.println("进货成功！\n");
				} else {
					System.out.println("进货失败！\n");
				}
				break;
			case 2:
				System.out.println("请输入新的商品信息：");
				System.out.println("商品名：");
				String name = sc.next();
				System.out.println("商品价格：");
				Double price = sc.nextDouble();
				System.out.println("商品会员价格：");
				Double v_price = sc.nextDouble();
				System.out.println("商品数量：");
				int amount = sc.nextInt();
				Goods goods = new Goods(name, price, v_price, amount);
				int res2 = es.newProduct(goods);
				if (res2 == 1) {
					System.out.println("添加成功！");
				} else {
					System.out.println("添加成功！");
				}
				System.out.println();
				break;
			case 0:
				flag = false;
				break;
			default:
				System.out.println("输入的查询选项有误，请重新输入！\n");
				break;
			}
		}
	}

	// 收银员界面
	public void cashierLogin() {
		System.out.println("请输入收银员的账号：");
		String num = sc.next();
		System.out.println("请输入收银员的密码：");
		String pwd = sc.next();
		boolean flag = true;
		while (flag) {
			Employee emp = es.login(num, pwd);
			if (emp != null) {
				System.out.println(">>>>>>>>>欢迎收银员：" + emp.getUsername() + "<<<<<<<");
				System.out.println("-> 1.收银结算");
				System.out.println("-> 2.会员积分查询");
				System.out.println("-> 3.开通会员");
				System.out.println("-> 4.上班打卡");
				System.out.println("-> 5.下班打卡");
				System.out.println("-> 0.退出");
				int in = sc.nextInt();
				switch (in) {
				case 1: {
					System.out.println("是否有会员卡：Y/N");
					String str = sc.next();
					String t_pho = null;
					String regex = "Y|N";
					if (str.matches(regex)) {
						if (str.equals("Y")) {
							System.out.println("会员手机号：");
							t_pho = sc.next();
						}
						System.out.print("商品编号：");
						int g_id = sc.nextInt();
						System.out.print("商品数量：");
						int amount = sc.nextInt();
						int res = es.checkBill(emp.getNumber(), str, t_pho, g_id, amount);
						if (res == 1) {
							System.out.println("账单结算成功！");
						} else {
							System.out.println("账单结算失败！");
						}
					} else {
						System.out.println("信息输入有误！\n");
					}
					break;
				}
				case 2: {
					System.out.println("请输入会员的手机号码予以查询：");
					String pho = sc.next();
					int res = es.checkPoints(pho);
					System.out.println("会员积分：" + res + "\n");
					break;
				}
				case 3: {
					System.out.println("请输入新会员信息：");
					System.out.println("会员工号：");
					String number = sc.next();
					System.out.println("会员姓名：");
					String name = sc.next();
					System.out.println("会员手机号码：");
					String phone = sc.next();
					Vip vip = new Vip(number, name, phone);
					int res2 = es.addVip(vip);
					if (res2 == 1) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
					System.out.println();
					break;
				}
				case 4: {
					int res1 = ts.goWork(emp.getNumber());
					if (res1 == 1) {
						System.out.println("上班打卡成功！");
					} else if (res1 == 2) {
						System.out.println("请勿重复打卡！");
					} else if (res1 == 3) {
						System.out.println("非工作日无需打卡！");
					} else {
						System.out.println("上班打卡失败，请重试！");
					}
					System.out.println();
					break;
				}
				case 5: {
					int res2 = ts.offWork(emp.getNumber());
					if (res2 == 1) {
						System.out.println("下班打卡成功！");
					} else if (res2 == 2) {
						System.out.println("请勿重复打卡！");
					} else if (res2 == 3) {
						System.out.println("非工作日无需打卡！");
					} else {
						System.out.println("下班打卡失败，请重试！");
					}
					System.out.println();
					break;
				}
				case 0:
					flag = false;
					break;
				default:
					System.out.println("输入的查询选项有误，请重新输入！\n");
					break;
				}
			} else {
				System.out.println("账号或密码错误！\n");
				flag = false;
			}
		}
	}

	// 管理员界面
	public void adminLogin() {
		System.out.println("请输入管理员的账号：");
		String num = sc.next();
		System.out.println("请输入管理员的密码：");
		String pwd = sc.next();
		boolean flag = true;
		while (flag) {
			Employee emp = es.login(num, pwd);
			if (emp != null) {
				ts.empOnline(emp.getNumber());
				System.out.println(">>>>>>>>>欢迎管理员：" + emp.getUsername() + "<<<<<<<");
				System.out.println("-> 1.收银员管理");
				System.out.println("-> 2.采购员管理");
				System.out.println("-> 3.员工出勤管理");
				System.out.println("-> 4.查询超市的营业额");
				System.out.println("-> 5.会员管理");
				System.out.println("-> 6.查询所有人员的信息");
				System.out.println("-> 7.上班打卡");
				System.out.println("-> 8.下班打卡");
				System.out.println("-> 0.退出");
				int input = sc.nextInt();
				switch (input) {
				case 1:
					manageCashier();
					break;
				case 2:
					manageBuyer();
					break;
				case 3:
					manageAttend();
					break;
				case 4:
					checkTurnover();
					break;
				case 5:
					manageVip();
					break;
				case 6:
					empList();
					break;
				case 7:
					int res1 = ts.goWork(emp.getNumber());
					if (res1 == 1) {
						System.out.println("上班打卡成功！");
					} else if (res1 == 2) {
						System.out.println("请勿重复打卡！");
					} else if (res1 == 3) {
						System.out.println("非工作日无需打卡！");
					} else {
						System.out.println("上班打卡失败，请重试！");
					}
					System.out.println();
					break;
				case 8:
					int res2 = ts.offWork(emp.getNumber());
					if (res2 == 1) {
						System.out.println("下班打卡成功！");
					} else if (res2 == 2) {
						System.out.println("请勿重复打卡！");
					} else if (res2 == 3) {
						System.out.println("非工作日无需打卡！");
					} else {
						System.out.println("下班打卡失败，请重试！");
					}
					System.out.println();
					break;
				case 0:
					flag = false;
					break;
				default:
					System.out.println("输入的查询选项有误，请重新输入！\n");
					break;
				}
			} else {
				System.out.println("账号或密码错误！\n");
				flag = false;
			}
		}
	}

	// 员工出勤管理
	public void manageAttend() {
		System.out.println(">>>>>>>>>请选择操作<<<<<<<<");
		System.out.println("-> 1.当日出勤状况");
		System.out.println("-> 2.指定日期出勤状况");
		System.out.println("-> 3.考勤详细信息");
		System.out.println("-> 0.退出");
		int in = sc.nextInt();
		switch (in) {
		case 1:
			ArrayList<Attend> attends = es.todayAttend();
			System.out.println("日期\t\t员工工号\t上班打卡时间\t\t下班打卡时间\t\t上班状态\t下班状态\t");
			if (attends.size() != 0) {
				attends.forEach(li -> System.out.println(li));
			} else {
				System.out.println("暂无当日出勤记录！");
			}
			System.out.println();
			break;
		case 2:
			System.out.println("请输入要查询的日期（格式为：年-月-日）：");
			String date1 = sc.next();
			String regex1 = "\\d{4}-\\d{1,2}-\\d{1,2}";
			if (date1.matches(regex1)) {
				ArrayList<Attend> attend2s = es.dateAttend(date1);
				System.out.println("日期\t\t员工工号\t上班打卡时间\t\t下班打卡时间\t\t上班状态\t下班状态\t");
				if (attend2s.size() != 0) {
					attend2s.forEach(li -> System.out.println(li));
				} else {
					System.out.println("暂无当日出勤记录！");
				}
			} else {
				System.out.println("日期格式输入有误！");
			}
			System.out.println();
			break;
		case 3:
			System.out.println("请输入要查询的日期（格式为：年-月-日）：");
			String date2 = sc.next();
			String regex2 = "\\d{4}-\\d{1,2}-\\d{1,2}";
			if (date2.matches(regex2)) {
				String str = es.attendCount(date2);
				System.out.println("旷工人数\t迟到人数\t早退人数\t具体人员\t");
				if (!"".equals(str)) {
					String[] split = str.split("/");
					System.out.println(split[0] + "\t" + split[1] + "\t" + split[2] + "\t" + split[3]);
				} else {
					System.out.println("暂无当日出勤记录！");
				}
			} else {
				System.out.println("日期格式输入有误！");
			}
			System.out.println();
			break;
		case 0:
			break;
		default:
			System.out.println("输入的查询选项有误，请重新输入！\n");
			break;
		}
	}

	// 管理采购员
	public void manageBuyer() {
		boolean flag = true;
		while (flag) {
			System.out.println(">>>>>>>>>请选择操作<<<<<<<<");
			System.out.println("-> 1.采购员名单");
			System.out.println("-> 2.添加采购员");
			System.out.println("-> 3.修改采购员信息");
			System.out.println("-> 0.退出");
			int in = sc.nextInt();
			switch (in) {
			case 1:
				ArrayList<Employee> list = es.buyerList();
				System.out.println("员工工号\t员工姓名\t员工密码\t性别\t手机号码\t	职位\t备注\t");
				list.forEach(li -> System.out.println(li));
				System.out.println();
				break;
			case 2:
				System.out.println("请输入要添加的采购员的信息：");
				System.out.println("采购员工号：");
				String num = sc.next();
				System.out.println("采购员姓名：");
				String name = sc.next();
				System.out.println("采购员密码：");
				String pwd = sc.next();
				System.out.println("采购员性别：");
				String sex = sc.next();
				System.out.println("采购员手机号码：");
				String phone = sc.next();
				Employee emp = new Employee(num, name, pwd, sex, phone);
				int res = es.addBuyer(emp);
				if (res == 1) {
					System.out.println("添加成功！");
				} else {
					System.out.println("添加失败！");
				}
				break;
			case 3:
				updateBuyer();
				break;
			case 0:
				flag = false;
				break;
			default:
				System.out.println("输入的查询选项有误，请重新输入！\n");
				break;
			}
		}
	}

	// 修改采购员
	private void updateBuyer() {
		boolean flag = true;
		System.out.println("请输入采购员工号：");
		String num = sc.next();
		Employee emp = es.findByNum(num);
		if (emp != null) {
			while (flag) {
				System.out.println(">>>>>>>>>请选择要修改的信息<<<<<<<<");
				System.out.println("-> 1.采购员姓名");
				System.out.println("-> 2.采购员性别");
				System.out.println("-> 3.采购员登录密码");
				System.out.println("-> 4.采购员手机号码");
				System.out.println("-> 5.修改任职信息");
				System.out.println("-> 0.退出");
				int in = sc.nextInt();
				switch (in) {
				case 1: {
					System.out.println("请输入姓名：");
					String name = sc.next();
					int res = es.editName(num, name);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 2: {
					System.out.println("请输入性别：");
					String sex = sc.next();
					int res = es.editSex(num, sex);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 3: {
					System.out.println("请输入新的手机号码：");
					String phone = sc.next();
					int res = es.editPhone(num, phone);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 4: {
					System.out.println("请输入密码：");
					String pwd = sc.next();
					int res = es.editPwd(num, pwd);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 5: {
					System.out.println("采购员就职请输入“数字1”，离职请输入“数字0”");
					int tmp = sc.nextInt();
					int res = es.takeOffice(num, tmp);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 0:
					flag = false;
					break;
				default:
					System.out.println("输入的查询选项有误，请重新输入！\n");
					break;
				}
			}
		} else {
			System.out.println("工号输入有误！\n");
		}

	}

	// 查询营业额
	public void checkTurnover() {
		boolean flag = true;
		while (flag) {
			System.out.println(">>>>>>>>>请选择操作<<<<<<<<");
			System.out.println("-> 1.本周营业额");
			System.out.println("-> 2.当月营业额");
			System.out.println("-> 3.本季度营业额");
			System.out.println("-> 4.年营业额");
			System.out.println("-> 0.退出");
			int in = sc.nextInt();
			switch (in) {
			case 1:
				int res1 = tos.thisWeek();
				System.out.println("本周营业额：" + res1 + "\n");
				break;
			case 2:
				int res2 = tos.thisMonth();
				System.out.println("当月营业额：" + res2 + "\n");
				break;
			case 3:
				int res3 = tos.thisSeason();
				System.out.println("本季度营业额：" + res3 + "\n");
				break;
			case 4:
				System.out.println("请输入查询的年份（格式：“2020”）：");
				String year = sc.next();
				String regex = "\\d{4}";
				if (year.matches(regex)) {
					int res4 = tos.whichYear(year);
					System.out.println("年营业额：" + res4 + "\n");
				} else {
					System.out.println("年份输入格式有误！");
				}
				break;
			case 0:
				flag = false;
				break;
			default:
				System.out.println("输入的查询选项有误，请重新输入！\n");
				break;
			}
		}
	}

	// VIP管理
	public void manageVip() {
		boolean flag = true;
		while (flag) {
			System.out.println(">>>>>>>>>请选择操作<<<<<<<<");
			System.out.println("-> 1.会员名单");
			System.out.println("-> 2.添加会员");
			System.out.println("-> 3.修改会员信息");
			System.out.println("-> 4.删除会员");
			System.out.println("-> 0.退出");
			int in = sc.nextInt();
			switch (in) {
			case 1:
				ArrayList<Vip> list = es.vipList();
				list.forEach(li -> System.out.println(li));
				System.out.println();
				break;
			case 2:
				System.out.println("请输入新会员信息：");
				System.out.println("会员工号：");
				String num = sc.next();
				System.out.println("会员姓名：");
				String name = sc.next();
				System.out.println("会员手机号码：");
				String phone = sc.next();
				Vip vip = new Vip(num, name, phone);
				int res = es.addVip(vip);
				if (res == 1) {
					System.out.println("添加成功！");
				} else {
					System.out.println("添加失败！");
				}
				break;
			case 3:
				updateVip();
				break;
			case 4:
				System.out.println("请输入会员的手机号：");
				String pho = sc.next();
				int res2 = es.deleteVip(pho);
				if (res2 == 1) {
					System.out.println("删除成功！");
				} else {
					System.out.println("删除失败！");
				}
				break;
			case 0:
				flag = false;
				break;
			default:
				System.out.println("输入的查询选项有误，请重新输入！\n");
				break;
			}
		}
	}

	// VIP修改
	public void updateVip() {
		boolean flag = true;
		System.out.println("请输入会员的手机号：");
		String pho = sc.next();
		Vip vip = es.findByPhone(pho);
		if (vip != null) {
			while (flag) {
				System.out.println(">>>>>>>>>请选择要修改的信息<<<<<<<<");
				System.out.println("-> 1.会员姓名");
				System.out.println("-> 2.会员电话");
				System.out.println("-> 0.退出");
				int in = sc.nextInt();
				switch (in) {
				case 1: {
					System.out.println("请输入姓名：");
					String name = sc.next();
					int res = es.v_editName(pho, name);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 2: {
					System.out.println("请输入新的电话号码：");
					String t_pho = sc.next();
					int res = es.v_editPhone(pho, t_pho);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 0:
					flag = false;
					break;
				default:
					System.out.println("输入的选项有误，请重新输入！\n");
					break;
				}
			}
		}
		System.out.println("没有此会员！\n");
	}

	// 所有员工信息
	public void empList() {
		boolean flag = true;
		while (flag) {
			System.out.println(">>>>>>>>>请选择操作<<<<<<<<");
			System.out.println("-> 1.所有员工信息清单");
			System.out.println("-> 0.退出");
			int in = sc.nextInt();
			switch (in) {
			case 1: {
				ArrayList<Employee> list = es.empList();
				System.out.println("员工工号\t员工姓名\t员工密码\t员工性别\t手机号码\t	职位\t备注\t");
				list.forEach(li -> System.out.println(li));
				System.out.println();
				break;
			}
			case 0:
				flag = false;
				break;
			default:
				System.out.println("输入的选项有误，请重新输入！\n");
				break;
			}
		}
	}

	// 管理收银员
	public void manageCashier() {
		boolean flag = true;
		while (flag) {
			System.out.println(">>>>>>>>>请选择操作<<<<<<<<");
			System.out.println("-> 1.收银员名单");
			System.out.println("-> 2.添加收银员");
			System.out.println("-> 3.修改收银员信息");
			System.out.println("-> 0.退出");
			int in = sc.nextInt();
			switch (in) {
			case 1:
				ArrayList<Employee> list = es.cashierList();
				System.out.println("员工工号\t员工姓名\t员工密码\t员工性别\t手机号码\t	职位\t备注\t");
				list.forEach(li -> System.out.println(li));
				System.out.println();
				break;
			case 2:
				System.out.println("请输入要添加的收银员的信息：");
				System.out.println("收银员工号：");
				String num = sc.next();
				System.out.println("收银员姓名：");
				String name = sc.next();
				System.out.println("收银员密码：");
				String pwd = sc.next();
				System.out.println("收银员性别：");
				String sex = sc.next();
				System.out.println("收银员手机号码：");
				String phone = sc.next();
				Employee emp = new Employee(num, name, pwd, sex, phone);
				int res = es.addCashier(emp);
				if (res == 1) {
					System.out.println("添加成功！");
				} else {
					System.out.println("添加失败！");
				}
				break;
			case 3:
				updateCashier();
				break;
			case 0:
				flag = false;
				break;
			default:
				System.out.println("输入的查询选项有误，请重新输入！\n");
				break;
			}
		}
	}

	// 修改收银员
	public void updateCashier() {
		boolean flag = true;
		System.out.println("请输入收银员工号：");
		String num = sc.next();
		Employee emp = es.findByNum(num);
		if (emp != null) {
			while (flag) {
				System.out.println(">>>>>>>>>请选择要修改的信息<<<<<<<<");
				System.out.println("-> 1.收银员姓名");
				System.out.println("-> 2.收银员性别");
				System.out.println("-> 3.收银员登录密码");
				System.out.println("-> 4.收银员手机号码");
				System.out.println("-> 5.修改任职信息");
				System.out.println("-> 0.退出");
				int in = sc.nextInt();
				switch (in) {
				case 1: {
					System.out.println("请输入姓名：");
					String name = sc.next();
					int res = es.editName(num, name);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 2: {
					System.out.println("请输入性别：");
					String sex = sc.next();
					int res = es.editSex(num, sex);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 3: {
					System.out.println("请输入新的手机号码：");
					String phone = sc.next();
					int res = es.editPhone(num, phone);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 4: {
					System.out.println("请输入密码：");
					String pwd = sc.next();
					int res = es.editPwd(num, pwd);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 5: {
					System.out.println("收银员就职请输入“数字1”，离职请输入“数字0”");
					int tmp = sc.nextInt();
					int res = es.takeOffice(num, tmp);
					if (res == 1) {
						System.out.println("修改成功！");
					} else {
						System.out.println("修改失败！");
					}
					break;
				}
				case 0:
					flag = false;
					break;
				default:
					System.out.println("输入的查询选项有误，请重新输入！\n");
					break;
				}
			}
		} else {
			System.out.println("工号输入有误！\n");
		}
	}

	// VIP登录
	public void vipLogin() {
		boolean flag = true;
		Vip vip = null;
		System.out.println("请输入手机号，按0返回上一层");
		String number = sc.next();
		if (number.equals("0")) {
			flag = false;
		}
		while (flag) {
			// 进入到service完成操作
			vip = vs.login(number);
			if (vip != null) {
				System.out.println(">>>>>>欢迎VIP客戶：" + vip.getName() + "<<<<<<<");
				System.out.println("您的个人信息如下:");
				System.out.println("客户ID：" + vip.getNumber());
				System.out.println("客户姓名：" + vip.getName());
				System.out.println("积分：" + vip.getScore());
				System.out.println("电话号码：" + vip.getPhone());
				System.out.println("注册日期：" + vip.getDate());
				System.out.println();
				System.out.println("按0返回上一层");
				String tmp = sc.next();
				if (tmp.equals("0")) {
					flag = false;
				}
			} else {
				System.out.println("没有此用户\n");
				flag = false;
			}
		}
	}

}