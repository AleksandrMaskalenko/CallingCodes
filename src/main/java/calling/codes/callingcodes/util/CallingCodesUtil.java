package calling.codes.callingcodes.util;

import calling.codes.callingcodes.model.CallingCodeModelDB;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;


import static calling.codes.callingcodes.util.Constants.WIKI_PHONE_CODES_URL;

@Service
@Log
public class CallingCodesUtil implements ApplicationRunner {

    @Autowired
    private CallingCodeModelDB callingCodeModelDB;

    @Override
    public void run(ApplicationArguments args) {
        Document document = download();
        if (document != null) {
            callingCodesParse(document);
            log.info("Calling codes receiving completed!");
        } else {
            log.warning("Can't be retrieve information from provided source." + Constants.WIKI_PHONE_CODES_URL);
            throw new RuntimeException("Can't be retrieve information from provided source.");
        }
    }


    private Document download() {
        try {
            log.info("Started retrieving information from " + Constants.WIKI_PHONE_CODES_URL);
            return Jsoup.connect(WIKI_PHONE_CODES_URL).get();
        } catch (Exception e) {
            log.warning("Can't be retrieve information from provided source." + e);
            return null;
        }
    }

    public void callingCodesParse(Document doc) {
        Elements allTableRows = doc.body().select(".wikitable.sortable").select("tbody").select("tr");
        allTableRows.forEach(row -> {
            Elements columns = row.select("td");
            if (columns.size() == 4) { //there is rows with less than 4 columns
                Elements codeColumns = columns.get(1).select("a[href]");//second column contains countryCode
                String phoneCodes = codeColumns.text();
                if (StringUtils.isNotEmpty(phoneCodes)) {
                    String[] codes = getSeparatedCodes(phoneCodes);
                    String countryName = columns.get(0).text();//country name stored in first column
                    callingCodeModelDB.addCodesToMap(codes, countryName);
                }
            }
        });
    }

    private String[] getSeparatedCodes(String phoneCodes) {
        String x = removeWikiSpecificCharacters(phoneCodes);
        return x.split("\\+");
    }

    public static String removeWikiSpecificCharacters(String str) {
        if (str != null) {
            String s = StringUtils.deleteWhitespace(str).replaceAll(",", "");
            if (s.contains("[")) {
                int i = s.indexOf("[");
                return s.substring(0, i);
            }
            return s;
        }
        return null;
    }


}
