package adp11;

import java.util.Random;

public class Symetric {
	private int sessionKey1;
	private int sessionKey2;
	private int cryptSize;
	private int[] cryptArray;
	private int arraySize;

	public Symetric() {
		init();
	}

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

	public void cryptArray(int[] intArr) {
		cryptArray = new int[cryptSize];
		cryptArray[0] = sessionKey1;
		cryptArray[1] = sessionKey2;

		int i = 0;
		while (i < arraySize - 1) {
			cryptArray[i + 8] = (intArr[i] + sessionKey1) % 95;
			cryptArray[i + 1 + 8] = (intArr[i + 1] + sessionKey2) % 95;
			i += 2;
		}
		if(arraySize%2!=0){
			cryptArray[i + 8] = (intArr[i] + sessionKey1) % 95;			
		}
	}

	public void decryptArray() {
		int i = 0;
		while (i < arraySize - 1) {
			cryptArray[i + 8] = (cryptArray[i + 8] + 95 - sessionKey1) % 95;
			cryptArray[i + 1 + 8] = (cryptArray[i + 1 + 8] + 95 - sessionKey2) % 95;
			i += 2;
		}
		if(arraySize%2!=0){
			cryptArray[i + 8] = (cryptArray[i + 8] + 95 - sessionKey1) % 95;			
		}
	}

	public char[] toCharFromInt() {
		char charArr[] = new char[arraySize];
		for (int i = 0; i < arraySize - 1; i++) {
			charArr[i] = (char) (cryptArray[i + 8] + 32);
			charArr[i + 1] = (char) (cryptArray[i + 1 + 8] + 32);
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

	public static void main(String[] args) {
		Symetric test1 = new Symetric();
		String text = "Hallo guten tag";

		int testInt[] = test1.stringToInt(text);
		test1.cryptArray(testInt);

		test1.decryptArray();
		char ausgabe[] = test1.toCharFromInt();
		System.out.println(ausgabe);

	}

}
