package game.states;

import java.awt.Graphics;

import game.Game;

public abstract class State {

	Game game;
	final int SPACING;

	public State(Game game) {
		
		this.game = game;
		SPACING = 50;
		
	}
	
	public abstract void update();
	public abstract void render(Graphics image);
	
}