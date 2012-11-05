/*
 * Copyright 2011 Fred,Fan Fangqing <fangqing.fan@hotmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.fanfq.livewallpaper.esdd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public abstract class ESDDAnimal implements Renderable {
	private static int MAX_SPEED = 100;
	private Context _context;
	private ESDD _esdd;
	private BlockSprite _blockSprite;//add by fanfq
	private BlockSprite _leftSprite;
	private BlockSprite _rightSprite;
	
	private int _direction = -1;
	private int _speedFraction;
	private long _previousTime;
	
	
	public ESDDAnimal(Context context, ESDD esdd){
		this._context = context;
		this._esdd = esdd;		
	}
	
	//add by fanfq
	protected void initialize(Bitmap bitmap,Point point){
		this._blockSprite = new BlockSprite(bitmap, point);
	}
	
//	protected void initialize(Bitmap leftBitmap, Bitmap rightBitmap, int fps, int totalFrames, Point startPoint, int speed){		
//		this._leftSprite = new BlockSprite(leftBitmap, fps, totalFrames, startPoint);
//		this._rightSprite = new BlockSprite(rightBitmap, fps, totalFrames, startPoint);		
//		this._speedFraction = (MAX_SPEED / speed) * 10;		 
//	}
	
	private BlockSprite getSprite(){
		return this._blockSprite;
//		if(this._direction < 0){
//			return this._leftSprite;
//		}		
//		return this._rightSprite;
	}
	
//	public int getDirection(){
//		BlockSprite sprite = this.getSprite();
//		int xPos = sprite.getXPos();
//		if(this._direction < 0){
//			xPos += sprite.getWidth();
//		}
//		if(xPos < this._esdd.getLeft()){
//			this._direction = 1;			
//		}else if(xPos > this._esdd.getRight()){
//			this._direction = -1;
//		}else{
//			// Do nothing
//		}
//		
//		return this._direction;
//	}
	
	public Context getContext(){
		return this._context;
	}
	
	public ESDD getESDD(){
		return this._esdd;
	}
	
	@Override
	public void render(Canvas canvas){
		long currentTime = System.currentTimeMillis();		
		this.getSprite().render(canvas, currentTime);
//		this.swim(currentTime);
	}
	
//	public void swim(long currentTime){
//		long diff = currentTime - this._previousTime;
//		if(diff > this._speedFraction){
//			int currentX = this.getSprite().getXPos();			
//			this.getSprite().setXPos(currentX + this.getDirection());
//			this._previousTime = currentTime;
//		}		
//	}
}
