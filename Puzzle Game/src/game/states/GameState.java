package game.states;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import game.levels.Level;
import game.levels.LevelTimer;
import game.userinterface.ClickListener;
import game.userinterface.TextButton;
import game.Game;
import game.graphics.Tile;

public class GameState extends State {
	
	private Level level;
	private int levelNum;
	private LevelTimer timer;
	
	private TextButton[] textButtons;
	
	public GameState(Game game) {
		
		super(game);
	
		levelNum = 1;
		level = new Level(game, levelNum);
		game.getAssets().setLevel(level);
		timer = new LevelTimer(game);
		
		createTextButtons();
		
	}
	
	private void createTextButtons() {
		
		final int IMPACTBOLD50_INDEX = 0;
		
		textButtons = new TextButton[2];
		textButtons[0] = new TextButton(game, game.getAssets().getFonts()[IMPACTBOLD50_INDEX], "NEXT", 
				        				game.getWidth() - level.getWidth() * Tile.getTileWidth() / 4, SPACING, new ClickListener() {

			@Override
			public void onClick() {
				
				final int MAX_LEVEL_NUM = 20;
				
				if (levelNum + 1 <= MAX_LEVEL_NUM) {
					levelNum += 1;
					level = new Level(game, levelNum);
					level.getEntityManager().getPlayer().resetCountMoves();
					game.getAssets().setLevel(level);
					game.getMouseManager().setLeftPressedToFalse();
					timer.resetTimer();
				}
				
			}
			
		});
		
		textButtons[1] = new TextButton(game, game.getAssets().getFonts()[IMPACTBOLD50_INDEX], "BACK", 
						level.getWidth() * Tile.getTileWidth() / 4, SPACING, new ClickListener() {

			@Override
			public void onClick() {
				
				if (levelNum - 1 > 0) {
					levelNum -= 1;
					level = new Level(game, levelNum);
					level.getEntityManager().getPlayer().resetCountMoves();
					game.getAssets().setLevel(level);
					game.getMouseManager().setLeftPressedToFalse();
					timer.resetTimer();
				}
				
			}
			
		});
		
	}
	
	@Override
	public void update() {
		
		for (TextButton button : textButtons) {
			button.update();
		}
		
		final int SPACE_INDEX = 4;
		
		if (game.getKeyManager().getInputs()[SPACE_INDEX]) {
			level = new Level(game, levelNum);
			level.getEntityManager().getPlayer().resetCountMoves();
			game.getAssets().setLevel(level);
			timer.resetTimer();
		}
		
		level.update();
		
	}

	@Override
	public void render(Graphics image) {
		
		for (TextButton textButton : textButtons) {
			textButton.render(image);
		}
		
		String levelNumString = "LEVEL " + levelNum;
		String countMoves = "MOVES: " + level.getEntityManager().getPlayer().getCountMoves();
		FontMetrics fontMetrics = image.getFontMetrics();
		
		game.getAssets().drawString(image, timer.getTimerString(), game.getWidth() / 2, SPACING, true, Color.white);
		game.getAssets().drawString(image, levelNumString, SPACING / 2, game.getHeight() - SPACING / 2, false, Color.white);
		game.getAssets().drawString(image, countMoves, game.getWidth() - fontMetrics.stringWidth(countMoves) - SPACING / 2, 
									game.getHeight() - SPACING / 2, false, Color.white);
		
		image.translate((game.getWidth() - level.getWidth() * Tile.getTileWidth()) / 2, 
						(game.getHeight() - level.getHeight() * Tile.getTileHeight()) / 2);
		level.render(image);
		
	}
	
}