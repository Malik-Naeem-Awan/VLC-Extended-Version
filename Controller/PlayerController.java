package sample.Controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.model.Tracks;
import sample.model.TracksDAO;
import sample.util.DBUtil;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayerController  implements Initializable{

    private MediaPlayer mediaPlayer;

    private String filepath;

    private ArrayList tracksData = null;

    @FXML
    private MediaView mediaView;

    @FXML
    public static BorderPane top;

    @FXML
    private Slider  seekslider;

    @FXML
    private Slider slider;

    /*

    @FXML
    private VBox bar;
    */

    /*
    @FXML
    private Button openFile;
    */

    private String statement;

    @FXML
    private Button startImage;

    @FXML
    public static Button title;

    @FXML
    public static Label VMP;

    private int i;
    /*
    private double xOffset=0;
    private double yOffset=0;
    */

    @FXML
    private void openFile (ActionEvent event) throws ClassNotFoundException, SQLException {

    /*  FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filepath = file.toURI().toString();
    */
        startImage.setBackground(null);

        if (filepath == null) {
            FileChooser fileChooser = new FileChooser();
           // fileChooser.setInitialDirectory(new File("C:\\[FreeCourseSite.com] Udemy -
            // The Complete Android Oreo Developer Course - Build 23 Apps!"));
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);
            fileChooser.setInitialDirectory(new File("C:\\Users\\Malik\\Desktop\\Tutorials"));
            File file = fileChooser.showOpenDialog(null);
            filepath = file.toURI().toString();

            if(filepath!=null){
                try {
                    Tracks tracks = TracksDAO.compareTrack(filepath);
                    if(tracks==null){
                        try {
                            TracksDAO.insertTrack(filepath);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else
                    {
                       try {
                           TracksDAO.updateTrackID(filepath);
                       } catch (SQLException ex){
                           ex.printStackTrace();
                       } catch (ClassNotFoundException ex){
                           ex.printStackTrace();
                       }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        /*       try {
              TracksDAO.insertTrack(filepath);
          } catch (SQLException e) {
              e.printStackTrace();
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          }
         */
        if (filepath != null) {
            playMedia();
        }
    }

    private void playMedia(){

        Media media = new Media(filepath);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();
        filepath=null;
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        slider.setValue(mediaPlayer.getVolume() * 100);

        slider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(slider.getValue() / 100);
            }
        });
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                seekslider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                seekslider.setValue(newValue.toSeconds());
            }
        });

        seekslider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                    mediaPlayer.seek(Duration.seconds(seekslider.getValue()));
                    mediaPlayer.play();

            }
        });
        mediaPlayer.play();
    }

    private  void PlaylistPlay() {

        if(filepath!=null) {
            Media media = new Media(filepath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            ++i;
            if (i == tracksData.size()) {
                System.out.println("End of Playlist");
                filepath=null;
            } else {
                filepath = tracksData.get(i).toString();
            }

            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
            slider.setValue(mediaPlayer.getVolume() * 100);

            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slider.getValue() / 100);
                }
            });
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    seekslider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                    seekslider.setValue(newValue.toSeconds());
                }

            });

            seekslider.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(seekslider.getValue()));
                    mediaPlayer.play();
                }

            });

            mediaPlayer.setOnEndOfMedia(() -> PlaylistPlay());
            mediaPlayer.play();
        }
    }

     @FXML
     void pauseV(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
            case SPACE:
                mediaPlayer.pause();
                break;
            case RIGHT:
            default:
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        recentMediaController.path=null;
        myMusicController.path=null;
        mediaPlayer.dispose();
        playlistController.path=null;
        Controller.array=null;
        favouriteTracksController.TID=null;
    }

    @FXML
    private void pauseVideo(ActionEvent event){
        mediaPlayer.pause();
    }

    @FXML
    private void playVideo(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    @FXML
    private void stopVideo(ActionEvent event){
        mediaPlayer.stop();
        mediaPlayer.play();
    }

    @FXML
    private void fastVideo(ActionEvent event){
        mediaPlayer.setRate(1.5);
        mediaPlayer.play();
    }

    @FXML
    private void fasterVideo(ActionEvent event){
        mediaPlayer.setRate(2);
        mediaPlayer.play();
    }

    @FXML
    private void slowVideo(ActionEvent event){
        mediaPlayer.setRate(.75);
        mediaPlayer.play();
    }

    @FXML
    private void slowerVideo(ActionEvent event){
        mediaPlayer.setRate(.5);
        mediaPlayer.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         i=0;
        if(playlistController.path!=null) {

            statement = "CREATE or REPLACE VIEW PTracks AS " +
                    " SELECT\n" +
                    " Unique " +
                    "Tracks_T.Track_ID ,Tracks_T.Track_Location\n" +
                    "  FROM Tracks_T,PTracks_T\n" +
                    "   WHERE  (tracks_t.track_id = ptracks_t.track_id AND pTracks_t.Playlist_ID =" + playlistController.path + ")";
            try {
                DBUtil.dbExecuteStatement(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                tracksData = TracksDAO.playTracks();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
                filepath=tracksData.get(i).toString();
                PlaylistPlay();
        }
        else if(Controller.array!=null){
            try {
                tracksData=TracksDAO.PATracks();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            filepath=tracksData.get(i).toString();
            PlaylistPlay();
        }
        else if (favouriteTracksController.TID!=null){
            filepath=favouriteTracksController.TID;
            playMedia();
        }
        else if(recentMediaController.path!=null) {
            filepath = recentMediaController.path;
            playMedia();
        }
        else if (myMusicController.path!=null) {
                filepath = myMusicController.path;
                playMedia();
        }
            System.out.println(filepath);
        }
    }
