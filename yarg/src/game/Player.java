package game;

public abstract class Player {
	protected String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	protected abstract void executeTurn() throws InterruptedException;
	
	public static final Player NULL = new Player("Null") {
		
		@Override
		protected void executeTurn() {};
		
		@Override
		public String toString() {
			return "NULL";
		}
	};
}