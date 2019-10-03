package calling.codes.callingcodes;

import calling.codes.callingcodes.util.CallingCodesUtil;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class CallingCodesUtilTest {

    @Test
    void callingCodesParse() {

        String clearStr1 = CallingCodesUtil.removeWikiSpecificCharacters("+123,+4412,+554");
        String clearStr2 = CallingCodesUtil.removeWikiSpecificCharacters("+1234");
        String clearStr3 = CallingCodesUtil.removeWikiSpecificCharacters("+12345[67]");
        String clearStr4 = CallingCodesUtil.removeWikiSpecificCharacters("+12 3, 456, +7[89]");
        String clearStr5 = CallingCodesUtil.removeWikiSpecificCharacters(null);

        assertEquals(clearStr1, "+123+4412+554");
        assertEquals(clearStr2, "+1234");
        assertEquals(clearStr3, "+12345");
        assertEquals(clearStr4, "+123456+7");
        assertSame(clearStr5, null);
    }
}