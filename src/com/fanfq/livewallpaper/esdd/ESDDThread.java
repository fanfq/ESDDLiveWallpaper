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

public class ESDDThread extends Thread{
	private ESDD _esdd;
	private boolean _running;
	
	public ESDDThread(ESDD esdd) {
		this._esdd = esdd;
	}
	
	public void switchOn(){
		this._running = true;
		this.start();
	}
	
	public void pause(){
		this._running = false;
		synchronized(this){
			this.notify();
		}
	}
	
	public void switchOff(){
		this._running = false;
		synchronized(this){
			this.notify();
		}
	}
	
	@Override
	public void run() {
		while(this._running){
			this._esdd.render();
		}
	}
}
