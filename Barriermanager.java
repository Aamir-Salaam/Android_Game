package com.example.lithium;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Barriermanager {
	
	Bitmap center;
	int shipHeight;
	int Num;
	int screenH;
	int dl;
	int TargetY=-1;
	int dpos;
	public GamePanel game_panel;
    ArrayList<Barrier> TopWalls=null;
    ArrayList<Barrier> BottomWalls=null;
	public Barriermanager(Bitmap decodeResource,GamePanel game_panel) {
		// TODO Auto-generated constructor stub
		center = decodeResource;
		this.game_panel = game_panel;
		
	}
   void setShiph(int h){
	   shipHeight=h;
	   
   }
   void setScreen(int width, int height){
	   screenH=height;
	   Num=(width)/center.getWidth()+4;
	   TopWalls=new ArrayList<Barrier>();
	   BottomWalls=new ArrayList<Barrier>();
	   for(int i=0; i<Num+1;i++){
		   Barrier BB= new Barrier(center,width+200+center.getWidth()*1,0);
		   BB.setManager(this);
		   TopWalls.add(BB);
		   Barrier BBB= new Barrier(center,  width+200+center.getWidth()*i, 0);
		   BBB.setManager(this);
		   BottomWalls.add(BBB);
	   }
	   Generate();
   }
private void Generate() {
	// TODO Auto-generated method stub
	int h = center.getHeight()/2;
	dl = screenH;
	dpos =screenH/2;
	int new_dl = screenH*3/5;
	int inc =  (dl-new_dl)/Num;
	for (int i = 0; i<Num+1; i++){
		dl = dl - inc;
		h =TopWalls.get(i).getBitmap().getHeight()/2;
		TopWalls.get(i).setY(dpos -dl/2-h);
		BottomWalls.get(i).setY(dpos +dl/2+h);
}
}
public void draw(Canvas canvas){
	for (int i=0;i<Num+1; i++){
		TopWalls.get(i).draw(canvas);
		BottomWalls.get(i).draw(canvas);
	}
}
public void update(float dt){
	for (int i=0;i<Num+1; i++){
		TopWalls.get(i).update(dt, true);
		BottomWalls.get(i).update(dt, false);
	}
}
}
