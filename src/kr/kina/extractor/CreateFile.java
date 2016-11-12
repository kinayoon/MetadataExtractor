package kr.kina.extractor;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
/**
 * Created by Yoon on 2016-11-03.
 */
public class CreateFile implements Runnable{

    private String filePath;  //"C:\\Users\\Yoon\\IdeaProjects\\MetadataExtractor\\metaData.xlsx"

    CreateFile(String name){
        this.filePath = name;
    }

    @Override
    public void run() {
        try {
            init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 1. creating xlsx file
     *  2. writing created metadata by mp3MetaDataParser
     *  @param filePath
     */

    private void init(String filePath) throws Exception {
        File file = new File(filePath);

        if(file.isFile() && file.exists()){ // find file
            System.out.println("File Exist ..");

        }else if(!file.isFile() && !file.exists()) {  // find not file
            createFile(filePath); //run method
            System.out.println("File Created!");
        }
    }

    /**
     *  creating .xlsx file & header by document
     *  @param filePath
     * */
    private void createFile(String filePath) throws FileNotFoundException, IOException {

        String fileName = new File(filePath).getName();

        XSSFWorkbook newWorkbook = new XSSFWorkbook();
        FileOutputStream stream = new FileOutputStream(fileName);
        newWorkbook.write(stream);
        stream.close();
    }
}
