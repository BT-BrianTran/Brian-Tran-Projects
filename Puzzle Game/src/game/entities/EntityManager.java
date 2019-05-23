package game.entities;

import java.awt.Graphics;
import java.util.ArrayList;

public class EntityManager {
	
	private Player player;
	private ArrayList<StarGoal> goals;
	private ArrayList<Star> stars;
	
	public EntityManager(Player player) {
		
		this.player = player;
		goals = new ArrayList<StarGoal>();
		stars = new ArrayList<Star>();
		
	}
	
	public void update() {
		
		player.update();
		
	}
	
	public void render(Graphics image) {
		
		for (StarGoal goal : goals) {
			goal.render(image);
		}
		
		for (Star star : stars) {
			star.render(image);
		}

		player.render(image);
		
	}
	
	public Player getPlayer() {
		
		return player;
		
	}
	
	public void addStarGoal(StarGoal goal) {
		
		goals.add(goal);
		
	}
	
	public void addStar(Star star) {
		
		stars.add(star);
		
	}
	
	public ArrayList<StarGoal> getStarGoals() {
		
		return goals;
		
	}
	
	public final ArrayList<Star> getStars() {
		
		return stars;
		
	}

}