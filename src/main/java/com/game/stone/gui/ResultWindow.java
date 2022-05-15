package com.game.stone.gui;

import java.io.InputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class ResultWindow extends Application {
	private GameWindow parent;
	private String palyer;
	private int score;
	private int turns;
	private boolean isGameRestarted;

	public ResultWindow(GameWindow parent, String palyer, int score, int turns) {
		this.parent = parent;
		this.palyer = palyer;
		this.score = score;
		this.turns = turns;
	}

	public void start(Stage primaryStage) throws Exception {

		InputStream input = getClass().getResourceAsStream("/result.png");
		Image image = new Image(input);
		BackgroundSize backgroundSize = new BackgroundSize(600, 500, true, true, true, true);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, backgroundSize);

		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(100, 10, 10, 90));
		String[] texts = new String[] { String.format("Yeah, %s won！", palyer), String.format("\tScore: %d", score),
				String.format("\tTurns: %d", turns), };
		for (String str : texts) {
			Text text = new Text(str);
			text.setFill(Color.CRIMSON);
			text.setFont(Font.font("Dialog", 50));
			text.setEffect(new Glow(1.0));
			vbox.getChildren().add(text);
		}

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(100, 10, 10, 10));
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(60);

		// restart
		Button newBtn = new Button("New Game");
		gridPane.add(newBtn, 0, 0);
		newBtn.setFont(Font.font(16));
		newBtn.setUserData(0);
		newBtn.setOnAction(e -> {
			parent.resetStart();
			primaryStage.close();
		});

		// view ranking
		Button rankBtn = new Button("View Ranking");
		gridPane.add(rankBtn, 1, 0);
		rankBtn.setFont(Font.font(16));
		rankBtn.setUserData(0);
		rankBtn.setOnAction(e -> {
			RankingWindow rankingWindow = new RankingWindow();
			try {
				rankingWindow.start(new Stage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		// exit game
		Button exitBtn = new Button("Exit Game");
		gridPane.add(exitBtn, 2, 0);
		exitBtn.setFont(Font.font(16));
		exitBtn.setUserData(0);
		exitBtn.setOnAction(e -> {
			Logger.debug("Game exit");
			System.exit(0);
		});

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10, 10, 10, 20));
		borderPane.setTop(vbox);
		borderPane.setCenter(gridPane);
		borderPane.setBackground(new Background(backgroundImage));

		Scene scene = new Scene(borderPane, 600, 500);
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setTitle("Game Result");
		Image icon = new Image(getClass().getResourceAsStream("/logo.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.setOnCloseRequest(e -> {
			parent.resetStart();
		});
		primaryStage.show();
	}
	public boolean isGameRestarted(){
		return isGameRestarted;
	}

	public void setisGameRestarted(boolean b) {
	}

}