package game.states;

import java.awt.Color;
import java.awt.Graphics;

import game.Game;

public class LoadingState extends State {
		
	private String text;
	private int index;
	private long startTime, timePassed;
		
	public LoadingState(Game game) {
		
		super(game);
		
	}
		
	@Override
	public void update() {
		
		final String NAME = "DEVELOPED BY BRIAN TRAN";
		final int WAIT_TO_ADD_LETTER = 150, WAIT_TO_CHANGE_STATE = 1000;
		final int MENU_INDEX = 1;
		
		timePassed += System.currentTimeMillis() - startTime;
		
		if (timePassed >= WAIT_TO_ADD_LETTER) {
			if (index <= NAME.length()) {
				text = NAME.substring(0, index);
				index++;
				timePassed = 0;
			}
			else {
				try {
					Thread.sleep(WAIT_TO_CHANGE_STATE);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				game.setState(game.getStates()[MENU_INDEX]);
			}
		}
		
		startTime = System.currentTimeMillis();
			
	}

	@Override
	public void render(Graphics image) {
		
		final int IMPACTBOLD75_INDEX = 1;
		
		image.setFont(game.getAssets().getFonts()[IMPACTBOLD75_INDEX]);
		game.getAssets().drawString(image, text, game.getWidth() / 2, game.getHeight() / 2, true, Color.green);
		
	}
		
}