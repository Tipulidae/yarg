package game;

public class ClickState {
	public boolean shift = false;
	public boolean ctrl = false;
	public boolean alt = false;
	public boolean enter = false;
	public String lastClickedTerritory = "Null";
	
	public static ClickState copy(ClickState st) {
		ClickState cs = new ClickState();
		cs.shift = st.shift;
		cs.ctrl = st.ctrl;
		cs.alt = st.alt;
		cs.enter = st.enter;
		cs.lastClickedTerritory = st.lastClickedTerritory;
		return cs;
	}
}
