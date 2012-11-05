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

import android.content.SharedPreferences;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class LiveWallpaper extends WallpaperService{

	public static final String SHARED_PREFS_NAME = "livewallpapersettings";

	private ESDDEngine _ESDDEngine;
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Engine onCreateEngine() {
		_ESDDEngine = new ESDDEngine();
		return _ESDDEngine;
	}
	
	class ESDDEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener{
		
		private ESDD _esdd;
		private SharedPreferences	mPreferences;
		
		
		public ESDDEngine(){
			mPreferences = LiveWallpaper.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
			mPreferences.registerOnSharedPreferenceChangeListener(this);
			onSharedPreferenceChanged(mPreferences, null);
		}
		
		@Override
		public void onVisibilityChanged(boolean visible) {
			if(visible){
				this._esdd.render();
			}
		}
		
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {		
			super.onSurfaceChanged(holder, format, width, height);			
		}
		
		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {			
			super.onSurfaceCreated(holder);
			this._esdd.render();
		}
		
		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			// TODO Auto-generated method stub
			super.onCreate(surfaceHolder);
			setTouchEventsEnabled(true);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {			
			super.onSurfaceDestroyed(holder);
//			this._esdd.stop();
		}

		@Override
		public void onTouchEvent(MotionEvent event) {
			super.onTouchEvent(event);
//			System.out.println("ontouch");
//			this._esdd.stop();
			if(event.getAction() == event.ACTION_UP)
			this._esdd.onTouch((int)event.getX(),(int)event.getY());
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			
			Config.BLOCK_STYLE = Integer.valueOf(sharedPreferences.getString("livewallpaper_block_style", "40"));
			Config.MODEL = Integer.valueOf(sharedPreferences.getString("livewallpaper_model", "0"));
			Config.MOTION = Integer.valueOf(sharedPreferences.getString("livewallpaper_movement_style", "0"));
			Config.isEffect =  sharedPreferences.getBoolean("livewallpaper_effect", false);
			this._esdd = new ESDD();
			this._esdd.initialize(getBaseContext(), getSurfaceHolder());
		}
		
	}

}
