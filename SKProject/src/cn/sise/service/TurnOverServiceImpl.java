package cn.sise.service;

import cn.sise.dao.TurnOverDao;
import cn.sise.dao.TurnOverDaoImpl;

public class TurnOverServiceImpl implements TurnOverService {
	private TurnOverDao tod = new TurnOverDaoImpl();

	@Override
	public int thisWeek() {
		return tod.thisWeek();
	}

	@Override
	public int thisMonth() {
		return tod.thisMonth();
	}

	@Override
	public int thisSeason() {
		return tod.thisSeason();
	}

	@Override
	public int whichYear(String year) {
		return tod.whichYear(year);
	}

}
