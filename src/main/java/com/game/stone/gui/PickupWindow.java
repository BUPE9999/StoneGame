package com.game.stone.gui;

import java.io.InputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class PickupWindow extends Application {
	private GameWindow parent;
	private GridPane pane;
	private String palyer;
	private int count;

	public PickupWindow(GameWindow parent, GridPane pane, String palyer) {
		this.parent = parent;
		this.pane = pane;
		this.palyer = palyer;
	}

	public void start(Stage primaryStage) throws Exception {
		InputStream input = getClass().getResourceAsStream("/pickup.png");
		Image image = new Image(input);
		BackgroundSize backgroundSize = new BackgroundSize(500, 150, true, true, true, true);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, backgroundSize);

		// pick up title
		String text = String.format("Hi %s,Please take some stones", palyer);
		Label infoLabel = new Label(text);
		infoLabel.setFont(Font.font(22));

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(60, 10, 10, 10));
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(20);

		// click handle
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button btn = (Button) event.getSource();
				count = (int) btn.getUserData();
				primaryStage.close();
				parent.updateCount(pane, count);
				Logger.debug("Have chosen: "+count+" stone(s)");
			}
		};

		// pick up number setting
		int size = pane.getChildren().size();
		int col = 0;

		// i == 1 so that we can still tank stone when 1 stone remaining
		for (int i = 1; i == 1 || i < size; i++) {
			if (size % i == 0) {
				Button btn = new Button("Block " + i);
				btn.setFont(Font.font(14));
				btn.setUserData(i);
				btn.setOnAction(handler);
				gridPane.add(btn, col++, 0);
			}

		}

		Button btn = new Button("Close");
		btn.setFont(Font.font(14));
		btn.setUserData(0);
		btn.setOnAction(handler);
		gridPane.add(btn, col, 0);

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10, 10, 10, 20));
		borderPane.setTop(infoLabel);
		borderPane.setCenter(gridPane);
		borderPane.setBackground(new Background(backgroundImage));

		Scene scene = new Scene(borderPane, 500, 150);
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setTitle("Pickup Stone");
		Image icon = new Image(getClass().getResourceAsStream("/logo.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.show();
	}

	public int getCount() {
		return count;
	}

}