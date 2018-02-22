package hub;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import general.Main;
import general.ui.Button;
import general.ui.TGDComponent;
import general.ui.TGDComponent.OnClickListener;

public class WorldPlateau extends BasicGameState {
	
	public static int ID = 9;
	public static String name = "Jeu de plateau";

	private StateBasedGame game;
	
	private SpiralTrack track;
	private int gridWidth;
	private int gridHeight;
	private int gridGap;
	private int playerHeight, playerWidth;
	private int turnNumber;
	
	private boolean menu;
	private Button plus, moins, ok;
	
	// private int [] cheminEntiers = {0, 0, 0, 0, 0, 0, 0, 0, 1};
	private int nbJoueur;
	private JoueurPlateau [] listeJoueurs;
	private boolean enterPress;
	
	@Override
	public void init (GameContainer container, StateBasedGame game) {
		WorldPlateau that;
		that = this;
		
		this.game = game;
		
		this.track = new SpiralTrack (128);
		this.gridWidth = 64;
		this.gridHeight = 64;
		this.gridGap = 16;
		this.playerHeight = this.gridHeight - 10;
		this.playerWidth = this.gridWidth - 10;
		
		this.turnNumber = 0; // numéro du tour
		this.listeJoueurs = new JoueurPlateau [2]; // 1 joueurs max pour l'instant
		
		// TEST JOUEUR
		//TODO Initialisation des joueurs en accord avec ce qui a été selectionné à l'aide des boutons
		this.listeJoueurs [0] = new JoueurPlateau (0, "NOM", "images/player/pion.png", this.playerHeight, this.playerWidth);
		this.listeJoueurs [1] = new JoueurPlateau (1, "NOM", "images/player/pion.png", this.playerHeight, this.playerWidth);

		// TODO Auto-generated method stub
		this.menu = true;
		this.nbJoueur = 2;
				
		this.plus = new Button ("+", container, 700, 50, 20, 20);
		this.plus.setOnClickListener (new OnClickListener () {
			
			@Override
			public void onClick (TGDComponent component) {
				if (that.nbJoueur < 4) {
					that.nbJoueur++;
				};
			}
			
		});
		
		this.moins = new Button ("-", container, 730, 50, 20, 20);
		this.moins.setOnClickListener (new OnClickListener () {

			@Override
			public void onClick (TGDComponent component) {
				if (that.nbJoueur > 1) {
					that.nbJoueur--;
				};
			}
			
		});
		
		this.ok = new Button ("Start", container, 500, 100, 80, 20);
		this.ok.setOnClickListener (new OnClickListener () {
			
			@Override
			public void onClick (TGDComponent component) {
				that.menu = false;
			}
			
		});
	}
	
	public void enter (GameContainer container, StateBasedGame game) {
		this.init (container, game);
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (this.menu) {
			
		} else { // Si le jeu est lancé : on est sur le plateau
			if (enterPress) { 
				listeJoueurs[turnNumber % nbJoueur].playRound(); // Lance le tour du joueur à qui c'est le tour	
				enterPress = false;
				turnNumber ++;
			}
			for (JoueurPlateau p: this.listeJoueurs) {
				p.update (container, game, delta, this);
			};
		};
	}
	
	public void render (GameContainer container,StateBasedGame game, Graphics context) throws SlickException {
		if (this.menu) {
			context.drawString ("nombre de joueurs : " + nbJoueur, 500, 50);
			this.plus.render (container, game, context);
			this.moins.render (container, game, context);
			this.ok.render (container, game, context);
		} else {
			// Affichage du plateau :
			int width = this.gridWidth - this.gridGap / 2;
			int height = this.gridHeight - this.gridGap / 2;
			int dx = (Main.width - this.gridWidth) / 2;
			int dy = (Main.height - this.gridHeight) / 2;
			int radius = this.gridGap / 2;
			Color backgroundColor = new Color (255, 0, 0);
			Color textColor = new Color (127, 0, 0);
			for (int i = 0; i < this.track.length; i++) {
				int [] xy = this.track.getCoordinates (i);
				context.setColor (backgroundColor);
				context.fillRoundRect ((float) xy [0] * this.gridWidth + dx, (float) xy [1] * this.gridHeight + dy, width, height, radius);
				context.setColor (textColor);
				context.drawString ("[" + i + "]", xy [0] * this.gridWidth + dx, xy [1] * this.gridHeight + dy);
			};
			// Fin d'affichage du plateau
			// Affichage des joueurs :
			for (JoueurPlateau p: this.listeJoueurs) {
				p.render (container, game, context);
			};
		}
	}
	
	public static void reset () {
		
	}

	@Override
	public void keyPressed (int key, char c) {
		switch (key) {
			case Input.KEY_ESCAPE:
				this.game.enterState (menus.MainMenu.ID, new FadeOutTransition (), new FadeInTransition ());
				break;
			case Input.KEY_ENTER:
				enterPress = true ;
				break;		
			default:
				super.keyPressed (key, c);
		};
	}
	
	public SpiralTrack getTrack () {
		return this.track;
	}
	
	public int getGridWidth () {
		return this.gridWidth;
	}

	public int getGridHeight () {
		return this.gridHeight;
	}
	
	@Override
	public int getID () {
		return WorldPlateau.ID;
	}
	
}
