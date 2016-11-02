
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

}
