package game2019;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;

public class Main extends Application {

	public static final int size = 20;
	public static final int scene_height = size * 22 + 100;
	public static final int scene_width = size * 22 + 200;

	public static Image image_floor;
	public static Image image_wall;
	public static Image hero_right, hero_left, hero_up, hero_down;

	public static Player me;
	public static List<Player> players = new ArrayList<Player>();

	private static Label[][] fields;
	private static TextArea scoreList;
//	private static ArrayList<String> playerNames = new ArrayList();
	private TextField nameTxt = new TextField("indtast navn");
	private TextField posXTxt = new TextField("indtast x posistion");
	private TextField posYTxt = new TextField("indtast y posistion");
	private static String[] board = { // 20x20
			"wwwwwwwwwwwwwwwwwwww", 
			"w        ww        w", 
			"w w  w  www w  w  ww", 
			"w w  w   ww w  w  ww",
			"w  w               w", 
			"w w w w w w w  w  ww", 
			"w w     www w  w  ww", 
			"w w     w w w  w  ww",
			"w   w w  w  w  w   w", 
			"w     w  w  w  w   w", 
			"w ww ww        w  ww", 
			"w  w w    w    w  ww",
			"w        ww w  w  ww", 
			"w         w w  w  ww", 
			"w        w     w  ww", 
			"w  w              ww",
			"w  w www  w w  ww ww", 
			"w w      ww w     ww", 
			"w   w   ww  w      w", 
			"wwwwwwwwwwwwwwwwwwww" 
			};

	// -------------------------------------------
	// | Maze: (0,0) | Score: (1,0) |
	// |-----------------------------------------|
	// | boardGrid (0,1) | scorelist |
	// | | (1,1) |
	// -------------------------------------------

	@Override
	public void start(Stage primaryStage) {

		try {
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0, 10, 0, 10));

			Text mazeLabel = new Text("Maze:");
			mazeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			Text scoreLabel = new Text("Score:");
			scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			scoreList = new TextArea();

			// connect og host knapper

			Button buttonConnect = new Button("connect");
			buttonConnect.minWidth(40);
			// ip
			TextField ipTxt = new TextField("Ip");
	

			buttonConnect.setOnAction(event -> {

				try {
					String ip = ipTxt.getText();
					connectButton(ip);
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			GridPane boardGrid = new GridPane();

			image_wall = new Image(getClass().getResourceAsStream("Image/wall4.png"), size, size, false, false);
			image_floor = new Image(getClass().getResourceAsStream("Image/floor1.png"), size, size, false, false);

			hero_right = new Image(getClass().getResourceAsStream("Image/heroRight.png"), size, size, false, false);
			hero_left = new Image(getClass().getResourceAsStream("Image/heroLeft.png"), size, size, false, false);
			hero_up = new Image(getClass().getResourceAsStream("Image/heroUp.png"), size, size, false, false);
			hero_down = new Image(getClass().getResourceAsStream("Image/heroDown.png"), size, size, false, false);

			fields = new Label[20][20];

			for (int j = 0; j < 20; j++) {

				for (int i = 0; i < 20; i++) {

					switch (board[j].charAt(i)) {

					case 'w':
						fields[i][j] = new Label("", new ImageView(image_wall));
						break;

					case ' ':
						fields[i][j] = new Label("", new ImageView(image_floor));
						break;

					default:
						throw new Exception("Illegal field value: " + board[j].charAt(i));
					}
					boardGrid.add(fields[i][j], i, j);
				}
			}
			scoreList.setEditable(false);

			grid.add(mazeLabel, 0, 0);
			grid.add(scoreLabel, 1, 0);
			grid.add(boardGrid, 0, 1);
			grid.add(scoreList, 1, 1);

			grid.add(buttonConnect, 1, 2);
			grid.add(ipTxt, 1, 3);
			grid.add(nameTxt,1,4);
			grid.add(posXTxt,0,3);
			grid.add(posYTxt,0,4);
			Scene scene = new Scene(grid, scene_width, scene_height);
			primaryStage.setScene(scene);
			primaryStage.show();

			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

				switch (event.getCode()) {

				case UP:
					planMove(0, -1, "up");
				
//			
					break;
				case DOWN:
					planMove(0, 1, "down");
//				
					break;
				case LEFT:
					planMove(-1, 0, "left");
//				
					break;
				case RIGHT:
					planMove(1, 0, "right");
			
					break;
				default:
					break;

				}
			});


			scoreList.setText(getScoreList());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void planMove(int delta_x, int delta_y, String direction) {
		//system.out.println("planMove ");
		me.direction = direction;
		int x = me.getXpos(), y = me.getYpos();
		//system.out.println("my xpos: "+x + " my ypos: "+y +" delta x: " +delta_x + " delta y: "+delta_y);

		
				
				try {
			
					Client.sendNameAndPos(me.name, (me.xpos +delta_x), (me.ypos +delta_y), direction);
					//system.out.println("send new move: "+ (me.xpos+delta_x) + " " + (me.ypos+delta_y));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		scoreList.setText(getScoreList());
	
	}
	
	public static void playerMoved(Player player, int newX, int newY, String direction) {
		player.direction = direction;
		int x = player.getXpos(), y = player.getYpos();



				
				if (board[newY].charAt(newX) == 'w')
				{
					System.out.println("mur");
					player.addPoints(-1);
				}
				else {
				Player p = getPlayerAt(newX, newY);
				
				if (p != null && !p.equals(player) ) {
					player.addPoints(10);
					p.addPoints(-10);
				}
				else {
					fields[x][y].setGraphic(new ImageView(image_floor));
					x =newX;
					y =newY;

				if (direction.equals("right")) {
					fields[x][y].setGraphic(new ImageView(hero_right));
				};
				
				if (direction.equals("left")) {
					fields[x][y].setGraphic(new ImageView(hero_left));
				};
				
				if (direction.equals("up")) {
					fields[x][y].setGraphic(new ImageView(hero_up));
				};
				
				if (direction.equals("down")) {
					fields[x][y].setGraphic(new ImageView(hero_down));
				};
				
				player.setXpos(x);
				player.setYpos(y);
				player.addPoints(1);
				}
				}
				//system.out.println("player moved" + player.getXpos() + player.getYpos());
				

		scoreList.setText(getScoreList());

	}
	
	public static void spawnPlayer(Player player, int newX, int newY, String direction) {
		player.direction = direction;
		//system.out.println("spawned player");
		int x = player.getXpos(), y = player.getYpos();

		if (board[newY].charAt(newX) == 'w') {
		
//		} else {
//		
//			Player p = getPlayerAt(newX, newY);
//	
//			if (p != null && p.equals( player)) {
		
			} else if(newX != x || newY != y) {
			
				fields[x][y].setGraphic(new ImageView(image_floor));
				

				if (direction.equals("right")) {
					fields[x][y].setGraphic(new ImageView(hero_right));
				};
				
				if (direction.equals("left")) {
					fields[x][y].setGraphic(new ImageView(hero_left));
				};
				
				if (direction.equals("up")) {
					fields[x][y].setGraphic(new ImageView(hero_up));
				};
				
				if (direction.equals("down")) {
					fields[x][y].setGraphic(new ImageView(hero_down));
				};
				
				player.setXpos(newX);
				player.setYpos(newY);
		
			
		}
		
		scoreList.setText(getScoreList());
		try {
		//	Client.sendNameAndPos(player.name, player.xpos, player.ypos, player.direction);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getScoreList() {
		StringBuffer b = new StringBuffer(100);
		
		for (Player p : players) {
			b.append(p + "\r\n");
		}
		return b.toString();
	}

	public static Player getPlayerAt(int x, int y) {
		for (Player p : players) {
			if (p.getXpos() == x && p.getYpos() == y) {
				return p;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void connectButton(String ip) throws UnknownHostException, IOException {

		try {
			Client.Connect(ip);
			int x =Integer.parseInt(posXTxt.getText());
			int y =Integer.parseInt(posYTxt.getText());
			String myName = nameTxt.getText();
			me = new Player(myName, x, y, "down");
			players.add(me);
			fields[x][y].setGraphic(new ImageView(image_floor));
		

			if (me.direction.equals("right")) {
				fields[x][y].setGraphic(new ImageView(hero_right));
			};
			
			if (me.direction.equals("left")) {
				fields[x][y].setGraphic(new ImageView(hero_left));
			};
			
			if (me.direction.equals("up")) {
				fields[x][y].setGraphic(new ImageView(hero_up));
			};
			
			if (me.direction.equals("down")) {
				fields[x][y].setGraphic(new ImageView(hero_down));
			};
			
	
			//Client.sendNameAndPos(me.name, me.xpos, me.ypos, me.direction);
			//spawnPlayer(me, Integer.parseInt(posXTxt.getText()), Integer.parseInt(posYTxt.getText()), "down");
			Client.spawnPlayer(myName, x, y, "down");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized static void readMessagefromClient (String message) {
		
		javafx.application.Platform.runLater(new Runnable(){
			@Override
			public void run() {
				
				String[] arr = message.split(" ");
				
				//system.out.println("message from client: "+ message);
				
//				for(Player p : players) { // lav en compareTo istedet
//					playerNames.add(p.name);
//				}
				if(arr[0].equals("score"))
				{
					for(Player p : players)
					{
						if (arr[1].equals(p.name))
							p.setPoints(Integer.parseInt(arr[2]));
					}
				}
				else {
				Player player  = new Player(arr[1],Integer.parseInt( arr[2]), Integer.parseInt(arr[3]), arr[4]);
				
				if(arr[0].equals("spawn"))
				{
					
					if(!players.contains(player)) {
											
						players.add(player);
						spawnPlayer(player, Integer.parseInt( arr[2]), Integer.parseInt(arr[3]), arr[4]);
						for(Player p : players)
						{
							p.setPoints(0);
						}
						
					}
				}
				else if (arr[0].equals("move"))
				{
					if(!players.contains(player)) {
						
						players.add(player);
						spawnPlayer(player, Integer.parseInt( arr[2]), Integer.parseInt(arr[3]), arr[4]);
						
					}
					for(Player p : players) {
						
						if(p.equals(player)) {
							player = p;
						}
					}
					playerMoved(player, Integer.parseInt(arr[2]),Integer.parseInt( arr[3]), arr[4]);
				}
				}
				

			}		
	});
	}
}