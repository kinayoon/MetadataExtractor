package kr.kina.extractor;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**  MySQL에 넣을 최종 메타데이터 필터링
 *   (20개의 메타데이터 중에서 7개만 사용할 것이므로)
 *   Created by Yoon on 2016-11-07.
 */
public class ReadingFile implements Runnable{

    private String path;
    private List<Map<String, String>> totalSongList;  //List : 전곡, Map : 한 곡
    private Map<Integer, String> headers;

    public ReadingFile(String path){
        this.path = path;
        totalSongList = new ArrayList<>();  //전곡 List
    }

    @Override
    public void run() {
        try{
            init(this.path);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void init(String filePath) throws IOException, InvalidFormatException {
        File file = new File(filePath);

        if(file.isFile() && file.exists()){
            readingData(file);
        }else{
            new Exception("읽어들일 데이터 파일이 존재하지 않습니다.");
        }
    }

    private void readingData(File path) throws IOException, InvalidFormatException {

        XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(OPCPackage.open(path.getName()));
        Sheet sheet = wb.getSheet(path.getName()); //sheet

        headers = new HashMap<>();  // index(추출해야 할 번호), String(key값으로 쓰일 컬럼명)

        Row header = sheet.getRow(0); // header
        String temp;  //header의 value값

        for(Cell cell : header){  // index, header's value
            temp = cell.getStringCellValue();
            if(temp.equals("xmpDM:genre"))  { headers.put(cell.getColumnIndex(), temp); }
            if(temp.equals("creator"))      { headers.put(cell.getColumnIndex(), temp); }
            if(temp.equals("xmpDM:album"))  { headers.put(cell.getColumnIndex(), temp); }
            if(temp.equals("xmpDM:trackNumber")) { headers.put(cell.getColumnIndex(), temp); }
            if(temp.equals("xmpDM:releaseDate")) { headers.put(cell.getColumnIndex(), temp); }
            if(temp.equals("title"))        { headers.put(cell.getColumnIndex(), temp); }
            if(temp.equals("xmpDM:duration"))    { headers.put(cell.getColumnIndex(), temp); }
            if(temp.equals("filename"))    { headers.put(cell.getColumnIndex(), temp); }
        }

        int colnum; // 현재 컬럼 index값
        String dataValue;  //현재 컬럼 index값에 담긴 value

        for(Row row : sheet){       //전체 데이터
            Map<String, String> song = new HashMap<>();  //한 곡만 담는 HashMap

            for(Cell cell : row){   //한 곡씩 추출
                colnum = cell.getColumnIndex();

                // 메타데이터 중에서 정해놓은 메타데이터(headers)의 value만 추출한다.
                if(headers.get(colnum) != null){
                    dataValue = row.getCell(colnum).toString();  //현재 컬럼에 해당하는 value
                   // System.out.println("dataValue TEST :  " + dataValue);

                    //value값이 제대로 null이 아니고, "null"이 아니고, 공백이 아닌 "제대로 된 값"일 경우에만 데이터를 사용할 것임.
                    if((!dataValue.equals(null)) && (!dataValue.equals("null")) && (!dataValue.equals(" "))) {
                        song.put(headers.get(colnum), row.getCell(colnum).getStringCellValue().trim());  //trim() 앞뒤공백제거
                      //  System.out.println("[song " + row.getRowNum() + "]  :  " + headers.get(colnum) + " | " + row.getCell(colnum).getStringCellValue());
                    }
                }
            }
            totalSongList.add(song); //전곡담기
           }
    }

    public List<Map<String, String>> getTotalSongList(){
        return this.totalSongList;
    }

    public Map<Integer, String> getHeaders(){  //추출키워드 for metadata
        return this.headers;
    }
}
