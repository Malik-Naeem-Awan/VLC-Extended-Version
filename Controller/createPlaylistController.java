package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.model.TracksDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class createPlaylistController  implements Initializable {

    public static String Playlist_Name;

    private Executor executer;

    @FXML
    private TextField PlalistNameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //For multithreading: Create executor that uses daemon threads:
        executer = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
    }

    @FXML
    public  void createPlaylist(){
        try {
            Playlist_Name = PlalistNameField.getText();
            if (PlalistNameField != null) {
                TracksDAO.insertPlaylist(Playlist_Name);
            }
            } catch(SQLException ex){
                ex.printStackTrace();
            } catch(ClassNotFoundException ex){
                ex.printStackTrace();
            }
            Controller.stage1.close();
            FlowToGUI("addTracks");
        }


public static Stage stageCreateP;
    private void  FlowToGUI(String UI){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/view/"+UI+".fxml"));
            Parent root=null;
            Parent root2 = (Parent) loader.load();


            stageCreateP=new Stage();
            stageCreateP.setScene(new Scene(root2));
            //stageCreateP.initStyle(StageStyle.UNDECORATED);
            javafx.scene.image.Image icon= new javafx.scene.image.Image("/sample/images/Icon.png");
            stageCreateP.getIcons().add(icon);
            stageCreateP.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }