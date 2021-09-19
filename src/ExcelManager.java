import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelManager {
    String fileName;
    // static 자료구조
    static Map<String, Campaign> campaigns;
    ExcelManager(String fileName, boolean isInitial) {
        this.fileName = fileName;
        if(isInitial) {
            initFile();
        }
        else {

        }
    }

    void initFile() { // 처음 캠페인 로딩
        try {
            campaigns  = new HashMap<>();
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class Campaign {
    String name;
    String AFCampaignName, AFChannelName;
    List<String> groups;

    public Campaign(String name, String AFCampaignName, String AFChannelName) {
        this.name = name;
        this.AFCampaignName = AFCampaignName;
        this.AFChannelName = AFChannelName;
        this.groups = new ArrayList<>();
    }
}