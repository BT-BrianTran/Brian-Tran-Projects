package game.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import game.graphics.Animation;
import game.graphics.Tile;

public class Player extends Entity {
	
	private Animation animDown, animUp, animLeft, animRight;

	private final int SPEED;
	private int xMove, yMove, playerXIfMove, playerYIfMove, lastXMove, lastYMove, countMoves;
		
	private Star collidedStar;
	private int starXIfMove, starYIfMove;
	
	public Player(Game game, int x, int y) {
		
		super(game, x, y);
		
		animDown = new Animation(game.getAssets().getPlayerDown());
		animUp = new Animation(game.getAssets().getPlayerUp());
		animLeft = new Animation(game.getAssets().getPlayerLeft());
		animRight = new Animation(game.getAssets().getPlayerRight());
		
		SPEED = 2;
		
	}
	
	void update() {
		
		if (!game.getAssets().getLevel().getLevelSolved()) {
			getInput();
			move();
			if (yMove > 0) {
				animDown.update();
			}
			else if (yMove < 0) {
				animUp.update();
			}
			else if (xMove > 0) {
				animRight.update();
			}
			else if (xMove < 0) {
				animLeft.update();
			}
		}

	}
	
	@Override
	void render(Graphics image) {
		
		image.drawImage(getCurrentAnimationFrame(), x, y, ENTITYWIDTH, ENTITYHEIGHT, null);
		
	}
	
	private void getInput() {
		
		if (x % Tile.getTileWidth() == 0 && y % Tile.getTileHeight() == 0) {
			final int UP_INDEX = 0;
			final int DOWN_INDEX = 1;
			final int LEFT_INDEX = 2;
			final int RIGHT_INDEX = 3;
			xMove = yMove = 0;
			if (game.getKeyManager().getInputs()[UP_INDEX]) {
				yMove = -SPEED;
				return;
			}
			else if (game.getKeyManager().getInputs()[DOWN_INDEX]) {
				yMove = SPEED;
				return;
			}
			else if (game.getKeyManager().getInputs()[LEFT_INDEX]) {
				xMove = -SPEED;
				return;
			}
			else if (game.getKeyManager().getInputs()[RIGHT_INDEX]) {
				xMove = SPEED;
				return;
			}
		}
		
	}
	
	private void move() {
		
		if (xMove > 0) {
			if (!starCollision(xMove, 0)) {
				playerXIfMove = (x + xMove + ENTITYWIDTH) / Tile.getTileWidth();
				movePlayerX();
			}
			else {
				starXIfMove = (collidedStar.x + xMove + ENTITYWIDTH) / Tile.getTileWidth();
				movePlayerAndStarX();
			}
		}
		else if (xMove < 0) {
			if (!starCollision(xMove, 0)) {
				playerXIfMove = (x + xMove) / Tile.getTileWidth();
				movePlayerX();
			}
			else {
				starXIfMove = (collidedStar.x + xMove) / Tile.getTileWidth();
				movePlayerAndStarX();
			}
		}
		else if (yMove < 0) { 
			if (!starCollision(0, yMove)) {
				playerYIfMove = (y + yMove) / Tile.getTileHeight();
				movePlayerY();
			}
			else {
				starYIfMove = (collidedStar.y + yMove) / Tile.getTileHeight();
				movePlayerAndStarY();
			}
		}
		else if (yMove > 0) { 
			if (!starCollision(0, yMove)) {
				playerYIfMove = (y + yMove + ENTITYHEIGHT) / Tile.getTileHeight();
				movePlayerY();
			}
			else {
				starYIfMove = (collidedStar.y + yMove + ENTITYHEIGHT) / Tile.getTileHeight();
				movePlayerAndStarY();
			}
		}
	
	}

	private void movePlayerX() {
		
		if (!tileCollision(playerXIfMove, y / Tile.getTileHeight()) && 
			!tileCollision(playerXIfMove, (y + ENTITYHEIGHT) / Tile.getTileHeight())) {
			x += xMove;
			if (x % Tile.getTileWidth() == 0) {
				countMoves += 1;
			}
		}
		
	}

	private void movePlayerAndStarX() {
		
		if (!blocked(xMove, 0) && !tileCollision(starXIfMove, collidedStar.y / Tile.getTileHeight()) && 
			!tileCollision(starXIfMove, (collidedStar.y + ENTITYHEIGHT) / Tile.getTileHeight())) {
			x += xMove;
			collidedStar.x += xMove;
			if (x % Tile.getTileWidth() == 0) {
				countMoves += 1;
			}
		}
		
	}

	private void movePlayerY() {
		
		if (!tileCollision(x / Tile.getTileWidth(), playerYIfMove) && 
			!tileCollision((x + ENTITYWIDTH) / Tile.getTileWidth(), playerYIfMove)) {
			y += yMove;
			if (y % Tile.getTileHeight() == 0) {
				countMoves += 1;
			}
		}
		
	}

	private void movePlayerAndStarY() {
		
		if (!blocked(0, yMove) && !tileCollision(collidedStar.x / Tile.getTileWidth(), starYIfMove) && 
			!tileCollision((collidedStar.x + ENTITYWIDTH) / Tile.getTileWidth(), starYIfMove)) {
			y += yMove;
			collidedStar.y += yMove;
			if (y % Tile.getTileHeight() == 0) {
				countMoves += 1;
			}
		}
	
	}

	private boolean starCollision(int xMove, int yMove) {
		
		for (Star star : game.getAssets().getLevel().getEntityManager().getStars()) {
			if (boundsIfMove(xMove, yMove).intersects(star.boundsIfMove(0, 0))) {
				collidedStar = star;
				return true;
			}
		}
		
		return false;
		
	}
	
	private boolean tileCollision(int x, int y) {
		
		return game.getAssets().getLevel().getTile(x, y).getSolid();
		
	}

	private boolean blocked(int xMove, int yMove) {
		
		for (Star star : game.getAssets().getLevel().getEntityManager().getStars()) {
			if (x + xMove * Tile.getTileWidth() * 2 / SPEED == star.x && y + yMove * Tile.getTileHeight() * 2 / SPEED == star.y) {
				return true;
			}
		}
		
		return false;
		
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		
		if (xMove == 0 && yMove == 0 && lastXMove == 0 && lastYMove == 0) {
			return animDown.getCurrentFrame();
		}
		else if (yMove > 0) {
			lastYMove = yMove;
			lastXMove = 0;
			return animDown.getCurrentFrame();
		}
		else if (yMove < 0) {
			lastYMove = yMove;
			lastXMove = 0;
			return animUp.getCurrentFrame();
		}
		else if (xMove < 0) {
			lastXMove = xMove;
			lastYMove = 0;
			return animLeft.getCurrentFrame();
		}
		else if (xMove > 0) {
			lastXMove = xMove;
			lastYMove = 0;
			return animRight.getCurrentFrame();
		}
		
		if (yMove == 0) {
			if (lastYMove > 0) {
				return animDown.resetIndex();
			}
			else if (lastYMove < 0) {
				return animUp.resetIndex();
			}
		}
		
		if (xMove == 0) {
			if (lastXMove < 0) {
				return animLeft.resetIndex();
			}
			else if (lastXMove > 0) {
				return animRight.resetIndex();
			}
		}
		
		return null;
		
	}
	
	public int getCountMoves() {
		
		return countMoves;
		
	}
	
	public void resetCountMoves() {
		
		countMoves = 0;
		
	}

}