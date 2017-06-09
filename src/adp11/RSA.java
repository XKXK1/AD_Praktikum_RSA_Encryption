package adp11;

import java.io.IOException;

public class RSA {
	
	long p, q, n, d, phiN;
	long e = 1;

	public RSA() {
		init();
	}

	long modpow(long value, long power, long mod) {
		long e = 1;

		for (int i = 0; i < power; i++) {
			e = ((e * value) % mod);
		}
		return e;
	}

	long modInverse(long a, long m) {
		a = a % m;
		for (int x = 1; x < m; x++) {
			if ((a * x) % m == 1)
				return x;
		}
		return m;
	}

	private long findGCD(long number1, long number2) {
		if (number2 == 0) {
			return number1;
		}
		return findGCD(number2, number1 % number2);
	}

	private final int primeNumbersUpTo500[] = { 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167,
			173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
			283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419,
			421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499 };

	private int findPrimeNumberUpTo500() {
		return primeNumbersUpTo500[(int) (Math.random() * primeNumbersUpTo500.length)];
	}

	public void init() {
		// selecting 2 three digit prime numbers which must be different
		p = findPrimeNumberUpTo500();
		q = findPrimeNumberUpTo500();

		while (q == p) {
			q = findPrimeNumberUpTo500();
		}
		n = p * q;

		// calculate �(n) = (p - 1).(q - 1)
		phiN = (p - 1) * (q - 1);

		// find e that gcd of e and �(n) is =1

		long gcd;
		do {
			e++;
			gcd = findGCD(e, phiN);
		} while ((e == phiN) || (gcd != 1));

		// Calculate d such that e.d = 1 (mod �(n))
		d = modInverse(e, phiN);

	}

	public long encrypt(long plaintext) {
		return modpow(plaintext, e, n);
	}

	public long decrypt(long ciphertext) {
		return modpow(ciphertext, d, n);
	}
	

	public void setN(long n) {
		this.n = n;
	}

	public void setD(long d) {
		this.d = d;
	}

	public void setE(long e) {
		this.e = e;
	}

	public static void main(String[] args) {
		RSA test = new RSA();
//		test.setN(167719);
//		test.setD(121379);
//		
//		long decryptedText = test.decrypt(147304);
//		System.out.printf("%c", (char)decryptedText);
//		
		
		long plaintext = 0;
		System.out.println("Enter any character : ");
		try {
			plaintext = System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long bplaintext, bciphertext;
		bplaintext = plaintext;
		bciphertext = test.encrypt(bplaintext);
		System.out.printf("Text : %c", (char) bplaintext);
		System.out.println("\nChiffrierText : " + bciphertext);
		bplaintext = test.decrypt(bciphertext);
		System.out.printf("Text nach Decryption  : %c ", (char) bplaintext);

		// String plain = "Hallo!";
		// char charArr[] =plain.toCharArray();
		// int intArr[] = new int[charArr.length];
		//
		// for(int i = 0; i < charArr.length; i++){
		// intArr[i]= charArr[i];
		// }
		//

	}

}
