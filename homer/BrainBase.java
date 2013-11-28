package edu.turtlekit2.warbot.homer;

import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;

public class BrainBase extends WarBrain{
	

	int energy;
	public BrainBase(){
		energy = 12000;
	}

	@Override
	public String action() {
	
		if(!emptyBag()){
			return "eat";
		}

		List<WarMessage> liste = getMessage();

		
		if (energy > getEnergy())
			this.broadcastMessage("WarRocketLauncher", "ComeDef", null);
		
		for(WarMessage m : liste){
			reply(m, "ici", null);
		}
		
		if(getEnergy() > 12000){
			setNextAgentCreate("Explorer");
			return "create";
		}
		
	//	System.out.println(getEnergy());
		energy = getEnergy();
		return "action";
	}
}
