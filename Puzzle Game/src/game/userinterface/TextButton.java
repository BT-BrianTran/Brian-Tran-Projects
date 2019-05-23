package game.userinterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.Game;

public class TextButton {
	
	private Game game;
	private Font font;
	private String text;
	private int x, y;
	private ClickListener click;
	private Rectangle bounds;
	private boolean hovering;
	
	public TextButton(Game game, Font font, String text, int x, int y, ClickListener click) {
		
		this.game = game;
		this.font = font;
		this.text = text;
		this.x = x;
		this.y = y;
		this.click = click;
	
	}
	
	public void update() {	
		
		if (bounds.contains(game.getMouseManager().getMouseX(), game.getMouseManager().getMouseY())) {
			hovering = true;
			if (game.getMouseManager().getLeftPressed()) {
				click.onClick();
			}
		}
		else {
			hovering = false;
		}
		
	}
	
	public void render(Graphics image) {
		
		image.setFont(font);
		FontMetrics fontMetrics = image.getFontMetrics();
		
		bounds = new Rectangle(x - fontMetrics.stringWidth(text) / 2, y - fontMetrics.getHeight() / 2, fontMetrics.stringWidth(text), 
				   			   fontMetrics.getHeight());
		
		if (hovering) {
			game.getAssets().drawString(image, text, x, y, true, Color.WHITE);
		}
		else {
			game.getAssets().drawString(image, text, x, y, true, Color.RED);
		}
		
	}
	
}