package game.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage texture;
	private boolean solid;
	private static int TILEWIDTH, TILEHEIGHT;

	public Tile(BufferedImage texture, boolean solid) {
		
		this.texture = texture;
		this.solid = solid;
		TILEWIDTH = TILEHEIGHT = 50;

	}
	
	public void render(Graphics image, int x, int y) {
		
		image.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
		
	}
	
	public boolean getSolid() {
		
		return solid;
		
	}
	
	public static int getTileWidth() {
		
		return TILEWIDTH;
		
	}

	public static int getTileHeight() {
	
		return TILEHEIGHT;
	
	}
	
}