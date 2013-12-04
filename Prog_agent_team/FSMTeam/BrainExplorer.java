package edu.turtlekit2.warbot.FSMTeam;


import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;
import edu.turtlekit2.warbot.percepts.Percept;
import edu.turtlekit2.warbot.waritems.WarFood;

public class BrainExplorer extends WarBrain{
	
	
	
	//Test implémentation des reflexes
	String reflexes()
	{
		
		List<Percept> listeP = getPercepts();
		List<WarMessage> listeM = getMessage();
		
		if (listeP.size() > 0 && !fullBag()){
		
				Percept bestFood = null;
				
				//test
				boolean basePercept = false;
				
			for (Percept p : listeP){
				
		
				
				if (p.getType().equals("WarFood")){
					if(bestFood == null){
						bestFood = p;
					}else if(p.getDistance() < bestFood.getDistance()){
						bestFood = p;
					}	
				}
				if (p.getType().equals("WarBase") && !p.getTeam().equals(getTeam()))
				{
					
					this.broadcastMessage("WarBase", "Base Spotted", null);
					basePercept = true;
				}
				
			}
			
			if(bestFood != null && bestFood.getDistance() < WarFood.MAX_DISTANCE_TAKE){
				return "take";
			}else if(bestFood != null && bestFood.getType().equals("WarFood")){
				setHeading(bestFood.getAngle());
			}else if(isBlocked()){
				
					return "stuck";
				//	setRandomHeading();
				}
			
		//Reste à faire si le sac est plein de le passer a la base si elle est capable de prendre	
		}else if(listeP.size() > 0 && fullBag())
		{
			System.out.println("sac plein");
			Percept bestFood = null;
			
			for(Percept p : listeP){
				if(p.getType().equals("WarBase") && p.getDistance() < WarFood.MAX_DISTANCE_TAKE){
					if(bestFood == null){
						bestFood = p;
					}else if(p.getDistance() < bestFood.getDistance()){
						bestFood = p;
					}
				}
			}
			
			if(bestFood != null && bestFood.getDistance() < WarFood.MAX_DISTANCE_TAKE){
				setAgentToGive(bestFood.getId());
				System.out.println("je vais donner");
				return "give";
			}else if(bestFood != null && bestFood.getType().equals("WarFood")){
				setHeading(bestFood.getAngle());
			}else{
				while(isBlocked()){
				//	setRandomHeading();
					return "stuck";
				}
			
			}
		}
		return null;
		
		

	}
	
	public BrainExplorer(){
		
	}
	
	public String action()
	{
		String act;
		act = reflexes();
	//	System.out.println("act = " + act);
		if (act != null && act != "stuck")
			return act;
		else if (act == "stuck")
			setRandomHeading();
		return "move";
	}
/*	public String action() {
		List<Percept> liste = getPercepts();
		List<WarMessage> listeM = getMessage();
		
		if(liste.size()>0 && !fullBag()){
			Percept bestFood = null;
			Percept basePercept = null;
			
			for(Percept p : liste){
				
				
				if (p.getType().equals("WarBase") && !p.getTeam().equals(getTeam()) )
				{
					this.broadcastMessage("WarRocketLauncher", "Base_Enemie", null);
				}
				
				if(p.getType().equals("WarFood")){
					if(bestFood == null){
						bestFood = p;
					}else if(p.getDistance() < bestFood.getDistance()){
						bestFood = p;
					}
				}
			}
			
			if(bestFood != null && bestFood.getDistance() < WarFood.MAX_DISTANCE_TAKE){
				return "take";
			}else if(bestFood != null && bestFood.getType().equals("WarFood")){
				setHeading(bestFood.getAngle());
			}else{
				while(isBlocked()){
					setRandomHeading();
				}
			}
		}else if(liste.size()>0 && fullBag()){
			Percept bestFood = null;
			
			for(Percept p : liste){
				if(p.getType().equals("WarBase") && p.getDistance() < WarFood.MAX_DISTANCE_TAKE){
					if(bestFood == null){
						bestFood = p;
					}else if(p.getDistance() < bestFood.getDistance()){
						bestFood = p;
					}
				}
			}
			
			if(bestFood != null && bestFood.getDistance() < WarFood.MAX_DISTANCE_TAKE){
				setAgentToGive(bestFood.getId());
				return "give";
			}else if(bestFood != null && bestFood.getType().equals("WarFood")){
				setHeading(bestFood.getAngle());
			}else{
				while(isBlocked()){
					setRandomHeading();
				}
			}
		}else if(fullBag() && listeM.size()==0){
			broadcastMessage("WarBase", "where", null);
		}else if(fullBag() && listeM.size()>0){
			setHeading(listeM.get(0).getAngle());
		}else{
			while(isBlocked()){
				setRandomHeading();
			}
		}
		return "move";
	}*/
}
