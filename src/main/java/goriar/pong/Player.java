package goriar.pong;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import goriar.main.PongPlay;

public class Player {

	private Rectangle playerRectangle;
	private int points;
	private int characterselect;
	private int combo;
	private int playerNum;

	// Constructor f�r einen neuen Spieler
	public Player(int x, int y, int width, int height, int c) {
		playerRectangle = new Rectangle(x, y, width, height);
		points = 0;
		characterselect = c;
		combo = 0;
		playerNum = x < PongPlay.WIDTH / 2 ? 1 : 2;
	}

	// zum erhalten der Form des Objekts
	public Shape getShape() {
		return playerRectangle;
	}

	// gibt die Punkte des Spielers an
	public int getPoints() {
		return points;
	}

	// ver�ndert die Punkte des Spielers
	public void setPoints(int points) {
		this.points = points;
	}

	// gibt den Charakter des Spielers an
	public int getChar() {
		return characterselect;
	}

	// ver�ndert den Charakter
	public void setChar(int c) {
		characterselect = c;
	}

	// setzt die Gr��e des Spielers zur�ck (f�r Charmove shrink)
	public void resetSize() {
		playerRectangle.setWidth(Gameplay.PLAYER_WIDTH);
		playerRectangle.setHeight(Gameplay.PLAYER_HEIGHT);
	}

	public void doMove(float delta, Player enemy) {

		// f�hrt entsprechend des Charakters den Charmove aus
		if (characterselect == 0) {
			Charmoves.jump(playerRectangle, delta, playerNum);
		} else {
			Charmoves.shrink(enemy.playerRectangle, delta, Gameplay.PLAYER_WIDTH, Gameplay.PLAYER_HEIGHT);
		}
	}

	public Image getImage() throws SlickException {

		// weist dem Spieler das Aussehen zu
		Image image = null;
		if (characterselect == 0) {
			image = new Image("Data/char1.png");
		} else {
			image = new Image("Data/char2.png");
		}
		return image;
	}

	public int getCombo() {
		return combo;
	}

	public void setCombo(int c) {
		combo = c;
	}

}
