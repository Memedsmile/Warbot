package edu.turtlekit2.warbot.GreenTeam;


import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;
import edu.turtlekit2.warbot.percepts.Percept;

public class BrainRocketLauncher extends WarBrain{
	
	public BrainRocketLauncher(){
		
	}
	
	@Override
	public String action() {
		if(!isReloaded()){
			if(!isReloading()){
				return "reload";
			}
		}
		
		List<Percept> listeP = getPercepts();
		List<WarMessage> listeM = getMessage();
		
		
		Percept bestPercept = null;
		
		if(listeM.size() == 0){
			for(Percept p : listeP){
				if(p.getType().equals("WarBase") && !p.getTeam().equals(getTeam())){
					bestPercept = p;
				}
			}
			
			if(bestPercept != null){
				broadcastMessage("WarRocketLauncher", "base", null);
				setAngleTurret(bestPercept.getAngle());
				return "fire";
			}else{
				while(isBlocked()){
					setRandomHeading();
				}
				return "move";
			}
		}else{
			for(Percept p : listeP){
				if(p.getType().equals("WarBase") && !p.getTeam().equals(getTeam())){
					bestPercept = p;
				}
			}
			
			if(bestPercept != null){
		//		broadcastMessage("WarRocketLauncher", "base", null);
				setAngleTurret(bestPercept.getAngle());
				return "fire";
			}else if(listeM.size()>0){
				
				//Reception d'un message
				WarMessage tmp = listeM.get(0);
				
				//les Launchers viennent defendre selon une certaine distance si le message est "Venez_def"
				if (tmp.getMessage() == "Venez_Def")
				{
					if (tmp.getDistance() < 250){
						reply(tmp, "j'arrive defendre", null);
						setHeading(tmp.getAngle());
					}
					else
					{ 
						reply(tmp,"je suis trop loin pour venir defendre",null);
					}

				}
				if (tmp.getMessage() == "Base_Enemie")
				{
					if (tmp.getDistance() < 250){
						reply(tmp,"j'arrive attaquer",null);
						setHeading(tmp.getAngle());
					}
					else
					{
						reply(tmp,"je suis trop loin pour venir attaquer",null);
					}
				}
				
			}
		}
		
		return "move";
	}
}
