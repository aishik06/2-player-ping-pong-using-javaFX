package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.Parent;


public class Controller {

    protected int score_p1 = 0;
    protected int score_p2 = 0;
    private final int max_score = 5;
    private static final double app_width = 800;
    private static final double app_height = 600;


    private static final int bat_width = 15;
    private static final int bat_height = 100;
    private static final int ball_radius = 15;

    private final Circle ball = new Circle(ball_radius);
    private final Rectangle bat1 = new Rectangle(bat_width, bat_height);
    private final Rectangle bat2 = new Rectangle(bat_width, bat_height);
    Text player1Score = new Text();
    Text t2 = new Text();

    private boolean ball_y_pos = true, ball_x_pos = false;
    protected Main.UserAction action = Main.UserAction.NONE;
    protected Main.UserAction action2 = Main.UserAction.NONE;

    private final Timeline timeline = new Timeline();
    private boolean running = true;

    protected Parent create_content(){
        Pane root = new Pane();
        player1Score.setTranslateY(app_height - 500);
        player1Score.setTranslateX(app_width - 600);
        player1Score.setFont(Font.font(30));
        t2.setTranslateY(app_height - 500);
        t2.setTranslateX(app_width - 200);
        t2.setFont(Font.font(30));
        root.setPrefSize(app_width, app_height);
        bat1.setTranslateY(app_height/2);
        bat1.setTranslateX(0);
        bat2.setTranslateY(app_height/2);
        bat2.setTranslateX(app_width - bat_width);
        bat1.setFill(Color.GRAY);
        bat2.setFill(Color.GRAY);

        KeyFrame frame = new KeyFrame(Duration.seconds(0.015), event -> {
            if(!running)
                return;

            switch(action){
                case UP_P1:
                    if(bat1.getTranslateY() - 5 >= 0)
                        bat1.setTranslateY(bat1.getTranslateY() - 5);
                    break;
                case DOWN_p1:
                    if(bat1.getTranslateY() + bat_height + 5 <= app_height)
                        bat1.setTranslateY(bat1.getTranslateY() + 5);
                    break;

                case NONE:
                    break;
            }
            switch(action2){
                case UP_P2:
                    if(bat2.getTranslateY() - 5 >= 0)
                        bat2.setTranslateY(bat2.getTranslateY() - 5);
                    break;
                case DOWN_P2:
                    if(bat2.getTranslateY() + bat_height + 5 <= app_height)
                        bat2.setTranslateY(bat2.getTranslateY() + 5);
                    break;
                case NONE:
                    break;
            }
            ball.setTranslateX(ball.getTranslateX() + (ball_x_pos ? -5 : 5));
            ball.setTranslateY(ball.getTranslateY() + (ball_y_pos ? 5 : -5));

            if(ball.getTranslateY() + ball_radius == app_height)
                ball_y_pos = false;
            else if(ball.getTranslateY() - ball_radius == 0)
                ball_y_pos = true;

            if((ball.getTranslateX() < bat1.getTranslateX() + bat_width) && ball.getTranslateY() >= bat1.getTranslateY() && ball.getTranslateY() <= bat1.getTranslateY() + bat_height)
                ball_x_pos = false;
            else if((ball.getTranslateX() + ball_radius > bat2.getTranslateX()) && ball.getTranslateY() >= bat2.getTranslateY() && ball.getTranslateY() <= bat2.getTranslateY() + bat_height)
                ball_x_pos = true;

            //If p1 misses the ball, p2 gets a point
            if(ball.getTranslateX() < bat1.getTranslateX() - bat_width){
                score_p2++;
                t2.setText(Integer.toString(score_p2));
                if(score_p2 == max_score){
                    stop_game();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player 2 has won");
                    alert.setTitle("Game has ended");
                    alert.setHeaderText("Congratulations player 2");
                    alert.show();
                }
                else {
                    restart_game();
                }
            }
            //if p2 misses, p1 gets a point
            if(ball.getTranslateX() > bat2.getTranslateX() + bat_width){
                score_p1++;
                player1Score.setText(Integer.toString(score_p1));
                if(score_p1 == max_score){
                    stop_game();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player 1 won");
                    alert.setTitle("Game has ended");
                    alert.setHeaderText("Congratulations player 1");
                    alert.show();
                }
                else {
                    restart_game();
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(ball, bat1, bat2, player1Score, t2);

        return root;
    }

    private void restart_game(){
        stop_game();
        start_game();
    }

    private void stop_game(){
        running = false;
        timeline.stop();
    }

    protected void start_game(){
        ball_y_pos = true;
        ball.setTranslateX(app_width/2);
        ball.setTranslateY(app_height/2);

        timeline.play();
        running = true;
    }
}
