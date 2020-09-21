package cn.sise.pojo;

public class Attend {
	private String work_date;
	private String e_id;
	private String clock_in;
	private String clock_off;
	private String diff_in;
	private String diff_off;

	@Override
	public String toString() {
		return work_date + "\t" + e_id + "\t" + clock_in + "\t" + clock_off + "\t" + diff_in + "\t" + diff_off + "\t";
	}

	public Attend(String work_date, String e_id, String clock_in, String clock_off, String diff_in, String diff_off) {
		super();
		this.work_date = work_date;
		this.e_id = e_id;
		this.clock_in = clock_in;
		this.clock_off = clock_off;
		this.diff_in = diff_in;
		this.diff_off = diff_off;
	}

	public Attend() {
		super();
	}

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}

	public String getE_id() {
		return e_id;
	}

	public void setE_id(String e_id) {
		this.e_id = e_id;
	}

	public String getClock_in() {
		return clock_in;
	}

	public void setClock_in(String clock_in) {
		this.clock_in = clock_in;
	}

	public String getClock_off() {
		return clock_off;
	}

	public void setClock_off(String clock_off) {
		this.clock_off = clock_off;
	}

	public String getDiff_in() {
		return diff_in;
	}

	public void setDiff_in(String diff_in) {
		this.diff_in = diff_in;
	}

	public String getDiff_off() {
		return diff_off;
	}

	public void setDiff_off(String diff_off) {
		this.diff_off = diff_off;
	}
}
