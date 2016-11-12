package kr.kina.extractor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.metadata.Metadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** 엑셀파일에 메타데이터 옮기기
 *  Created by Yoon on 2016-11-03.
 */
public class WritingFile implements Runnable{

    private String excelFilePath;
    private List<List<String>> xmpDMList;  //전곡

    WritingFile(String path, List<List<String>> xmp){
        this.excelFilePath = path;
        this.xmpDMList = xmp;
    }

    @Override
    public void run() {
        try {
            init(excelFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Check exist .xlsx file
     * */
    private void init(String path) throws Exception {

        File excelFile = new File(path);  //엑셀파일

        if(excelFile.isFile() && excelFile.exists()){  //파일이 있다면, open & insert
            System.out.println("엑셀 파일에 데이터를 삽입하고 있습니다.. ");
            insertData(excelFile, xmpDMList);

        }else if( !excelFile.isFile() && !excelFile.exists() ){ //파일이 없다면, 에러
            new Exception("파일이 없습니다.");
        }
    }


    /**
     *  Writing metaData on 'xlsx' file
     * */
    private void insertData(File excelFile, List<List<String>> totalSongList) throws IOException {

        String[] header = new String[]{"xmpDM:genre", "creator", "xmpDM:album", "xmpDM:trackNumber", "xmpDM:releaseDate",
                "meta:author", "xmpDM:artist", "dc:creator", "xmpDM:audioCompressor", "title",
                "xmpDM:audioChannelType", "version", "xmpDM:logComment", "xmpDM:audioSampleRate", "channels",
                "dc:title", "Author", "xmpDM:duration", "Content-Type", "samplerate", "filename"};

        XSSFWorkbook newWorkbook = new XSSFWorkbook();
        XSSFSheet sheet1 = newWorkbook.createSheet(excelFile.getName());

        int cellNum = 0;
        XSSFRow row1 = sheet1.createRow(0);

        //header 생성
        for(String title : header){
            Cell cell1 = row1.createCell(cellNum);
            cell1.setCellValue(title);
            cellNum++;
        }

        //루프 한 번당(row) 한 곡씩 Write, 루프가 끝나면 한 줄 생성(row++)
        int rowNum = 1;
        for (List<String> song : totalSongList){
            //System.out.println("---------------" + rowNum + " : " + song);
            cellNum = 0;
            XSSFRow row = sheet1.createRow(rowNum);
            rowNum++;

            for(int h=0; h<song.size(); h++){
                row.createCell(cellNum++).setCellValue(song.get(h).trim());
            }
        }
        FileOutputStream stream = new FileOutputStream(excelFile.getName());
        newWorkbook.write(stream);
        stream.close();
    }
}
