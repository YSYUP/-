package cn.sise.dao;

public interface TurnOverDao {

	int thisWeek();

	int thisMonth();

	int thisSeason();

	int whichYear(String year);

}
