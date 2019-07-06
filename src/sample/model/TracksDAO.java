package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TracksDAO {

    public  static  String adrress;

    // file:/C:/%5BFreeCourseSite.com%5D%20Udemy%20-%20The%20Complete%20Android%20Oreo%20
    // Developer%20Course%20-%20Build%2023%20Apps!/Lesson%2048.%20Introduction.mp4
    //
    public static Tracks compareTrack (String  trackLocation) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT  Track_Location FROM tracks_t WHERE Track_Location='"+trackLocation+"'";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);
            Tracks track= null;
            //Send ResultSet to the getTrackFromResultSet method and get Tracks object
            if(rsEmp!=null) {
                track = getTrackFromResultSet(rsEmp);
            }
            else{
                System.out.println("While searching a track with " + trackLocation + " No Address Found Available" );
                return null;
            }
            //Return Track object
            return track;
        } catch (SQLException e) {
            System.out.println("While searching a track with " + trackLocation + " Address, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }
    //*******************************
    //SELECT a Track
    //*******************************
    public static Tracks searchTrack (String  trackId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT  * FROM Atracks WHERE track_id="+trackId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rstrack = DBUtil.dbExecuteQuery(selectStmt);
            Tracks track= null;
            //Send ResultSet to the getTrackFromResultSet method and get Tracks object
            if(rstrack!=null) {
                track = AddressWithId(rstrack);
            }
            else{
                System.out.println("While searching a track with " + trackId + " No Track Available" );
                return null;
            }
            //Return Track object
            return track;
        } catch (SQLException e) {
            System.out.println("While searching a track with " + trackId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    private static Tracks AddressWithId(ResultSet rs2) throws SQLException
    {
        Tracks track = null;
        if (rs2.next()) {
            track = new Tracks();
            track.setTrack_ID(rs2.getInt("TRACK_ID"));
            track.setMedia_Address(rs2.getString("TRACK_LOCATION"));
            track.setTrack_Name(rs2.getString("Track_Name"));
            track.setArtist_Name(rs2.getString("Artist_Name"));
            track.setAlbum_Name(rs2.getString("Album_Name"));
        }
        else {
            System.out.println("No Track_Found Sorry!");
        }
        return track;
    }

    public static Tracks searchAddress (String  trackId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT  Track_Location FROM tracks_t WHERE track_id="+trackId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);
            Tracks track= null;
            //Send ResultSet to the getTrackFromResultSet method and get Tracks object
            if(rsEmp!=null) {
                track = getTrackFromResultSet(rsEmp);
            }
            else{
                System.out.println("While searching a track with " + trackId + " No Track Available" );
            }
            //Return Track object
            return track;
        } catch (SQLException e) {
            System.out.println("While searching a track with " + trackId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Track Object's attributes and return Track object.
    private static Tracks getTrackFromResultSet(ResultSet rs) throws SQLException
    {
        Tracks track = null;
        if (rs.next()) {
            track = new Tracks();
            track.setAddress(rs.getString("TRACK_LOCATION"));
        }
        else {
            System.out.println("No Track Found");
        }
        return track;
    }

    //Searching PTracksID if Track Already exists in the playlist?
    public static Tracks searchTracks(String  trackId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT  Track_ID FROM Ptracks_t WHERE track_id="+trackId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);
            Tracks track= null;
            //Send ResultSet to the getTrackFromResultSet method and get Tracks object
            if(rsEmp!=null) {
                track = getTrackId(rsEmp);
            }
            else{
                System.out.println("While searching a track with " + trackId + " No Track Available" );
            }
            //Return Track object
            return track;
        } catch (SQLException e) {
            System.out.println("While searching a track with " + trackId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    private static Tracks getTrackId(ResultSet rs) throws SQLException
    {
        Tracks track = null;
        if (rs.next()) {
            track = new Tracks();
            track.setAddress(String.valueOf(rs.getInt("TRACK_ID")));
        }
        else {
            System.out.println("No Track Found");
        }
        return track;
    }

    public static ObservableList<Tracks> searchArtists () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt = "Select * from Artist_T";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getTrackList method and get employee object
            ObservableList<Tracks> trackList = getArtistlist(rsEmp);

            //Return Track object
            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from Tracks operation
    private static ObservableList<Tracks> getArtistlist(ResultSet rs) throws SQLException {
        //Declare a observable List which comprises of Track objects
        ObservableList<Tracks> TrackList = FXCollections.observableArrayList();

        while (rs.next()) {
            Tracks tracks = new Tracks();
            tracks.setArtist_ID(rs.getInt("ARTIST_ID"));
            tracks.setArtist_Name(rs.getString("ARTIST_NAME"));

            //Add tracks to the ObservableList
            TrackList.add(tracks);
        }
        //return trackList (ObservableList of Tracks)
        return TrackList;
    }

    //*******************************
    //SELECT Tracks
    //*******************************
    public static ObservableList<Tracks> fullData() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt = "Select * from ATRACKS";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getTrackList method and get employee object
            ObservableList<Tracks> trackList = getFullList(rsEmps);

            //Return Track object
            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from Tracks operation
    private static ObservableList<Tracks> getFullList(ResultSet rs) throws SQLException {
        //Declare a observable List which comprises of Track objects
        ObservableList<Tracks> TrackList = FXCollections.observableArrayList();

        while (rs.next()) {
            Tracks tracks = new Tracks();
            tracks.setTrack_ID(rs.getInt("TRACK_ID"));
            tracks.setMedia_Address(rs.getString("TRACK_LOCATION"));
            tracks.setTrack_Name(rs.getString("TRACK_NAME"));
            tracks.setAlbum_Name(rs.getString("ALBUM_NAME"));
            tracks.setArtist_Name(rs.getString("ARTIST_NAME"));

            //Add tracks to the ObservableList
            TrackList.add(tracks);
        }
        //return trackList (ObservableList of Tracks)
        return TrackList;
    }

    public static ObservableList<Tracks> searchTracks () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt = "Select Unique Track_id,Track_Location from tracks_t Order by Track_id";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getTrackList method and get employee object
            ObservableList<Tracks> trackList = getTrackList(rsEmps);

            //Return Track object
            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from Tracks operation
    private static ObservableList<Tracks> getTrackList(ResultSet rs) throws SQLException {
        //Declare a observable List which comprises of Track objects
        ObservableList<Tracks> TrackList = FXCollections.observableArrayList();

        while (rs.next()) {
            Tracks tracks = new Tracks();
            tracks.setTrack_ID(rs.getInt("TRACK_ID"));
            tracks.setMedia_Address(rs.getString("TRACK_LOCATION"));

            //Add tracks to the ObservableList
            TrackList.add(tracks);
        }
        //return trackList (ObservableList of Tracks)
        return TrackList;
    }

    //*******************************
    //SELECT Tracks
    //*******************************
    public static ObservableList<Tracks> searchPlaylists () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt =
                "Select * from PLAYLIST_T";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getTrackList method and get employee object
            ObservableList<Tracks> trackList = getPlaylist(rsEmps);

            //Return Track object
            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from Tracks operation
    private static ObservableList<Tracks> getPlaylist(ResultSet rs) throws SQLException {
        //Declare a observable List which comprises of Track objects
        ObservableList<Tracks> TrackList = FXCollections.observableArrayList();

        while (rs.next()) {
            Tracks tracks = new Tracks();
            tracks.setPlaylist_ID(rs.getInt("PLAYLIST_ID"));
            tracks.setPlaylist_Name(rs.getString("PLAYLIST_NAME"));

            //Add tracks to the ObservableList
            TrackList.add(tracks);
        }
        //return trackList (ObservableList of Tracks)
        return TrackList;
    }

    public static ObservableList<Tracks> searchAlbums() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt = "Select * from Album_t ";
        //Album_id,Album_Name,Artist_ID
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getTrackList method and get employee object
            ObservableList<Tracks> AlbumList = getAlbumList(rsEmps);

            //Return Track object
            return AlbumList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from Tracks operation
    private static ObservableList<Tracks> getAlbumList(ResultSet rs) throws SQLException {
        //Declare a observable List which comprises of Track objects
        ObservableList<Tracks> AlbumList = FXCollections.observableArrayList();

        while (rs.next()) {
            Tracks tracks = new Tracks();
            tracks.setAlbum_ID(rs.getInt("Album_ID"));
            tracks.setAlbumArtist_ID(rs.getInt("Artist_ID"));
            tracks.setAlbum_Name(rs.getString("Album_Name"));

            //Add tracks to the ObservableList
            AlbumList.add(tracks);
        }
        //return trackList (ObservableList of Tracks)
        return AlbumList;
    }

    //*************************************
    //DELETE a Track
    //*************************************
    public static void deleteTrackWithId (String trackId) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =
                "BEGIN\n" +
                        "   DELETE FROM Tracks_T\n" +
                        "         WHERE Track_id ="+ trackId +";\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
    public static void deletePlaylistTrackWithId (String trackid) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =
                "BEGIN\n" +
                        "   DELETE FROM PTracks_T\n" +
                        "         WHERE Track_id ="+ trackid +";\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //INSERT a Track
    //*************************************
    public static void insertTrack (String track_location) throws SQLException, ClassNotFoundException {
        //Declare a Insert statement
        String updateStmt =
                "BEGIN\n" +
                        "INSERT_track(sequence1.nextval, '"+track_location+"', SYSDATE);\n" +
                        "END;";

        //Execute Insert operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
            System.out.println("Track Inserted!");
        } catch (SQLException e) {
            System.out.print("Error occurred while Insert Operation: " + e);
            throw e;
        }
    }

    //Insert Playlist into Playlist Table :
    public static void insertPlaylist (String PlaylistName) throws SQLException, ClassNotFoundException {
        //Declare a Insert statement
        String updateStmt =
                "BEGIN\n" +
                        "INSERT INTO PLAYLIST_T VALUES(PLAYLIST_SEQUENCE.nextval, '"+PlaylistName+"');\n" +
                        "END;";
        //Execute Insert operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
            System.out.println("Playlist Inserted to Table!");
        } catch (SQLException e) {
            System.out.print("Error occurred while Insert Operation: " + e);
            throw e;
        }
    }

    //Insert Tracks to Playlist:
    public static void insertTrackToPlaylist (String PlaylistID, String PlaylistName) throws SQLException, ClassNotFoundException {
        //Declare a Insert statement
        String updateStmt =
                "BEGIN\n" +
                        "INSERT INTO PTracks_T VALUES("+ PlaylistID+ ","+PlaylistName+");\n" +
                        "END;";
        //Execute Insert operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
            System.out.println("Track Inserted to Playlist!");
        } catch (SQLException e) {
            System.out.print("Error occurred while Insert Operation: " + e);
            throw e;
        }
    }

    //Get Playlist ID
    public static Tracks PlaylistID (String  PlaylistName) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT  Playlist_ID FROM Playlist_t WHERE Playlist_Name='"+PlaylistName+"'";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);
            Tracks track= null;
            //Send ResultSet to the getTrackFromResultSet method and get Tracks object
            if(rsEmp!=null) {
                track = getPlaylistID(rsEmp);
            }
            else{
                System.out.println("While searching a track with " + PlaylistName + " No Track Available" );
            }
            //Return Track object
            return track;
        } catch (SQLException e) {
            System.out.println("While searching a track with " +PlaylistName + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Track Object's attributes and return Track object.
    private static Tracks getPlaylistID(ResultSet rs) throws SQLException
    {
        Tracks track = null;
        if (rs.next()) {
            track = new Tracks();
            track.setIDP(rs.getInt("Playlist_ID"));
        }
        else {
            System.out.println("No Track Found");
        }
        return track;
    }

    //Sorting the Tracks by TrackID:
    public static ObservableList<Tracks> sortby_ID() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt = "Select Track_id,Track_Location from tracks_t Order by track_id DESC";

        //Execute SELECT statement
        try {
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);

            ObservableList<Tracks> trackList = getTrackList(rsEmp);
            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select opt failed : " + e);
            //Return exception
            throw e;
        }
    }

    //Sorting The Tracks By Date ADDED:
    public static ObservableList<Tracks> sortby_date () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt = "Select * from Atracks Order by date_added";

        //Execute SELECT statement
        try {
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);

            ObservableList<Tracks> trackList = getFullList(rsEmp);

            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select opt failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Sorting The Tracks By Artist Name:
    public static ObservableList<Tracks> sortby_Artist() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt = "Select * from Atracks Order by Artist_Name";

        //Execute SELECT statement
        try {
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);

            ObservableList<Tracks> trackList = getFullList(rsEmp);

            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select opt failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Sorting The Tracks By Date ADDED:
    public static ObservableList<Tracks> sortby_Album() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt = "Select * from Atracks Order by Album_Name";

        //Execute SELECT statement
        try {
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);

            ObservableList<Tracks> trackList = getFullList(rsEmp);

            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select opt failed: " + e);
            //Return exception
            throw e;
        }
    }

    //*************************************
    //UPDATE a Track`s Track ID :
    //*************************************
    public static void updateTrackID (String trackAddress) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt =
                "BEGIN\n" +
                        "   UPDATE Tracks_T\n" +
                        "      SET Track_ID = sequence1.nextval\n" +
                        "    WHERE Track_Location = '" + trackAddress + "';\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    public static void insertFavouriteID(int trackID) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt =
                "BEGIN\n" +
                        "   UPDATE Tracks_T\n" +
                        "      SET favourite_ID = 1\n" +
                        "    WHERE Track_ID = '" + trackID + "';\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    public static void delFavouriteID(int trackID) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt =
                "BEGIN\n" +
                        "   UPDATE Tracks_T\n" +
                        "      SET favourite_ID = 0\n" +
                        "    WHERE Track_ID = '" + trackID + "';\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    //Updating Playlist Track ID :
    public static void updatePlaylistTrackID (String TrackID) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt =
                "BEGIN\n" +
                        "   UPDATE PTracks_T\n" +
                        "      SET Track_ID = sequence1.nextval -1\n" +
                        "    WHERE Track_ID = " + TrackID + ";\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    //Select Columns from view PTracks :
    public static ObservableList<Tracks> createView() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt =
                "Select * from PTracks";

                /*        "CREATE or REPLACE VIEW PTracks AS " +
                        " SELECT\n" +
                        " PTracks_T.Track_ID As Ptrack_ID,PTracks_T.Playlist_ID , " +
                        "Tracks_T.Track_ID ,Tracks_T.Track_Location\n" +
                        "  FROM Tracks_T,PTracks_T\n" +
                        "   WHERE  tracks_t.track_id = ptracks_t.track_id";
                */
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getTrackList method and get employee object
            ObservableList<Tracks> trackList = getPlaylistTracks(rsEmps);

            //Return Track object
            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from Tracks operation
    private static ObservableList<Tracks> getPlaylistTracks(ResultSet rst) throws SQLException {
        ObservableList<Tracks> TkList = FXCollections.observableArrayList();

        while (rst.next()) {
            Tracks tracks = new Tracks();
            tracks.setTrack_ID(rst.getInt("TRACK_ID"));
            tracks.setMedia_Address(rst.getString("TRACK_LOCATION"));

            TkList.add(tracks);
        }
        return TkList;
    }

    public static ObservableList<Tracks> Ftracks() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        String selectStmt =
                "Select * from Tracks_T where favourite_ID=1";
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getTrackList method and get employee object
            ObservableList<Tracks> trackList = getFavouriteTracks(rsEmps);

            //Return Track object
            return trackList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from Tracks operation
    private static ObservableList<Tracks> getFavouriteTracks(ResultSet rst) throws SQLException {
        ObservableList<Tracks> TkList = FXCollections.observableArrayList();

        while (rst.next()) {
            Tracks tracks = new Tracks();
            tracks.setTrack_ID(rst.getInt("TRACK_ID"));
            tracks.setMedia_Address(rst.getString("TRACK_LOCATION"));

            TkList.add(tracks);
        }
        return TkList;
    }

    //Selecting Tracks to be played from the whole asked Playlist id!
    public static ArrayList playTracks() throws SQLException, ClassNotFoundException {
            //Declare a SELECT statement,
            ArrayList arrayList= new ArrayList();
            String selectStmt =
                    "Select Track_Location from PTracks";

                /*        "CREATE or REPLACE VIEW PTracks AS " +
                        " SELECT\n" +
                        " PTracks_T.Track_ID As Ptrack_ID,PTracks_T.Playlist_ID , " +
                        "Tracks_T.Track_ID ,Tracks_T.Track_Location\n" +
                        "  FROM Tracks_T,PTracks_T\n" +
                        "   WHERE  tracks_t.track_id = ptracks_t.track_id";
                */

            //Execute SELECT statement
            try {
                //Get ResultSet from dbExecuteQuery method
                ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

                //Send ResultSet to the getTrackList method and get employee object

                while (rsEmps.next()) {
                    Tracks tracks = new Tracks();
                    adrress=rsEmps.getString("TRACK_LOCATION");
                    //Add tracks to the ObservableList
                    arrayList.add(adrress);
                }
                //Return Track object
                return arrayList;
            } catch (SQLException e) {
                System.out.println("SQL select operation has been failed: " + e);
                //Return exception
                throw e;
            }
    }

    public static ArrayList PATracks() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement,
        ArrayList arrayList= new ArrayList();
        String selectStmt =
                "Select Track_Location from Tracks_t";

                /*        "CREATE or REPLACE VIEW PTracks AS " +
                        " SELECT\n" +
                        " PTracks_T.Track_ID As Ptrack_ID,PTracks_T.Playlist_ID , " +
                        "Tracks_T.Track_ID ,Tracks_T.Track_Location\n" +
                        "  FROM Tracks_T,PTracks_T\n" +
                        "   WHERE  tracks_t.track_id = ptracks_t.track_id";
                */
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getTrackList method and get employee object

            while (rsEmps.next()) {
                Tracks tracks = new Tracks();
                adrress=rsEmps.getString("TRACK_LOCATION");
                //Add tracks to the ObservableList
                arrayList.add(adrress);
            }
            //Return Track object
            return arrayList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }
}