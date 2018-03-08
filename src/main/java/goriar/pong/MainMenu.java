package goriar.pong;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {

	int stateID = -1;
	Image background = null;
	Image instruc = null;
	Sound fx = null;
	TrueTypeFont truetypefont = null;
	int menuX = 100;
	int menuY = 200;
	int instX = 100;
	int instY = 250;
	MState state;

	MainMenu(int stateID) {
		this.stateID = stateID;
	}

	enum MState {
		Start, Instruction
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		background = new Image("Data/main_menu_bg.jpg");
		instruc = new Image("Data/instructions.jpg");

		Font font = new Font("Verdana", Font.BOLD, 40);
		truetypefont = new TrueTypeFont(font, true);

		state = MState.Start;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (state == MState.Start) {
			// zeichnet den Hintergrund und die Auswahlm�glichkeiten
			background.draw(0, 0);
			truetypefont.drawString(menuX, menuY, "Spiel starten!", Color.red);
			truetypefont.drawString(instX, instY, "Instructions", Color.red);
		} else
			// zeichnet die Anleitung
			instruc.draw(0, 0);
	}


	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();

		if (state == MState.Start) {
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			boolean insideStart = false;
			boolean insideInst = false;

			// pr�ft ob die Maus �ber einer der Auswahlm�glichkeiten liegt
			if (mouseX >= menuX && mouseX <= menuX + truetypefont.getWidth("Spiel starten!") && mouseY >= menuY
					&& mouseY <= menuY + truetypefont.getHeight("Spiel starten")) {
				insideStart = true;
			}

			if (mouseX >= instX && mouseX <= instX + truetypefont.getWidth("Instructions") && mouseY >= instY
					&& mouseY <= instY + truetypefont.getHeight("Instructions")) {
				insideInst = true;
			}

			// pr�ft ob die Maus dann geklickt wurde
			if (insideStart) {
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					sbg.enterState(PongPlay.CHARACTERSELECT);
				}
			}
			if (insideInst) {
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					state = MState.Instruction;
				}
			}
		} else {
			// Um zum Hauptmen� zur�ck zu kehren dr�ckt man ESC
			if (input.isKeyDown(Input.KEY_ESCAPE))
				state = MState.Start;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
