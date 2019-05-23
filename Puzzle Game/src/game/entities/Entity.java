package game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.Game;

abstract class Entity {

	Game game;
	int x, y;
	final int ENTITYWIDTH, ENTITYHEIGHT;

	Entity(Game game, int x, int y) {
		
		this.game = game;
		this.x = x;
		this.y = y;
		ENTITYWIDTH = ENTITYHEIGHT = 49;
	
	}
	
	abstract void render(Graphics image);
	
	Rectangle boundsIfMove(int xMove, int yMove) { 
		
		return new Rectangle(x + xMove, y + yMove, ENTITYWIDTH, ENTITYHEIGHT);
		
	}
	
	public float getX() {
		
		return x;
		
	}

	public float getY() {
		
		return y;
		
	}

}