package cn.sise.dao;

public interface TimeDao {

	void workDate();

	void empOnline(String e_id);

	int goWork(String e_id);

	int offWork(String e_id);
}
