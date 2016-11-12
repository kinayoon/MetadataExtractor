package kr.kina.test;

import kr.kina.persistence.MetadataDAO;
import kr.kina.persistence.SqlSessionClass;
import kr.kina.vo.AlbumVO;
import kr.kina.vo.ArtistVO;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/** Junit을 대신해서 Test쓰레드로 사용하였음
 *  Created by Yoon on 2016-11-09.
 */
public class MyBatisTest implements MetadataDAO {

    private String resource="kr/kina/persistence/mybatis-config.xml";
    private String namespace = "kr.kina.persistence.MetaDataMapper";

    public static void main(String[] args) throws Exception {
        MyBatisTest test = new MyBatisTest();
        //test.insertArtist();
        //test.insertAlbum();
        test.selectTest();
    }

    @Override
    public void insertArtist() throws Exception {
        SqlSession session = new SqlSessionClass(resource).getSqlSessionFactory();

        ArtistVO vo = new ArtistVO();
        vo.setArtist("muse4");

        try {
            session.insert(namespace + ".artist", vo);
            session.commit();
        }finally {
            if(session != null){
                session.close();
            }
        }

    }

    @Override
    public void insertAlbum() throws Exception {
        SqlSession session = new SqlSessionClass(resource).getSqlSessionFactory();

        AlbumVO vo = new AlbumVO();
        vo.setArtist("muse");
        vo.setAlbum("1집");
        vo.setGenre("Rock");
        vo.setReleasedate("2016-01-05");

        try{
           // session.insert(namespace+".album", vo);
            String album ="다시 시작해 OST Part.20";
           int a =  session.selectOne(namespace+".select", album);
            session.commit();
            System.out.println(a);
        }finally {
            session.close();
        }
    }

    @Override
    public void insertSong() throws Exception {}

    public void selectTest() throws Exception {
        SqlSession session = new SqlSessionClass(resource).getSqlSessionFactory();

        try{
            String album = "colors";
            String artist ="miss A";
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("album", album);
            paramMap.put("artist", artist);

            int id = session.selectOne(namespace + ".select", paramMap);
            System.out.println("찾은 값 : " + id);

            int lastId = session.selectOne(namespace+".selectLastId");
            System.out.println("마지막 앨범아이디 : "+ lastId);


        }finally {
            session.close();
        }
    }
}
