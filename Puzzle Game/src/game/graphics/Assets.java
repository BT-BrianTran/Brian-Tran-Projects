package game.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.levels.Level;

public class Assets {
	
	private BufferedImage[] images, playerDown, playerUp, playerRight, playerLeft;
	private Tile[] tiles;
	private Font[] fonts;
	private Level level;
	
	public Assets() {
		
		BufferedImage spriteSheet = loadImage("/textures/Spritesheet.png");
		BufferedImage goal = crop(spriteSheet, 50, 25, 50, 40);
		BufferedImage star = crop(spriteSheet, 100, 0, 50, 50);
		
		images = new BufferedImage[2];
		images[0] = goal;
		images[1] = star;
		
		final int PLAYER_FRAME_SIZE = 3;
		
		playerDown = new BufferedImage[PLAYER_FRAME_SIZE];
		playerDown[0] = crop(spriteSheet, 45, 65, 35, 36);
		playerDown[1] = crop(spriteSheet, 0, 65, 35, 36);
		playerDown[2] = crop(spriteSheet, 95, 65, 35, 36);
		
		playerUp = new BufferedImage[PLAYER_FRAME_SIZE];
		playerUp[0] = crop(spriteSheet, 45, 137, 35, 36);
		playerUp[1] = crop(spriteSheet, 0, 137, 35, 36);
		playerUp[2] = crop(spriteSheet, 95, 137, 35, 36);
		
		playerRight = new BufferedImage[PLAYER_FRAME_SIZE];
		playerRight[0] = crop(spriteSheet, 45, 101, 35, 36);
		playerRight[1] = crop(spriteSheet, 0, 101, 35, 36);
		playerRight[2] = crop(spriteSheet, 95, 101, 35, 36);
		
		playerLeft = new BufferedImage[PLAYER_FRAME_SIZE];
		playerLeft[0] = crop(spriteSheet, 45, 173, 35, 36);
		playerLeft[1] = crop(spriteSheet, 0, 173, 35, 36);
		playerLeft[2] = crop(spriteSheet, 95, 173, 35, 36);
		
		Tile floor = new Tile(crop(spriteSheet, 0, 0, 50, 50), false);
		Tile grass = new Tile(crop(spriteSheet, 150, 100, 50, 50), false);
		Tile brick = new Tile(crop(spriteSheet, 150, 50, 50, 50), true);
		Tile wood = new Tile(crop(spriteSheet, 150, 0, 50, 50), true);
		
		tiles = new Tile[4];
		tiles[0] = floor;
		tiles[1] = grass;
		tiles[2] = brick;
		tiles[3] = wood;

		fonts = new Font[3];
		fonts[0] = new Font("Impact", Font.BOLD, 50);
		fonts[1] = new Font("Impact", Font.BOLD, 75);
		fonts[2] = new Font("Impact", Font.BOLD, 100);
		
	}
	
	private BufferedImage loadImage(String fileName) {
		
		try {
			return ImageIO.read(Assets.class.getResource(fileName));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private BufferedImage crop(BufferedImage image, int x, int y, int width, int height) {
		
		return image.getSubimage(x, y, width, height);
		
	}
	
	public void drawString(Graphics image, String text, int x, int y, boolean center, Color color) {
		
		if (center) {
			FontMetrics fontMetrics = image.getFontMetrics();
			x = x - fontMetrics.stringWidth(text) / 2;
			y = (y - fontMetrics.getHeight() / 2) + fontMetrics.getAscent();
		}
		
		image.setColor(color);
		image.drawString(text, x, y);
		
	}
	
	public BufferedImage[] getImages() {
		
		return images;
		
	}
	
	public BufferedImage[] getPlayerDown() {
		
		return playerDown;
		
	}
	
	public BufferedImage[] getPlayerUp() {
		
		return playerUp;
		
	}
	
	public BufferedImage[] getPlayerRight() {
		
		return playerRight;
		
	}
	
	public BufferedImage[] getPlayerLeft() {
		
		return playerLeft;
		
	}
	
	public Tile[] getTiles() {
		
		return tiles;
		
	}
	
	public Font[] getFonts() {
		
		return fonts;
		
	}
	
	public Level getLevel() {
		
		return level;
		
	}

	public void setLevel(Level level) {
		
		this.level = level;
		
	}
	
}