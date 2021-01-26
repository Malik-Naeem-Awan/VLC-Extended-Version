package sample.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Controller.playlistController;
import sample.model.Tracks;
import sample.model.TracksDAO;
import sample.util.DBUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PlaylistTracksController  implements Initializable {

    @FXML
    private TableView FTrackTable;

    @FXML
    TableColumn<Tracks, Integer> FTrackID;

    @FXML
    TableColumn<Tracks, String> FMedia_Address;

    private Executor executer;

    private String pID;

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
            pID= playlistController.path;
            statement="CREATE or REPLACE VIEW PTracks AS " +
                    " SELECT\n" +
                    " Unique " +
                    "Tracks_T.Track_ID ,Tracks_T.Track_Location\n" +
                    "  FROM Tracks_T,PTracks_T\n" +
                    "   WHERE  (tracks_t.track_id = ptracks_t.track_id AND pTracks_t.Playlist_ID ="+pID+")" ;
            DBUtil.dbExecuteStatement(statement);
            tracksData = TracksDAO.createView();
            //Populate Tracks on TableView
            populateTracks(tracksData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}