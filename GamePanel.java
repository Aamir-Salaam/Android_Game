package com.example.lithium;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

	public MainThread thread;
	public boolean Pause_game;
	private Background background;
	private Ship ship;
	private Bonus coin;
	private Barriermanager BM;
	public float Shipspeed;
	public int ScreenWidth;
	public int ScreenHeight;
	public Game game;
	
	public GamePanel(Context context, Game game,int ScreenWidth,int ScreenHeight) {
		super(context);
		getHolder().addCallback(this);
		this.game=game; 
		thread = new MainThread(getHolder(),this);
		
		background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.simplespace),ScreenWidth , this);
		BM= new Barriermanager(BitmapFactory.decodeResource(getResources(), R.drawable.barrier),this);
		BM.setScreen(ScreenWidth, ScreenHeight);
		ship = new Ship(BitmapFactory.decodeResource(getResources(), R.drawable.shipun),100,0,ScreenWidth,ScreenHeight);
		coin = new Bonus(BitmapFactory.decodeResource(getResources(), R.drawable.astbis),-200,-200);
		ArrayList<Bitmap> animation = new ArrayList<Bitmap>();
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.expone));
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.exptwo));
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.expthree));
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.expfour));
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.expfive));
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.expsix));
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.expseven));
		ship.setBoomAnimation(animation);
		coin.setBarrierManager(BM);
		setFocusable(true);
		Shipspeed = ScreenWidth/2.f;
		this.ScreenWidth = ScreenWidth;
		this.ScreenHeight = ScreenHeight;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			ship.up=true;
		}
		if(event.getAction()==MotionEvent.ACTION_UP){
			ship.up=false;
		}
		return true;
	}
	
	void Draw(Canvas canvas){
		if (!Pause_game)
			if(canvas!=null){
				
			   canvas.drawColor(Color.BLACK);
		       background.draw(canvas);
		       coin.draw(canvas);
		       ship.draw(canvas);
		       BM.draw(canvas);
			}
	}
	
	void Update(float dt){
		
		ship.update(dt);
		
		if(!ship.death){
			background.update(dt);
			coin.update(dt);
			BM.update(dt);
			ArrayList<Point> coin_point = new ArrayList<Point>(coin.GetArray());
			if(ship.bump(coin_point.get(0), coin_point.get(1), coin_point.get(2), coin_point.get(3))){
				coin.setX(-200);
				coin.setY(-200);
				
				Message msg=BM.game_panel.game.handler.obtainMessage();
				msg.what=0;
				BM.game_panel.game.handler.sendMessage(msg);
			}
		for(int i =0;i<BM.TopWalls.size();i++){
			ArrayList< Point>temp = new ArrayList<Point>(BM.TopWalls.get(i).GetArray());
			ArrayList< Point>temp2 = new ArrayList<Point>(BM.BottomWalls.get(i).GetArray());
			if ((ship.bump(temp.get(0), temp.get(1), temp.get(2), temp.get(3)))||(ship.bump(temp2.get(0), temp2.get(1), temp2.get(2), temp2.get(3))))
			{
				ship.death=true;
				MediaPlayer mp = MediaPlayer.create(game,R.raw.blast);
				mp.start();
				Message msg=BM.game_panel.game.handler.obtainMessage();
				msg.what=1;
				BM.game_panel.game.handler.sendMessage(msg);
			}
		}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		boolean retry = true;
		while (retry) {
			try{
				thread.join();
				retry = false;
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
		}
		
	}

}
