package game.states;

import java.awt.Color;
import java.awt.Graphics;

import game.Game;
import game.userinterface.ClickListener;
import game.userinterface.TextButton;

public class ControlState extends State {

	private TextButton textButton;
	private final int IMPACTBOLD50_INDEX;
	
	public ControlState(Game game) {
		
		super(game);
		
		IMPACTBOLD50_INDEX = 0;
		
		textButton = new TextButton(game, game.getAssets().getFonts()[IMPACTBOLD50_INDEX], "BACK", game.getWidth() / 2, 
									game.getHeight() - SPACING, new ClickListener() {

			@Override
			public void onClick() {
				
				final int MENU_INDEX = 1;
				
				game.setState(game.getStates()[MENU_INDEX]);
				
			}
			
		});
		
	}
	
	@Override
	public void update() {
		
		textButton.update();
		
	}
	
	@Override
	public void render(Graphics image) {
		
		image.setFont(game.getAssets().getFonts()[IMPACTBOLD50_INDEX]);
		game.getAssets().drawString(image, "HOW TO PLAY", game.getWidth() / 2, SPACING, true, Color.white);
		textButton.render(image);
		
	}
	
}