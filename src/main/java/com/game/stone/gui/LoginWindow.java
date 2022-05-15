package com.game.stone.gui;

import java.io.InputStream;

import com.game.stone.util.DbUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class LoginWindow extends Application {
	private TextField player1Text;
	private TextField player2Text;
	private boolean isGameStarted;

	public void start(Stage primaryStage) throws Exception {
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(-30, 10, 10, 10));

		InputStream input = getClass().getResourceAsStream("/login.png");
		Image image = new Image(input);
		BackgroundSize backgroundSize = new BackgroundSize(500, 350, true, true, true, true);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, backgroundSize);
		gridPane.setBackground(new Background(backgroundImage));

		gridPane.setAlignment(Pos.CENTER);
		gridPane.setVgap(40);
		gridPane.setHgap(10);

		Scene scene = new Scene(gridPane, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login Game");
		Image icon = new Image(getClass().getResourceAsStream("/logo.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.show();

		{
			Label label = new Label("Player1：");
			label.setFont(Font.font(20));
			gridPane.add(label, 0, 0);

			player1Text = new TextField();
			player1Text.setPromptText("Please enter your name");
			player1Text.setFont(Font.font(20));
			gridPane.add(player1Text, 1, 0);

		}
		{
			Label label = new Label("Player2：");
			label.setFont(Font.font(20));
			gridPane.add(label, 0, 1);

			player2Text = new TextField();
			player2Text.setPromptText("Please enter your name");
			player2Text.setFont(Font.font(20));
			gridPane.add(player2Text, 1, 1);

		}

		HBox box = new HBox();
		box.setPadding(new Insets(10, 50, 10, 10));
		box.setSpacing(50);
		{
			Button startBtn = new Button(" Start  ");
			startBtn.setFont(Font.font(20));
			startBtn.setOnAction(e -> {
				String player1 = player1Text.getText().trim();
				String player2 = player2Text.getText().trim();
				GameWindow gameWindow = new GameWindow(player1, player2);
				try {
					DbUtil.initData();
					gameWindow.start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Logger.debug("Welcome "+player1);
				Logger.debug("Welcome "+player2);
				primaryStage.close();
				Logger.debug("Game Start");
			});
			gridPane.add(startBtn, 0, 2);

			Button exitBtn = new Button("  Exit  ");
			exitBtn.setFont(Font.font(20));
			exitBtn.setOnAction(e -> {
				Logger.debug("Game exit");
				System.exit(0);
			});

			box.getChildren().addAll(startBtn, exitBtn);
			gridPane.add(box, 1, 2, 2, 1);
		}
	}

	public boolean isGameStarted(){
		return isGameStarted;
	}

	public void setGameStarted(boolean b) {
	}

	public static void main(String[] args) {
		launch(args);
	}
}