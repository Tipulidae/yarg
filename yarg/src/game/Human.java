package game;

public class Human extends Player {
	private Control control;
	private ClickMonitor cm;
	private ClickState input;
	
	public Human(String name, Control control, ClickMonitor cm) {
		super(name);
		this.control = control;
		this.cm = cm;
	}
	
	
	public void handleClick() {
		switch (control.phase()) {
		case REINFORCE:
			int amount = Math.min(input.ctrl ? 5 : 1, control.reinforcementsRemaining());
			control.perform(new Action(input.lastClickedTerritory, amount));
			break;
		case ATTACK:
			System.out.println("Attack phase!");
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
			if (endTurn()) {
				control.endTurn();
				return;
			}
			else handleClick();
		}
	}
	
	private boolean endTurn() {
		return input.enter;
	}
}
