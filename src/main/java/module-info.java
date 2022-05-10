module com.game.stone {
    requires javafx.base;
    requires javafx.controls;
    requires com.google.gson;
    requires org.junit.jupiter.api;
    requires org.tinylog.api;

    opens com.game.stone.model;
    opens com.game.stone.util to javafx.base;

    exports com.game.stone.gui;
}