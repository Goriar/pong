package goriar.pong;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Gameplay extends BasicGameState {

	public static final int BALL_RADIUS = 12;
	public static final int PLAYER_WIDTH = 50;
	public static final int PLAYER_HEIGHT = 100;
	protected static final int[][] PLAYER1_COMBO_MARKERS = new int[][] {
			new int[] { 18, 17, 138, 117, 190, 0, 335, 163 }, new int[] { 18, 17, 138, 117, 190, 163, 335, 331 },
			new int[] { 18, 17, 138, 117, 190, 331, 335, 500 }, new int[] { 18, 17, 138, 117, 335, 0, 479, 163 },
			new int[] { 18, 17, 138, 117, 335, 163, 479, 331 }, new int[] { 18, 17, 138, 117, 335, 331, 479, 500 },
			new int[] { 18, 17, 138, 117, 479, 0, 623, 163 }, new int[] { 18, 17, 138, 117, 479, 163, 623, 331 },
			new int[] { 18, 17, 138, 117, 623, 0, 770, 163 }, new int[] { 18, 17, 138, 117, 623, 163, 770, 331 },
			new int[] { 18, 17, 138, 117, 623, 331, 770, 500 } };
	protected static final int[][] PLAYER2_COMBO_MARKERS = new int[][] {
			new int[] { 782, 17, 662, 117, 190, 0, 335, 163 }, new int[] { 782, 17, 662, 117, 190, 163, 335, 331 },
			new int[] { 782, 17, 662, 117, 190, 331, 335, 500 }, new int[] { 782, 17, 662, 117, 335, 0, 479, 163 },
			new int[] { 782, 17, 662, 117, 335, 163, 479, 331 }, new int[] { 782, 17, 662, 117, 335, 331, 479, 500 },
			new int[] { 782, 17, 662, 117, 479, 0, 623, 163 }, new int[] { 782, 17, 662, 117, 479, 163, 623, 331 },
			new int[] { 782, 17, 662, 117, 623, 0, 770, 163 }, new int[] { 782, 17, 662, 117, 623, 163, 770, 331 },
			new int[] { 782, 17, 662, 117, 623, 331, 770, 500 } };

	public static final String BAR_PIC = "Data/bar.png";
	Image bg;
	Image p1;
	Image p2;
	Image cbar1;
	Image cbar2;
	Image cborder1;
	Image cborder2;
	Image pball;

	Ball ball;
	Player player1;
	Player player2;
	GState currentstate;
	Font score;
	Font message;
	UnicodeFont ttfscore;
	UnicodeFont gamemessage;
	private static boolean superhit1;
	private static boolean superhit2;
	private static boolean charmove1;
	private static boolean charmove2;
	boolean ultra1;
	boolean ultra2;
	int defend;
	int tries;
	int balls;
	Ball[] ultraball1;
	Ball[] ultraball2;

	int stateID = 0;

	Gameplay(int stateID) {
		this.stateID = stateID;
	}

	public static void setSuperhit1(boolean val) {
		superhit1 = val;
	}

	public static void setSuperhit2(boolean val) {
		superhit2 = val;
	}

	public static void setCharmove1(boolean val) {
		charmove1 = val;
	}

	public static void setCharmove2(boolean val) {
		charmove2 = val;
	}

	public static boolean isSuperhit1() {
		return superhit1;
	}

	public static boolean isSuperhit2() {
		return superhit2;
	}

	public static boolean isCharmove1() {
		return charmove1;
	}

	public static boolean isCharmove2() {
		return charmove2;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player1 = new Player(20, 300, PLAYER_WIDTH, PLAYER_HEIGHT, -1);
		player2 = new Player(730, 300, PLAYER_WIDTH, PLAYER_HEIGHT, -1);
		ball = new Ball(PongPlay.WIDTH / 2, PongPlay.HEIGHT / 2 + 50);
		score = new Font("Verdana", Font.PLAIN, 50);
		message = new Font("Verdana", Font.BOLD, 27);
		ttfscore = new UnicodeFont(score);
		gamemessage = new UnicodeFont(message);
		ttfscore.addAsciiGlyphs();
		ttfscore.getEffects().add(new ColorEffect(java.awt.Color.white));
		ttfscore.loadGlyphs();
		gamemessage.addAsciiGlyphs();
		gamemessage.getEffects().add(new ColorEffect(java.awt.Color.white));
		gamemessage.loadGlyphs();
		currentstate = GState.INIT;
		setSuperhit1(false);
		setSuperhit2(false);
		ultra1 = false;
		ultra2 = false;
		setCharmove1(false);
		setCharmove2(false);
		defend = 0;
		tries = 0;
		balls = 0;
		ultraball1 = new Ball[6];
		for (int i = 0; i < 6; i++) {
			ultraball1[i] = new Ball(player1.getShape().getCenterX(), player1.getShape().getCenterY());
			ultraball1[i].setXSpeed(1200);
			ultraball1[i].setYSpeed(0);
		}
		ultraball2 = new Ball[6];
		for (int i = 0; i < 6; i++) {
			ultraball2[i] = new Ball(player2.getShape().getCenterX(), player2.getShape().getCenterY());
			ultraball2[i].setXSpeed(-1200);
			ultraball2[i].setYSpeed(0);
		}

		bg = new Image("Data/play_bg.jpg");
		cbar1 = new Image(BAR_PIC);
		cborder1 = new Image(BAR_PIC);
		cbar2 = new Image(BAR_PIC);
		cborder2 = new Image(BAR_PIC);
		pball = new Image("Data/ball1.png");

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		// zeichnet den Hintergrund und die Spieler
		bg.draw(0, 0);
		p1 = player1.getImage();
		p2 = player2.getImage();
		p2 = p2.getFlippedCopy(true, false);
		p1.draw(player1.getShape().getX(), player1.getShape().getY(), player1.getShape().getWidth(),
				player1.getShape().getHeight());
		p2.draw(player2.getShape().getX(), player2.getShape().getY(), player2.getShape().getWidth(),
				player2.getShape().getHeight());

		// Zeichnet die B�lle des Ultramoves
		if (currentstate == GState.ULTRA1) {
			for (int i = tries; i <= balls; i++)
				pball.draw(ultraball1[i].getShape().getMinX(), ultraball1[i].getShape().getMinY(),
						ultraball1[i].getShape().getMaxX(), ultraball1[i].getShape().getMaxY(), 0, 0, 103, 96);
		} else if (currentstate == GState.ULTRA2) {
			for (int i = tries; i <= balls; i++)
				pball.draw(ultraball2[i].getShape().getMinX(), ultraball2[i].getShape().getMinY(),
						ultraball2[i].getShape().getMaxX(), ultraball2[i].getShape().getMaxY(), 0, 0, 103, 96);
		}

		else
		// falls jemand einen superhit ausf�hrt ver�ndert sich der Rand des Balls
		if (isSuperhit1() && ball.getXSpeed() > 0)
			pball.draw(ball.getShape().getMinX(), ball.getShape().getMinY(), ball.getShape().getMaxX(),
					ball.getShape().getMaxY(), 0, 96, 103, 186);
		else if (isSuperhit2() && ball.getXSpeed() < 0)
			pball.draw(ball.getShape().getMinX(), ball.getShape().getMinY(), ball.getShape().getMaxX(),
					ball.getShape().getMaxY(), 0, 186, 103, 280);
		else
			pball.draw(ball.getShape().getMinX(), ball.getShape().getMinY(), ball.getShape().getMaxX(),
					ball.getShape().getMaxY(), 0, 0, 103, 96);

		// Zeichnet alle Spielnachrichten
		String scoreText = player1.getPoints() + "     " + player2.getPoints();
		ttfscore.drawString(PongPlay.WIDTH / 2f - ttfscore.getWidth(scoreText) / 2f, 5, scoreText, Color.cyan);

		if (currentstate == GState.PLAYER_1_WINS) {
			gamemessage.drawString(250, 70, "Spieler 1 Siegt!");
			gamemessage.drawString(250, 100, "Enter für neues Spiel");
		}

		if (currentstate == GState.PLAYER_2_WINS) {
			gamemessage.drawString(250, 70, "Spieler 2 Siegt!");
			gamemessage.drawString(250, 100, "Enter für neues Spiel");
		}

		if (currentstate == GState.BALL_OUT) {
			gamemessage.drawString(250, 100, "Enter für neuen Ball");
		}

		if (currentstate == GState.GAME_START) {
			gamemessage.drawString(200, 100, "Enter um Spiel zu starten");
		}

		// zeichnet die Comboleisten
		cborder1.draw(10, 5, 150, 130, 0, 0, 174, 208);
		cborder2.draw(790, 5, 650, 130, 0, 0, 174, 208);

		// f�llt die Comboleiste entsprechend zur combovariable
		drawComboBox(1, player1.getCombo());
		drawComboBox(2, player2.getCombo());
	}

	private void drawComboBox(int playerIndex, int combo) {
		if (playerIndex == 1) {
			cbar1.draw(PLAYER1_COMBO_MARKERS[combo][0], PLAYER1_COMBO_MARKERS[combo][1],
					PLAYER1_COMBO_MARKERS[combo][2], PLAYER1_COMBO_MARKERS[combo][3], PLAYER1_COMBO_MARKERS[combo][4],
					PLAYER1_COMBO_MARKERS[combo][5], PLAYER1_COMBO_MARKERS[combo][6], PLAYER1_COMBO_MARKERS[combo][7]);
		} else {
			cbar2.draw(PLAYER2_COMBO_MARKERS[combo][0], PLAYER2_COMBO_MARKERS[combo][1],
					PLAYER2_COMBO_MARKERS[combo][2], PLAYER2_COMBO_MARKERS[combo][3], PLAYER2_COMBO_MARKERS[combo][4],
					PLAYER2_COMBO_MARKERS[combo][5], PLAYER2_COMBO_MARKERS[combo][6], PLAYER2_COMBO_MARKERS[combo][7]);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		initGameState();

		float milliDelta = delta / 1000.0f;
		Input input = gc.getInput();

		// Legt die Steuerung der Spieler fest
		if (input.isKeyDown(Input.KEY_S) && player1.getShape().getMaxY() <= PongPlay.HEIGHT) {
			player1.getShape().setY(player1.getShape().getY() + 500 * milliDelta);
		}

		if (input.isKeyDown(Input.KEY_W) && player1.getShape().getMinY() >= 150) {
			player1.getShape().setY(player1.getShape().getY() - 500 * milliDelta);
		}

		if (input.isKeyDown(Input.KEY_DOWN) && player2.getShape().getMaxY() <= PongPlay.HEIGHT) {
			player2.getShape().setY(player2.getShape().getY() + 500 * milliDelta);
		}

		if (input.isKeyDown(Input.KEY_UP) && player2.getShape().getMinY() >= 150) {
			player2.getShape().setY(player2.getShape().getY() - 500 * milliDelta);
		}

		if ((currentstate == GState.GAME_START || currentstate == GState.BALL_OUT)
				&& input.isKeyDown(Input.KEY_ENTER)) {
			// wartet bis ein Spieler Enter dr�ckt damit, das Spiel weiter geht
			currentstate = GState.PLAY;
		}

		if ((currentstate == GState.PLAYER_1_WINS || currentstate == GState.PLAYER_2_WINS)
				&& input.isKeyDown(Input.KEY_ENTER)) {
			// Resetet den ball, die punkte und die combos
			player1.setPoints(0);
			player2.setPoints(0);
			ball = new Ball(PongPlay.WIDTH / 2, PongPlay.HEIGHT / 2 + 50);
			player1.setCombo(0);
			player2.setCombo(0);
			currentstate = GState.PLAY;
		}

		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			// f�hrt ins Hauptmen� zur�ck und Resetet die Spielercharaktere
			CharacterSelect.setPlayer1(-1);
			CharacterSelect.setPlayer2(-1);
			player1.setCombo(0);
			player2.setCombo(0);
			player1.setPoints(0);
			player2.setPoints(0);
			setSuperhit1(false);
			setSuperhit2(false);
			ultra1 = false;
			ultra2 = false;
			currentstate = GState.INIT;
			sbg.enterState(PongPlay.MAINMENU);
		}

		playGameState(input, milliDelta);

		ultra1State(milliDelta, input);

		ultra2State(milliDelta, input);
	}

	private void ultra2State(float milliDelta, Input input) {
		// Verl�uft gleich, wie der 1. Ultramove au�er, dass sich die Positionen der
		// B�lle entsprechend ver�ndert
		if (currentstate == GState.ULTRA2) {
			if (player1.getPoints() == 20) {
				currentstate = GState.PLAYER_1_WINS;
			}

			if (player2.getPoints() == 20) {
				currentstate = GState.PLAYER_2_WINS;
			}

			if (input.isKeyPressed(Input.KEY_LEFT) && balls < 5) {
				balls++;
			}

			ultraball2[balls].getShape().setX(player2.getShape().getX() - 60);
			ultraball2[balls].getShape().setY(player2.getShape().getCenterY());

			for (int i = tries; i < balls; i++) {
				ultraball2[i].getShape().setX(ultraball2[i].getShape().getX() + milliDelta * ultraball2[i].getXSpeed());
				ultraball2[i].getShape().setY(ultraball2[i].getShape().getY() + milliDelta * ultraball2[i].getYSpeed());

				if (Colission.p1col(player1.getShape(), ultraball2[i].getShape())) {
					defend++;
					tries++;
					ultraball2[i].getShape().setX(PongPlay.WIDTH + 500f);
				}
				if (ultraball2[i].getShape().getX() < 0) {
					tries++;
					player2.setPoints(player2.getPoints() + 1);
				}
			}
			if (tries == 5) {
				if (defend == 5) {
					tries = 4;
					balls = 4;
					defend = 0;
					currentstate = GState.ULTRA1;
				} else {
					tries = 0;
					balls = 0;
					defend = 0;
					ball = new Ball(PongPlay.WIDTH / 2, PongPlay.HEIGHT / 2 + 50);
					currentstate = GState.BALL_OUT;
				}
			}
		}
	}

	private void ultra1State(float milliDelta, Input input) {
		if (currentstate == GState.ULTRA1) {
			if (player1.getPoints() == 20) {
				currentstate = GState.PLAYER_1_WINS;
			}

			if (player2.getPoints() == 20) {
				currentstate = GState.PLAYER_2_WINS;
			}

			if (input.isKeyPressed(Input.KEY_D) && balls < 5) {
				balls++;
			}
			// setzt die Startposition der B�lle fest
			ultraball1[balls].getShape().setX(player1.getShape().getX() + 70);
			ultraball1[balls].getShape().setY(player1.getShape().getCenterY());

			// bewegt alle aktiven b�lle. tries z�hlt wie oft b�lle schon beim gegner
			// ankamen. balls
			// gibt die maximale Anzahl an b�llen an.
			for (int i = tries; i < balls; i++) {
				ultraball1[i].getShape().setX(ultraball1[i].getShape().getX() + milliDelta * ultraball1[i].getXSpeed());
				ultraball1[i].getShape().setY(ultraball1[i].getShape().getY() + milliDelta * ultraball1[i].getYSpeed());

				if (Colission.p2col(player2.getShape(), ultraball1[i].getShape())) {
					// pr�ft ob der Gegner einen Ball abwehren konnte
					defend++;
					tries++;
					// setzt den ball aus dem Feld. Kurz danach wird er nicht mehr verwendet
					ultraball1[i].getShape().setX(-500);
				}
				if (ultraball1[i].getShape().getX() > PongPlay.WIDTH) {
					// pr�ft ob gepunktet wurde
					tries++;
					player1.setPoints(player1.getPoints() + 1);
				}
			}
			if (tries == 5) {
				// nach 5 B�llen wird das Spiel normal fortgesetzt...
				if (defend == 5) {
					// ... es sei denn der Gegner konnte 5 B�lle abwehren. In diesem Fall kann er
					// selbst einen Ball
					// zur�ck spielen
					tries = 4;
					balls = 4;
					defend = 0;
					currentstate = GState.ULTRA2;
				} else {
					// alle Variabeln werden vor dem Eintritt ins normale Spiel wieder resettet
					tries = 0;
					balls = 0;
					defend = 0;
					ball = new Ball(PongPlay.WIDTH / 2, PongPlay.HEIGHT / 2 + 50);
					currentstate = GState.BALL_OUT;
				}
			}
		}
	}

	private void playGameState(Input input, float milliDelta) {
		if (currentstate == GState.PLAY) {
			if (input.isKeyPressed(Input.KEY_D) && ball.getShape().getMinX() < player1.getShape().getMaxX() + 50
					&& ball.getXSpeed() < 0) {
				// fragt ab ob der ball sich auf den Schl�ger zu bewegt und ob der Ball im
				// Schlagraum ist
				setSuperhit1(true);
			}

			if (input.isKeyPressed(Input.KEY_LEFT) && ball.getShape().getMaxX() > player2.getShape().getMinX() - 50
					&& ball.getXSpeed() > 0) {
				setSuperhit2(true);
			}

			if (input.isKeyDown(Input.KEY_A)) {

				// fragt ab ob der Ultramove bereit ist und ob der ball sich kurz vorm Spieler
				// befindet
				if (ultra1 && ball.getShape().getCenterX() < player1.getShape().getCenterX() + 80
						&& ball.getShape().getMaxY() >= player1.getShape().getMinY()
						&& ball.getShape().getMinY() <= player1.getShape().getMaxY()) {
					balls = 0;
					player1.setCombo(0);
					setSuperhit2(false);
					currentstate = GState.ULTRA1;
				}
				// falls kein Ultramove bereit ist wird abgefragt ob der Charmove bereit ist
				if (!ultra1 && player1.getCombo() >= 3 && !isCharmove1()) {
					setCharmove1(true);
					player1.setCombo(player1.getCombo() - 3);
				}
			}

			if (input.isKeyDown(Input.KEY_RIGHT)) {
				if (ultra2 && ball.getShape().getCenterX() > player2.getShape().getCenterX() - 80
						&& ball.getShape().getMaxY() >= player2.getShape().getMinY()
						&& ball.getShape().getMinY() <= player2.getShape().getMaxY()) {
					balls = 0;
					player2.setCombo(0);
					setSuperhit1(false);
					currentstate = GState.ULTRA2;
				}
				if (!ultra2 && player2.getCombo() >= 3 && !isCharmove2()) {
					setCharmove2(true);
					player2.setCombo(player2.getCombo() - 3);
				}
			}
			gameplayLogic(milliDelta);
		}
	}

	private void gameplayLogic(float milliDelta) {
		if (currentstate == GState.PLAY) { // playing

			// Bewirkt die Ballbewegung, indem die festgelegte Geschwindigkeit, also der
			// Sprung den der Ball
			// pro Frame macht, zur Position hinzugef�gt wird. Die Geschwindigkeit wird mit
			// delta mal genommen
			// damit es frameraten unabh�ngig ist

			ball.getShape().setX(ball.getShape().getX() + milliDelta * ball.getXSpeed());
			ball.getShape().setY(ball.getShape().getY() + milliDelta * ball.getYSpeed());

			// fragt ab ob der ball mit der Decke kollidiert und dreht die Geschwindigkeit
			// von Y um
			Colission.bottom(ball);

			// fragt ab ob der ball mit dem Boden kollidiert und dreht die Geschwindigkeit
			// von Y um
			Colission.top(ball);

			// fragt ab ob der Ball mit den Spielern kollidiert und dreht ja nachdem wo er
			// auftrifft die Geschwindigkeit
			// von X bzw. Y um
			Colission.guard1(player1, ball);
			Colission.guard2(player2, ball);

			// fragt ab ob der Ultramove bereit ist
			if (player1.getCombo() >= 10)
				ultra1 = true;
			else
				ultra1 = false;

			if (player2.getCombo() >= 10)
				ultra2 = true;
			else
				ultra2 = false;

			// Spieler punktet
			if (ball.getShape().getX() > PongPlay.WIDTH || ball.getShape().getX() < 0) {
				// fragt ab welche Spieler gepunktet hat
				if (ball.getShape().getX() > PongPlay.WIDTH)
					player1.setPoints(player1.getPoints() + 1);
				else
					player2.setPoints(player2.getPoints() + 1);

				// resetet die Spieler und den Ball
				ball = new Ball(PongPlay.WIDTH / 2, PongPlay.HEIGHT / 2 + 50);
				setSuperhit1(false);
				setSuperhit2(false);
				setCharmove1(false);
				setCharmove2(false);
				player1.getShape().setX(20);
				player2.getShape().setX(730);
				player1.getShape().setY(PongPlay.HEIGHT / 2f);
				player2.getShape().setY(PongPlay.HEIGHT / 2f);
				player1.resetSize();
				player2.resetSize();
				Charmoves.t1.reset();
				Charmoves.t2.reset();
				currentstate = GState.BALL_OUT;
			}

			// fragt ab ob einer der Spieler gewonnen hat
			if (player1.getPoints() == 20) {
				currentstate = GState.PLAYER_1_WINS;
			}

			if (player2.getPoints() == 20) {
				currentstate = GState.PLAYER_2_WINS;
			}

			// f�hrt den Charmove aus
			if (isCharmove1()) {
				player1.doMove(milliDelta, player2);
				if (Charmoves.isFinished1())
					setCharmove1(false);
				Charmoves.setFinished1(false);
			}

			if (isCharmove2()) {
				player2.doMove(milliDelta, player1);
				if (Charmoves.isFinished2())
					setCharmove2(false);
				Charmoves.setFinished2(false);
			}

		}
	}

	private void initGameState() {
		// legt die Characters neu fest
		if (currentstate == GState.INIT) {
			player1.setChar(CharacterSelect.getPlayer1Char());
			player2.setChar(CharacterSelect.getPlayer2Char());
			ball = new Ball(PongPlay.WIDTH / 2, PongPlay.HEIGHT / 2 + 50);
			currentstate = GState.GAME_START;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
