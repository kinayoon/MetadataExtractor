package kr.kina.extractor;

import java.io.IOException;
import java.util.Date;

/** 메인쓰레드
 *  역할 : ROOT PATH(모든 데이터가 들어있는 최종 파일경로)로부터 파일경로를 추출,
 *  추출한 mp3 파일의 메타데이터를 파싱 (Apache Tika Library),
 *  엑셀파일(.xlsx)에 데이터를 삽입시키는 것 (Apache Poi Library)
 *
 *  메인쓰레드 위에 총 4개의 쓰레드가 순차적으로 실행됨
 *  Created by Yoon on 2016-11-02.
 */
public class MainThread {

    public static void main(String[] args) throws IOException {

        Date date = new Date();
        String rootPath = "D:\\genie data";
        String excelfile = "C:\\Users\\Yoon\\IdeaProjects\\MetadataExtractor\\20161110.xlsx";

        FilePathExtractor extractedFileList = new FilePathExtractor(rootPath);
        MetaDataExtractor metaDataExtractor = new MetaDataExtractor(extractedFileList.getTotalFilePath());

        //Thread
        Thread t1 = new Thread(extractedFileList);
        Thread t2 = new Thread(metaDataExtractor);
        Thread t3 = new Thread(new CreateFile(excelfile));
        Thread t4 = new Thread(new WritingFile(excelfile, metaDataExtractor.getXmpList()));


        System.out.println("파일경로 추출시작 ------------- "+ date.toString());
        t1.start();
        try{ t1.join(); }catch (Exception e){e.printStackTrace();}

        System.out.println("메타데이터 추출시작 ------------- "+ date.toString());
        t2.start();

        try{ t2.join(); }catch (Exception e){e.printStackTrace();}
        System.out.println("엑셀 파일 생성 ------------- "+ date.toString());

        t3.start();

        try{ t3.join(); }catch (Exception e){e.printStackTrace();}
        System.out.println("엑셀에 입력 ------------- "+ date.toString());
        t4.start();

    }
}
