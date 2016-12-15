package game;

public class Computer extends Player {
	private Control control;

	public Computer(String name, Control c) {
		super(name);
		control = c;
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
