package game;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Game extends Thread {
	private Map<String,Player> players = new HashMap<>();
	private Player currentPlayer;
	private Semaphore beginTurn = new Semaphore(0);
	private Semaphore endTurn = new Semaphore(0);
	private ClickMonitor cm = new ClickMonitor();
	private Control control;
	private WorldMapInfo wm;
	
	private int tempTurnCounter = 0;
	
	public Game(WorldMap wm) {
		super("Game thread");
		control = new Control(wm);
		this.wm = wm;
	}
	
	public void addComputer(String name) {
		//players.add(new Computer(name, control));
		players.put(name, new Computer(name, control, wm));
		control.addPlayer(name);
	}
	
	public void addHuman(String name) {
		//players.add(new Human(name, control, cm));
		players.put(name, new Human(name, control, wm, cm));
		control.addPlayer(name);
	}
	
	
	
	@Override
	public void run() {
		control.randomizeTerritoryOwners();
		executor.start();
		try {
			
			while (!gameOver()) {
				currentPlayer = players.get(control.currentPlayer());
				beginTurn.release();
				if (!endTurn.tryAcquire(5,TimeUnit.MINUTES)) {
					executor.interrupt();
					endTurn.acquire();
				}
				
				tempTurnCounter++;
				
				/*
				for (int i = 0; i < players.size(); i++) {
					currentPlayer = players.get(i);
					control.prepareNextTurn(currentPlayer.toString());
					beginTurn.release();
					//endTurn.acquire();
					if (!endTurn.tryAcquire(5,TimeUnit.MINUTES)) {
						executor.interrupt();
					}

				}
				tempTurnCounter++;
				*/
				
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Game Over!");
	}
	
	
	public ClickMonitor getClickMonitor() {
		return cm;
	}
	
	public Control getControl() {
		return control;
	}
	
	private boolean gameOver() {
		return tempTurnCounter > 5;
	}
	
	
	private Thread executor = new Thread("Executor thread") {
		@Override
		public void run() {
			while (!isInterrupted()) {
				try {
					beginTurn.acquire();
					currentPlayer.executeTurn();
					endTurn.release();
				} catch (InterruptedException e) {
					System.out.println(currentPlayer.toString()+" took too long. Turn forfeited!");
					control.endTurn();
					endTurn.release();
				}
			}
		}
	};
}
