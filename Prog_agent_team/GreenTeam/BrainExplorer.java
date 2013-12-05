package edu.turtlekit2.warbot.GreenTeam;

import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;
import edu.turtlekit2.warbot.percepts.Percept;
import edu.turtlekit2.warbot.waritems.WarFood;

public class BrainExplorer extends WarBrain{
	
	int taille_sac = 3;
	
	public BrainExplorer(){
		taille_sac = 3;
	}
	
	public Percept getFriendlyBestBase(List<Percept> listeP){
		
		Percept bestBase = null;
		for(Percept p : listeP){
			if(p.getType().equals("WarBase") && p.getDistance() < WarFood.MAX_DISTANCE_TAKE){
				if(bestBase == null){
					bestBase = p;
				}else if(p.getDistance() < bestBase.getDistance()){
					bestBase = p;
				}
			}
		}
		return bestBase;
	}
	public Percept getBestFood(List<Percept> listeP)
	{
		Percept bestFood = null;
		
		for (Percept p : listeP){
			
			if (p.getType().equals("WarFood"))
			{
				if(bestFood == null){
					bestFood = p;
				}else if(p.getDistance() < bestFood.getDistance()){
					bestFood = p;
				}
			}
			
		}
		return bestFood;
		
	}
	@Override
	public String action() {
		
		if (taille_sac == 0)
		{
			System.out.println("J'ai tout donner");
			taille_sac = 3;
		}
		else if (taille_sac < 3)
		{
			taille_sac--;
			return "give";
		}
		List<Percept> liste = getPercepts();
		List<WarMessage> listeM = getMessage();
		
		if(liste.size()>0 && !fullBag()){
			Percept bestFood = null;
			
		   bestFood = getBestFood(liste); 
		   
			if(bestFood != null && bestFood.getDistance() < WarFood.MAX_DISTANCE_TAKE){
				return "take";
			//Si la nourriture est hors porté de prise 
			}else if(bestFood != null && bestFood.getType().equals("WarFood")){
				setHeading(bestFood.getAngle());
			}else{
				while(isBlocked()){
					setRandomHeading();
				}
			}
		}else if(liste.size()>0 && fullBag()){
			Percept bestBase = null;
		
			bestBase = this.getFriendlyBestBase(liste);
			if(bestBase != null && bestBase.getDistance() < WarFood.MAX_DISTANCE_TAKE){
				setAgentToGive(bestBase.getId());
				taille_sac--;
				return "give";
			}else if(bestBase != null && bestBase.getType().equals("WarFood")){
				setHeading(bestBase.getAngle());
			}else{
				while(isBlocked()){
					setRandomHeading();
				}
			}
		}
		//Si le sac est plein et que la base ne nous a pas envoyé de message
		else if(fullBag() && listeM.size()==0){
			broadcastMessage("WarBase", "where", null);
		}else if(fullBag() && listeM.size()>0){
			setHeading(listeM.get(0).getAngle());
		}else{
			while(isBlocked()){
				setRandomHeading();
			}
		}
		return "move";
	}
}
