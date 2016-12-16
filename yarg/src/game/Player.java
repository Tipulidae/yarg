package game;

public abstract class Player {
	protected String name;
	protected Control control;
	protected WorldMapInfo wm;
	
	public Player(String name, Control control, WorldMapInfo wm) {
		this.name = name;
		this.control = control;
		this.wm = wm;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	protected abstract void executeTurn() throws InterruptedException;
	
	public static final Player NULL = new Player("Null", null, null) {
		@Override
		protected void executeTurn() {};
	};
}
