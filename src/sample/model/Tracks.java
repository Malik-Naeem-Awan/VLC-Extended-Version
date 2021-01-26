package sample.model;

import javafx.beans.property.*;


public class Tracks {

    //Declare Track Table Column
    private IntegerProperty Track_ID;
    private StringProperty Media_Address;
    private StringProperty Track_Name;
    private String address;
    private static int IDP;
    private IntegerProperty Playlist_ID;
    private StringProperty Playlist_Name;
    private IntegerProperty Artist_ID;
    private StringProperty Artist_Name;
    private IntegerProperty Album_ID;
    private StringProperty Album_Name;
    private IntegerProperty AlbumArtist_ID;

    public Tracks(){
        this.Track_ID= new SimpleIntegerProperty();
        this.Media_Address= new SimpleStringProperty();
        this.Playlist_ID= new SimpleIntegerProperty();
        this.Playlist_Name= new SimpleStringProperty();
        this.Artist_ID= new SimpleIntegerProperty();
        this.Artist_Name= new SimpleStringProperty();
        this.Album_ID= new SimpleIntegerProperty();
        this.AlbumArtist_ID = new SimpleIntegerProperty();
        this.Album_Name= new SimpleStringProperty();
        this.Track_Name=new SimpleStringProperty();
    }

    public String getTrack_Name() {
        return Track_Name.get();
    }

    public StringProperty track_NameProperty() {
        return Track_Name;
    }

    public void setTrack_Name(String track_Name) {
        this.Track_Name.set(track_Name);
    }

    public int getPlaylist_ID() {
        return Playlist_ID.get();
    }

    public IntegerProperty playlist_IDProperty() {
        return Playlist_ID;
    }

    public void setPlaylist_ID(int playlist_ID) {
        this.Playlist_ID.set(playlist_ID);
    }

    public String getPlaylist_Name() {
        return Playlist_Name.get();
    }

    public StringProperty playlist_NameProperty() {
        return Playlist_Name;
    }

    public void setPlaylist_Name(String playlist_Name) {
        this.Playlist_Name.set(playlist_Name);
    }

    public IntegerProperty track_IDProperty() {
        return Track_ID;
    }

    public int getArtist_ID() {
        return Artist_ID.get();
    }

    public IntegerProperty artist_IDProperty() {
        return Artist_ID;
    }

    public void setArtist_ID(int artist_ID) {
        this.Artist_ID.set(artist_ID);
    }

    public String getArtist_Name() {
        return Artist_Name.get();
    }

    public StringProperty artist_NameProperty() {
        return Artist_Name;
    }

    public void setArtist_Name(String artist_Name) {
        this.Artist_Name.set(artist_Name);
    }

    public int getTrack_ID() {
        return Track_ID.get();
    }

    public void setTrack_ID(int track_ID) {
        this.Track_ID.set(track_ID);
    }

    public String getMedia_Address() {
        return Media_Address.get();
    }

    public void setMedia_Address(String media_Address) {
        this.Media_Address.set(media_Address);
    }

    public IntegerProperty Track_IDProperty() {
        return Track_ID;
    }

    public StringProperty media_AddressProperty() {
        return Media_Address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        //  System.out.println(this.address);
    }

    public static int getIDP() {
        return IDP;
    }

    public void setIDP(int IDP) {
        this.IDP=IDP;
    }

    public int getAlbum_ID() {
        return Album_ID.get();
    }

    public IntegerProperty album_IDProperty() {
        return Album_ID;
    }

    public void setAlbum_ID(int album_ID) {
        this.Album_ID.set(album_ID);
    }

    public String getAlbum_Name() {
        return Album_Name.get();
    }

    public StringProperty album_NameProperty() {
        return Album_Name;
    }

    public void setAlbum_Name(String album_Name) {
        this.Album_Name.set(album_Name);
    }

    public int getAlbumArtist_ID() {
        return AlbumArtist_ID.get();
    }

    public IntegerProperty albumArtist_IDProperty() {
        return AlbumArtist_ID;
    }

    public void setAlbumArtist_ID(int albumArtist_ID) {
        this.AlbumArtist_ID.set(albumArtist_ID);
    }
}
