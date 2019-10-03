package calling.codes.callingcodes;

import calling.codes.callingcodes.model.CallingCodeModelDB;
import calling.codes.callingcodes.model.ResponseModel;
import calling.codes.callingcodes.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = CallingCodesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IntegrationTest {

    @Autowired
    CallingCodeModelDB db;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    void dataLoadedTest() {

        String country1 = db.getCountryNameByCode("371");
        String country2 = db.getCountryNameByCode("1");

        assertEquals(country1, "Latvia");
        assertEquals(country2, "United States");
    }

    @Test
    void requestTest1() {
        ResponseEntity<ResponseModel> response = restTemplate.getForEntity("http://localhost:8080/api/identifyCountry/+37112345678", ResponseModel.class);

        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody().getResponse(),"Latvia");
        assertEquals(response.getBody().getResponseCode(), 200);
    }

    @Test
    void requestTest2() {
        ResponseEntity<ResponseModel> response = restTemplate.getForEntity("http://localhost:8080/api/identifyCountry/+3711234567891234", ResponseModel.class);

        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody().getResponse(), Constants.NUMBER_TOO_LONG_ERROR_MSG);
        assertEquals(response.getBody().getResponseCode(), 400);
    }
}
