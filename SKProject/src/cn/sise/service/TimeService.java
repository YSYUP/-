package cn.sise.service;

public interface TimeService {
	void workDate();
	
	void empOnline(String e_id);

	int goWork(String e_id);

	int offWork(String e_id);

}
