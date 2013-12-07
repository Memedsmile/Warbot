package edu.turtlekit2.warbot.GreenTeam;

import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;

public class BrainBase extends WarBrain{
	
	int cpt =0;
	int nb=0;

	public BrainBase(){
		
	}

	
	//Retourne le nombre d'explorer encore vivant
	public int getEffectifExplorer()
	{
		broadcastMessage("WarExplorer","Effectif",null);
		List<WarMessage> liste = getMessage();
		
		int nb = 0;
		for (WarMessage m : liste)
		{  
			if (m.getMessage() == "present")
				nb++;
		}
		System.out.println(nb);
		return nb;
	}
	
	@Override
	public String action() {
		
		cpt++;
		if(!emptyBag()){
			return "eat";
		}
		nb = 0;
		
		
	
		
		if (cpt == 3)
		{	
			cpt = 0;
			this.broadcastMessage("WarExplorer", "Effectif",null);
		}
		List<WarMessage> liste = getMessage();
		for(WarMessage m : liste){
			
			if (m.getMessage() == "present")
				nb++;
			
			if (m.getMessage() == "where")
				reply(m, "ici", null);
		}
		
		//Nb est le nombre effectif
		if (nb > 0)
		{
			//Distribution des taĉhes
			broadcastMessage("WarExplorer","Metier",null);
			
			int totalDistance = 0;
			
			for (WarMessage m : liste)
			{
				if(m.getMessage() == "jenvoie_ma_position")
					totalDistance += m.getDistance(); 
			}
			for (WarMessage m : liste)
			{
				if(m.getMessage() == "jenvoie_ma_position")
				{
					if(m.getDistance() < totalDistance)
						reply(m,"tu seras ceuilleur",null);
					else
						reply(m,"tu seras espion",null);
					
				}
			}
		}
		
		if(getEnergy() > 12000){
			setNextAgentCreate("Explorer");
			return "create";
		}
		
		return "idle";
	}
}
