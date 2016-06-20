package com.example.lithium;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game extends Activity {
    
	final static int UPDATE_SCORE=0;
	final static int DEATH =1;
	final static int LOSE = 2;
	
	View pausebutton;
	View pausemenu;
	View WinDialog;
	View LoseDialog;
	RelativeLayout Rel_main_game;
    GamePanel game_panel;
    TextView txt;
    
    MediaPlayer MainMusic;
    
    int get_coin=0;
    int score =0;
    final Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(msg.what==UPDATE_SCORE){
				i_get_coin();
			}
			if(msg.what==DEATH){
				postDelayed(new Runnable() {
					
					@Override
					public void run() {
						Message msg= handler.obtainMessage();
						msg.what=LOSE;
						handler.sendMessage(msg);
						
					}
				}, 3000);
			}
			if(msg.what==LOSE){
				i_lose();
			}
			super.handleMessage(msg);
		}
		
	};
    
	OnClickListener Continue_List = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			pausemenu.setVisibility(View.GONE);
			pausebutton.setVisibility(View.VISIBLE);
			game_panel.Pause_game=false;
		}
	};
	
	OnClickListener To_Main_Menu_List = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			game_panel.thread.setRunning(false);
			Game.this.finish();
			
					}
	};
	OnClickListener Pause_click = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			pausebutton.setVisibility(View.GONE);
			pausemenu.setVisibility(View.VISIBLE);
			game_panel.Pause_game=true;//Pause Start
			
		}
	};
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		Rel_main_game=(RelativeLayout)findViewById(R.id.main_game_r);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		final int heights = dm.heightPixels;
		final int widths = dm.widthPixels;
		game_panel = new GamePanel(getApplicationContext(),this,widths,heights);
		Rel_main_game.addView(game_panel);
		
		RelativeLayout RR = new RelativeLayout(this);
		RR.setBackgroundResource(R.drawable.pausemenu);
		RR.setGravity(Gravity.CENTER);
		Rel_main_game.addView(RR,270,85);
		RR.setX(0);
		txt= new TextView(this);
		txt.setTextColor(Color.RED);
		txt.setText("Score: " + score);
		RR.addView(txt);
		
		LayoutInflater myinflater = (LayoutInflater)getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
		pausebutton = myinflater.inflate(R.layout.pause, null, false);
	    pausebutton.setX(widths-175);
	    pausebutton.setY(0);
	    Rel_main_game.addView(pausebutton);
	    pausebutton.setOnClickListener(Pause_click);
	    pausebutton.getLayoutParams().height=150;
	    pausebutton.getLayoutParams().width=150;
	    pausemenu = myinflater.inflate(R.layout.pause_menu, null, false);
	    Rel_main_game.addView(pausemenu);
	    pausemenu.setVisibility(View.GONE);
	    ImageView img=(ImageView)findViewById(R.id.imageView);
	    ImageView Cont = (ImageView)findViewById(R.id.imCont);
	    ImageView MainMenuTo = (ImageView)findViewById(R.id.toMain);
	    TextView TextCont = (TextView)findViewById(R.id.continue_g);
	    TextView TextMainMenu = (TextView)findViewById(R.id.go_MainMenu);
	    Cont.setOnClickListener(Continue_List);
	    MainMenuTo.setOnClickListener(To_Main_Menu_List);
	    
	    WinDialog= myinflater.inflate(R.layout.win, null, false);
		Rel_main_game.addView(WinDialog);
		ImageView Win_to_main = (ImageView) WinDialog.findViewById(R.id.toMain);
		Win_to_main.setOnClickListener(To_Main_Menu_List);
		WinDialog.setVisibility(View.GONE);
		
		LoseDialog= myinflater.inflate(R.layout.lose, null, false);
		Rel_main_game.addView(LoseDialog);
		ImageView Lose_to_main = (ImageView) LoseDialog.findViewById(R.id.toMain);
		Lose_to_main.setOnClickListener(To_Main_Menu_List);
		LoseDialog.setVisibility(View.GONE);
	    
		MainMusic = MediaPlayer.create(Game.this,R.raw.spacesound);
		MainMusic.setVolume(0.3f, 0.3f);
		MainMusic.start();
	}
	
	


	@Override
	public void onBackPressed() {
		pausebutton.setVisibility(View.GONE);
		pausemenu.setVisibility(View.VISIBLE);
		game_panel.Pause_game=true;//Pause Start

	}




	@Override
	protected void onStop() {
		if(MainMusic.isPlaying()){
			MainMusic.stop();
		}
		super.onStop();
	}




	protected void i_lose() {
	
		if(MainMusic.isPlaying()){
			MainMusic.stop();
		}
		MainMusic=MediaPlayer.create(Game.this,R.raw.youlose);
		MainMusic.start();
		
	  game_panel.Pause_game=true;
	  LoseDialog.setVisibility(View.VISIBLE);
		
	}


	protected void i_get_coin() {
		get_coin++;
		score+=200;
		txt.setText("Score: " + score);
		MediaPlayer mp = MediaPlayer.create(Game.this,R.raw.gotcoin);
		mp.start();
		if(get_coin==100){
			i_win();
		}
	}


	private void i_win() {
		
		if(MainMusic.isPlaying()){
			MainMusic.stop();
		}
		MainMusic=MediaPlayer.create(Game.this,R.raw.youwin);
		MainMusic.start();
		game_panel.Pause_game=true;
		WinDialog.setVisibility(View.VISIBLE);
		
	}


}
