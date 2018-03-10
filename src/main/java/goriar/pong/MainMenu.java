package goriar.pong;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {

	int stateID = -1;
	Image background = null;
	Image instruc = null;
	Sound fx = null;
	UnicodeFont unicodeFont = null;
	int menuX = 100;
	int menuY = 200;
	int instX = 100;
	int instY = 250;
	MState state;

	public static final String INSTRUCTION_STRING = "Instructions";
	public static final String GAME_START_STRING = "Spiel starten!";

	MainMenu(int stateID) {
		this.stateID = stateID;
	}

	enum MState {
		START, INSTRUCTIONS
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		background = new Image("Data/main_menu_bg.jpg");
		instruc = new Image("Data/instructions.jpg");

		Font font = new Font("Verdana", Font.BOLD, 40);
		unicodeFont = new UnicodeFont(font);
		unicodeFont.addAsciiGlyphs();
		unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
		unicodeFont.loadGlyphs();

		state = MState.START;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (state == MState.START) {
			// zeichnet den Hintergrund und die Auswahlm�glichkeiten
			background.draw(0, 0);
			unicodeFont.drawString(menuX, menuY, GAME_START_STRING, Color.red);
			unicodeFont.drawString(instX, instY, INSTRUCTION_STRING, Color.red);
		} else
			// zeichnet die Anleitung
			instruc.draw(0, 0);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();

		if (state == MState.START) {
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			boolean insideStart = false;
			boolean insideInst = false;

			// pr�ft ob die Maus �ber einer der Auswahlm�glichkeiten liegt
			if (mouseX >= menuX && mouseX <= menuX + unicodeFont.getWidth(GAME_START_STRING) && mouseY >= menuY
					&& mouseY <= menuY + unicodeFont.getHeight(GAME_START_STRING)) {
				insideStart = true;
			}

			if (mouseX >= instX && mouseX <= instX + unicodeFont.getWidth(INSTRUCTION_STRING) && mouseY >= instY
					&& mouseY <= instY + unicodeFont.getHeight(INSTRUCTION_STRING)) {
				insideInst = true;
			}

			// pr�ft ob die Maus dann geklickt wurde
			if (insideStart && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				sbg.enterState(PongPlay.CHARACTERSELECT);
			}
			if (insideInst && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				state = MState.INSTRUCTIONS;
			}
		} else {
			// Um zum Hauptmen� zur�ck zu kehren dr�ckt man ESC
			if (input.isKeyDown(Input.KEY_ESCAPE))
				state = MState.START;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
