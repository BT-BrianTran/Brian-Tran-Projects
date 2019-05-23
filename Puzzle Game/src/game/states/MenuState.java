package game.states;

import java.awt.Color;
import java.awt.Graphics;

import game.userinterface.ClickListener;
import game.userinterface.TextButton;
import game.Game;

public class MenuState extends State {

	private TextButton[] textButtons;

	public MenuState(Game game) {
		
		super(game);
		createTextButtons();
		
	}
	
	private void createTextButtons() {
		
		final int GAME_INDEX = 2;
		final int CONTROLS_INDEX = 3;
		final int CREDITS_INDEX = 4;
		final int IMPACTBOLD50_INDEX = 0;
		
		textButtons = new TextButton[4];
		textButtons[0] = new TextButton(game, game.getAssets().getFonts()[IMPACTBOLD50_INDEX], "PLAY", game.getWidth() / 2, game.getHeight() / 2, 
						new ClickListener() {

			@Override
			public void onClick() {
				
				game.setState(game.getStates()[GAME_INDEX]);
				
			}
			
		});
		
		textButtons[1] = new TextButton(game, game.getAssets().getFonts()[IMPACTBOLD50_INDEX], "CONTROLS", game.getWidth() / 2, 
						game.getHeight() / 2 + SPACING * 2, new ClickListener() {

			@Override
			public void onClick() {
			
				game.setState(game.getStates()[CONTROLS_INDEX]);
				
			}
			
		});
		
		textButtons[2] = new TextButton(game, game.getAssets().getFonts()[IMPACTBOLD50_INDEX], "CREDITS", game.getWidth() / 2, 
						game.getHeight() / 2 + SPACING * 4, new ClickListener() {

			@Override
			public void onClick() {
				
				game.setState(game.getStates()[CREDITS_INDEX]);
				
			}
			
		});
		
		textButtons[3] = new TextButton(game, game.getAssets().getFonts()[IMPACTBOLD50_INDEX], "EXIT", game.getWidth() / 2, 
						game.getHeight() / 2 + SPACING * 6, new ClickListener() {

			@Override
			public void onClick() {
				
				System.exit(0);
				
			}
			
		});

	}

	@Override
	public void update() {
		
		for (TextButton button : textButtons) {
			button.update();
		}
		
	}

	@Override
	public void render(Graphics image) {
		
		final int IMPACTBOLD75_INDEX = 1;
		
		image.setFont(game.getAssets().getFonts()[IMPACTBOLD75_INDEX]);
		game.getAssets().drawString(image, game.getTitle(), game.getWidth() / 2, game.getHeight() / 2 - SPACING * 3, true, Color.GREEN);
		
		for (TextButton textButton : textButtons) {
			textButton.render(image);
		}
		
	}

}