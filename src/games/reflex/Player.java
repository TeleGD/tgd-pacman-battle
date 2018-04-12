package games.reflex;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import general.AppInput;
import general.AppPlayer;

public class Player {
	private static final int nbImages=3;
	private int pos; //pos designe le milieu
	private int state;
	private Color color;
	private Image image;
	private Random rand;
	private int controllerID;
	private boolean next;
	private Button bouton;

	public Player(AppPlayer appPlayer, int n, int i) {
		state=0;
		next = true;
		this.controllerID = appPlayer.getControllerID();
		rand = new Random();
		pos=1280*(2*i+1)/(2*n);
		try {
			image = new Image("images/reflex/p"+rand.nextInt(nbImages)+".png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		image.draw(pos-image.getWidth()/2, 550-state*image.getHeight()/World.GOAL);
		if (bouton!=null) {
			bouton.render(container, game, context);
		}
	}

	public int getPos() {
		return pos;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (next) {
			next = false;
			bouton = new ButtonP(this, rand.nextInt(4));
		} else {
			AppInput appInput = (AppInput) container.getInput();
			if (appInput.isControlPressed((int) Math.pow(2,bouton.getNumero()),controllerID)) {
				state+=1;
				next=true;
			}
			if (state==World.GOAL) {
				//victoire
			}
		}
		
	}

}
