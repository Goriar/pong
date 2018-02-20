package goriar.pong;

import java.util.Random;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Ball {
	
	private Circle ball;
	private float xspeed;
	private float yspeed;
	
	//Constructor um ein neues Ballobjekt zu erstellen
	public Ball(float x, float y){
		ball = new Circle(x,y,Gameplay.BALL_RADIUS);
		Random r = new Random();
		
		// w�hlt zuf�llig die Richtung des Balls
		if(r.nextBoolean()){
			// +200 damit der Ball sich eher auf den Spieler zu bewegt
			xspeed = r.nextInt(600)+200;
			yspeed = 800-xspeed;
		}
			else{
			xspeed = (r.nextInt(600)+200)*-1;
			yspeed = 800+xspeed;
			}
	}
	
	// gibt die Geschwindigkeit an in der der Ball sich auf der X Achse bewegt
	public float getXSpeed(){
		return xspeed;
	}
	
	// gibt die Geschwindigkeit an in der der Ball sich auf der Y Achse bewegt
	public float getYSpeed(){
		return yspeed;
	}
	
	// setzt die neue X Geschwindigkeit fest
	public void setXSpeed(float x){
		xspeed=x;
	}
	
	//setzt die neue Y Geschwindigkeit fest
	public void setYSpeed(float y){
		yspeed=y;
	}
	
	//gibt die Form des Balls zur�ck
	public Shape getShape(){
		return ball;
	}
}
