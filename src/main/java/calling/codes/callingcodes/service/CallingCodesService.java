package calling.codes.callingcodes.service;

import calling.codes.callingcodes.model.CallingCodeModelDB;
import calling.codes.callingcodes.model.ResponseModel;
import calling.codes.callingcodes.util.PhoneNotValidException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static calling.codes.callingcodes.util.Constants.*;

@Service
@Log
public class CallingCodesService {

    private CallingCodeModelDB codeModelList;

    @Autowired
    public CallingCodesService(CallingCodeModelDB codeModelList) {
        this.codeModelList = codeModelList;
    }

    public ResponseModel getCountryByPhoneNumber(String phoneNumber) {
        try {
            validatePhoneNumber(phoneNumber);
            String callingCode = getCallingCode(phoneNumber);
            String countryName = codeModelList.getCountryNameByCode(callingCode);
            return new ResponseModel(countryName, 200);
        } catch (RuntimeException e) {
            return new ResponseModel(e.getMessage(), 400);
        } catch (Exception e) {
            return new ResponseModel(INVALID_COUNTRY_CODE_ERROR_MSG, 400);
        }
    }


    public void validatePhoneNumber(String phoneNumber) {

        String phone;

        if (phoneNumber != null && phoneNumber.substring(0, 1).equals("+")) {
            phone = phoneNumber.replaceAll("[\\D]", "");
        } else {
            throw new PhoneNotValidException(INVALID_COUNTRY_CODE_ERROR_MSG);
        }

        if (phone.length() < 8) {
            throw new PhoneNotValidException(NUMBER_TOO_SHORT_ERROR_MSG);
        } else if (!phone.matches("^[0-9]*$")) {
            throw new PhoneNotValidException(NOT_A_NUMBER_ERROR_MSG);
        } else if (phone.length() > 15) {
            throw new PhoneNotValidException(NUMBER_TOO_LONG_ERROR_MSG);
        } else if (phone.indexOf('0') == 0) {
            throw new PhoneNotValidException(INVALID_COUNTRY_CODE_ERROR_MSG);
        }
    }

    public String getCallingCode(String phoneNumber) {
        String phone = phoneNumber.replaceAll("[\\D]", "");
        return phone.substring(0, 3);
    }
}
