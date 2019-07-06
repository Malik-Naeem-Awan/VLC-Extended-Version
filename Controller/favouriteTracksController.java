package sample.Controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Controller.playlistController;
import sample.model.Tracks;
import sample.model.TracksDAO;
import sample.util.DBUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class favouriteTracksController  implements Initializable {

    @FXML
    private TextField searchfield;
    @FXML
    private TableView FTrackTable;

    @FXML
    TableColumn<Tracks, Integer> FTrackID;

    @FXML
    TableColumn<Tracks, String> FMedia_Address;

    private Executor executer;

    public static String TID=null;

    private String statement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //For multithreading: Create executor that uses daemon threads:
        executer = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        FTrackID.setCellValueFactory(cellData -> cellData.getValue().Track_IDProperty().asObject());
        FMedia_Address.setCellValueFactory(cellData -> cellData.getValue().media_AddressProperty());
        FTracks();
    }

    private void populateTracks(ObservableList<Tracks> trackData) throws ClassNotFoundException {
        //Set items to the TrackTable
        FTrackTable.setItems(trackData);
    }

    @FXML
    public void FTracks() {
        ObservableList<Tracks> tracksData = null;
        try {
            tracksData = TracksDAO.Ftracks();
            //Populate Tracks on TableView
            populateTracks(tracksData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void PlayID(){
        TID=searchfield.getText();
        try {
            Tracks tracks=  TracksDAO.searchAddress(TID);
            if(tracks.getAddress()!=null)
                TID = tracks.getAddress();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        DisplayPlayer();
    }

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

}