import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

@SuppressWarnings("unused")
public class OceanExplorer extends Application {
	boolean[][] islandMap;
	final int dimension = 10;
	final int islandCount = 10;
	final int scale = 50;
	OceanMap oceanMap;
	
	Image shipImage;
	ImageView shipImageView;
	Image islandImage;
	ImageView islandImageView;
	Image pirateShipImage;
	ImageView pirateShipImageView;
	
	AnchorPane root;
	Scene scene;
	Ship ship;

	public void loadShipImage() {
		try {
			Image shipImage = new Image("ship.png", 50, 50, true, true);
			shipImageView = new ImageView(shipImage);
			shipImageView.setX(oceanMap.getShipLocation().x * scale);
			shipImageView.setY(oceanMap.getShipLocation().y * scale);
			root.getChildren().add(shipImageView);
		} catch (Exception ex) {
			System.out.println("Unable to open file" + shipImage + "'");
			ex.printStackTrace();
		}
	}

	private void loadIslandImage() {
		try {
			Image islandImage = new Image("pirateIsland.png", 50, 50, true, true);
			islandImageView = new ImageView(islandImage);
			islandImageView.setX(oceanMap.getIslandLocation().x * scale);
			islandImageView.setX(oceanMap.getIslandLocation().y * scale);
			root.getChildren().add(islandImageView);
		} catch (Exception ex) {
			System.out.println("Unable to open file" + islandImage + "'");
			ex.printStackTrace();
		}

	}

	public void drawMap() {
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				Rectangle rect = new Rectangle(x * scale, y * scale, scale, scale);
				rect.setStroke(Color.BLACK); // We want the black outline
				rect.setFill(Color.PALETURQUOISE); // I like this color better than BLUE
				root.getChildren().add(rect); // Add to the node tree in the pane
			}
			ship = new Ship(oceanMap);
			loadShipImage();
		}
	}
	public void reset_grid_button() {
		
	}

	public void startSailing() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				switch (key.getCode()) {
				case RIGHT:
					ship.goEast();
					break;
				case LEFT:
					ship.goWest();
					break;
				case UP:
					ship.goNorth();
					break;
				case DOWN:
					ship.goSouth();
					break;
				default:
					break;
				}
				shipImageView.setX(ship.getShipLocation().x * scale);
				shipImageView.setY(ship.getShipLocation().y * scale);
			}
		});
	}

	@Override
	public void start(Stage OceanStage) throws Exception {
		oceanMap = new OceanMap(dimension, islandCount);
		islandMap = oceanMap.getMap();
		root = new AnchorPane();
		drawMap();
		ship = new Ship(oceanMap);
		loadShipImage();
		Button reset = new Button();
		reset.setText("Reset");
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				reset_grid_button();	
			}
		});
		
		Scene scene = new Scene(root, 500, 600);
		root.getChildren().add(reset);
		OceanStage.setTitle("Columbus Game");
		OceanStage.setScene(scene);
		OceanStage.show();
		//startSailing();
	}

	public static void main(String[] args) {
		launch(args);
	}

}