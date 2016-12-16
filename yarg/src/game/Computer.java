package game;

public class Computer extends Player {

	public Computer(String name, Control c, WorldMapInfo wm) {
		super(name,c,wm);
	}

	@Override
	protected void executeTurn() throws InterruptedException {
		System.out.println("Computer's turn! Thread: " + Thread.currentThread().getName());
		Thread.sleep(2000);
		control.perform(new Action("Alaska", 10));
		//Thread.sleep(1000);
		control.endTurn();
		//control.addReinforcements("Alaska", 10);
	}
}
