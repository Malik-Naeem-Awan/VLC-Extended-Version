package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.util.DBUtil;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/VMP_GUI.fxml"));

        Scene scene= new Scene(root, 1300, 690);

        javafx.scene.image.Image icon= new javafx.scene.image.Image("/sample/images/Icon.png");

        // DBUtil db con= new DBUtil();
        // db con.dbConnect();
        //db con.dbExecuteQuery("select * from tracks_t");

        primaryStage.setTitle("VM Player");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.show();

        // To close All Windows Stages When Primary Stage set to close!
        primaryStage.setOnCloseRequest(
                event -> {
                    try {
                        DBUtil.dbDisconnect();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Platform.exit();
                });
    }
}
