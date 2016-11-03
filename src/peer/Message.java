package peer;

public class Message {

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
	
	public void setBody() {
		this.body = body;
	}
	
	public void setWho() {
		this.who = who;
	}

	public String toString() {
		return "Marker: " + marker + "\nBody: " + body + "\nWho: " + who;
	}
	
}
