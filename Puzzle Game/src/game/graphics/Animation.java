package game.graphics;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] animFrames;
	private int index;
	
	private final int CHANGE_FRAME_SPEED;
	private long timePassed, startTime;
	
	public Animation(BufferedImage[] animFrames) {
		
		this.animFrames = animFrames;
		CHANGE_FRAME_SPEED = 150;
		
	}
	
	public void update() {
		
		timePassed += System.currentTimeMillis() - startTime;
		
		if (timePassed >= CHANGE_FRAME_SPEED) {
			index++;
			if (index == animFrames.length) {
				index = 0;
			}
			timePassed = 0;
		}
		
		startTime = System.currentTimeMillis();
		
	}
	
	public BufferedImage getCurrentFrame() {
		
		return animFrames[index];
		
	}
	
	public BufferedImage resetIndex() {
		
		index = 0;
		
		return animFrames[index];
		
	}

}