package game.entities;

import java.awt.Graphics;

import game.Game;

public class Star extends Entity {
	
	public Star(Game game, int x, int y) {
		
		super(game, x, y);
		
	}
	
	@Override
	void render(Graphics image) {
		
		final int STAR_INDEX = 1;
		
		image.drawImage(game.getAssets().getImages()[STAR_INDEX], x, y, ENTITYWIDTH, ENTITYHEIGHT, null);
		
	}
	
}