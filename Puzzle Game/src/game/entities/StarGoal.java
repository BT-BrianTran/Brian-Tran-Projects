package game.entities;

import java.awt.Graphics;

import game.Game;

public class StarGoal extends Entity {
	
	public StarGoal(Game game, int x, int y) {
		
		super(game, x, y);
		
	}
	
	@Override
	void render(Graphics image) {
		
		final int GOAL_INDEX = 0;
		
		image.drawImage(game.getAssets().getImages()[GOAL_INDEX], x, y, ENTITYWIDTH, ENTITYHEIGHT, null);
		
	}
	
}