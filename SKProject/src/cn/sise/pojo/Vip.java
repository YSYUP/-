package cn.sise.pojo;

/**
 * 
 * 功能描述:映射数据库的表字段
 * 
 * @author 吴正洪
 * @date 2020年9月2日
 * @version 1.0
 */
public class Vip {

	private String number;
	private String name;
	private int score;
	private String phone;
	private String date;

	public Vip(String number, String name, int score, String phone, String date) {
		super();
		this.number = number;
		this.name = name;
		this.score = score;
		this.phone = phone;
		this.date = date;
	}

	public Vip(String number, String name, String phone) {
		super();
		this.number = number;
		this.name = name;
		this.phone = phone;
	}

	public Vip() {
		super();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Vip [number=" + number + ", name=" + name + ", score=" + score + ", phone=" + phone + ", date=" + date
				+ "]";
	}

}
