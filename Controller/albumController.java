package sample.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.model.Tracks;
import sample.model.TracksDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class albumController  implements Initializable {

    @FXML
    private TableView FTrackTable;

    @FXML
    TableColumn<Tracks, Integer> FAlbumID;

    @FXML
    TableColumn<Tracks, String> FArtist_Name;

    @FXML
    TableColumn<Tracks, Integer> FArtistID;

    private Executor executer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //For multithreading: Create executor that uses daemon threads:
        executer = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        FAlbumID.setCellValueFactory(cellData -> cellData.getValue().album_IDProperty().asObject());
        FArtistID.setCellValueFactory(cellData -> cellData.getValue().albumArtist_IDProperty().asObject());
        FArtist_Name.setCellValueFactory(cellData -> cellData.getValue().album_NameProperty());

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
            tracksData = TracksDAO.searchAlbums();
            //Populate Tracks on TableView
            populateTracks(tracksData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}