package cn.sise.service;

import cn.sise.dao.TimeDao;
import cn.sise.dao.TimeDaoImpl;

public class TimeServiceImpl implements TimeService {
	private TimeDao td = new TimeDaoImpl();

	@Override
	public void workDate() {
		td.workDate();
	}

	@Override
	public void empOnline(String e_id) {
		td.empOnline(e_id);
	}

	@Override
	public int goWork(String e_id) {
		return td.goWork(e_id);
	}

	@Override
	public int offWork(String e_id) {
		return td.offWork(e_id);
	}

}
