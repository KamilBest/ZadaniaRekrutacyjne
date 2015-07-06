
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BarcodeAnalyzerTest {
    private BarcodeAnalyzer barcodeAnalyzer;
    private BarcodeChecker barcodeChecker;
    protected BarcodeScannerInput input;

    @Before
    public void setUp() {
        input =new BarcodeScannerInput() {
            public String getBarcode() {
                return null;
            }

            public int getBarcodeType() {
                return 0;
            }
        };
        barcodeAnalyzer = new BarcodeAnalyzer(input);
        barcodeChecker=new BarcodeChecker();
    }

    /**
     * Checking whether adding leading zero is working.
     */
    @Test
    public void checkAddingLeadingZero()
    {
        assertEquals("0123456",barcodeAnalyzer.addLeadingZero("123456"));
        assertEquals("0333",barcodeAnalyzer.addLeadingZero("333"));
        assertEquals("00000",barcodeAnalyzer.addLeadingZero("0000"));
        assertEquals("0142353", barcodeAnalyzer.addLeadingZero("142353"));

        assertNotEquals("0142353", barcodeAnalyzer.addLeadingZero("42353"));
        assertNotEquals("142353", barcodeAnalyzer.addLeadingZero("142353"));
    }

    /**
     * Checking whether method returning correct barcode - without addons and with adding leading zero if it had been cutt of.
     */
    @Test
    public void checkReturningCorrectBarcode() {
        assertEquals("12345678", barcodeAnalyzer.returnCorrectBarcode(barcodeChecker,"12345678", 1));
        assertEquals("12345678", barcodeAnalyzer.returnCorrectBarcode(barcodeChecker,"1234567893", 1));
        assertEquals("11111111", barcodeAnalyzer.returnCorrectBarcode(barcodeChecker,"1111111189345", 1));

        assertEquals("1234567890123", barcodeAnalyzer.returnCorrectBarcode(barcodeChecker,"1234567890123", 2));
        assertEquals("1234567890123", barcodeAnalyzer.returnCorrectBarcode(barcodeChecker,"123456789012399", 2));
        assertEquals("1234567890123", barcodeAnalyzer.returnCorrectBarcode(barcodeChecker,"123456789012399999", 2));

        assertEquals("0075678164125", barcodeAnalyzer.returnCorrectBarcode(barcodeChecker,"075678164125", 2));
        assertEquals("00123456", barcodeAnalyzer.returnCorrectBarcode(barcodeChecker,"0123456", 1));
    }
}