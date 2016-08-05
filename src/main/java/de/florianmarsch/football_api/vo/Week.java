package de.florianmarsch.football_api.vo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Week {

	private Integer id;
	private Integer number;
	private boolean active;
	private Date from;
	private Date to;
	private List<Date> days = new ArrayList<Date>();

	public Week(JSONObject aJsonObject) {
		try {
			String name = aJsonObject.getString("name");
			number = Integer.valueOf(name.substring(0, name.indexOf(".")));

			id = aJsonObject.getInt("id");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			from = df.parse(aJsonObject.getString("started_at"));
			to = df.parse(aJsonObject.getString("ended_at"));
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date now = cal.getTime();
			cal.setTime(from);
			Date current = from;
			days.add(current);
			while (current.before(to)) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				current = cal.getTime();
				days.add(current);
			}
			if (days.contains(now)) {
				active = Boolean.TRUE;
			} else {
				active = Boolean.FALSE;
			}
		} catch (JSONException e) {
			throw new RuntimeException("Error converting Week from JSON : " + e.getMessage());
		} catch (ParseException pe) {
			throw new RuntimeException("Error parsing Date from JSON : " + pe.getMessage());

		}
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public List<Date> getDays() {
		return days;
	}

	public void setDays(List<Date> days) {
		this.days = days;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Week other = (Week) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	public JSONObject toJson() {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			JSONObject json = new JSONObject();
			json.put("number", number);
			json.put("active", active);
			json.put("from",df.format(from) );
			json.put("to",df.format( to));
			JSONArray dates = new JSONArray();
			for (Date date : days) {
				dates.put(df.format(date));
			}
			json.put("dates", dates);
			return json;
		} catch (JSONException e) {
			throw new RuntimeException("Error creating JSON : " + e.getMessage());
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
