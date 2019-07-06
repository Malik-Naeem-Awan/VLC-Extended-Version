package sample.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.model.Tracks;
import sample.model.TracksDAO;
import sun.plugin2.os.windows.Windows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class playlistController  implements Initializable {

    @FXML
    private TableView FTrackTable;

    @FXML
    TableColumn<Tracks, Integer> FTrackID;

    @FXML
    TableColumn<Tracks, String> FMedia_Address;

    @FXML
    private JFXTextField PIDField;

    private ArrayList arrayList;

    private  String statement;

    private Executor executer;

    public static String path=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //For multithreading: Create executor that uses daemon threads:
        executer = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        FTrackID.setCellValueFactory(cellData -> cellData.getValue().playlist_IDProperty().asObject());
        FMedia_Address.setCellValueFactory(cellData -> cellData.getValue().playlist_NameProperty());
        FTracks();
    }

    private void populateTracks (ObservableList<Tracks> trackData) throws ClassNotFoundException {
        //Set items to the TrackTable
        FTrackTable.setItems(trackData);
    }

    @FXML
    public void FTracks(){
        ObservableList<Tracks> tracksData = null;
        try {
            //Get all Tracks information
            tracksData = TracksDAO.searchPlaylists();
            //Populate Tracks on TableView
            populateTracks(tracksData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void PlaylistTracks() {
        path = PIDField.getText();
        FlowToGUI("PlaylistTracks");
    }

    @FXML
    public void playTracks() throws SQLException, ClassNotFoundException, LoadException {
        path=PIDField.getText();
        DisplayPlayer();

    }

    private Stage stage;
    public void DisplayPlayer() {
        try {

            final double[] xOff = {0};
            final double[] yOff = {0};
            String ad= "/sample/view/VMP_P.fxml";
            FXMLLoader loader =new FXMLLoader(getClass().getResource(ad));
            Parent root=null;
            Parent root3 = loader.load();

            Stage Nstage=new Stage();
            Nstage.setScene(new Scene(root3));
            Nstage.initStyle(StageStyle.UNDECORATED);
            javafx.scene.image.Image icon= new javafx.scene.image.Image("/sample/images/Icon.png");
            Nstage.getIcons().add(icon);

            root3.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOff[0] = event.getSceneX();
                    yOff[0] = event.getSceneY();
                }
            });

            root3.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Nstage.setX(event.getScreenX() - xOff[0]);
                    Nstage.setY(event.getScreenY() - yOff[0]);
                }
            });

            root3.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent doubleClicked) {
                    if(doubleClicked.getClickCount()==2){
                        if(Nstage.isFullScreen()){
                            Nstage.setFullScreen(false);
                        }
                        else
                            Nstage.setFullScreen(true);
                    }
                }
            });
            Nstage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void  FlowToGUI(String UI){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/view/"+UI+".fxml"));
            Parent root4 = (Parent) loader.load();

            String ad="/sample/images/Icon.png";
            Stage stage=new Stage();
            stage.setScene(new Scene(root4));
            javafx.scene.image.Image icon= new javafx.scene.image.Image(ad);
            stage.getIcons().add(icon);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public  void addTracks(){
        path=PIDField.getText();
        FlowToGUI("addTracks");
    }

}