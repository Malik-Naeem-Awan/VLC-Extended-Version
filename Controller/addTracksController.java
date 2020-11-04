package sample.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.model.Tracks;
import sample.model.TracksDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class addTracksController  implements Initializable {

    public static String ID;
    public static String PlaylistName;
    public static int IDP;
    private String playlistID = null;

    @FXML
    private TextField searchField;

    @FXML
    private TableView FTrackTable;

    @FXML
    TableColumn<Tracks, Integer> FTrackID;

    @FXML
    TableColumn<Tracks, String> FMedia_Address;

    private Executor exe;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exe = Executors.newCachedThreadPool((runnable) -> {
            Thread t2 = new Thread(runnable);
            t2.setDaemon(true);
            return t2;
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
            tracksData = TracksDAO.searchTracks();
            populateTracks(tracksData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertTrack() {

        if (playlistController.path != null) {
            if (searchField.getText() != null) {
                ID = searchField.getText();
                try {
                    TracksDAO.insertTrackToPlaylist(ID, playlistController.path);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (searchField.getText() != null) {
            ID = searchField.getText();
            PlaylistName = createPlaylistController.Playlist_Name;
            Tracks tracks = null;
            try {
                tracks = TracksDAO.searchTracks(searchField.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (tracks.getAddress() != null) {
                System.out.println("Track Already in the Playlist!");
            } else if (tracks.getAddress() == null) {
                try {
                    TracksDAO.PlaylistID(PlaylistName);
                    IDP = Tracks.getIDP();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    TracksDAO.insertTrackToPlaylist(ID, String.valueOf(IDP));
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

