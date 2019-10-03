package calling.codes.callingcodes.model;

import calling.codes.callingcodes.util.PhoneNotValidException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static calling.codes.callingcodes.util.Constants.INVALID_COUNTRY_CODE_ERROR_MSG;

@Service
public class CallingCodeModelDB {

    private Map<String, String> phoneCodesMap = new HashMap<>();

    public String getCountryNameByCode(String code) {
        String country;

        if (code.substring(0, 1).equals("1")) {
            country = phoneCodesMap.get("1");
        } else {
            country = phoneCodesMap.get(code);

            if (country == null) {
                country = phoneCodesMap.get(code.substring(0, 2));
            }
        }

        if (country == null) {
            throw new PhoneNotValidException(INVALID_COUNTRY_CODE_ERROR_MSG);
        }

        return country;
    }

    public void addCodesToMap(String[] codes, String countryName) {
        Arrays.stream(codes).forEach(code -> {
            if (StringUtils.isNotEmpty(code)) {
                phoneCodesMap.put(code, countryName);
            }
        });
    }
}
