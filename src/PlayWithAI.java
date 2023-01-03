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

public class PlayWithAI extends Application {
    private int[][] board = new int[25][25];
    private boolean isBlack = true;
    private boolean isOver = false;
    private Pane pane = new Pane();
    private GridPane gridPane = new GridPane();
    private Button button = new Button("重新开始");
    Label label = new Label();

    @Override
    public void start(Stage primaryStage) throws Exception {
        gridPane.add(pane, 0, 0);
        gridPane.add(label, 0, 2);
        gridPane.add(button, 1, 2);
        Scene scene = new Scene(gridPane, 810, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("五子棋人机对局");
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
                String winner = isBlack ? "你" : "电脑";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("游戏结束");
                alert.setHeaderText(winner + "赢了");
                alert.show();
            }
            isBlack = !isBlack;
            label.setText(isBlack ? "黑棋" : "白棋");
            if (!isOver) {
                int[] point = getBestPoint();
                board[point[0]][point[1]] = isBlack ? 1 : 2;
                drawPiece(point[0], point[1]);
                if (isWin(point[0], point[1])) {
                    isOver = true;
                    String winner = isBlack ? "你" : "电脑";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("游戏结束");
                    alert.setHeaderText(winner + "赢了");
                    alert.show();
                }
                isBlack = !isBlack;
                label.setText(isBlack ? "现在轮到黑方执棋" : "现在轮到白方执棋");
            }
        });
    }

    private void drawBoard() {
        for (int i = 0; i < 25; i++) {
            Line line = new Line(15, 15 + i * 30, 735, 15 + i * 30);
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);
        }
        for (int i = 0; i < 25; i++) {
            Line line = new Line(15 + i * 30, 15, 15 + i * 30, 735);
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);
        }
    }

    private void drawPiece(int row, int col) {
        Circle circle = new Circle(15);
        circle.setCenterX(15 + col * 30);
        circle.setCenterY(15 + row * 30);
        circle.setFill(isBlack ? Color.BLACK : Color.WHITE);
        circle.setStroke(Color.BLACK);
        pane.getChildren().add(circle);
    }

    //判断是否胜利
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
        return count >= 5;
    }

    //电脑操作原理
    public int[] getBestPoint() {
        int[] point = new int[2];
        int blackMax=0;
        int whiteMax;
        int temp;
        int allMax=0;
        for(int i=0;i<25;i++){
            for(int j=0;j<25;j++){
                if(board[i][j]==0){
                    whiteMax=checkMax(i,j,2);
                    blackMax=checkMax(i,j,1);
                    temp = (whiteMax > blackMax) ? whiteMax : blackMax;
                    if(temp>allMax){
                        allMax=temp;
                        point[0]=i;
                        point[1]=j;
                    }
                }
            }
        }
        return point;
    }

    public int checkMax(int row,int col,int color){
        int cnt=1;
        int max=0;
        for(int i=1;i<5;i++){
            if(col-i>=0&&board[row][col-i]==color){
                cnt++;
            }else{
                break;
            }
        }
        for(int i=1;i<5;i++){
            if(col+i<25&&board[row][col+i]==color){
                cnt++;
            }else{
                break;
            }
        }
        if(cnt>max){
            max=cnt;
        }
        cnt=1;
        for(int i=1;i<5;i++){
            if(row-i>=0&&board[row-i][col]==color){
                cnt++;
            }else{
                break;
            }
        }
        for(int i=1;i<5;i++){
            if(row+i<25&&board[row+i][col]==color){
                cnt++;
            }else{
                break;
            }
        }
        if(cnt>max){
            max=cnt;
        }
        cnt=1;
        for(int i=1;i<5;i++){
            if(row-i>=0&&col-i>=0&&board[row-i][col-i]==color){
                cnt++;
            }else{
                break;
            }
        }
        for(int i=1;i<5;i++){
            if(row+i<25&&col+i<25&&board[row+i][col+i]==color){
                cnt++;
            }else{
                break;
            }
        }
        if(cnt>max){
            max=cnt;
        }
        cnt=1;
        for(int i=1;i<5;i++){
            if(row-i>=0&&col+i<25&&board[row-i][col+i]==color){
                cnt++;
            }else{
                break;
            }
        }
        for(int i=1;i<5;i++){
            if(row+i<25&&col-i>=0&&board[row+i][col-i]==color){
                cnt++;
            }else{
                break;
            }
        }
        if(cnt>max){
            max=cnt;
        }
        return max;
    }

    public static void main(String[] args) {
        launch(args);
    }
}