package calling.codes.callingcodes.controller;

import calling.codes.callingcodes.service.CallingCodesService;
import calling.codes.callingcodes.model.ResponseModel;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(method = RequestMethod.GET, path = "api/identifyCountry", produces = MediaType.APPLICATION_JSON_VALUE)
@Log
public class CallingCodesController {

    @Autowired
    private CallingCodesService callingCodesService;

    @RequestMapping(path = "{phoneNumber}")
    public ResponseEntity getCountryByPhoneCode(@PathVariable("phoneNumber") String phoneNumber,
                                                HttpServletRequest httpServletRequest) {
        log.info("Request from " + httpServletRequest.getRemoteAddr() + " phone number: " + phoneNumber);
        ResponseModel response = callingCodesService.getCountryByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(response);
    }
}
