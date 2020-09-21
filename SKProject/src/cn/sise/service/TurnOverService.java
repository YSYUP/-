package cn.sise.service;

public interface TurnOverService {
	// 本周营业额
	int thisWeek();

	// 当月营业额
	int thisMonth();

	// 本季度营业额 （月份 + 2）/ 3
	int thisSeason();

	// 年营业额
	int whichYear(String year);
}
