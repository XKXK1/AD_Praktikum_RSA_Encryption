package adp11;

import java.util.Random;

public class Symetric {
	private int sessionKey1;
	private int sessionKey2;
	private int cryptSize;
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
		cryptSize = intArr.length + 8;

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
		int[] cryptArray = new int[cryptSize];
		cryptArray[0] = sessionKey1;
		cryptArray[1] = sessionKey2;

		int i = 0;
		while (i < arraySize - 1) {
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
	 * Encrypting an intArray with the same 2 session Keys
	 * 
	 * @param intArr
	 * @return
	 */
	public int[] decryptArray(int[] intArr) {
		int outArr[] = new int[intArr.length];
		int i = 0;
		while (i < arraySize - 1) {
			outArr[i + 8] = (intArr[i + 8] + 95 - sessionKey1) % 95;
			outArr[i + 1 + 8] = (intArr[i + 1 + 8] + 95 - sessionKey2) % 95;
			i += 2;
		}
		if (arraySize % 2 != 0) {
			outArr[i + 8] = (intArr[i + 8] + 95 - sessionKey1) % 95;
		}
		return outArr;
	}

	public char[] toCharFromInt(int[] intArr) {
		char charArr[] = new char[arraySize];
		for (int i = 0; i < arraySize - 1; i++) {
			charArr[i] = (char) (intArr[i + 8] + 32);
			charArr[i + 1] = (char) (intArr[i + 1 + 8] + 32);
		}
		return charArr;
	}

	private void init() {
		Random rand = new Random();

		sessionKey1 = rand.nextInt(94) + 1;
		sessionKey2 = rand.nextInt(94) + 1;

		while (sessionKey1 == sessionKey2) {
			sessionKey2 = rand.nextInt(94) + 1;
		}
	}

	public void decryptFromOutside(int[] decrypted, int k1, int k2) {
		setSessionKey1(k1);
		setSessionKey2(k2);
		int[] decryptedArr = decryptArray(decrypted);
		char[] outArr = toCharFromInt(decryptedArr);
		System.out.println("Decrypted String:\n" + outArr.toString());

	}

	public void setSessionKey1(int sessionKey1) {
		this.sessionKey1 = sessionKey1;
	}

	public void setSessionKey2(int sessionKey2) {
		this.sessionKey2 = sessionKey2;
	}

	public static void main(String[] args) {
		Symetric test1 = new Symetric();
		String text = "Hallo guten tag";
		int stringArray[] = test1.stringToInt(text);
		int encrypted[] = test1.cryptArray(stringArray);
		int decrypted[] = test1.decryptArray(encrypted);
		char[] outArr = test1.toCharFromInt(decrypted);
		System.out.println(outArr);

	}

}
