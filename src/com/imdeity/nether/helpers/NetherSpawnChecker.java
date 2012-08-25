package com.imdeity.nether.helpers;

import java.util.TimerTask;

import com.imdeity.nether.DeityNether;

public class NetherSpawnChecker extends TimerTask {

	@Override
	public void run() { //Every 5 seconds, the run() method will check to see if the nether world has generated after being deleted
		if(DeityNether.plugin.netherNeedsSpawn()) {
			WorldHelper.addNetherSpawn(); //The nether needs a spawn location!
			DeityNether.plugin.config.set("nether-needs-spawn", false); //The config reflects the nether having a spawn
			DeityNether.netherSpawnChecker.cancel(); //We cancel the timertask because the nether has a spawn and we don't need to check anymore
		}else{
			DeityNether.timer.cancel(); //We cancel the timertask - the nether has not reset and will not need a spawn
		}
		
	}
}
