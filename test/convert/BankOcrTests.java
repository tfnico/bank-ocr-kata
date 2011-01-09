package convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import convert.AccountNumber;
import convert.StickDigitToNumeralConverter;

public class BankOcrTests {

	Map<String, String> useCase = new HashMap<String, String>();

	@Before
	public void assignDataForTesting() {
		
		useCase.put("111111111", 
				"                           " +
				"  |  |  |  |  |  |  |  |  |" +
				"  |  |  |  |  |  |  |  |  |" +
				"                           ");
		
		useCase.put("000000051", 
				" _  _  _  _  _  _  _  _    " +
				"| || || || || || || ||_   |" +
				"|_||_||_||_||_||_||_| _|  |" +
				"                           ");
		
		useCase.put("123456789", 
				"    _  _     _  _  _  _  _ " +
				"  | _| _||_||_ |_   ||_||_|" + 
				"  ||_  _|  | _||_|  ||_| _|" +
				"                           ");		
		
		useCase.put("000000000", 
				" _  _  _  _  _  _  _  _  _ " +
				"| || || || || || || || || |" +
				"|_||_||_||_||_||_||_||_||_|" +
				"                           ");
	}
	
	@Test
	public void accountNumberIsArrayOfNineDigits() {
		ArrayList<String> arrayToCheck = AccountNumber.divideOcrAccountNumberIntoOcrDigits(useCase.get("000000000"));
		Assert.assertEquals(9, arrayToCheck.size());
	}

	@Test
	public void thirdParsedDigitMatchesStickZero() {
		String stringToCheck = AccountNumber.divideOcrAccountNumberIntoOcrDigits(useCase.get("000000000")).get(3).toString();
		Assert.assertEquals(StickDigitToNumeralConverter.ZERO, stringToCheck);
	}

	@Test
	public void stickZeroIsRecognizedAsZero() {
		StickDigitToNumeralConverter converter = new StickDigitToNumeralConverter();
		String convertedDigit = converter.makeNumeral(StickDigitToNumeralConverter.ZERO);
		Assert.assertEquals("0", convertedDigit);
	}

	@Test
	public void stickOneIsRecognizedAsOne() {
		StickDigitToNumeralConverter converter = new StickDigitToNumeralConverter();
		String convertedDigit = converter.makeNumeral(StickDigitToNumeralConverter.ONE);
		Assert.assertEquals("1", convertedDigit);
	}
 
	@Test
	public void stick1234567879IsRecognized() {
		String NUMBER_TO_CHECK = "123456789";
		AccountNumber accountNumber = new AccountNumber(useCase.get(NUMBER_TO_CHECK));
		Assert.assertEquals(NUMBER_TO_CHECK, accountNumber.textVersion);
	}

	@Test
	public void checkSumShowsValidAccountNumberIsValid() {
		String NUMBER_TO_CHECK = "123456789";
		AccountNumber accountNumber = new AccountNumber(useCase.get(NUMBER_TO_CHECK));
		Assert.assertTrue(accountNumber.isValid);
	}	

	@Test
	public void checkSumShowsInvalidAccountNumberIsInvalid() {
		String NUMBER_TO_CHECK = "111111111";
		AccountNumber accountNumber = new AccountNumber(useCase.get(NUMBER_TO_CHECK));
		Assert.assertFalse(accountNumber.isValid);
	}
}
