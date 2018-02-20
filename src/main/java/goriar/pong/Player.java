package goriar.pong;


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Player {
	
	private Rectangle player;
	private int points;
	private int characterselect;
	private int combo;
	
	// Constructor f�r einen neuen Spieler
	public Player(int x,int y, int width, int height, int c){
		player = new Rectangle(x,y,width,height);
		points = 0;
		characterselect = c;
		combo = 0;
	}
	
	// zum erhalten der Form des Objekts
	public Shape getShape(){
		return player;
	}
	
	// gibt die Punkte des Spielers an
	public int getPoints(){
		return points;
	}
	
	// ver�ndert die Punkte des Spielers
	public void setPoints(int points){
		this.points=points;
	}
	
	// gibt den Charakter des Spielers an
	public int getChar(){
		return characterselect;
	}
	
	// ver�ndert den Charakter
	public void setChar(int c){
		characterselect=c;
	}
	
	// setzt die Gr��e des Spielers zur�ck (f�r Charmove shrink)
	public void resetSize(){
		player.setWidth(Gameplay.PLAYER_WIDTH);
		player.setHeight(Gameplay.PLAYER_HEIGHT);
	}
	
	public void doMove(float delta, Player enemy){
		
		// f�hrt entsprechend des Charakters den Charmove aus
		switch(characterselect){
		case 0: Charmoves.jump(player,delta); break;
		case 1: Charmoves.shrink(enemy.player,delta, Gameplay.PLAYER_WIDTH, Gameplay.PLAYER_HEIGHT);break;
		}
	}
	
	public Image getImage() throws SlickException{
		
		// weist dem Spieler das Aussehen zu
		Image image = null;
		switch (characterselect){
		case 0 : image = new Image("Data/char1.png");break;
		case 1 : image = new Image("Data/char2.png");break;
		}
		return image;
	}
	
	public int getCombo(){
		return combo;
	}
	
	public void setCombo(int c){
		combo = c;
	}
	
}
