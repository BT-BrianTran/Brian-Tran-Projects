package game.levels;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import game.entities.EntityManager;
import game.entities.Player;
import game.entities.Star;
import game.entities.StarGoal;
import game.graphics.Tile;
import game.Game;

public class Level {

	private Game game;
	
	private int[][] tileMap;
	private int width, height;
	private EntityManager entityManager;
	
	private boolean levelSolved;
	
	public Level(Game game, int levelNum) {
		
		this.game = game;
		loadLevel(levelNum);
			
	}
	
	private void loadLevel(int levelNum) {
		
		String levelFile = loadFileAsString("res/levels/Level" + levelNum + ".txt");
		String objects[] = levelFile.split("\\s+");
		
		final int WIDTH_INDEX = 0, HEIGHT_INDEX = 1, SPAWN_X_INDEX = 2, SPAWN_Y_INDEX = 3, OFFSET = 4;
		width = parseInt(objects[WIDTH_INDEX]);
		height = parseInt(objects[HEIGHT_INDEX]);
		int SPAWN_X = parseInt(objects[SPAWN_X_INDEX]);
		int SPAWN_Y = parseInt(objects[SPAWN_Y_INDEX]);
		
		tileMap = new int[width][height];
		entityManager = new EntityManager(new Player(game, SPAWN_X * Tile.getTileWidth(), SPAWN_Y * Tile.getTileHeight()));
		
		final int STAR_ID = 4, GOAL_ID = 5, STAR_AND_GOAL_ID = 6;
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileMap[x][y] = parseInt(objects[x + y * width + OFFSET]);
				if (tileMap[x][y] == STAR_ID) {
					entityManager.addStar(new Star(game, x * Tile.getTileWidth(), y * Tile.getTileHeight()));
				}
				else if (tileMap[x][y] == GOAL_ID) {
					entityManager.addStarGoal(new StarGoal(game, x * Tile.getTileWidth(), y * Tile.getTileHeight()));
				}
				else if (tileMap[x][y] == STAR_AND_GOAL_ID) {
					entityManager.addStar(new Star(game, x * Tile.getTileWidth(), y * Tile.getTileHeight()));
					entityManager.addStarGoal(new StarGoal(game, x * Tile.getTileWidth(), y * Tile.getTileHeight()));
				}
			}
		}
		
	}
	
	private String loadFileAsString(String fileName) {
		
		StringBuilder builder = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return builder.toString();
		
	}
	
	private int parseInt(String number) {
		
		try {
			return Integer.parseInt(number);
		} 
		catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	public void update() {
		
		entityManager.update();
		
	}
	
	public void render(Graphics image) {
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getTile(x, y).render(image, x * Tile.getTileWidth(), y * Tile.getTileHeight());
			}
		}
		
		entityManager.render(image);
		
		if (checkLevelSolved()) {
			final int IMPACTBOLD100_INDEX = 2;
			image.setFont(game.getAssets().getFonts()[IMPACTBOLD100_INDEX]);
			game.getAssets().drawString(image, "SOLVED", width * Tile.getTileWidth() / 2, height * Tile.getTileHeight() / 2, true, 
										Color.green);
		}
		
	}
	
	public Tile getTile(int x, int y) {
		
		final int FLOOR_ID = 0, GRASS_ID = 1, BRICK_ID = 2, WOOD_ID = 3;
		
		if (tileMap[x][y] == FLOOR_ID) {
			return game.getAssets().getTiles()[FLOOR_ID];
		}
		else if (tileMap[x][y] == GRASS_ID) {
			return game.getAssets().getTiles()[GRASS_ID];
		}
		else if (tileMap[x][y] == BRICK_ID) {
			return game.getAssets().getTiles()[BRICK_ID];
		}
		else if (tileMap[x][y] == WOOD_ID) {
			return game.getAssets().getTiles()[WOOD_ID];
		}
		
		return game.getAssets().getTiles()[FLOOR_ID];
		
	}
	
	public boolean checkLevelSolved() {
		
		int goalsCompleted = 0;
		
		for (int i = 0; i < entityManager.getStars().size(); i++) {
			for (int j = 0; j < entityManager.getStarGoals().size(); j++) {
				if (entityManager.getStars().get(i).getX() == entityManager.getStarGoals().get(j).getX() && 
					entityManager.getStars().get(i).getY() == entityManager.getStarGoals().get(j).getY()) {
					goalsCompleted += 1;
				}
			}
		}
		
		if (goalsCompleted == entityManager.getStarGoals().size()) {
			if (!levelSolved) {
				game.getMusicPlayer().playMusic("LevelSolved");
			}
			levelSolved = true;
		}
		
		return levelSolved;
		
	}
	
	public int getWidth() {
		
		return width;
		
	}
	
	public int getHeight() {
		
		return height;
		
	}

	public EntityManager getEntityManager() {
		
		return entityManager;
		
	}
	
	public boolean getLevelSolved() {
		
		return levelSolved;
		
	}

}