package game;

public class Human extends Player {
	private Control control;
	private Phase currentPhase = Phase.REINFORCE;
	private GameInfo info;
	private ClickMonitor cm;
	private ClickState input;
	
	public Human(String name, Control control, ClickMonitor cm) {
		super(name);
		this.control = control;
		info = control.getInfo();
		this.cm = cm;
	}
	
	
	public void handleClick() {
		switch (currentPhase) {
		case REINFORCE:
			if (info.ownerOf(input.lastClickedTerritory).equals(name)) {
				int amount = input.ctrl ? 5 : 1;
				control.addReinforcements(input.lastClickedTerritory, amount);
			}
			break;
		case ATTACK:
			break;
		case MOVE:
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void executeTurn() throws InterruptedException {
		System.out.println("Human's turn");
		cm.flush();
		while (true) {
			input = cm.getHumanInput();
			if (endTurn()) return;
			else handleClick();
		}
	}
	
	@Override
	public String toString() {
		return "Human";
	}
	
	private boolean endTurn() {
		return input.enter;
	}
}
