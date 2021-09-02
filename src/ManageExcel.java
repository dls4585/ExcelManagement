import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ManageExcel {
    String fileName;
    // static 자료구조
    ManageExcel(String fileName, boolean isInitial) {
        this.fileName = fileName;
        if(isInitial) {
            initFile();
        }
        else {
        }
    }

    public void initFile() {
        try {
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
