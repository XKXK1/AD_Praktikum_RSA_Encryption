package adp11;

public class Hibrid {

	public RSA rsa;
	public Symetric sym;

	public Hibrid() {
		// Constructors of RSA and Symetric will already create Keys
		rsa = new RSA();
		sym = new Symetric();
	}

	/**
	 * Encrypts an incoming String with the Hybrid Method.
	 * @param str String which will be encrypted.
	 * @return Encrypted String
	 */
	public String encrypt(String str) {
		int[] kryptArr = sym.stringToInt(str);

		// encrypten der beiden session keys
		long s0 = rsa.encrypt(kryptArr[0]);
		long s1 = rsa.encrypt(kryptArr[1]);

		// aufteilen der keys auf jeweils 4 indizes des Arrays
		kryptArr[0] = (int) (s0 / Math.pow(95, 3));
		kryptArr[4] = (int) (s1 / Math.pow(95, 3));

		kryptArr[1] = (int) ((s0 % Math.pow(95, 3)) / Math.pow(95, 2));
		kryptArr[5] = (int) ((s1 % Math.pow(95, 3)) / Math.pow(95, 2));

		kryptArr[2] = (int) ((s0 % Math.pow(95, 2)) / Math.pow(95, 1));
		kryptArr[6] = (int) ((s1 % Math.pow(95, 2)) / Math.pow(95, 1));

		kryptArr[3] = (int) (s0 % 95);
		kryptArr[7] = (int) (s1 % 95);

		return sym.toStringFromInt(kryptArr);
	}

	/**
	 * Decrypts an incoming string which was encrypted with the hybrid algorithm
	 * 
	 * @param str String which will be decrypted.
	 * @return Decrypted String
	 */
	public String decrypt(String str) {
		int[] kryptArr = sym.stringToInt(str);

		long s0 = kryptArr[3];
		kryptArr[3] = 0;
		long s1 = kryptArr[7];
		kryptArr[7] = 0;

		// zusammenfuegen der keys zu jeweils einem index im Array, damit das
		// Symetric verfahren korrekt decrypten kann
		for (int i = 0; i < 3; i++) {
			s0 += kryptArr[i] * Math.pow(95, 3 - i);
			kryptArr[i] = 0;
			s1 += kryptArr[i + 4] * Math.pow(95, 3 - i);
			kryptArr[i + 4] = 0;
		}

		// decrypten der beiden zusammengefuegten schluessel
		kryptArr[0] = (int) rsa.decrypt(s0);
		kryptArr[1] = (int) rsa.decrypt(s1);

		int[] decryptArr = sym.decryptArray(kryptArr);

		return sym.toStringFromInt(decryptArr);
	}

	public static void main(String[] args) {
		Hibrid hib = new Hibrid();
		hib.rsa.d = 10709;
		hib.rsa.n = 54071;
		System.out.println(hib.decrypt("  wp !V@yy>%A"));
	}
}
