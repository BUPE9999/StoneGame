package com.game.stone.gui;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.game.stone.model.Record;
import com.game.stone.util.DbUtil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.tinylog.Logger;

public class GameWindow extends Application {
	private String player1;
	private String player2;
	private int score1;
	private int score2;
	private int turns1;
	private int turns2;
	private int second;
	private Random random = new Random();
	private Label leftTurns;
	private Label rightTurns;
	private Label leftScore;
	private Label rightScore;
	private Label timeLab;
	private boolean first = true;
	private Timeline timeline = new Timeline();
	private EventHandler<MouseEvent> handler;
	private Map<Integer, GridPane> paneMap = new HashMap<>(5);

	public GameWindow(String player1, String player2) {
		this.player1 = player1;
		this.player2 = player2;

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				second++;
				timeLab.setText(String.format("Time: %02d:%02d", second / 60, second % 60));
			}
		});
		timeline.getKeyFrames().add(keyFrame);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane topPane = new GridPane();
		topPane.setPadding(new Insets(10, 10, 10, 10));
		topPane.setAlignment(Pos.CENTER);
		topPane.setHgap(50);
		topPane.setStyle("-fx-background-color: #a15252");
		{
			Label leftLabel = new Label("Player1:" + player1);
			leftLabel.setFont(Font.font(25));
			topPane.add(leftLabel, 0, 0);

			InputStream input = getClass().getResourceAsStream("/vs.png");
			Image image = new Image(input);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(500);
			imageView.setFitHeight(100);
			topPane.add(imageView, 1, 0, 1, 2);

			Label rightLabel = new Label("Player2:" + player2);
			rightLabel.setFont(Font.font(25));
			topPane.add(rightLabel, 2, 0);
		}
		{
			leftTurns = new Label("Turns1:" + turns1);
			leftTurns.setFont(Font.font(25));
			topPane.add(leftTurns, 0, 1);

			rightTurns = new Label("Turns2:" + turns2);
			rightTurns.setFont(Font.font(25));
			topPane.add(rightTurns, 2, 1);
		}
		{
			leftScore = new Label("Score1:" + score1);
			leftScore.setFont(Font.font(25));
			topPane.add(leftScore, 0, 2);

			rightScore = new Label("Score2:" + score2);
			rightScore.setFont(Font.font(25));
			topPane.add(rightScore, 2, 2);

			HBox hbox = new HBox();
			hbox.setAlignment(Pos.CENTER);
			timeLab = new Label("Time: 00:00");
			timeLab.setFont(Font.font(25));
			hbox.getChildren().add(timeLab);
			topPane.add(hbox, 1, 2);
		}

		this.handler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				GridPane subPane = (GridPane) event.getSource();
				String player = first ? player1 : player2;
				PickupWindow pickupWindow = new PickupWindow(GameWindow.this, subPane, player);
				try {
					pickupWindow.start(new Stage());
					Logger.debug("Stone taken");

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};

		VBox vbox = new VBox();
		vbox.setSpacing(20);
		vbox.setPadding(new Insets(20, 0, 0, 0));
		vbox.setStyle("-fx-background-color: #c78441");

		GridPane onePane = new GridPane();
		onePane.setPadding(new Insets(0, 100, 0, 0));
		onePane.setHgap(140);
		onePane.setAlignment(Pos.CENTER);
		for (int i = 1; i <= 2; i++) {
			GridPane pane = new GridPane();
			pane.setHgap(2);
			pane.setVgap(2);

			pane.setAlignment(Pos.CENTER);
			pane.setUserData(i);
			pane.setOnMouseClicked(handler);
			onePane.add(pane, i, 0);
			paneMap.put(i, pane);
		}

		GridPane twoPane = new GridPane();
		twoPane.setPadding(new Insets(10, 50, 10, -100));
		twoPane.setHgap(50);
		twoPane.setAlignment(Pos.CENTER);
		for (int i = 3; i <= 5; i++) {
			GridPane pane = new GridPane();
			pane.setHgap(2);
			pane.setVgap(2);

			pane.setAlignment(Pos.CENTER);
			pane.setUserData(i);
			pane.setOnMouseClicked(handler);
			twoPane.add(pane, i, 0);
			paneMap.put(i, pane);
		}

		vbox.getChildren().addAll(onePane, twoPane);
		initStoneCells();

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(topPane);
		borderPane.setCenter(vbox);
		timeline.play();

		Scene scene = new Scene(borderPane, 1000, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Stone Game");
		Image icon = new Image(getClass().getResourceAsStream("/logo.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.show();
	}


	/**
	 * Returns nothing
	 * init every stone cell by picture
	 * @return nothing
	 */
	private void initStoneCells() {
		Set<Integer> set = new HashSet<>();
		for (GridPane pane : paneMap.values()) {
			int type = 1 + random.nextInt(5);
			while (set.contains(type)) {
				type = 1 + random.nextInt(5);
			}
			set.add(type);
			int n = 2 + random.nextInt(9);
			for (int i = 0; i < n; i++) {
				InputStream input = getClass().getResourceAsStream("/" + type + ".png");
				ImageView imageView = new ImageView(new Image(input));
				imageView.setUserData(i);
				imageView.setFitWidth(60);
				imageView.setFitHeight(60);
				pane.add(imageView, i % 4, i / 4);
			}
		}
	}

	/**
	 * Returns nothing
	 * @param pane is the base pane to display
	 * @param count is the number of turing and score
	 * @return nothing
	 */
	public void updateCount(Pane pane, int count) {
		if (count > 0) {
			if (first) {
				turns1++;
				score1 += count;
			} else {
				turns2++;
				score2 += count;
			}
			leftTurns.setText("Turns1:" + turns1);
			rightTurns.setText("Turns2:" + turns2);
			leftScore.setText("Score1:" + score1);
			rightScore.setText("Score2:" + score2);

			int size = pane.getChildren().size();
			for (int i = 1; i <= count; i++) {
				pane.getChildren().remove(size - i);
			}
			int sum = 0;
			for (GridPane sub : paneMap.values()) {
				sum += sub.getChildren().size();
			}

			if (sum > 0) {
				first = !first;
			} else {
				timeline.stop();
				String player = first ? player1 : player2;
				int score = first ? score1 : score2;
				int turns = first ? turns1 : turns2;
				Record vo = new Record();
				vo.setDate(String.format("%tF", new Date()));
				vo.setTime(String.format("%tT", new Date()));
				vo.setPlayer(player);
				vo.setTurns(turns);
				vo.setScore(score);
				vo.setSecond(second);
				DbUtil.addRecord(vo);
				Logger.debug("Result saved");

				ResultWindow resultWindow = new ResultWindow(GameWindow.this, player, score, turns);
				try {
					resultWindow.start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void resetStart() {
		this.score1 = 0;
		this.score2 = 0;
		this.turns1 = 0;
		this.turns2 = 0;
		this.second = 0;
		this.first = true;
		leftTurns.setText("Turns1:" + turns1);
		rightTurns.setText("Turns2:" + turns2);
		leftScore.setText("Score1:" + score1);
		rightScore.setText("Score2:" + score2);
		initStoneCells();
		Logger.debug("Restart");

	}

}