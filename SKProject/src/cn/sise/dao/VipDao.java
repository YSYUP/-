package cn.sise.dao;

import cn.sise.pojo.Vip;

public interface VipDao {
	
	//1.通过number或手机号查询vip
	Vip findByNuber(String number);

}
