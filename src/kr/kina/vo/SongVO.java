package kr.kina.vo;

/** table song에 insert시킬 값 객체 (VO)
 *  Created by Yoon on 2016-11-07.
 */
public class SongVO {

    int albumid;  //select albumid from album where artist=? and album=? and genre=?
    String album;
    String title;
    String artist;
    double duration;  //단위 :  밀리세컨즈
    String tracknum;
    String releaseDate;
    String filepath;

    public SongVO(){ }

    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String filepath) {
        this.title = createFilename(filepath);
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        if(artist==null){ this.artist = null; }
        this.artist = artist;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        if(duration == null){ this.duration = 0.0; }

        double num = Double.parseDouble(duration) + 0.0;
        this.duration = num;
    }

    public String getTracknum() {
        return tracknum;
    }

    public void setTracknum(String tracknum) {
        if(tracknum==null){this.tracknum="트랙정보없음";}
        this.tracknum = tracknum;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        if(filepath == null){ this.filepath = null;}
        this.filepath = filepath;
    }
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        if(releaseDate == null) { this.releaseDate ="정보없음"; }
        this.releaseDate = releaseDate;
    }

    //파일이름 (filename) : 파일경로로부터 추출하기
    private String createFilename(String filepath){
        String[] nameArr = filepath.split("\\\\");
        String name = nameArr[nameArr.length-1];

        if(name.endsWith(".mp3")){
            return name.replaceAll(".mp3","");
        }
        return name;   //파일이름
    }


}
