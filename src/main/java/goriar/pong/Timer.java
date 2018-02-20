package goriar.pong;

// Wird f�r den Charmove shrink ben�tigt
public class Timer {
	private boolean stop;
	private float end;
	private float current;
	
	//Constructor f�r einen neuen Timer
	public Timer(){
		//5 Sekunden 
		this.end=5;
		this.current=0;
		this.stop=true;
	}
	
	// f�gt Zeit zum Timer hinzu
	public void addTime(float delta){
		current=current+delta;
		stop=false;
		if(current>=end){
			stop=true;
			current=0;
		}
	}
	
	// setzt den TImer zur�ck
	public void reset(){
		stop=true;
		current=0;
	}
	
	// gibt die aktuelle Zeit 
	public double getTime(){
		return current;
	}
	
	// Pr�ft ob die Zeit abgelaufen ist
	public boolean isOver(){
		return stop;
	}
}
