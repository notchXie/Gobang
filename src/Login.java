import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Login extends Application {
    private TextField tfUsername = new TextField();
    private PasswordField pfPassword = new PasswordField();
    private Button btLogin = new Button("登录");
    private Button btRegister = new Button("注册");
    private Button btWithoutAI = new Button("双人模式（无需登录）");
    private Label lbUsername = new Label("用户名");
    private Label lbPassword = new Label("密码");
    private Label lbMessage = new Label("登录与电脑对战"+"\n"+"不登陆仅可进行双人对战");
    private String username;
    private String password;
    private String message;
    private String[] user = new String[2];

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);
        pane.add(lbUsername, 0, 0);
        pane.add(tfUsername, 1, 0);
        pane.add(lbPassword, 0, 1);
        pane.add(pfPassword, 1, 1);
        pane.add(btLogin, 0, 2);
        pane.add(btRegister, 1, 2);
        pane.add(btWithoutAI, 2, 3);
        pane.add(lbMessage, 1, 4);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10, 10, 10, 10));

        btLogin.setOnAction(e -> {
            username = tfUsername.getText();
            password = pfPassword.getText();
            try {
                Scanner input = new Scanner(new File("user.txt"));
                while (input.hasNext()) {
                    user = input.nextLine().split(" ");
                    if (username.equals(user[0]) && password.equals(user[1])) {
                        message = "登录成功";
                        lbMessage.setText(message);
                        PlayWithAI playWithAI = new PlayWithAI();
                        try {
                            playWithAI.start(primaryStage);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    } else {
                        message = "账号或密码错误";
                        lbMessage.setText(message);
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        btRegister.setOnAction(e -> {
            username = tfUsername.getText();
            password = pfPassword.getText();
            if (username.equals("") || password.equals("")) {
                lbMessage.setText("用户名或密码不能为空");
            } else {
                user[0] = username;
                user[1] = password;
                lbMessage.setText("注册成功");
            }
            try {
                File file = new File("user.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(username + " " + password);
                bw.newLine();
                bw.close();
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btWithoutAI.setOnAction(e -> {
            Gobang gobang = new Gobang();
            try {
                gobang.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(pane, 420, 280);
        primaryStage.setTitle("登录界面");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
