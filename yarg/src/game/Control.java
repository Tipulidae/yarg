package game;

import java.util.Observable;
import java.util.Random;

import javax.swing.SwingUtilities;

public class Control extends Observable implements Info {
	private WorldMap wm;
	private String currentPlayer;
	private Phase phase = Phase.START;
	private int reinforcements;
	
	public Control(WorldMap wm) {
		this.wm = wm;
		
		// TODO
		// temporary!
		currentPlayer = "green";
	}
	
	
	public synchronized void perform(Action action) {
		if (!RuleBook.isValid(action, this)) {
			System.out.println("Invalid Move! Throw exception blablabla");
			return;
		}
		
		switch (phase) {
		case REINFORCE:
			reinforce(action.from,action.amount);
			break;
		case ATTACK:
			System.out.println("Attacking "+action.to+" with "+action.amount+" troops from "+action.from+"!");
			break;
		case MOVE:
			System.out.println("Reinforcing "+action.to+" with "+action.amount+" troops from "+action.from+"!");
			break;
		default:
			System.out.println("Can't perform actions if the game hasn't started!");
		}
	}
	
	public synchronized void endTurn() {
		phase = Phase.REINFORCE;
		
		// TODO
		// Temporary!!!
		reinforcements = 10;
		if (currentPlayer.equals("red"))
			currentPlayer = "green";
		else
			currentPlayer = "red";
		
		update();
	}
	
	
	// Temporary function
	public synchronized void randomizeTerritoryOwners() {
		if (phase != Phase.START) {
			System.out.println("Can't randomize territories after the game has started!");
			return;
		}
		Random r = new Random();
		for (TerritoryActual t : wm.allTerritories()) {
			t.setOwner("nobody");
			
			switch (r.nextInt(2)) {
			case 0:
				t.setOwner("red");
				break;
			case 1:
				t.setOwner("green");
				break;
			case 2:
				t.setOwner("blue");
				break;
			default:
				break;
			}
		}
		
		endTurn();
	}
	
	
	private void reinforce(String t, int n) {
		wm.territory(t).addTroops(n);
		reinforcements -= n;
		if (reinforcements <= 0) {
			endPhase();
		} else {
			update();
		}
	}
	
	private void endPhase() {
		System.out.println(phase+" Phase Complete!");
		phase = Phase.next(phase);
		update();
	}
	
	private void update() {
		System.out.println("update!");
		//GameInfo gi = new GameInfo(phase, currentPlayer, reinforcements);
		setChanged();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				notifyObservers(new GameInfo(phase, currentPlayer, reinforcements));
			}
		});
	}
	
	
	@Override
	public synchronized Phase phase() {
		return phase;
	}

	@Override
	public synchronized String currentPlayer() {
		return currentPlayer;
	}


	@Override
	public synchronized int reinforcementsRemaining() {
		return reinforcements;
	}


	@Override
	public synchronized int previouslyMoved(String from) {
		return 0;
	}


	@Override
	public synchronized boolean currentPlayerOwnsTerritory(String t) {
		return wm.territoryExists(t) && currentPlayer.equals(wm.ownerOf(t));
	}
}


