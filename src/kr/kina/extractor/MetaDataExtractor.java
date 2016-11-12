package kr.kina.extractor;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/** 메타데이터 추출하는 쓰레드
 *  Created by Yoon on 2016-11-03.
 */
public class MetaDataExtractor implements Runnable{

    private InputStream inputStream;
    private BodyContentHandler handler;
    private Metadata metadata;
    private ParseContext parseContext;
    private Mp3Parser mp3Parser;
    private String[] xmpDML;

    private List<List<String>> xmpList;  // 최종 모든 파일에 대한 메타데이터 리스트 (전곡)
    private List<File> filePathList;  //생성자를 통해 파일경로 리스트를 받음


    MetaDataExtractor(List<File> list) {
        this.filePathList = list;
        xmpList = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            fileMetaData(filePathList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 파일을 하나씩 꺼내는 메소드
     */
    private String[] fileMetaData(List<File> file) throws Exception {

        for (File song : file) {
            try {
                getMetaData(song);  //메타데이터 추출하는 메소드
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return xmpDML;
    }

    /**
     * 메타데이터 추출하는 메소드
     * 단위 : 1개의 파일
     */
    private void getMetaData(File song) throws Exception {

        inputStream = new FileInputStream(new File(song.toString()));
        handler = new BodyContentHandler();
        metadata = new Metadata();
        parseContext = new ParseContext();

        mp3Parser = new Mp3Parser();
        mp3Parser.parse(inputStream, handler, metadata, parseContext);
        inputStream.close();

        List<String> songMetadata = new ArrayList<String>();

        xmpDML = metadata.names();  //한 곡에 해당되는 메타데이터를 xmpDML에 담기

        for (String data : xmpDML) {  // 하나씩 꺼내는 것. 20개의 메타데이터를.
            songMetadata.add(metadata.get(data));
            System.out.println("["+data + "]  :  " + metadata.get(data));
        }
        songMetadata.add(song.toString());  //마지막 인덱스값으로 파일경로가 들어감
        xmpList.add(songMetadata);  //전곡데이터에 한곡씩 add시킴
    }

    public List<List<String>> getXmpList() {
        return this.xmpList;
    }


}