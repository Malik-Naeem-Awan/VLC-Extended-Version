package sample.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import sample.model.Tracks;
import sample.model.TracksDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class myMusicController implements Initializable {

    public static String path;
    public static String currentTrackID;

    @FXML
    private TextField searchField;

    @FXML
    private TableView FTrackTable;

    @FXML
    TableColumn<Tracks, Integer> FTrackID;

    @FXML
    TableColumn<Tracks, String> FMedia_Address;

    private Executor executer;


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

    private void populateTracks (ObservableList<Tracks> trackData) throws ClassNotFoundException {
        //Set items to the TrackTable
        FTrackTable.setItems(trackData);
    }

    private void Address() throws SQLException, ClassNotFoundException {
        try {
            currentTrackID=searchField.getText();
            Tracks tracks = TracksDAO.searchAddress(searchField.getText());
            //Populate Track on TableView and Display on TextArea
            if(tracks.getAddress()!=null)
                path = tracks.getAddress();
            TracksDAO.updateTrackID(path);
            TracksDAO.updatePlaylistTrackID(currentTrackID);
            FTracks();
            DisplayPlayer();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void DisplayPlayer() {
        try {

            final double[] xOffset = {0};
            final double[] yOffset = {0};
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/sample/view/VMP_P.fxml"));
            Parent root=null;
            Parent root1 = loader.load();

            Stage stage=new Stage();
            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UNDECORATED);
            javafx.scene.image.Image icon= new javafx.scene.image.Image("/sample/images/Icon.png");
            stage.getIcons().add(icon);

            root1.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset[0] = event.getSceneX();
                    yOffset[0] = event.getSceneY();
                }
            });

            root1.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset[0]);
                    stage.setY(event.getScreenY() - yOffset[0]);
                }
            });

            root1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent doubleClicked) {
                    if(doubleClicked.getClickCount()==2){
                        if(stage.isFullScreen()){
                            stage.setFullScreen(false);
                        }
                        else
                            stage.setFullScreen(true);
                    }
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchAddress (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        Address();
    }

    @FXML
    public void FTracks(){
        ObservableList<Tracks> tracksData = null;
        try {
            //Get all Tracks information
            tracksData = TracksDAO.searchTracks();
            //Populate Tracks on TableView
            populateTracks(tracksData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}