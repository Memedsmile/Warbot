package edu.turtlekit2.warbot.GreenTeam;

import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;

public class BrainBase extends WarBrain{
	

	public BrainBase(){
		
	}

	@Override
	public String action() {
		
		if(!emptyBag()){
			return "eat";
		}

		List<WarMessage> liste = getMessage();
		
		for(WarMessage m : liste){
			
			if (m.getMessage() == "where")
				reply(m, "ici", null);
		}
		
		if(getEnergy() > 12000){
			setNextAgentCreate("Explorer");
			return "create";
		}
		
		return "idle";
	}
}
