package game;

public final class Action {
	public final String from;
	public final String to;
	public final int amount;
	
	// Reinforce 1
	public Action(String from) {
		this(from,1);
	}
	
	// Reinforce amount
	public Action(String from, int amount) {
		this(from,"null",amount);
	}
	
	// Attack 1
	public Action(String from, String to) {
		this(from,to,1);
	}
	
	// Attack amount
	public Action(String from, String to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
}
