import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelManager {
    static ExcelManager instance;
    String fileName;
    String path;
    // static 자료구조
    Map<String, Campaign> campaigns;
    static final String baseURL = "https://ably.onelink.me/vJEo";
    static final String pid = "kakao mobile banner";
    static final String lookBack = "1d";

    public static ExcelManager getInstance() {
        if(instance == null) {
            instance = new ExcelManager();
        }
        return instance;
    }

    public void init(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
        initFile();
    }
    public Set<String> getCampaigns() {
        return campaigns.keySet();
    }
    void initFile() { // 처음 캠페인 로딩
        try {
            campaigns  = new HashMap<>();
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if(row.getRowNum() < 6) continue;
                if(row.getCell(0) == null) break;

                String campaignName = row.getCell(3).getStringCellValue();
                String group = row.getCell(7).getStringCellValue();
                if(!campaigns.containsKey(campaignName)) {
                    String AFCampaignName = row.getCell(4).getStringCellValue();
                    String AFChannelName = row.getCell(5).getStringCellValue();
                    Campaign newCampaign = new Campaign(campaignName, AFCampaignName, AFChannelName);
                    newCampaign.groups.add(group);
                    campaigns.put(campaignName, newCampaign);

                } else {
                    if(campaigns.get(campaignName).groups.contains(group)) continue;
                    campaigns.get(campaignName).groups.add(group);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean makeLink(List<String> selected, String[] soJae) {
        // 링크 생성한 파일 만들기
        String newFile = path + "링크파일.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(); //sheet 생성
        XSSFRow row = sheet.createRow(1); //0행 생성
        indexInit(row);

        int i = 2;
        for (String line : soJae) {
            String[] array = line.split("\t");
            String SJName = array[0];
            String webDP = array[1];
            String shortDP = array[2];

            for (String targetString : selected) {
                Campaign target = campaigns.get(targetString);
                boolean isRetarget = targetString.contains("앱구매");

                for (String group : target.groups) {
                    XSSFRow curRow = sheet.createRow(i++);
                    curRow.createCell(1).setCellValue(target.name);
                    curRow.createCell(2).setCellValue(group);
                    curRow.createCell(3).setCellValue(SJName);
                    curRow.createCell(4).setCellValue(baseURL);
                    curRow.createCell(5).setCellValue(pid);
                    curRow.createCell(6).setCellValue(target.AFCampaignName);
                    curRow.createCell(7).setCellValue(target.AFChannelName);
                    curRow.createCell(8).setCellValue(lookBack);
                    curRow.createCell(9).setCellValue(webDP);
                    curRow.createCell(10).setCellValue(webDP);
                    curRow.createCell(11).setCellValue(webDP);
                    curRow.createCell(12).setCellValue(shortDP);
                    curRow.createCell(13).setCellValue("madit");
                    curRow.createCell(14).setCellValue(isRetarget ? "TRUE" : "");
                    curRow.createCell(15).setCellValue("");
                    curRow.createCell(16).setCellValue(isRetarget ? "&is_retargeting=true" : "");
                }
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(newFile);
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void indexInit(XSSFRow row) {
        row.createCell(1).setCellValue("캠페인");
        row.createCell(2).setCellValue("그룹");
        row.createCell(3).setCellValue("소재명(랜딩_문구_이미지)");
        row.createCell(4).setCellValue("기본");
        row.createCell(5).setCellValue("?pid=");
        row.createCell(6).setCellValue("&c=");
        row.createCell(7).setCellValue("&af_channel=");
        row.createCell(8).setCellValue("&af_click_lookback=");
        row.createCell(9).setCellValue("&af_web_dp=");
        row.createCell(10).setCellValue("&af_ios_url=");
        row.createCell(11).setCellValue("&af_android_url=");
        row.createCell(12).setCellValue("&af_dp=");
        row.createCell(13).setCellValue("&af_prt=");
        row.createCell(14).setCellValue("&is_retargeting=");
        row.createCell(15).setCellValue("af_reengagement_window=");
        row.createCell(16).setCellValue("retarg, reengage 수식");
        row.createCell(17).setCellValue("최종링크");
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