package goriar.pong;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CharacterSelect extends BasicGameState {

	private static final String CHARACTER_SELECT_PIC = "Data/charselect.png";
	private static final String MAIN_MENU_BG_PIC = "Data/main_menu_bg.jpg";
	int stateID = 1;

	CharacterSelect(int stateID) {
		this.stateID = stateID;
	}

	// 0 : Char mit dem Move jump
	// 1 : Char mit dem Move shrink
	public static int player1 = -1;
	public static int player2 = -1;

	Image char1;
	Image char2;
	Image pick1;
	Image pick2;
	Image bg;

	int p1;
	int p2;

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		char1 = new Image(CHARACTER_SELECT_PIC);
		char2 = new Image(CHARACTER_SELECT_PIC);
		pick1 = new Image(CHARACTER_SELECT_PIC);
		pick2 = new Image(CHARACTER_SELECT_PIC);
		bg = new Image(MAIN_MENU_BG_PIC);

		// Cursor
		p1 = 0;
		p2 = 1;

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// zeichnet den Hintergrund und die Bilder der Chars
		bg.draw(0, 0);

		char1.draw(PongPlay.WIDTH / 2 - 190, PongPlay.HEIGHT / 2 - 100, PongPlay.WIDTH / 2 - 60,
				PongPlay.HEIGHT / 2 + 100, 247, 16, 431, 240);
		char2.draw(PongPlay.WIDTH / 2 + 200, PongPlay.HEIGHT / 2 - 100, PongPlay.WIDTH / 2 + 60,
				PongPlay.HEIGHT / 2 + 100, 39, 16, 230, 240);

		// Zeichnet den Cursor entsprechend seiner aktuellen Position
		if (p1 == 0 && p2 == 0)
			pick1.draw(PongPlay.WIDTH / 2 - 200, PongPlay.HEIGHT / 2 - 100, PongPlay.WIDTH / 2 - 60,
					PongPlay.HEIGHT / 2 + 100, 440, 240, 640, 472);
		else if (p1 == 1 && p2 == 1)
			pick1.draw(PongPlay.WIDTH / 2 + 200, PongPlay.HEIGHT / 2 - 100, PongPlay.WIDTH / 2 + 60,
					PongPlay.HEIGHT / 2 + 100, 440, 240, 640, 472);

		else {
			if (p1 == 0)
				pick1.draw(PongPlay.WIDTH / 2 - 200, PongPlay.HEIGHT / 2 - 100, PongPlay.WIDTH / 2 - 60,
						PongPlay.HEIGHT / 2 + 100, 31, 240, 233, 472);
			else
				pick1.draw(PongPlay.WIDTH / 2 + 200, PongPlay.HEIGHT / 2 - 100, PongPlay.WIDTH / 2 + 60,
						PongPlay.HEIGHT / 2 + 100, 31, 240, 233, 472);

			if (p2 == 0)
				pick2.draw(PongPlay.WIDTH / 2 - 200, PongPlay.HEIGHT / 2 - 100, PongPlay.WIDTH / 2 - 60,
						PongPlay.HEIGHT / 2 + 100, 239, 240, 438, 472);
			else
				pick2.draw(PongPlay.WIDTH / 2 + 200, PongPlay.HEIGHT / 2 - 100, PongPlay.WIDTH / 2 + 60,
						PongPlay.HEIGHT / 2 + 100, 239, 240, 438, 472);
		}

		// Zeichnet die Spielnachrichten ein
		g.setColor(Color.yellow);
		g.drawString("Mit A und D ausw�hlen mit W bestaetigen", PongPlay.WIDTH / 2 - 200, PongPlay.HEIGHT / 2 + 200);

		g.setColor(Color.green);
		g.drawString("Mit LINKS und RECHTS ausw�hlen mit HOCH bestaetigen", PongPlay.WIDTH / 2 - 200,
				PongPlay.HEIGHT / 2 + 240);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		Input input = gc.getInput();

		// Legt die Steuerung des Cursors fest
		if (input.isKeyDown(Input.KEY_D) && CharacterSelect.player1 < 0)
			p1 = Math.min(p1 + 1, 1);
		if (input.isKeyDown(Input.KEY_A) && CharacterSelect.player1 < 0)
			p1 = Math.max(p1 - 1, 0);

		if (input.isKeyDown(Input.KEY_RIGHT) && CharacterSelect.player2 < 0)
			p2 = Math.min(p2 + 1, 1);
		if (input.isKeyDown(Input.KEY_LEFT) && CharacterSelect.player2 < 0)
			p2 = Math.max(p2 - 1, 0);

		// Sobald die Auswahl Taste gedr�ckt wird, wird das Ziel des Cursor dem Spieler
		// zugewiesen
		if (input.isKeyDown(Input.KEY_W)) {
			CharacterSelect.setPlayer1(p1);
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			CharacterSelect.setPlayer2(p2);
		}

		if (CharacterSelect.player1 != -1 && CharacterSelect.player2 != -1) {
			// sobald beide Spieler ausgew�hlt wurden beginnt das Spiel
			sbg.enterState(PongPlay.GAMEPLAY);
		}

		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			CharacterSelect.setPlayer1(-1);
			CharacterSelect.setPlayer2(-1);
			sbg.enterState(PongPlay.MAINMENU);
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

	public static void setPlayer1(int character) {
		CharacterSelect.player1 = character;
	}

	public static void setPlayer2(int character) {
		CharacterSelect.player2 = character;
	}

}
