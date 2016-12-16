package game;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

import javax.swing.SwingUtilities;

public class Control extends Observable implements Info {
	private WorldMap wm;
	private String currentPlayer;
	private Phase phase = Phase.START;
	private int reinforcements;
	
	private Deque<String> players = new LinkedList<String>();
	
	public Control(WorldMap wm) {
		this.wm = wm;
		
		// TODO
		// temporary!
		currentPlayer = "green";
		System.out.println("Control initialized");
	}
	
	
	public synchronized void perform(Action action) {
		if (!RuleBook.isValid(action, this, wm)) {
			System.out.println("Invalid Move! Throw exception blablabla");
			return;
		}
		
		switch (phase) {
		case REINFORCE:
			reinforce(action.from,action.amount);
			break;
		case ATTACK:
			System.out.println("Attacking "+action.to+" with "+action.amount+" troops from "+action.from+"!");
			attack(action.from, action.to, action.amount);
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
		
		players.addLast(currentPlayer);
		currentPlayer = players.removeFirst();
		
		reinforcements = wm.calculateReinforcements(currentPlayer);
		
		update();
	}
	
	public void addPlayer(String name) {
		players.addLast(name);
	}
	
	// Temporary function
	public synchronized void randomizeTerritoryOwners() {
		if (phase != Phase.START) {
			System.out.println("Can't randomize territories after the game has started!");
			return;
		}
		Random r = new Random();
		for (String t : wm.allTerritories()) {
			//t.setOwner("nobody");
			
			switch (r.nextInt(2)) {
			case 0:
				wm.setOwner(t,"red");
				break;
			case 1:
				wm.setOwner(t,"green");
				break;
			case 2:
				//t.setOwner("blue");
				break;
			default:
				break;
			}
		}
		
		endTurn();
	}
	
	public synchronized void endPhase() {
		System.out.println(phase+" Phase Complete!");
		phase = Phase.next(phase);
		if (phase == Phase.REINFORCE)
			endTurn();
		else
			update();
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
	
	private void attack(String from, String to, int amount) {
		int defenders = Math.min(2,wm.territory(to).getTroops());
		
		if (amount == 0) return;
		if (defenders == 0) {
			wm.setOwner(to, currentPlayer);
			wm.territory(to).addTroops(2);
			wm.territory(from).addTroops(-2);
		}
		
		/*
		Random r = new Random();
		int[] diceA = new int[amount];
		int[] diceD = new int[defenders];
		for (int i=0; i<amount; i++) {
			diceA[i] = r.nextInt(6);
		}
		for (int i=0; i<defenders; i++) {
			diceD[i] = r.nextInt(6);
		}*/
		
		
		
		
	}
	
	
	private void update() {
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


