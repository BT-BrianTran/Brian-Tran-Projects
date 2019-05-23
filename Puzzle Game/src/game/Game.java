package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import game.audio.MusicPlayer;
import game.graphics.Assets;
import game.input.KeyManager;
import game.input.MouseManager;
import game.states.ControlState;
import game.states.CreditState;
import game.states.GameState;
import game.states.LoadingState;
import game.states.MenuState;
import game.states.State;

public class Game implements Runnable {
	
	private Canvas canvas;
	private String title;
	private int width, height;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private MusicPlayer musicPlayer;
	
	private Assets assets;

	private State[] states;
	private State currentState;

	private Thread thread;
	private boolean running;

	private void init() {
		
		createWindow();
		
		musicPlayer = new MusicPlayer();
		musicPlayer.playMusic("Background");
		
		assets = new Assets();
		
		states = new State[5];
		states[0] = new LoadingState(this);
		states[1] = new MenuState(this);
		states[2] = new GameState(this);
		states[3] = new ControlState(this);
		states[4] = new CreditState(this);
		
		currentState = states[0];
		
	}
	
	private void createWindow() {
		
		title = "Starpusher";
		width = height = 1000;
		
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		canvas.addMouseListener(mouseManager);
		canvas.addMouseMotionListener(mouseManager);
		
		JFrame frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(canvas);
		frame.pack();
		frame.addKeyListener(keyManager);
		frame.addMouseListener(mouseManager);
		frame.addMouseMotionListener(mouseManager);
		
	}
	
	private void update() {
		
		currentState.update();
		
	}
	
	private void render() {
		
		BufferStrategy bufferStratgey = canvas.getBufferStrategy();
		
		if (bufferStratgey == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		
		Graphics image = bufferStratgey.getDrawGraphics();
		image.setColor(Color.blue);
		image.fillRect(0, 0, width, height);
		currentState.render(image);
		bufferStratgey.show();
		image.dispose();
		
	}
	
	@Override
	public void run() {
		
		final int MAX_FPS = 60;
		final long TARGET_TIME = 1000 / MAX_FPS;
		long startTime;
		long timePassed;
		long waitTime;
		
		init();
		
		while (running) {
			startTime = System.nanoTime();
			update();
			render();
			timePassed = System.nanoTime() - startTime;
			waitTime = TARGET_TIME - timePassed / 1000000;
			if (waitTime > 0) {
				try {
					Thread.sleep(waitTime);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		stop();
		
	}
	
	private synchronized void start() {
		
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	private synchronized void stop() {
		
		running = false;
		
		try {
			thread.join();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}

	public String getTitle() {
		
		return title;
		
	}
		
	public int getWidth() {
		
		return width;
		
	}
	
	public int getHeight() {
		
		return height;
		
	}
	
	public MusicPlayer getMusicPlayer() {
		
		return musicPlayer;
		
	}
	
	public KeyManager getKeyManager() {
		
		return keyManager;
		
	}
	
	public MouseManager getMouseManager() {
		
		return mouseManager;
		
	}
	
	public Assets getAssets() {
		
		return assets;
		
	}
	
	public State[] getStates() {
		
		return states;
		
	}
	
	public State getState() {
		
		return currentState;
		
	}
	
	public void setState(State state) {
		
		currentState = state;
		
	}
	
	public static void main(String[] args) {
		
		Game game = new Game();
		game.start();
	
	}
	
}