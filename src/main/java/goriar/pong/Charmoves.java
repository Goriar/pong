package goriar.pong;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Charmoves {

	static boolean reached1=false;
	static boolean reached2=false;
	
	// in Gameplay wird nach diesen Variabeln gefragt
	static boolean finished1=false;
	static boolean finished2=false;
	
	
	static Timer t1 = new Timer();
	static Timer t2 = new Timer();
	
	public static void jump(Rectangle player,float delta){
		
		// pr�ft welcher Spieler den Sprung ausf�hrt und in welche Richtung er sich bewegen soll
		if(reached1==false && player.getX()<PongPlay.WIDTH/2){
			finished1=false;
			player.setX(player.getX()+1000*delta);
			//pr�ft ob die Sprungdistanz erreicht wurde
			if(player.getX()>=PongPlay.WIDTH/2-30){
				//setzt den Spieler zur�ck falls er zu weit springt
				player.setX(PongPlay.WIDTH/2-30);
				reached1=true;
			}
		}
		// pr�ft welcher Spieler den Sprung ausf�hrt und in welche Richtung er sich bewegen soll
		if(reached1 && player.getX()<PongPlay.WIDTH/2){
			finished1=false;
			player.setX(player.getX()-1000*delta);
			//pr�ft ob der Spieler wieder auf der Ausgangsposition angekommen ist
			if(player.getX()<=20){
				//setzt den Spieler zur�ck falls er zu weit springt
				player.setX(20);
				// setzt reached zur�ck und gibt an, dass der Move fertig ist
				reached1=false;
				finished1=true;
			}
		}
		
		// Das Gleiche wie oben, jedoch mit dem 2.Spieler
		if(reached2==false && player.getX()>PongPlay.WIDTH/2){
			finished2=false;
			player.setX(player.getX()-1000*delta);
			if(player.getX()<=PongPlay.WIDTH/2+10){
				player.setX(PongPlay.WIDTH/2+10);
				reached2=true;
			}
		}
		if(reached2  && player.getX()>PongPlay.WIDTH/2){
			finished2=false;
			player.setX(player.getX()+1000*delta);
			if(player.getX()>=750){
				player.setX(750);
				reached2=false;
				finished2=true;
			}
		}
	}
	
	public static void shrink (Rectangle enemy, float delta, float width, float height){
		
		//pr�ft um welchen Spieler es sich handelt und ob der Move bereits fertig ist
		if (finished1 == false && enemy.getX()>=PongPlay.WIDTH/2){
			// verkleinert den Gegner und f�gt Zeit zum Timer hinzu
			t1.addTime(delta);
			enemy.setSize(width, height-35);
			// pr�ft ob der Timer abgelaufen ist
			if(t1.isOver()){
				//vergr��ert ihn wieder und zeigt an, dass der Move fertig ist
				enemy.setSize(width, height);
				finished1=true;
			}
		}
		
		// Das Gleiche wie oben, nur mit Spieler 2
		if (finished2 == false && enemy.getX()<=PongPlay.WIDTH/2){
			t2.addTime(delta);
			enemy.setSize(width, height-35);
			if(t2.isOver()){
				enemy.setSize(width, height);
				finished2=true;
			}
		}
	}
}
