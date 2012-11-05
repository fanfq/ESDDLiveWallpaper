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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class Block extends ESDDAnimal {
	private static final int TOTAL_FRAMES_IN_SPRITE = 2;
	private static final int CLOWN_FISH_FPS = 2;
	private Point point;
	private boolean isFlagged;
	private int mColor;

	public Block(Context context, ESDD esdd, Point point) {
		super(context, esdd);
		createBlock(context, esdd, point);
	}

	public void createBlock(Context context, ESDD esdd, Point point) {
		this.point = point;
		BitmapFactory.Options options = new BitmapFactory.Options();
		if (Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE == Config.BLOCK_STYLE_30PX) {
			options.inSampleSize = 2;
		}
		options.inPurgeable = true;
		setFlagged(false);
		Bitmap mBitmap = null;
		switch ((int) (Math.random() * 4)) {
		case 0:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.o40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.o60_1, options);
			}
			this.mColor = 0;
			break;
		case 1:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.g40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.g60_1, options);
			}
			this.mColor = 1;
			break;
		case 2:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.r40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.r60_1, options);
			}
			this.mColor = 2;
			break;
		case 3:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.y40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.y60_1, options);
			}
			this.mColor = 3;
			break;
		}
		this.initialize(mBitmap, point);
	}

	public void moveBlock(Context context, ESDD esdd, Point point, int index) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		if (Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE == Config.BLOCK_STYLE_30PX) {
			options.inSampleSize = 2;
		}
		options.inPurgeable = true;
		setFlagged(false);
		Bitmap mBitmap = null;
		switch (index) {
		case 0:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.o40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.o60_1, options);
			}
			this.mColor = 0;
			break;
		case 1:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.g40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.g60_1, options);
			}
			this.mColor = 1;
			break;
		case 2:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.r40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.r60_1, options);
			}
			this.mColor = 2;
			break;
		case 3:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.y40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.y60_1, options);
			}
			this.mColor = 3;
			break;
		case 4:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.block40, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.block60, options);
			}
			this.mColor = 4;
			break;
		}
		this.initialize(mBitmap, point);
	}

	public void openBlock(Context context, ESDD esdd, Point point) {
		this.setFlagged(true);
		Bitmap mBitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		if (Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE == Config.BLOCK_STYLE_30PX) {
			options.inSampleSize = 2;
		}
		switch (this.mColor) {
		case 0:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.o40_2, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.o60_2, options);
			}
			break;
		case 1:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.g40_2, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.g60_2, options);
			}
			break;
		case 2:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.r40_2, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.r60_2, options);
			}
			break;
		case 3:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.y40_2, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.y60_2, options);
			}
			break;
		case 4:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.block40, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.block60, options);
			}
			break;
		}
		this.initialize(mBitmap, point);
	}

	public void backBlock(Context context, ESDD esdd, Point point) {
		this.setFlagged(false);
		Bitmap mBitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		if (Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE == Config.BLOCK_STYLE_30PX) {
			options.inSampleSize = 2;
		}
		switch (this.mColor) {
		case 0:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.o40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.o60_1, options);
			}
			break;
		case 1:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.g40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.g60_1, options);
			}
			break;
		case 2:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.r40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.r60_1, options);
			}
			break;
		case 3:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.y40_1, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.y60_1, options);
			}
			break;
		case 4:
			if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.block40, options);
			}else{
				mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
						R.drawable.block60, options);
			}
			break;
		}
		this.initialize(mBitmap, point);
	}

	public void deleteBlock(Context context, ESDD esdd, Point point) {
		this.setFlagged(false);
		this.mColor = 4;
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap mBitmap = null;
		if(Config.BLOCK_STYLE == Config.BLOCK_STYLE_20PX || Config.BLOCK_STYLE==Config.BLOCK_STYLE_40PX){
			mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
				R.drawable.block40, options);
		}else{
			mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.block60, options);
		}

		this.initialize(mBitmap, point);
	}

	public void setFlagged(boolean b) {
		isFlagged = b;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public int getColor() {
		return mColor;
	}

	public Point getPiont() {
		return this.point;
	}

	public void render(Canvas canvas) {
		super.render(canvas);
	}

}
