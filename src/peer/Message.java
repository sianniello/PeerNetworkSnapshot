package peer;

import java.io.Serializable;
import java.security.Timestamp;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class Message implements Serializable{

	private Marker marker;
	private String body;
	private int who;
	
	public Message(Marker marker, String body, int who) {
		this.marker = marker;
		this.body = body;
		this.who = who;
	}
	
	public Marker getMarker() {
		return marker;
	}
	
	public String getBody() {
		return body;
	}
	
	public int getWho() {
		return who;
	}
	
	public void setMarker(Marker marker) {
		this.marker = marker;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public void setWho(int who) {
		this.who = who;
	}

	public String toString() {
		return "Marker: " + marker.toString() + "\nBody: " + body + "\nWho: " + who + "\n";
	}
	
}
