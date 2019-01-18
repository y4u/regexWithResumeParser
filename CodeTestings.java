package reader;

public class CodeTestings {
	static int count = 0;
	static int power;
	static int reversed;
	static int remainder;

	public static void main(String[] args) {

		System.out.println(inputConversion(5));
		System.out.println(powerGeneration(3456));
	}

	private static int powerGeneration(int base) {
		int sum=0;
		while (base != 0) {
			int digit = base % 10;
			reversed = reversed * 10 + digit;
			base /= 10;
			count++;
		}

		while (reversed != 0) {
			remainder = reversed % 10;
			reversed = reversed / 10;
				System.out.print(remainder + "*" + count+"+");
				sum=(int) (sum+Math.pow(remainder, count));
		}
		return sum;
	}

	private static int inputConversion(int i) {

		int output = (i == 10) ? 5 : 10;
		return output;
	}

}
