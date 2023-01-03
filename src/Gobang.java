import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Gobang extends Application {
    private int[][] board = new int[25][25];
    private boolean isBlack = true;
    private boolean isOver = false;
    private Pane pane = new Pane();
    private GridPane gridPane = new GridPane();
    private Button button = new Button("重新开始");
    Label temp_label = new Label();
    Label label = new Label("黑棋先行");

    @Override
    public void start(Stage primaryStage) throws Exception {
        gridPane.add(pane, 0, 0);
        gridPane.add(temp_label, 0, 1);
        gridPane.add(label, 0, 2);
        gridPane.add(button, 1, 2);
        Scene scene = new Scene(gridPane, 810, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("五子棋双人对局");
        primaryStage.show();
        drawBoard();

        button.setOnAction(e -> {
            isOver = false;
            isBlack = true;
            board = new int[25][25];
            pane.getChildren().clear();
            drawBoard();
        });

        pane.setOnMouseClicked(e -> {
            if (isOver) {
                return;
            }
            int x = (int) e.getX();
            int y = (int) e.getY();
            int col = x / 30;
            int row = y / 30;
            if (board[row][col] != 0) {
                return;
            }
            board[row][col] = isBlack ? 1 : 2;
            drawPiece(row, col);
            if (isWin(row, col)) {
                isOver = true;
                String winner = isBlack ? "黑棋" : "白棋";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("游戏结束");
                alert.setHeaderText(winner + "获胜，点击重新开始进行下一局");
                alert.show();
            }
            isBlack = !isBlack;
        });
    }

    private void drawPiece(int row, int col) {
        Circle circle = new Circle(15);
        circle.setCenterX(col * 30 + 15);
        circle.setCenterY(row * 30 + 15);
        circle.setFill(isBlack ? Color.BLACK : Color.WHITE);
        circle.setStroke(Color.BLACK);
        pane.getChildren().add(circle);
        label.setText("当前是" + (isBlack ? "白棋" : "黑棋") + "落子");
    }

    private void drawBoard() {
        for (int i = 0; i < 25; i++) {
            Line line = new Line(15, 15 + i * 30, 15 + 24 * 30, 15 + i * 30);
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);
        }
        for (int i = 0; i < 25; i++) {
            Line line = new Line(15 + i * 30, 15, 15 + i * 30, 15 + 24 * 30);
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);
        }
    }

    private boolean isWin(int row, int col) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (col - i >= 0 && board[row][col - i] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 5; i++) {
            if (col + i < 25 && board[row][col + i] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        for (int i = 1; i < 5; i++) {
            if (row - i >= 0 && board[row - i][col] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 5; i++) {
            if (row + i < 25 && board[row + i][col] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        for (int i = 1; i < 5; i++) {
            if (row - i >= 0 && col - i >= 0 && board[row - i][col - i] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 5; i++) {
            if (row + i < 25 && col + i < 25 && board[row + i][col + i] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        for (int i = 1; i < 5; i++) {
            if (row - i >= 0 && col + i < 25 && board[row - i][col + i] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 5; i++) {
            if (row + i < 25 && col - i >= 0 && board[row + i][col - i] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}