package com.example.lithium;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	
	private SurfaceHolder surfaceholder;
	private GamePanel gamepanel;
	private boolean running;
	float dt;

	public MainThread(SurfaceHolder holder, GamePanel gamePanel) {
		
		this.surfaceholder = holder;
		this.gamepanel = gamePanel ;
		dt = 0;
	}
	
	void setRunning(boolean running){
		
		this.running = running;
		
	}
	
	
	@Override
	public void run() {
		
		Canvas canvas;
		
		while(running){
			if(!gamepanel.Pause_game){
				
				long StartDraw = System.currentTimeMillis();
				canvas = null;
				try{
					canvas = this.surfaceholder.lockCanvas();
					synchronized (surfaceholder) {
						gamepanel.Update(dt);
						gamepanel.Draw(canvas);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				finally{
					if(canvas!=null){
						surfaceholder.unlockCanvasAndPost(canvas);
					}
				}
				
				
				
				
				long EndDraw = System.currentTimeMillis();
				dt = (EndDraw-StartDraw)/1000.f;
				
			}
		}
		
		
	}

}
