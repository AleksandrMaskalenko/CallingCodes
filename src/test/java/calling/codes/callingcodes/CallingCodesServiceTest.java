package calling.codes.callingcodes;

import calling.codes.callingcodes.model.CallingCodeModelDB;
import calling.codes.callingcodes.service.CallingCodesService;
import calling.codes.callingcodes.util.Constants;
import calling.codes.callingcodes.util.PhoneNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

class CallingCodesServiceTest {

    @Test
    void validatePhoneNumberTest() {
        CallingCodeModelDB db = new CallingCodeModelDB();
        CallingCodesService callingCodesService = new CallingCodesService(db);

        try{
            callingCodesService.validatePhoneNumber("123456789");
        } catch (PhoneNotValidException e) {
            assertEquals(e.getMessage(), Constants.INVALID_COUNTRY_CODE_ERROR_MSG);
        }

        try{
            callingCodesService.validatePhoneNumber("+1234567891234567");
        } catch (PhoneNotValidException e) {
            assertEquals(e.getMessage(), Constants.NUMBER_TOO_LONG_ERROR_MSG);
        }

        try{
            callingCodesService.validatePhoneNumber("+1234a567891");
        } catch (PhoneNotValidException e) {
            assertEquals(e.getMessage(), Constants.NOT_A_NUMBER_ERROR_MSG);
        }

        try{
            callingCodesService.validatePhoneNumber("+1234567");
        } catch (PhoneNotValidException e) {
            assertEquals(e.getMessage(), Constants.NUMBER_TOO_SHORT_ERROR_MSG);
        }

        try{
            callingCodesService.validatePhoneNumber("+0123456789");
        } catch (PhoneNotValidException e) {
            assertEquals(e.getMessage(), Constants.INVALID_COUNTRY_CODE_ERROR_MSG);
        }
    }

    @Test
    void getCallingCodeTest() {
        CallingCodeModelDB db = new CallingCodeModelDB();
        CallingCodesService callingCodesService = new CallingCodesService(db);

        String code = callingCodesService.getCallingCode("+37129997827");
        String code1 = callingCodesService.getCallingCode("+372-29-997-827");
        assertEquals(code, "371");
        assertEquals(code1, "372");
    }
}