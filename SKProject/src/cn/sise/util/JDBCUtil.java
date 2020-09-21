package cn.sise.util;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

@SuppressWarnings("all")
public class JDBCUtil {

	private static Connection ct;
	private static PreparedStatement ps;
	private static ResultSet result;
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	// 初始化
	static {

		try {
			FileReader fr = readFile();
			// 加载驱动
			Class.forName(driver);
			// 关闭资源
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功能描述:读取配置文件信息
	 * 
	 * @author 高东
	 * @date 2020年9月1日
	 * @return
	 */
	public static FileReader readFile() {
		// 创建文件输入流
		FileReader fr = null;
		try {
			fr = new FileReader("config/db.properties");
			// 创建properties对象
			Properties pr = new Properties();
			pr.load(fr);
			// 读取配置文件内的内容
			driver = pr.getProperty("driver");
			url = pr.getProperty("url");
			user = pr.getProperty("user");
			password = pr.getProperty("password");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fr;
	}

	/**
	 * 
	 * 功能描述:获得连接
	 * 
	 * @author 高东
	 * @date 2020年9月1日
	 * @return
	 */
	public static Connection getConnection() {

		try {
			ct = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ct;
	}

	// create table student(id,primary key);
	/**
	 * 
	 * 功能描述:创建表
	 * 
	 * @author 高东
	 * @date 2020年9月1日
	 * @param obj 要创建的表的对象
	 * @return
	 */
	public static int create(Object obj) {
		// 定义sql语句
		String sql = "create table ";
		// 获得class对象
		Class<? extends Object> cl = obj.getClass();
		// 获得表名
		String tableName = cl.getSimpleName();
		sql = sql + tableName;
		// 定义数据表的字段和类型
		String fname = "";
		String ftype = "";
		// 获得所有属性
		Field[] fields = cl.getDeclaredFields();
		for (Field field : fields) {
			String type = field.getType().getSimpleName();
			ftype = getType(type) + ",";
			fname += field.getName() + " " + ftype;
		}
		// 去除多余逗号
		String name = "(" + fname.substring(0, fname.length() - 1) + ")";
		// 将第一个逗号替换成主键关键字
		String value = name.replaceFirst(",", " primary key,");
		sql = sql + value + ";";
		// 返回结果
		int result = 0;
		try {
			// 进行预编译
			getConnection();
			ps = ct.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			close();
		}
		// 执行SQL
		return result;
	}

	/**
	 * 
	 * 功能描述: 查询所有数据
	 * 
	 * @author 高东
	 * @date 2020年9月1日
	 * @param obj
	 * @return
	 */
	public static ArrayList<Object> selectAll(Object obj) {
		// 定义sql语句
		String sql = "select * from ";
		// 获得class对象
		Class<? extends Object> cl = obj.getClass();
		// 获得表名
		String tableName = cl.getSimpleName();
		sql += tableName;
		// 得到对象属性类型
		Field[] fields = cl.getDeclaredFields();
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			// 进行预编译
			getConnection();
			ps = ct.prepareStatement(sql);
			// 得到返回值
			result = ps.executeQuery();
			while (result.next()) {
				Object obj2 = cl.newInstance();// 1.9
				// 将数据赋值给对象
				for (Field field : fields) {
					String name = field.getName();
					String type = field.getType().getSimpleName();
					// 暴力反射
					field.setAccessible(true);
					// 判断数据库数据是否为int类型
					if (type.equals("int")) {
						int value = result.getInt(name);
						field.set(obj2, value);
					} else {
						String value = result.getString(name);
						field.set(obj2, value);
					}

				}
				// 将对象赋值给集合
				list.add(obj2);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			close();
		}
		return list;
	}

	/**
	 * 查询单条数据 功能描述:
	 * 
	 * @author 高东
	 * @date 2020年9月1日
	 * @param obj
	 * @param index
	 * @return
	 */
	public static Object selectByPrimaryKey(Object obj, int index) {
		// 定义sql语句
		String sql = "select * from ";
		// 获得class对象
		Class<? extends Object> cl = obj.getClass();
		// 获得表名
		String tableName = cl.getSimpleName();

		// 得到对象属性类型
		Field[] fields = cl.getDeclaredFields();
		sql += tableName + " where " + fields[0].getName() + " = " + index + ";";//
		getConnection();
		try {
			ps = ct.prepareStatement(sql);
			result = ps.executeQuery();
			while (result.next()) {
				for (Field field : fields) {
					// 暴力反射
					field.setAccessible(true);
					String name = field.getName();
					String type = field.getType().getSimpleName();
					// 判断数据库数据是否为int类型
					if (type.equals("int")) {
						int value = result.getInt(name);
						field.set(obj, value);
					} else {
						String value = result.getString(name);
						field.set(obj, value);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			close();
		}
		return obj;
	}

	/**
	 * 
	 * 功能描述:添加数据
	 * 
	 * @author 高东
	 * @date 2020年9月1日
	 * @param obj
	 * @return
	 */
	public static int insert(Object obj) {
		// 定义sql语句
		String sql = "insert into ";
		// 获得class对象
		Class<? extends Object> cl = obj.getClass();
		// 获得表名
		String tableName = cl.getSimpleName();
		sql += tableName;
		String name = "";
		String value = "";
		// 得到对象属性类型
		Field[] fields = cl.getDeclaredFields();
		try {
			for (Field field : fields) {
				name += field.getName() + ",";
				field.setAccessible(true);
				Object object = field.get(obj);
				value += "'" + object.toString() + "',";
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
//		//删除多余逗号
		String vName = name.substring(0, name.length() - 1);
		String vValue = value.substring(0, value.length() - 1);
		sql += "(" + vName + ") values(" + vValue + ");";
		int result = 0;
		try {
			// 进行预编译
			getConnection();
			ps = ct.prepareStatement(sql);
			result = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			close();
		}
		return result;
	}

	/**
	 * 
	 * 功能描述:删除单条数据
	 * 
	 * @author 高东
	 * @date 2020年9月1日
	 * @param obj
	 * @param index
	 * @return
	 */
	public static int deleteByPrimaryKey(Object obj, int index) {
		// 定义sql语句
		String sql = "delete from ";
		// 获得class对象
		Class<? extends Object> cl = obj.getClass();
		// 获得表名
		String tableName = cl.getSimpleName();
		// 得到对象属性类型
		Field[] fields = cl.getDeclaredFields();
		sql += tableName + " where " + fields[0].getName() + " = " + index + ";";
		getConnection();
		int result = 0;
		try {
			ps = ct.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			close();
		}

		return result;
	}

	/**
	 * 
	 * 功能描述:修改单条数据 通过id修改数据
	 * 
	 * @author 吴正洪
	 * @date 2020年9月1日
	 * @param obj   要修改对象
	 * @param index 要修改的id
	 * @return
	 */
	public static int updateByPrimaryKey(Object obj, int index) {
		// 定义sql语句
		String sql = "update ";
		// 获得class对象
		Class<? extends Object> cl = obj.getClass();
		// 获得表名
		String tableName = cl.getSimpleName();
		sql += tableName + " set ";
		String name = "";
		String value = "";
		// 得到对象属性类型
		Field[] fields = cl.getDeclaredFields();
		try {
			for (Field field : fields) {
				name = field.getName();
				field.setAccessible(true);
				Object object = field.get(obj);
				value += name + "='" + object.toString() + "',";
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		// 去除多余逗号
		String fValue = value.substring(0, value.length() - 1);
		sql += fValue + " where " + fields[0].getName() + " = " + index + ";";
		getConnection();
		int result = 0;
		try {
			ps = ct.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			close();
		}
		return result;
	}

	// 3.封装一个查询的方法
	public static ResultSet executeQuery(String sql, Object... obj) {

		// 获得连接
		getConnection();
		// 得到发送对象
		try {
			ps = ct.prepareStatement(sql);

			// 处理占位符
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i + 1, obj[i]);
				}

			}

			// 发送sql语句
			result = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	// 4.封装一个增删改的方法
	public static int executeUpdate(String sql, Object... obj) {
		int in = 0;
		// 获得连接
		getConnection();
		// 得到发送对象
		try {
			ps = ct.prepareStatement(sql);

			// 处理占位符
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i + 1, obj[i]);
					
				}
			}
			// 发送sql语句
			in = ps.executeUpdate();
			// 关闭资源
			close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return in;
	}

	// 关闭资源
	public static void close() {
		try {
			if (ps != null) {
				ps.close();
			}
			if (result != null) {
				result.close();
			}
			if (ct != null) {
				ct.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 判断属性类型
	public static String getType(String type) {
		if (type.equals("String") || type.equals("byte") || type.equals("char") || type.equals("boolean")
				|| type.equals("short")) {
			type = "varchar(255)";
			return type;
		}
		return type;
	}

}
