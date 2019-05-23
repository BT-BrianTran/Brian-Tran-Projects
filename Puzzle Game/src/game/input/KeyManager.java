package game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] inputs;
	
	public KeyManager() {
		
		inputs = new boolean[5];
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			inputs[0] = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			inputs[1] = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			inputs[2] = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			inputs[3] = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			inputs[4] = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			inputs[0] = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			inputs[1] = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			inputs[2] = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			inputs[3] = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			inputs[4] = false;
		}
		
	}
	
	public boolean[] getInputs() {
		
		return inputs;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}