package cn.sise.service;

import cn.sise.dao.VipDao;
import cn.sise.dao.VipDaoImpl;
import cn.sise.pojo.Vip;

public class VipServiceImpl implements VipService {
	private VipDao vd = new VipDaoImpl();

	@Override
	public Vip login(String number) {
		// 调用dao层
		return vd.findByNuber(number);
	}

}
