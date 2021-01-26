package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.model.Tracks;
import sample.model.TracksDAO;
import sample.util.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Controller
{
    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox Vbox;

    @FXML
    private TextField searchField;

    @FXML
    private TableView TrackTable;

    @FXML
    TableColumn<Tracks, Integer> TrackID;

    @FXML
    TableColumn<Tracks, String> Media_Address;

    @FXML
    TableColumn<Tracks, String> Track_Name;

    @FXML
    TableColumn<Tracks, String> Album_Name;

    @FXML
    TableColumn<Tracks, String> Artist_Name;

    private String statement;
    //For MultiThreading
    private Executor exec;

    public static ArrayList array=null;
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {

        //For multithreading: Create executor that uses daemon threads:
        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        TrackID.setCellValueFactory(cellData -> cellData.getValue().Track_IDProperty().asObject());
        Media_Address.setCellValueFactory(cellData -> cellData.getValue().media_AddressProperty());
        Track_Name.setCellValueFactory(cellData -> cellData.getValue().track_NameProperty());
        Album_Name.setCellValueFactory(cellData -> cellData.getValue().album_NameProperty());
        Artist_Name.setCellValueFactory(cellData -> cellData.getValue().artist_NameProperty());

        AllTracks();
    }

    //Search any track with  given in Search field:
    @FXML
    private void searchTrack (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            Tracks tracks = TracksDAO.searchTrack(searchField.getText());
            //Populate Track on TableView and Display on TextArea
            if(tracks!=null) {
                populateTrack(tracks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Delete a Track with a given Track Id from DB
    @FXML
    private void deleteTrack (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            TracksDAO.deleteTrackWithId(searchField.getText());
            TracksDAO.deletePlaylistTrackWithId(searchField.getText());
        } catch (SQLException e) {
            System.out.println("not deleted");
            throw e;
        }
    }

    //Get all Tracks
    @FXML
    private void getTracks(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        AllTracks();
    }

    public void AllTracks(){
        ObservableList<Tracks> tracksData1 = null;
        try {
         /*   statement = "CREATE or REPLACE VIEW PTracks AS " +
                    " SELECT\n" +
                    " Unique " +
                    "Tracks_T.Track_ID ,Tracks_T.Track_Location\n" +
                    "  FROM Tracks_T,PTracks_T\n" +
                    "   WHERE  (tracks_t.track_id = ptracks_t.track_id AND pTracks_t.Playlist_ID =" + playlistController.path + ")";
            */
           statement= "CREATE or REPLACE VIEW ATracks AS\n" +
                    "  SELECT  Tracks_T.Track_ID ,Tracks_T.Date_Added, Tracks_T.Track_Location,Tracks_T.Track_Name,\n" +
                    "  album_t.album_id,album_t.album_name,artist_t.artist_id,artist_t.artist_name\n" +
                    "  FROM Tracks_T,Album_t,Artist_t\n" +
                    "  where album_t.album_id=tracks_t.album_id AND artist_t.artist_id=album_t.artist_id";
            try {
                DBUtil.dbExecuteStatement(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //Selecting data from View created above:
            tracksData1 = TracksDAO.fullData();
            populateTracks(tracksData1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Populate Tracks
    @FXML
    private void populateTrack (Tracks tracks) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Tracks> trackData = FXCollections.observableArrayList();
        //Add Track to the ObservableList
        trackData.add(tracks);
        //Set items to the TrackTable
        TrackTable.setItems(trackData);
    }

    //Populate Track for TableView with MultiThreading (This is for example usage)
   /* private void fillTrackTable(ActionEvent event) throws SQLException, ClassNotFoundException {
        Task<List<Tracks>> task = new Task<List<Tracks>>(){
            @Override
            public ObservableList<Tracks> call() throws Exception{
                return TracksDAO.searchTracks();
            }
        };

        task.setOnFailed(e-> task.getException().printStackTrace());
        task.setOnSucceeded(e-> TrackTable.setItems((ObservableList<Tracks>) task.getValue()));
        exec.execute(task);
    }
*/
    //Populate Track
    // @FXML
    // private void populateTrack (Tracks tracks) throws ClassNotFoundException {
    //Declare and ObservableList for table view
    // ObservableList<Tracks> TrackData = FXCollections.observableArrayList();
    //Add Track to the ObservableList
    // TrackData.add(tracks);
    //Set items to the TrackTable
    // TrackTable.setItems(TrackData);
    // }

    //Set Track information to Text Area
    // @FXML
    // private void setTrackInfoToTextArea ( employee emp) {
    // resultArea.setText("First Name: " + Track.getFirstName() + "\n" +
    // "Last Name: " + Track.getLastName());
    // }

    //Populate Track for TableView and Display Track on TextArea
   /* @FXML
    private void populateAndShowTrack(Tracks tracks) throws ClassNotFoundException {
        if (tracks != null) {
           // populateTracks((tracks);
            //setTrackInfoToTextArea(emp);
        } else {
           // resultArea.setText("This Track does not exist!\n");
        }
    }
*/
    //Populate Tracks for TableView
    @FXML
    private void populateTracks (ObservableList<Tracks> trackData) throws ClassNotFoundException {
        //Set items to the TrackTable
        TrackTable.setItems(trackData);
    }

    @FXML
    private void playlist() {
        FlowToGUI("playlist");
    }

    @FXML
    private void Music() {
        FlowToGUI("myMusic");
    }

    @FXML
    private void recentMedia() {
        FlowToGUI("recentMedia");
    }

    @FXML
    private void artist() {
        FlowToGUI("artist");
    }

    @FXML
    private void albums() {
        FlowToGUI("albums");
    }

    @FXML
    private void DisplayPlayer(ActionEvent event) {
        FlowToPlayer();
    }

    @FXML
    private void createPlaylist(){
        FlowToGUI("createPlaylist");
    }

    public static Stage stage1;
    private void FlowToPlayer(){
        try {

            final double[] x = {0};
            final double[] y = {0};
            String v= "/sample/view/VMP_P.fxml";
            FXMLLoader loader =new FXMLLoader(getClass().getResource(v));
            Parent root=null;
            Parent root12 = loader.load();
            Stage stagep=new Stage();
            stagep.setScene(new Scene(root12));
            stagep.initStyle(StageStyle.UNDECORATED);
            stagep.initModality(Modality.WINDOW_MODAL);
            String ab="/sample/images/Icon.png";
            javafx.scene.image.Image icon= new javafx.scene.image.Image(ab);
            stagep.getIcons().add(icon);

            root12.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    x[0] = event.getSceneX();
                    y[0] = event.getSceneY();
                }
            });

            root12.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stagep.setX(event.getScreenX() - x[0]);
                    stagep.setY(event.getScreenY() - y[0]);
                }
            });

            root12.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent doubleClicked) {
                    if(doubleClicked.getClickCount()==2){
                        if(stagep.isFullScreen()){
                            stagep.setFullScreen(false);
                        }
                        else
                        {
                            stagep.setFullScreen(true);
                        }
                    }
                }
            });
            stagep.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void  FlowToGUI(String UI){
        try {
            String vi="/sample/view/"+UI+".fxml";
            FXMLLoader loader=new FXMLLoader(getClass().getResource(vi));
            Parent root=null;
            Parent root5 = (Parent) loader.load();

            String ads="/sample/images/Icon.png";
            stage1=new Stage();
            stage1.initModality(Modality.APPLICATION_MODAL);
            if(UI =="aboutWindow"){
                stage1.setTitle("ABOUT");
                stage1.setResizable(false);
            }
            else if(UI == "playlist"){
                stage1.setTitle("Playlist");
            }
            else if(UI == "helpWindow"){
                stage1.setTitle("Help");
                stage1.setResizable(false);
            }
            else if(UI == "album"){
                stage1.setTitle("Album");
            }
            else if(UI == "artist"){
                stage1.setTitle("Artist");
            }
            else if(UI == "myMusic"){
                stage1.setTitle("Music");
            }
            else if(UI =="recentMedia"){
                stage1.setTitle("RecentMedia");
            }
            else if(UI =="createPlaylist"){
                stage1.setTitle("New Playlist");
                stage1.initStyle(StageStyle.UNDECORATED);
            }
            else if(UI =="DatabaseInfo"){
                stage1.setTitle("Database Info");
                stage1.setResizable(false);
            }
            else if(UI =="favouriteTracks"){
                stage1.setTitle("Favourite Tracks");
                stage1.setResizable(false);
            }


            stage1.setScene(new Scene(root5));
            javafx.scene.image.Image icon= new javafx.scene.image.Image(ads);
            stage1.getIcons().add(icon);
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void aboutWindow(){
        FlowToGUI("aboutWindow");
    }

    @FXML
    void helpWindow(){
        FlowToGUI("helpWindow");
    }

    @FXML
    void  favourites(){
        System.out.println("Favourit Tracks");
    }

    @FXML
    void DatabaseInfo(){
        FlowToGUI("DatabaseInfo");
    }

    @FXML
    void addtoFavourites(){
        int track_id= Integer.parseInt(searchField.getText());
        try {
            TracksDAO.insertFavouriteID(track_id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delFavourite(){
        int track_id= Integer.parseInt(searchField.getText());
        try {
            TracksDAO.delFavouriteID(track_id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void favouriteTracks(){
        FlowToGUI("favouriteTracks");
    }
    @FXML
    void Album() throws SQLException, ClassNotFoundException {

        ObservableList<Tracks> tracksData = null;
        try {
            tracksData = TracksDAO.sortby_Album();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Populate Employees on TableView
        try {
            populateTracks(tracksData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Artist(){
        ObservableList<Tracks> tracksData = null;
        try {
            tracksData = TracksDAO.sortby_Artist();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Populate Employees on TableView
        try {
            populateTracks(tracksData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void  playAllTracks() throws SQLException, ClassNotFoundException {
        array =TracksDAO.PATracks();
        FlowToPlayer();

    }

    @FXML
    void date_added(ActionEvent event) {
        by_date();
    }
    public void by_date(){
        ObservableList<Tracks> tracksData = null;
        try {
            tracksData = TracksDAO.sortby_date();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Populate Employees on TableView
        try {
            populateTracks(tracksData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
