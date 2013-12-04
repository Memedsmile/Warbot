package edu.turtlekit2.warbot.FSMTeam;

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

	//	System.out.println("energy = " + getEnergy());
		//Detection d'attaque de la base
		if (energy > getEnergy())
			this.broadcastMessage("WarRocketLauncher", "Venez_Def", null);
		List<WarMessage> liste = getMessage();
		
		for(WarMessage m : liste){
			reply(m, "ici", null);
		}
		
	/*	if(getEnergy() > 12000){
			setNextAgentCreate("Explorer");
			return "create";
		}*/
		
		energy = getEnergy();
		return "idle";
	}
}
