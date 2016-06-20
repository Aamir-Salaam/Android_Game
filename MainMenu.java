package com.example.lithium;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainMenu extends Activity {
  
	MediaPlayer  MainMenuMusic;
	
	RelativeLayout Btn;
    ImageView ImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
      
        
        Btn = (RelativeLayout)findViewById(R.id.button_start);
        ImageButton = (ImageView)findViewById(R.id.image_button);
      
      Btn.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			return false;
		}
	});
      
      Btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainMenu.this,Game.class);
			startActivity(i);
			
		}
	});
      
      
    }
	@Override
	protected void onStart() {
		MainMenuMusic = MediaPlayer.create(MainMenu.this, R.raw.youwin);
		MainMenuMusic.setVolume(0.3f, 0.3f);
		MainMenuMusic.start();
		super.onStart();
	}
	@Override
	protected void onStop() {
		if(MainMenuMusic.isPlaying()){
			MainMenuMusic.stop();
		}
		super.onStop();
	}
   

}

