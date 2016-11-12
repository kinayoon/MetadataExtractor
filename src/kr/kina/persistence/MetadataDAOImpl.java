package kr.kina.persistence;

import kr.kina.vo.AlbumVO;
import kr.kina.vo.ArtistVO;
import kr.kina.vo.SongVO;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**  MdtadataDAO 구현클래스
 *   역할 : insert data to MySQL
 *   Created by Yoon on 2016-11-08.
 */
public class MetadataDAOImpl implements MetadataDAO{

    private String resource="kr/kina/persistence/mybatis-config.xml";
    private String namespace = "kr.kina.persistence.MetaDataMapper";
    private List<Map<String,String>> list;  //Reading file List

    public MetadataDAOImpl(List<Map<String,String>> list) throws Exception{
        this.list = list;
    }

    private SqlSession getSession() throws IOException {
       return new SqlSessionClass(resource).getSqlSessionFactory();
    }


    @Override
    public void insertArtist() throws Exception{

        Set<String> alignValue = new HashSet<>();  //중복값 제거를 위해 HashSet
        SqlSession session = getSession();
        ArtistVO vo;

        for(Map song : list){
            alignValue.add((String)song.get("creator"));
        }

        String[] getArtistName = alignValue.toString().split("[,]");  //중복값 제거 후, 배열에 저장

        try {
            for(String name : getArtistName){
                vo = new ArtistVO();
                vo.setArtist(name.trim().toLowerCase());
                session.insert(namespace + ".artist", vo);
            }
            session.commit();  //한번에 commit
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public void insertAlbum() throws Exception{

        SqlSession session = getSession();
        Set<String> albumValue = new HashSet<>();  //중복값 제거

        for (Map song : list){
            albumValue.add((String) song.get("xmpDM:album"));
        }

        String[] getAlbumName = albumValue.toString().split("[,]");  //중복값 제거 후, 배열에 저장
        int size = getAlbumName.length-1;

        //전곡(list)에서 중복값 배열(getAlbumName)의 값과 같은 값을 발견하면, insert 한 후, 배열의 값을 삭제
        for(Map song : list){
            String name = (String) song.get("xmpDM:album");
            if(song.get("xmpDM:album").equals("xmpDM:album")){continue;}

            for (int j = 0; j < getAlbumName.length; j++) {

                String hashsetName = getAlbumName[j].trim();

                if(hashsetName.equals(name)){  //중복값제거 배열에 있는 값을 전곡과 비교..
                    session.insert(namespace+".album",
                            setAlbumVO(hashsetName,((String)song.get("creator")),
                                    ((String)song.get("xmpDM:genre")), ((String)song.get("xmpDM:releaseDate")))
                    );
                    //배열의 값 삭제 (중복값 방지)
                    for(int i=j; i<size; i++) {
                        getAlbumName[i] = getAlbumName[i+1];
                    }
                    size--;
                }
            }
            session.commit();
        }//전체 for문 끝
        session.close();
    }

    @Override
    public void insertSong() throws Exception{

        SqlSession session = getSession();
        String album, artist ,filepath, date, tracknum, duration, genre;

        try {
        for(Map song : list) {
            if (song.get("xmpDM:duration").equals("xmpDM:duration")) { continue; }  //Except meta data's title

            album = ((String) song.get("xmpDM:album")).trim();
            artist = ((String) song.get("creator")).trim();
            date = ((String) song.get("xmpDM:releaseDate")).trim();
            genre = ((String) song.get("xmpDM:genre")).trim();
            tracknum = ((String) song.get("xmpDM:trackNumber")).trim();
            duration = ((String) song.get("xmpDM:duration")).trim();
            filepath = ((String) song.get("filename")).trim();

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("album", album);
            paramMap.put("artist", artist);
            paramMap.put("genre",genre);

            Integer id = session.selectOne(namespace + ".select", paramMap);
            if (id != null) {
                session.insert(namespace + ".song",
                        setSongVO(id, album, artist, duration, tracknum, filepath, date));
                session.commit();
//                System.out.println("성공 : " + id + " | " + album + " | " + artist);

            } else { //albumid값이 없다면 부여 (select * from album order by albumid desc limit 1; 나온 int값에 +1)
                int lastId = session.selectOne(namespace+".selectLastId");
                session.insert(namespace + ".song",
                        setSongVO(lastId+1, album, artist, duration, tracknum, filepath, date));
                session.insert(namespace + ".addAlbum",
                        setAlbumVO(album, artist, ((String)song.get("xmpDM:genre")),date));
            }

            }// Foreach END
        }finally {
            session.close();
        }
    }


    //AlbumVO객체 생성
    private AlbumVO setAlbumVO(String album, String artist, String genre, String date) throws Exception{
        AlbumVO vo = new AlbumVO();
        vo.setAlbum(album);
        vo.setArtist(artist);
        vo.setGenre(genre);
        vo.setReleasedate(date);
        return vo;
    }

    //SongVO객체 생성
    private SongVO setSongVO(int id, String album, String artist,
                             String duration, String tracknum, String filepath, String date) throws Exception {
        SongVO vo = new SongVO();
        vo.setAlbumid(id);
        vo.setAlbum(album);
        vo.setTitle(filepath);  //filepath에서 노래타이틀 추출해서 저장
        vo.setArtist(artist);
        vo.setDuration(duration);
        vo.setTracknum(tracknum);
        vo.setReleaseDate(date);
        vo.setFilepath(filepath);
        return vo;
    }

}
