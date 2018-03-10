package goriar.pong;

import org.newdawn.slick.geom.Shape;

public class Colission {

	private Colission() {
		throw new IllegalStateException("Utility Class");
	}

	public static boolean p1col(Shape player, Shape ball) {

		// prüft ob der Ball sich mit Spieler 1 überschneidet
		return ball.getMinX() < player.getMaxX() && ball.getMaxX() > player.getMinX()
				&& ball.getMaxY() > player.getMinY() && ball.getMinY() < player.getMaxY();

	}

	public static boolean p2col(Shape player, Shape ball) {

		// prüft ob der Ball sich mit Spieler 2 �berschneidet
		return ball.getMaxX() > player.getMinX() && ball.getMinX() < player.getMaxX()
				&& ball.getMaxY() > player.getMinY() && ball.getMinY() < player.getMaxY();
	}

	public static boolean p1up(Shape player, Shape ball) {

		// pr�ft ob der Ball �ber dem 1.Spieler liegt
		return ball.getCenterX() < player.getMaxX() && ball.getMinY() < player.getMinY();
	}

	public static boolean p1down(Shape player, Shape ball) {

		// pr�ft ob der Ball unter dem 1.Spieler liegt
		return ball.getCenterX() < player.getMaxX() && ball.getMaxY() > player.getMaxY();
	}

	public static boolean p2up(Shape player, Shape ball) {

		// pr�ft ob der Ball �ber dem 2.Spieler liegt
		return ball.getCenterX() > player.getMinX() && ball.getCenterY() < player.getMinY();
	}

	public static boolean p2down(Shape player, Shape ball) {

		// pr�ft ob der Ball unter dem 2.Spieler liegt
		return ball.getCenterX() > player.getMinX() && ball.getCenterY() > player.getMaxY();
	}

	public static void guard1(Player player, Ball ball) {

		if (p1col(player.getShape(), ball.getShape())) {
			Gameplay.setSuperhit2(false);

			if (Gameplay.isCharmove1() && player.getChar() == 0) {
				// beim Sprung wird die Geschwindigkeit leicht erh�t
				if (ball.getXSpeed() > 0)
					ball.setXSpeed(ball.getXSpeed() + 15);
				else
					ball.setXSpeed(ball.getXSpeed() - 15);
			}

			if (p1up(player.getShape(), ball.getShape())) {
				// falls der Ball oben auftrifft wird die Y Bewegung umgedreht
				ball.setYSpeed(ball.getYSpeed() * -1);
				// der Ball wird aus dem Spieler raus gesetzt
				float p = ball.getShape().getMaxY() - player.getShape().getMinY();
				ball.getShape().setY(ball.getShape().getY() + p);
			} else {
				if (p1down(player.getShape(), ball.getShape())) {
					// falls der Ball unten auftrifft wird die Y Bewegung umgedreht
					ball.setYSpeed(ball.getYSpeed() * -1);
					// der Ball wird aus dem Spieler raus gesetzt
					float p = player.getShape().getMaxY() - ball.getShape().getMinY();
					ball.getShape().setCenterY(ball.getShape().getY() + p);
				} else {
					// abfrage f�r den charmove : jump
					// n�tig damit er die Geschwindigkeit nicht umdreht falls der Ball sich auf den
					// Gegner zu bewegt
					if (ball.getXSpeed() < 0) {
						if (Gameplay.isSuperhit1()) {
							// falls ein Superhit ausgef�hrt wurde wird die Geschwindigkeit erh�t
							player.setCombo(player.getCombo() + 1);
							ball.setXSpeed(ball.getXSpeed() - 100);
						}
						ball.setXSpeed(ball.getXSpeed() * -1);
					}
					float p = player.getShape().getMaxX() - ball.getShape().getMinX();
					ball.getShape().setX(ball.getShape().getX() + p);
				}
			}
		}
	}

	public static void guard2(Player player, Ball ball) {

		// Das Gleiche wie oben, nur dass es auf den 2.Spieler angewandt wird
		if (p2col(player.getShape(), ball.getShape())) {
			Gameplay.setSuperhit1(false);
			;

			if (Gameplay.isCharmove2() && player.getChar() == 0) {
				if (ball.getXSpeed() < 0)
					ball.setXSpeed(ball.getXSpeed() - 15);
				else
					ball.setXSpeed(ball.getXSpeed() + 15);
			}
			if (p2up(player.getShape(), ball.getShape())) {
				ball.setYSpeed(ball.getYSpeed() * -1);
				float p = ball.getShape().getMaxY() - player.getShape().getMinY();
				ball.getShape().setY(ball.getShape().getY() + p);
			} else {
				if (p2down(player.getShape(), ball.getShape())) {
					ball.setYSpeed(ball.getYSpeed() * -1);
					float p = player.getShape().getMaxY() - ball.getShape().getMinY();
					ball.getShape().setY(ball.getShape().getY() + p);
				} else {
					if (ball.getXSpeed() > 0) {
						if (Gameplay.isSuperhit2()) {
							player.setCombo(player.getCombo() + 1);
							ball.setXSpeed(ball.getXSpeed() + 100);
						}
						ball.setXSpeed(ball.getXSpeed() * -1);
					}
					float p = ball.getShape().getMaxX() - player.getShape().getMinX();
					ball.getShape().setX(ball.getShape().getX() - p);
				}
			}
		}
	}

	public static void bottom(Ball ball) {
		if (ball.getShape().getMaxY() >= PongPlay.HEIGHT) {
			// dreht die Y Geschwindigkeit um und setzt den Ball aus dem Rand heraus, falls
			// der Ball am unteren Rand
			// aufkommt
			float p = ball.getShape().getMaxY() - PongPlay.HEIGHT;
			ball.getShape().setY(ball.getShape().getY() - p);
			ball.setYSpeed(ball.getYSpeed() * -1);
		}
	}

	public static void top(Ball ball) {
		if (ball.getShape().getMinY() <= 150) {
			// dreht die Y Geschwindigkeit um und setzt den Ball aus dem Rand heraus, falls
			// der Ball am oberen Rand
			// aufkommt
			float p = 150 - ball.getShape().getMinY();
			ball.getShape().setY(ball.getShape().getY() + p);
			ball.setYSpeed(ball.getYSpeed() * -1);
		}
	}
}
