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
	private static ArrayList<String> playerNames = new ArrayList();

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

			Scene scene = new Scene(grid, scene_width, scene_height);
			primaryStage.setScene(scene);
			primaryStage.show();

			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

				switch (event.getCode()) {

				case UP:
					//playerMoved(0, -1, "up");
					try {
						Client.sendNameAndPos(me.name, me.xpos, me.ypos-1, "up");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case DOWN:
					//playerMoved(0, +1, "down");
					try {
						Client.sendNameAndPos(me.name, me.xpos, me.ypos+1, "down");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case LEFT:
					//playerMoved(-1, 0, "left");
					try {
						Client.sendNameAndPos(me.name, me.xpos-1, me.ypos, "left");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case RIGHT:
					//playerMoved(+1, 0, "right");
					try {
						Client.sendNameAndPos(me.name, me.xpos+1, me.ypos, "right");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					break;

				}
			});

			// Setting up standard players

//			me = new Player("Victor", 8, 4, "down");
//			players.add(me);
//			fields[9][4].setGraphic(new ImageView(hero_up));

			scoreList.setText(getScoreList());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playerMoved(int delta_x, int delta_y, String direction) {
		me.direction = direction;
		int x = me.getXpos(), y = me.getYpos();

		if (board[y + delta_y].charAt(x + delta_x) == 'w') {
			me.addPoints(-1);
		} else {
			Player p = getPlayerAt(x + delta_x, y + delta_y);

			if (p != null) {
				me.addPoints(10);
				p.addPoints(-10);
			} else {
				me.addPoints(1);

				fields[x][y].setGraphic(new ImageView(image_floor));
				x += delta_x;
				y += delta_y;

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
				
				me.setXpos(x);
				me.setYpos(y);
			}
		}
		scoreList.setText(getScoreList());
		try {
			Client.sendNameAndPos(me.name, me.xpos, me.ypos, me.direction);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void playerMoved(Player player, int newX, int newY, String direction) {
		player.direction = direction;
		int x = player.getXpos(), y = player.getYpos();

		if (board[newY].charAt(newX) == 'w') {
			player.addPoints(-1);
		} else {
		
			Player p = getPlayerAt(newX, newY);
	
		

			if (p != null && p != player) {
				player.addPoints(10);
				p.addPoints(-10);
			} else {
				player.addPoints(1);

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
			}
		}
		
		
		scoreList.setText(getScoreList());
		try {
		//	Client.sendNameAndPos(player.name, player.xpos, player.ypos, player.direction);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void spawnPlayer(Player player, int newX, int newY, String direction) {
		player.direction = direction;
		int x = player.getXpos(), y = player.getYpos();

		if (board[newY].charAt(newX) == 'w') {
		
		} else {
		
			Player p = getPlayerAt(newX, newY);
	
		

			if (p != null && p != player) {
//			
			} else {
			

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
			}
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

			me = new Player("Victor", 9, 4, "down");
			players.add(me);
			
	
			Client.sendNameAndPos(me.name, me.xpos, me.ypos, me.direction);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized static void readMessagefromClient (String message) {
		
		
		javafx.application.Platform.runLater(new Runnable(){
			@Override
			public void run()
			{
				String[] arr = message.split(" ");
				
				System.out.println("message from client "+ message);
				for(Player p : players) { // lav en compareTo istedet
					playerNames.add(p.name);
				}
				Player player  = new Player(arr[0],Integer.parseInt( arr[1]), Integer.parseInt(arr[2]), arr[3]);
				
				if(!playerNames.contains(arr[0])) {
					
					
					players.add(player);
					spawnPlayer(player, Integer.parseInt( arr[1]), Integer.parseInt(arr[2]), arr[3]);
				}
				else {
				
				for(Player p : players)
				{
					if(p.name.equals(arr[0]))
						player = p;
						
				}
		
				playerMoved(player, Integer.parseInt(arr[1]),Integer.parseInt( arr[2]), arr[3]);
				
				}

			}


		
		
	});
	}
}