package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    Controller c1 = new Controller();
    protected enum UserAction{
        NONE, UP_P1, DOWN_p1, UP_P2, DOWN_P2
    }

    @Override
    public void start(Stage primaryStage){

        Scene scene = new Scene(c1.create_content());
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case W:
                    c1.action = UserAction.UP_P1;
                    break;
                case S:
                    c1.action = UserAction.DOWN_p1;
                    break;
                case O:
                    c1.action2 = UserAction.UP_P2;
                    break;
                case L:
                    c1.action2 = UserAction.DOWN_P2;
                    break;
            }
        });
        primaryStage.setTitle("2 player Pong");
        primaryStage.setScene(scene);
        primaryStage.show();
        c1.start_game();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
