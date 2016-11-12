package kr.kina.service;

import kr.kina.persistence.MetadataDAO;
import kr.kina.persistence.MetadataDAOImpl;

import java.util.List;
import java.util.Map;

/**  DAO호출
 *  Created by Yoon on 2016-11-06.
 */
public class MetadataService {

    private List<Map<String, String>> totalList;
    private MetadataDAO dao;

    MetadataService(List<Map<String, String>> totalList) throws Exception{
        this.totalList = totalList;
        dao = new MetadataDAOImpl(totalList);
    }

    public void insertArtistData() throws Exception {
        dao.insertArtist();
    }

    public void insertAlbumData() throws Exception {
        dao.insertAlbum();
    }

    public void insertSongData() throws Exception {
        dao.insertSong();
    }

}
