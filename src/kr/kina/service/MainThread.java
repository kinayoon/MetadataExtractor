package kr.kina.service;

import kr.kina.extractor.ReadingFile;

/** 메인쓰레드
 *  역할 : 엑셀파일(.xlsx)로부터 데이터 읽어들이고, MySQL에 데이터삽입시키는 것
 *  Created by Yoon on 2016-11-06.
 */
public class MainThread {
    
    public static void main(String[] args) throws Exception {

        String readfile = "C:\\Users\\Yoon\\IdeaProjects\\MetadataExtractor\\20161108.xlsx";

        // ReadingFile 객체에서 엑셀파일에 있는 메타데이터 추출..
        ReadingFile readingFile= new ReadingFile(readfile);
        Thread t1 = new Thread(readingFile);
        t1.start();

        try{ t1.join(); } catch(Exception e){ e.printStackTrace(); }

        // insert data -> MySQL
        MetadataService service = new MetadataService(readingFile.getTotalSongList());
        service.insertArtistData();
        service.insertAlbumData();
        service.insertSongData();
    }

}