package game;

import java.awt.event.KeyEvent;

public class ClickMonitor {
	private ClickState state = new ClickState();
	private boolean stateUpdated = false;
	
	
	public synchronized void flush() {
		state.lastClickedTerritory = "Null";
		state.enter = false;
	}
	
	public synchronized ClickState getHumanInput() throws InterruptedException  {
		while (!stateUpdated) wait();
		
		ClickState st = ClickState.copy(state);
		state.enter = false;
		stateUpdated = false;
		return st;
	}
	
	public synchronized void click(String str) {
		state.lastClickedTerritory = str;
		stateUpdated = true;
		notifyAll();
	}

	public synchronized void keyDown(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_ENTER:
			state.enter = true;
			stateUpdated = true;
			notifyAll();
			break;
		case KeyEvent.VK_ALT:
			state.alt = true;
			break;
		case KeyEvent.VK_SHIFT:
			state.shift = true;
			break;
		case KeyEvent.VK_CONTROL:
			state.ctrl = true;
			break;
		}
	}
	
	public synchronized void keyUp(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_ALT:
			state.alt = false;
			break;
		case KeyEvent.VK_SHIFT:
			state.shift = false;
			break;
		case KeyEvent.VK_CONTROL:
			state.ctrl = false;
			break;
		}
	}
}
