package kr.kina.vo;


/** table album에 insert시킬 값 객체 (VO)
 *  Created by Yoon on 2016-11-07.
 */
public class AlbumVO {

    String album;
    String artist;
    String genre;
    String releasedate;

    public AlbumVO(){}

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        if(album.equals(null) && album.equals(" ")){ this.album = "정보없음"; }
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {  //foreign key (by artist table)
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if(genre.equals(null) && genre.equals(" ")){ this.genre = "정보없음"; }
        this.genre = genre;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        if(releasedate.equals(null) && releasedate.equals(" ")){ this.releasedate = "정보없음"; }
        this.releasedate = releasedate;
    }
}