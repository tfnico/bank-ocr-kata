package convert;

import java.util.ArrayList;

public class AccountNumber {

	public String textVersion = "";
	public boolean isValid;
	final static int WIDTH_OF_OCR_NUMERAL = 3;
	final static int NUMBER_OF_DIGITS = 9;

	public AccountNumber(String ocrAccountNumber) {
		this.textVersion = createAccountNumberFromOcr(ocrAccountNumber);
		isValid = checkSum();
	}

	public static ArrayList<String> divideOcrAccountNumberIntoOcrDigits(String ocrAccountNumber) {

		ArrayList<String> accountNumberAsOcrDigits = new ArrayList<String>();

		for (int digit = 0; digit < NUMBER_OF_DIGITS; digit++) {

			int startOfFirstLine = (digit * WIDTH_OF_OCR_NUMERAL);
			int startOfSecondLine = startOfFirstLine + (WIDTH_OF_OCR_NUMERAL * NUMBER_OF_DIGITS);
			int startOfThirdLine = startOfSecondLine + (WIDTH_OF_OCR_NUMERAL * NUMBER_OF_DIGITS);

			String firstLineOfOcrDigit = ocrAccountNumber.substring(startOfFirstLine,(startOfFirstLine + WIDTH_OF_OCR_NUMERAL));
			String secondLineOfOcrDigit = ocrAccountNumber.substring(startOfSecondLine,(startOfSecondLine + WIDTH_OF_OCR_NUMERAL));
			String thirdLineOfOcrDigit = ocrAccountNumber.substring(startOfThirdLine,(startOfThirdLine + WIDTH_OF_OCR_NUMERAL));

			String nextDigit = firstLineOfOcrDigit + secondLineOfOcrDigit + thirdLineOfOcrDigit;
			accountNumberAsOcrDigits.add(nextDigit);
		}
		return accountNumberAsOcrDigits;
	}

	public static String createAccountNumberFromOcr(String ocrToInterpret) {

		ArrayList<String> accountNumberAsOcrDigits = divideOcrAccountNumberIntoOcrDigits(ocrToInterpret);

		String accountNumber = "";

		StickDigitToNumeralConverter converter = new StickDigitToNumeralConverter();
		final int ACCOUNT_NUMBER_LENGTH = accountNumberAsOcrDigits.size();

		for (int digit = 0; digit < ACCOUNT_NUMBER_LENGTH; digit++) {
			accountNumber = accountNumber + (converter.makeNumeral(accountNumberAsOcrDigits.get(digit)));
		}

		return accountNumber;
	}

	public boolean checkSum() {
		
		int checkSumCalculation = 0;
		int currentDigit;
		
		for (int digit = 0; digit < NUMBER_OF_DIGITS; digit++) {
			
			String thisCharacter = textVersion.substring(digit, digit+1);
			currentDigit = Integer.parseInt(thisCharacter);
			checkSumCalculation = checkSumCalculation + ((NUMBER_OF_DIGITS-digit) * currentDigit);
		}

		return ((checkSumCalculation % 11) == 0);
	}
}
