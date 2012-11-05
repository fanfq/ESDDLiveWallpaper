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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

public class ESDD {
	
	private final String tag = "ESDD";
	
	private ESDDThread _esddThread;
	private SurfaceHolder _surfaceHolder;
	private ArrayList<Block> _blockList;
	private Bitmap _backgroundImage;
	private Context _context;

	private int rows;
	private int columns;
	private Block[][] blocks;

	public void render() {
		Canvas canvas = null;
		try {

			canvas = this._surfaceHolder.lockCanvas(null);
			synchronized (this._surfaceHolder) {
				this.onDraw(canvas);
			}

		} finally {
			if (canvas != null) {
				this._surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	protected void onDraw(Canvas canvas) {
		this.renderBackGround(canvas);
		for (Renderable renderable : this._blockList) {
			renderable.render(canvas);
		}
	};

	// public void start(){
	// this._esddThread.switchOn();
	// }

	// public void stop(){
	// boolean retry = true;
	// this._esddThread.switchOff();
	// while (retry) {
	// try {
	// this._esddThread.join();
	// retry = false;
	// } catch (InterruptedException e) {
	// // we will try it again and again...
	// }
	// }
	// }

	public int getLeft() {
		return 0;
	}

	public int getRight() {
		return this._backgroundImage.getWidth();
	}

	public void initialize(Context context, SurfaceHolder surfaceHolder) {

		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getApplicationContext().getResources().getDisplayMetrics();
		Config.screenHight = dm.heightPixels;
		Config.screenWidth = dm.widthPixels;
		rows = dm.widthPixels / Config.BLOCK_STYLE;
		columns = dm.heightPixels / Config.BLOCK_STYLE;
		blocks = new Block[rows][columns];

		this._esddThread = new ESDDThread(this);
		this._surfaceHolder = surfaceHolder;
		this._blockList = new ArrayList<Block>();
		this._context = context;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		this._backgroundImage = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.bg, options);
		this.addBlocks();
	}

	// 3BLOCK_STYLE*480

	private void addBlocks() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Point point = new Point(i * Config.BLOCK_STYLE, j
						* Config.BLOCK_STYLE);
				blocks[i][j] = new Block(this._context, this, point);
				this._blockList.add(blocks[i][j]);
			}
		}
	}

	public void onTouch(int x, int y) {
		final int i = x / Config.BLOCK_STYLE;
		final int j = y / Config.BLOCK_STYLE;
		long beganTimes = System.currentTimeMillis();
		switch (Config.MODEL) {
		case 0:// CLASSIC MODEL

			if (classicModel(i, j)) {
			} else {
				count = 0;
				rippleUncover(i, j);
			}

			break;
		default:// LOOP MODEL
			if (loopModel(i, j)) {
			} else {
				count = 0;
				rippleUncover(i, j);
			}
			break;
		}
		Log.d(tag, "-----ripple tts:"+(System.currentTimeMillis() - beganTimes));
		long beganTimes1 = System.currentTimeMillis();
		render();
		Log.d(tag, "-ui refresh tts:"+(System.currentTimeMillis() - beganTimes1));
		Log.d(tag, "------total tts:"+(System.currentTimeMillis() - beganTimes));
		return;
	}

	private boolean classicModel(int rowClicked, int columnClicked) {
		boolean result = false;

		if(cacheList!=null && cacheList.size()>1){
			
			if(blocks[rowClicked][columnClicked].isFlagged()){
				motion(cacheList);
				result = true;
			}
			
			for(Block block:cacheList){
				int i = block.getPiont().x/Config.BLOCK_STYLE;
				int j = block.getPiont().y/Config.BLOCK_STYLE;
				if (blocks[i][j].isFlagged()) {
					blocks[i][j].backBlock(this._context, this, new Point(i
							* Config.BLOCK_STYLE, j * Config.BLOCK_STYLE));
				}
			}
			cacheList = new ArrayList<Block>();
		}
		return result;
	}

	int clreanCount = 0;
	private static int count;

	private boolean loopModel(int rowClicked, int columnClicked) {

		boolean result = false;
		if(cacheList!=null && cacheList.size()>1){
		
			if(blocks[rowClicked][columnClicked].isFlagged()){
				motion(cacheList);
				result = true;
			}
			
			for(Block block:cacheList){
				int i = block.getPiont().x/Config.BLOCK_STYLE;
				int j = block.getPiont().y/Config.BLOCK_STYLE;
				if (blocks[i][j].isFlagged()) {
					blocks[i][j].backBlock(this._context, this, new Point(i
							* Config.BLOCK_STYLE, j * Config.BLOCK_STYLE));
				}
			}
			cacheList = new ArrayList<Block>();
		}
		
		return result;
	}

	private List<Block> cacheList = new ArrayList<Block>();

	private void rippleUncover(int rowClicked, int columnClicked) {

		if (blocks[rowClicked][columnClicked].isFlagged()
				|| blocks[rowClicked][columnClicked].getColor() == 4) {
			return;
		}

		count++;

		if (count > 1) {
			cacheList.add(blocks[rowClicked][columnClicked]);
			blocks[rowClicked][columnClicked].openBlock(this._context, this,
					new Point(rowClicked * Config.BLOCK_STYLE, columnClicked
							* Config.BLOCK_STYLE));
			// System.out.println(rowClicked + "," + columnClicked);
		}

		if ((rowClicked - 1 >= 0)
				&& (columnClicked >= 0)
				&& (blocks[rowClicked - 1][columnClicked].getColor() == blocks[rowClicked][columnClicked]
						.getColor())) {
			rippleUncover(rowClicked - 1, columnClicked);
		}
		if ((rowClicked >= 0)
				&& (columnClicked - 1 >= 0)
				&& (blocks[rowClicked][columnClicked - 1].getColor() == blocks[rowClicked][columnClicked]
						.getColor())) {
			rippleUncover(rowClicked, columnClicked - 1);
		}
		if ((rowClicked + 1 < rows)
				&& (columnClicked <= columns)
				&& (blocks[rowClicked + 1][columnClicked].getColor() == blocks[rowClicked][columnClicked]
						.getColor())) {
			rippleUncover(rowClicked + 1, columnClicked);
		}
		if ((rowClicked <= rows)
				&& (columnClicked + 1 < columns)
				&& (blocks[rowClicked][columnClicked + 1].getColor() == blocks[rowClicked][columnClicked]
						.getColor())) {
			rippleUncover(rowClicked, columnClicked + 1);
		}

		return;
	}

	private void renderBackGround(Canvas canvas) {
		// canvas.drawBitmap(this._backgroundImage, 0, 0, null);
	}
	
	private void motion(List<Block> blockList){
		switch(Config.MODEL){
		case Config.MODEL_CLASSIC:
			switch(Config.MOTION){
			case Config.MOTION_TOP:
				Collections.sort(blockList,new Comparator<Block>() {
					@Override
					public int compare(Block a, Block b) {
						int v = a.getPiont().x - b.getPiont().x;
						if(v ==0){
							v = a.getPiont().y-b.getPiont().y;
		                } 
						return v;
					}
				});
				for(Block block:blockList){
					int i = block.getPiont().x/Config.BLOCK_STYLE;
					int j = block.getPiont().y/Config.BLOCK_STYLE;
					blocks[i][j].deleteBlock(this._context, this, new Point(i * Config.BLOCK_STYLE,j*Config.BLOCK_STYLE));
					int k;
					for(k=j;k>0;k--){
						blocks[i][k].moveBlock(this._context, this,
								new Point(i * Config.BLOCK_STYLE, k
										* Config.BLOCK_STYLE),
								blocks[i][k-1].getColor());
						blocks[i][k-1].deleteBlock(this._context, this,
								new Point(i * Config.BLOCK_STYLE, k
										* Config.BLOCK_STYLE));
					}
					blocks[i][k].deleteBlock(this._context, this,
							new Point(i * Config.BLOCK_STYLE, k
									* Config.BLOCK_STYLE));
				}
				break;
			case Config.MOTION_BOTTOM:
				Collections.sort(blockList,new Comparator<Block>() {
					@Override
					public int compare(Block a, Block b) {
						int v = b.getPiont().x - a.getPiont().x;
						if(v ==0){
							v = b.getPiont().y-a.getPiont().y;
		                } 
						return v;
					}
				});
				for(Block block:blockList){
					int i = block.getPiont().x/Config.BLOCK_STYLE;
					int j = block.getPiont().y/Config.BLOCK_STYLE;
					blocks[i][j].deleteBlock(this._context, this, new Point(i * Config.BLOCK_STYLE,j*Config.BLOCK_STYLE));
					int k;
					for(k=j;k<Config.screenHight/Config.BLOCK_STYLE-1;k++){
						blocks[i][k].moveBlock(this._context, this,
								new Point(i * Config.BLOCK_STYLE, k
										* Config.BLOCK_STYLE),
								blocks[i][k+1].getColor());
						blocks[i][k+1].deleteBlock(this._context, this,
								new Point(i * Config.BLOCK_STYLE, k
										* Config.BLOCK_STYLE));
					}
					blocks[i][k].deleteBlock(this._context, this,
							new Point(i * Config.BLOCK_STYLE, k
									* Config.BLOCK_STYLE));
				}
				break;
			case Config.MOTION_LEFT:
				Collections.sort(blockList,new Comparator<Block>() {
					@Override
					public int compare(Block a, Block b) {
						int v = a.getPiont().y - b.getPiont().y;
						if(v ==0){
							v = a.getPiont().x-b.getPiont().x;
		                } 
						return v;
					}
				});
				for(Block block:blockList){
					int i = block.getPiont().x/Config.BLOCK_STYLE;
					int j = block.getPiont().y/Config.BLOCK_STYLE;
					blocks[i][j].deleteBlock(this._context, this, new Point(i * Config.BLOCK_STYLE,j*Config.BLOCK_STYLE));
					int k;
					for(k=i;k>0;k--){
						blocks[k][j].moveBlock(this._context, this,
								new Point(k * Config.BLOCK_STYLE, j
										* Config.BLOCK_STYLE),
								blocks[k - 1][j].getColor());
						blocks[k - 1][j].deleteBlock(this._context, this,
								new Point(k * Config.BLOCK_STYLE, j
										* Config.BLOCK_STYLE));
					}
					blocks[k][j].deleteBlock(this._context, this,
							new Point(k * Config.BLOCK_STYLE, j
									* Config.BLOCK_STYLE));
				}
				break;
			case Config.MOTION_RIGHT:
				Collections.sort(blockList,new Comparator<Block>() {
					@Override
					public int compare(Block a, Block b) {
						int v = b.getPiont().y - a.getPiont().y;
						if(v ==0){
							v = b.getPiont().x-a.getPiont().x;
		                } 
						return v;
					}
				});
				for(Block block:blockList){
					int i = block.getPiont().x/Config.BLOCK_STYLE;
					int j = block.getPiont().y/Config.BLOCK_STYLE;
					blocks[i][j].deleteBlock(this._context, this, new Point(i * Config.BLOCK_STYLE,j*Config.BLOCK_STYLE));
					int k;
					for(k=i;k<Config.screenWidth/Config.BLOCK_STYLE-1;k++){
						blocks[k][j].moveBlock(this._context, this,
								new Point(k * Config.BLOCK_STYLE, j
										* Config.BLOCK_STYLE),
								blocks[k + 1][j].getColor());
						blocks[k + 1][j].deleteBlock(this._context, this,
								new Point(k * Config.BLOCK_STYLE, j
										* Config.BLOCK_STYLE));
					}
					blocks[k][j].deleteBlock(this._context, this,
							new Point(k * Config.BLOCK_STYLE, j
									* Config.BLOCK_STYLE));
				}
				break;
			}
			break;
		case Config.MODEL_LOOP:
			switch(Config.MOTION){
			case Config.MOTION_TOP:
				Collections.sort(blockList,new Comparator<Block>() {
					@Override
					public int compare(Block a, Block b) {
						int v = a.getPiont().x - b.getPiont().x;
						if(v ==0){
							v = a.getPiont().y-b.getPiont().y;
		                } 
						return v;
					}
				});
				for(Block block:blockList){
					int i = block.getPiont().x/Config.BLOCK_STYLE;
					int j = block.getPiont().y/Config.BLOCK_STYLE;
					blocks[i][j].deleteBlock(this._context, this, new Point(i * Config.BLOCK_STYLE,j*Config.BLOCK_STYLE));
					int k;
					for(k=j;k>0;k--){
						blocks[i][k].moveBlock(this._context, this,
								new Point(i * Config.BLOCK_STYLE, k
										* Config.BLOCK_STYLE),
								blocks[i][k-1].getColor());
					}
					blocks[i][k].createBlock(this._context, this,
							new Point(i * Config.BLOCK_STYLE, k
									* Config.BLOCK_STYLE));
				}
				break;
			case Config.MOTION_BOTTOM:
				Collections.sort(blockList,new Comparator<Block>() {
					@Override
					public int compare(Block a, Block b) {
						int v = b.getPiont().x - a.getPiont().x;
						if(v ==0){
							v = b.getPiont().y-a.getPiont().y;
		                } 
						return v;
					}
				});
				for(Block block:blockList){
					int i = block.getPiont().x/Config.BLOCK_STYLE;
					int j = block.getPiont().y/Config.BLOCK_STYLE;
					blocks[i][j].deleteBlock(this._context, this, new Point(i * Config.BLOCK_STYLE,j*Config.BLOCK_STYLE));
					int k;
					for(k=j;k<Config.screenHight/Config.BLOCK_STYLE-1;k++){
						blocks[i][k].moveBlock(this._context, this,
								new Point(i * Config.BLOCK_STYLE, k
										* Config.BLOCK_STYLE),
								blocks[i][k+1].getColor());
					}
					blocks[i][k].createBlock(this._context, this,
							new Point(i * Config.BLOCK_STYLE, k
									* Config.BLOCK_STYLE));
				}
				break;
			case Config.MOTION_LEFT:
				Collections.sort(blockList,new Comparator<Block>() {
					@Override
					public int compare(Block a, Block b) {
						int v = a.getPiont().y - b.getPiont().y;
						if(v ==0){
							v = a.getPiont().x-b.getPiont().x;
		                } 
						return v;
					}
				});
				for(Block block:blockList){
					int i = block.getPiont().x/Config.BLOCK_STYLE;
					int j = block.getPiont().y/Config.BLOCK_STYLE;
					blocks[i][j].deleteBlock(this._context, this, new Point(i * Config.BLOCK_STYLE,j*Config.BLOCK_STYLE));
					int k;
					for(k=i;k>0;k--){
						blocks[k][j].moveBlock(this._context, this,
								new Point(k * Config.BLOCK_STYLE, j
										* Config.BLOCK_STYLE),
								blocks[k - 1][j].getColor());
					}
					blocks[k][j].createBlock(this._context, this,
							new Point(k * Config.BLOCK_STYLE, j
									* Config.BLOCK_STYLE));
				}
				break;
			case Config.MOTION_RIGHT:
				Collections.sort(blockList,new Comparator<Block>() {
					@Override
					public int compare(Block a, Block b) {
						int v = b.getPiont().y - a.getPiont().y;
						if(v ==0){
							v = b.getPiont().x-a.getPiont().x;
		                } 
						return v;
					}
				});
				for(Block block:blockList){
					int i = block.getPiont().x/Config.BLOCK_STYLE;
					int j = block.getPiont().y/Config.BLOCK_STYLE;
					blocks[i][j].deleteBlock(this._context, this, new Point(i * Config.BLOCK_STYLE,j*Config.BLOCK_STYLE));
					int k;
					for(k=i;k<Config.screenWidth/Config.BLOCK_STYLE-1;k++){
						blocks[k][j].moveBlock(this._context, this,
								new Point(k * Config.BLOCK_STYLE, j
										* Config.BLOCK_STYLE),
								blocks[k + 1][j].getColor());
					}
					blocks[k][j].createBlock(this._context, this,
							new Point(k * Config.BLOCK_STYLE, j
									* Config.BLOCK_STYLE));
				}
				break;
			}
			break;
		}
	}
}
