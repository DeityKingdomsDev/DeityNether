package com.imdeity.nether.cmds;

import com.imdeity.deityapi.api.DeityCommandHandler;

public class CmdHandler extends DeityCommandHandler {
	
	public CmdHandler(String plugin) {
		super(plugin, "Nether");
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initRegisteredCommands() {
		this.registerCommand("join", "", "Teleports the user to the nether", new JoinSubCommand(), "Deity.nether.general");
		this.registerCommand("leave", "", "Teleports the user out of the nether and back to the main world", new LeaveSubCommand(), "Deity.nether.general");
		this.registerCommand("regen", "", "Regenerates the nether", new RegenSubCommand(), "Deity.nether.regen");
		this.registerCommand("time", "", "Tells the user the amount of time they have left in the nether", new TimeSubCommand(), "Deity.nether.general");
	}
}
