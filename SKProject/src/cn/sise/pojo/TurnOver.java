package cn.sise.pojo;

public class TurnOver {
	private int s_quantity;
	private int c_price;
	private int vip_price;
	private String s_vip_number;

	public int getS_quantity() {
		return s_quantity;
	}

	public void setS_quantity(int s_quantity) {
		this.s_quantity = s_quantity;
	}

	public int getC_price() {
		return c_price;
	}

	public void setC_price(int c_price) {
		this.c_price = c_price;
	}

	public int getVip_price() {
		return vip_price;
	}

	public void setVip_price(int vip_price) {
		this.vip_price = vip_price;
	}

	public String getS_vip_number() {
		return s_vip_number;
	}

	public void setS_vip_number(String s_vip_number) {
		this.s_vip_number = s_vip_number;
	}

	public TurnOver(int s_quantity, int c_price, int vip_price, String s_vip_number) {
		super();
		this.s_quantity = s_quantity;
		this.c_price = c_price;
		this.vip_price = vip_price;
		this.s_vip_number = s_vip_number;
	}

	@Override
	public String toString() {
		return "TurnOver [s_quantity=" + s_quantity + ", c_price=" + c_price + ", vip_price=" + vip_price
				+ ", s_vip_number=" + s_vip_number + "]";
	}
}
