package adp11;

import java.util.Random;

public class Symetric {
	private int sessionKey1;
	private int sessionKey2;
	private int arraySize;

	public Symetric() {
		init();
	}

	/**
	 * Forming a String into an "int" array
	 * 
	 * @param text
	 *            A String which will be transformed into an int Array.
	 * @return The int Array will be returned.
	 */
	public int[] stringToInt(String text) {

		char charArr[] = text.toCharArray();
		arraySize = charArr.length;
		int intArr[] = new int[charArr.length];

		for (int i = 0; i < charArr.length; i++) {
			intArr[i] = charArr[i] - 32;
		}
		return intArr;
	}

	/**
	 * Encrypting an intArray with 2 created session Keys.
	 * 
	 * @param intArr
	 *            The given intArray will be encrypted.
	 * @return The Encrypted Array which will be unable to read without
	 *         decrypting again.
	 */
	public int[] cryptArray(int[] intArr) {
		int[] cryptArray = new int[intArr.length+8];
		cryptArray[0] = sessionKey1;
		cryptArray[1] = sessionKey2;

		int i = 0;
		while (i < intArr.length - 1) {
			cryptArray[i + 8] = (intArr[i] + sessionKey1) % 95;
			cryptArray[i + 1 + 8] = (intArr[i + 1] + sessionKey2) % 95;
			i += 2;
		}
		if (arraySize % 2 != 0) {
			cryptArray[i + 8] = (intArr[i] + sessionKey1) % 95;
		}
		return cryptArray;
	}

	/**
	 * Decrypting an intArray with the same 2 session Keys which were used to
	 * Encrypt the array.
	 * 
	 * @param intArr
	 *            The expected intArray will be decrypted.
	 * @return The decrypted Array which now will be readable.
	 */
	public int[] decryptArray(int[] intArr) {
		int outArr[] = new int[intArr.length-8];
		int i = 0;
		sessionKey1 = intArr[0];
		sessionKey2 = intArr[1];
		while (i < intArr.length - 9) {
			outArr[i] = (intArr[i + 8] + 95 - sessionKey1) % 95;
			outArr[i + 1] = (intArr[i + 1 + 8] + 95 - sessionKey2) % 95;
			i += 2;
		}
		if (intArr.length % 2 != 0) {
			outArr[i] = (intArr[i + 8] + 95 - sessionKey1) % 95;
		}
		return outArr;
	}

	/**
	 * Forming an array of type "int" into an array of type "char".
	 * 
	 * @param intArr
	 *            The expected int array will be formed into a char array
	 * @return The char array.
	 */
	public String toStringFromInt(int[] intArr) {
		char charArr[] = new char[intArr.length];
		int i = 0;
		
		while (i < intArr.length){
			charArr[i] = (char) (intArr[i] + 32);
			i++;
		}	
		String outString = new String(charArr);
		return outString;
	}
	
	public char[] toCharFromEncryptInt(int[] intArr) {
		char charArr[] = new char[intArr.length];
		for (int i = 0; i < intArr.length - 1; i++) {
			charArr[i] = (char) ((intArr[i]) +32);
		}
		return charArr;
	}

	/**
	 * Initialising with two random session Keys which will be used for
	 * encrypting and decrypting again. The values can be between 1-94 and will
	 * not be equal.
	 */
	private void init() {
		Random rand = new Random();

		sessionKey1 = rand.nextInt(94) + 1;
		sessionKey2 = rand.nextInt(94) + 1;

		while (sessionKey1 == sessionKey2) {
			sessionKey2 = rand.nextInt(94) + 1;
		}
	}

	/**
	 * This method is used to decrypt a Message which is coming from another
	 * Author created with different session Keys than this session.
	 * 
	 * @param encrypted
	 *            The encrypted array which will be decrypted.
	 * @param k1
	 *            First session Key from the Session in which the incoming
	 *            Message was decrypted.
	 * @param k2
	 *            Second session Key from the Session in which the incoming
	 *            Message was decrypted.
	 */
	public void decryptFromOutside(String text) {
		int encrypted[] = stringToInt(text);
		
		setSessionKey1(encrypted[0]);
		setSessionKey2(encrypted[1]);

		int[] decryptedArr = decryptArray(encrypted);
		String outArr = toStringFromInt(decryptedArr);
		System.out.println( outArr);

	}

	public void setSessionKey1(int sessionKey1) {
		this.sessionKey1 = sessionKey1;
	}

	public void setSessionKey2(int sessionKey2) {
		this.sessionKey2 = sessionKey2;
	}

	public static void main(String[] args) {
		Symetric test1 = new Symetric();
		String text = "Ste";
		int stringArray[] = test1.stringToInt(text);
		int encrypted[] = test1.cryptArray(stringArray);
		System.out.println(test1.toStringFromInt(encrypted));
		int decrypted[] = test1.decryptArray(encrypted);
		String out = test1.toStringFromInt(decrypted);
		
		System.out.println(out);
		String outs = "}N      @D`1j4@Db8cB";
		
		test1.decryptFromOutside(outs);


	}

	public int getSessionKey1() {
		return sessionKey1;
	}

	public int getSessionKey2() {
		return sessionKey2;
	}

}
