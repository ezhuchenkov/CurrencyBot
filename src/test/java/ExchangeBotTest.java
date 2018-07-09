import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExchangeBotTest {

    @Test
    public void testExtractCurrency() {
        String currency = ExchangeBot.extractCurrency("ABcDEf");
        assertEquals("ABC", currency);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void testShorter() {
//        Exception exception = null;
//        try {
            ExchangeBot.extractCurrency("AB");
//        } catch (StringIndexOutOfBoundsException e) {
//            exception = e;
//        }
//        assertNotNull(exception);
    }

}
