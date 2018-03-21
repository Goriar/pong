package goriar.pong;

import org.newdawn.slick.geom.Rectangle;

import goriar.main.PongPlay;

public class Charmoves {

	static boolean reached1 = false;
	static boolean reached2 = false;

	// in Gameplay wird nach diesen Variabeln gefragt
	private static boolean finished1 = false;

	public static boolean isFinished1() {
		return finished1;
	}

	public static void setFinished1(boolean finished1) {
		Charmoves.finished1 = finished1;
	}

	private static boolean finished2 = false;

	public static boolean isFinished2() {
		return finished2;
	}

	public static void setFinished2(boolean finished2) {
		Charmoves.finished2 = finished2;
	}

	static Timer t1 = new Timer();
	static Timer t2 = new Timer();

	private Charmoves() {
		throw new IllegalStateException("Utility Class");
	}

	public static void jump(Rectangle player, float delta, int playerNum) {

		if (playerNum == 1) {
			if (!reached1) {
				reached1 = move(player, delta, 1, PongPlay.WIDTH / 2 - 30);
			} else {
				finished1 = move(player, delta, -1, 20);
				reached1 = !finished1;
			}
		} else {
			if (!reached2) {
				reached2 = move(player, delta, -1, PongPlay.WIDTH / 2 + 10);
			} else {
				finished2 = move(player, delta, 1, 750);
				reached2 = !finished2;
			}
		}

	}

	public static boolean move(Rectangle player, float delta, int direction, int placeToReach) {
		// pr�ft welcher Spieler den Sprung ausf�hrt und in welche Richtung er sich
		// bewegen soll
		player.setX(player.getX() + 1000 * direction * delta);
		boolean reached = false;
		if (direction > 0) {
			reached = player.getX() >= placeToReach;
		} else {
			reached = player.getX() <= placeToReach;
		}
		// pr�ft ob die Sprungdistanz erreicht wurde
		if (reached) {
			// setzt den Spieler zur�ck falls er zu weit springt
			player.setX(placeToReach);
			return true;
		}
		return false;
	}

	public static void shrink(Rectangle enemy, float delta, float width, float height) {

		// pr�ft um welchen Spieler es sich handelt und ob der Move bereits fertig ist
		if (!finished1 && enemy.getX() >= PongPlay.WIDTH / 2) {
			// verkleinert den Gegner und f�gt Zeit zum Timer hinzu
			t1.addTime(delta);
			enemy.setSize(width, height - 35);
			// pr�ft ob der Timer abgelaufen ist
			if (t1.isOver()) {
				// vergr��ert ihn wieder und zeigt an, dass der Move fertig ist
				enemy.setSize(width, height);
				finished1 = true;
			}
		}

		// Das Gleiche wie oben, nur mit Spieler 2
		if (!finished2 && enemy.getX() <= PongPlay.WIDTH / 2) {
			t2.addTime(delta);
			enemy.setSize(width, height - 35);
			if (t2.isOver()) {
				enemy.setSize(width, height);
				finished2 = true;
			}
		}
	}
}
