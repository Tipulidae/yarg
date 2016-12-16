package game;

public class Human extends Player {
	private ClickMonitor cm;
	private ClickState input;
	
	private String from = null;
	
	public Human(String name, Control control, WorldMapInfo wm, ClickMonitor cm) {
		super(name, control, wm);
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
			String t = input.lastClickedTerritory;
			if (wm.territoryExists(t)) {
				if (wm.isOwnerOf(name, input.lastClickedTerritory)) {
					from = input.lastClickedTerritory;
				} else if (from != null) {
					control.perform(new Action(from,t,3));
				}
			} else {
				from = null;
			}
			
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
				control.endPhase();
				from = null;
				return;
			}
			else handleClick();
		}
	}
	
	private boolean endTurn() {
		return input.enter;
	}
}
