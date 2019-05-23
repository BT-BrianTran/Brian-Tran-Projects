package game.levels;

import java.util.Timer;
import java.util.TimerTask;

import game.Game;

public class LevelTimer {
	
	private Game game;
	private Timer timer;
	
	private final int SEC_IN_MILLI = 1000, SECS_PER_MIN = 60, SECS_PER_HOUR = 3600;
	private int seconds;
	
	public LevelTimer(Game game) {
		
		this.game = game;
		startTimer();
		
	}
	
	private void startTimer() {
		
		timer = new Timer();
		
		TimerTask task = new TimerTask() {
			
			public void run() {
				
				final int GAME_INDEX = 2;
				
				if (game.getState().equals(game.getStates()[GAME_INDEX])) {
					if (!game.getAssets().getLevel().getLevelSolved()) {
						seconds++;
					}
					else {
						timer.cancel();
					}
				}
		
			}
			
		};
		
		timer.schedule(task, SEC_IN_MILLI, SEC_IN_MILLI);
		
	}
	
	public void resetTimer() {
		
		seconds = 0;
		timer.cancel();
        startTimer();

	}
	
	public String getTimerString() {
		
		return String.format("%02d:%02d:%02d", seconds / SECS_PER_HOUR, seconds % SECS_PER_HOUR / SECS_PER_MIN, seconds % SECS_PER_MIN);
		
	}

}