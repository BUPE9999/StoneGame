package com.game.stone.gui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.game.stone.model.Record;
import com.game.stone.util.DbUtil;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class RankingWindow extends Application {

	public void start(Stage primaryStage) throws Exception {
		Logger.debug("show ranking");

		InputStream input = getClass().getResourceAsStream("/rank.png");
		Image image = new Image(input);
		BackgroundSize backgroundSize = new BackgroundSize(800, 500, true, true, true, true);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, backgroundSize);

		VBox vbox = new VBox(25);
		vbox.setPadding(new Insets(30, 10, 10, 30));
		List<String> list = new ArrayList<>();
		list.add("Top\tPlayer\tScore  GameTime\t   DateTime");
		int i = 0;
		for (Record vo : DbUtil.getTopRanking(5)) {
			i++;
			list.add(String.format("  %d\t%-10s\t%4d\t\t%02d:%02d\t%s %s", i, vo.getPlayer(), vo.getScore(),
					vo.getSecond() / 60, vo.getSecond() % 60, vo.getDate().substring(2), vo.getTime().substring(0, 5)));
		}
		for (String str : list) {
			Text text = new Text(str);
			text.setFill(Color.BLUE);
			text.setFont(Font.font("Dialog", 32));
			text.setEffect(new Glow(1.0));
			vbox.getChildren().add(text);
		}

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10, 50, 10, 10));
		hbox.setAlignment(Pos.CENTER);
		Button closeBtn = new Button("Close");
		closeBtn.setFont(Font.font(16));
		closeBtn.setOnAction(e -> {
			primaryStage.close();
			Logger.debug("ranking close");
		});
		hbox.getChildren().add(closeBtn);

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10, 10, 10, 20));
		borderPane.setCenter(vbox);
		borderPane.setBottom(hbox);
		borderPane.setBackground(new Background(backgroundImage));

		Scene scene = new Scene(borderPane, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Game Ranking");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		Image icon = new Image(getClass().getResourceAsStream("/logo.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.show();
	}

}