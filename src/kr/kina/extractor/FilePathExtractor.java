package kr.kina.extractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** 파일경로 추출하기
 *  Created by Yoon on 2016-11-06.
 */
public class FilePathExtractor implements Runnable{

    private List<File> totalFilePath;
    private String filePath;

    FilePathExtractor(String filepath) {  //Root Path는 메인쓰레드에서 생성자를 통해 받는다.
        this.filePath = filepath;
        totalFilePath = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            extract(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*  <Root Path에서 파일경로 추출하기>
        1. 폴더와 파일로 구분
        2. 만약 폴더라면, 폴더 안에 폴더가 있는 체크. n개의 폴더 depth는 재귀호출로 처리
           (폴더 안에 폴더, 그 안에 폴더 등을 대비 -> 재귀호출로 반복함)
        3. 만약 파일이라면, List에 파일명 추가
    */
    private void extract(String rootPath) throws IOException {

        File[] file = new File(rootPath).listFiles();  // 1depth 폴더 추출

        for(File name : file){
            if(name.isDirectory()) {
                extract(name.getCanonicalPath());  // N depth 폴더 안에서 재귀 호출

            }else if(name.isFile()) { // 1depth 안에서 파일만 추출
                if( name.getName().endsWith("mp3") || name.getName().endsWith(".mp4")) {
                    totalFilePath.add(name.getCanonicalFile());
                }
            }
        }
    }

    public List<File> getTotalFilePath(){
        return this.totalFilePath;
    }



}
