package goriar.pong;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class PongPlay extends StateBasedGame {
	
	public static final int MAINMENU = 0;
	public static final int GAMEPLAY = 1;
	public static final int CHARACTERSELECT = 2;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	
	public PongPlay(){
	    super("Pong");
	    
	    //legt die Gamestates an
	    this.addState(new MainMenu(MAINMENU));
	    this.addState(new Gameplay(GAMEPLAY));
	    this.addState(new CharacterSelect(CHARACTERSELECT));
	    this.enterState(MAINMENU);
	}

	public static void main(String[]args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new PongPlay());
		
		app.setDisplayMode(800,600,false);
		app.setVSync(true);
		app.start();
		
		
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		//ruft die init Methoden der Gamestates auf
		this.getState(MAINMENU).init(gc,this);
		this.getState(CHARACTERSELECT).init(gc,this);
		this.getState(GAMEPLAY).init(gc, this);
		
	}

	
}
