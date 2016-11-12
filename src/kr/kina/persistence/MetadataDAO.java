package kr.kina.persistence;


/** DAO인터페이스
 *  Created by Yoon on 2016-11-09.
 */
public interface MetadataDAO {

    void insertArtist() throws Exception;
    void insertAlbum() throws Exception;
    void insertSong() throws Exception;
}
