import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*Zekeriya Onur Yakýþkan 2017 bahar dönemi bbm104 assignment 1 */
public class HelloJava {
	public static double func1(double x) {
		return x * x - x + 3;

	}

	/* (3sin(x)-4)^2 */
	public static double func2(double x) {
		return (3 * Math.sin(x) - 4) * (3 * Math.sin(x) - 4);
	}

	public static double factorial(int x) {
		if (x == 0)
			return 1;
		else if (x == 1)
			return 1;
		else
			return x * factorial(x - 1);

	}

	/*
	 * arcsinh functions result in maclaurin series (when |x|<1)
	 * ((-1)^n*(2n!)*x^(2n+1))/4^n*(n!)^2*(2n+1) my arcsinh function is the
	 * summition above when n <= 30
	 **/
	public static double arcsinh(double x) {
		int count = 0;
		double result = 0;
		double dividend;
		double divisor;
		while (count <= 30) {
			dividend = (Math.pow(-1.0, count) * factorial(2 * count)) * Math.pow(x, 2.0 * count + 1);
			divisor = (Math.pow(4.0, count) * Math.pow(factorial(count), 2) * (2 * count + 1));
			result = result + dividend / divisor;

			count++;
		}
		return result;
	}

	/* rienmann sum for func1 */
	public static double riemannSumFunc1(double firstval, double lastval, double sumnumber) {
		if (firstval > lastval) {
			double keep = firstval;
			firstval = lastval;
			lastval = keep;
		}
		double range = (lastval - firstval) / sumnumber;
		double xnow = firstval + (range / 2.0);
		double result = func1(xnow);
		sumnumber--;
		while (sumnumber > 0) {
			xnow = xnow + range;
			result = result + func1(xnow);
			sumnumber--;
		}
		return result * range;
	}

	/* riemann sum for func2 */
	public static double riemannSumFunc2(double firstval, double lastval, double sumnumber) {
		if (firstval > lastval) {
			double keep = firstval;
			firstval = lastval;
			lastval = keep;
		}
		double range = (lastval - firstval) / sumnumber;
		double xnow = firstval + (range / 2.0);
		double result = func2(xnow);
		sumnumber--;
		while (sumnumber > 0) {
			xnow = xnow + range;
			result = result + func2(xnow);
			sumnumber--;
		}
		return result * range;
	}

	/*
	 * riemann sum for func3 function when first and last value is in the
	 * interval |x|<1
	 */
	public static double riemannSumFunc3(double firstval, double lastval, double sumnumber) {
		if (firstval > lastval) {
			double keep = firstval;
			firstval = lastval;
			lastval = keep;
		}
		double range = (lastval - firstval) / sumnumber;
		double xnow = firstval + (range / 2.0);
		double result = arcsinh(xnow);
		sumnumber--;
		while (sumnumber > 0) {
			xnow = xnow + range;
			result = result + arcsinh(xnow);
			sumnumber--;
		}
		return result * range;
	}

	/* Determines which riemannsum method to use and its parametres */
	public static double riemannSum(String func, double firstval, double lastval, double sumnumber) {
		if (func.equals("Func1"))
			return riemannSumFunc1(firstval, lastval, sumnumber);
		else if (func.equals("Func2"))
			return riemannSumFunc2(firstval, lastval, sumnumber);
		else// (func == ""Func3")
			return riemannSumFunc3(firstval, lastval, sumnumber);

	}

	/*
	 * this method returns every digit in a number as an int array example:
	 * 1255-->[1,2,5,5] it will be used to get digits in order to find armstrong
	 * numbers
	 */
	public static int[] getDigits(int number, int digit) {
		int[] digitArray = new int[digit];
		int digitNumber;
		for (int i = digit - 1; i > -1; i--) {
			digitNumber = number - (number / 10) * 10;
			digitArray[i] = digitNumber;
			number = number / 10;
		}
		return digitArray;

	}

	/*
	 * returns armstrong numbers which has x digits it returns an array
	 */
	public static int[] armstrong(int x) {
		int[] xthpowerofDigits = new int[10];// this array keeps xth power of n
												// -1<n<10 n€N
		int digit = 0;
		while (digit < 10) {
			xthpowerofDigits[digit] = (int) Math.pow(digit, x);
			digit++;
		}
		int[] bigArmsArray = new int[100];// i dont know how big my array so i
											// made a very big array first
		int armsCount = 0;// will help me to determine my armstrong arrays
							// length
		int[] digitArray = new int[x];
		int biggestnumber = (int) Math.pow(10, x);
		int smallestnumber = (int) Math.pow(10, x - 1);
		if(x == 1)
			smallestnumber = 0;
		for (int i = smallestnumber; i < biggestnumber; i++) {
			int armstrongsum = 0;
			digitArray = getDigits(i, x);
			for (int y = 0; y < x; y++) {
				armstrongsum = armstrongsum + xthpowerofDigits[digitArray[y]];

			}
			/*
			 * below if loop looks whether the number is an armstrong number or
			 * not if it is an armstrong number, it is been added to big array
			 */
			if (armstrongsum == i) {
				bigArmsArray[armsCount] = i;
				armsCount++;
			}
		}
		int[] armstrongArray = new int[armsCount];
		// below return array has its final form
		for (int i = 0; i < armsCount; i++)
			armstrongArray[i] = bigArmsArray[i];
		return armstrongArray;

	}


	public static void riemannOutput(String line, String[] lineArray) {
		double firstval = Double.parseDouble(lineArray[2]);
		double lastval = Double.parseDouble(lineArray[3]);
		double sumnumber = Double.parseDouble(lineArray[4]);
		System.out.println(line + " Result: " + riemannSum(lineArray[1], firstval, lastval, sumnumber));
	}

	public static void armstrongOutput(String line, String[] lineArray) {
		int digitNumber = Integer.parseInt(lineArray[1]);
		System.out.print(line + " Result:");
		for (int digit = 1; digit < digitNumber + 1; digit++){
			int[] armslist = armstrong(digit);
			//System.out.print(" length "+digit+" "+armslist.length);
			for (int i = 0; i < armslist.length; i++)
				System.out.print(" "+armslist[i]);
		}
		System.out.println();
	}

	public static void arcsinhOutput(String line, String[] lineArray) {
		double x = Double.parseDouble(lineArray[1]);
		System.out.println(line + " Result: " + arcsinh(x));
	}

	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(new File(args[0]));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] lineArray = line.split(" ");

				if (lineArray[0].equals("IntegrateReimann"))
					riemannOutput(line, lineArray);
				else if (lineArray[0].equals("Armstrong"))
					armstrongOutput(line, lineArray);
				else if (lineArray[0].equals("Arcsinh"))
					arcsinhOutput(line, lineArray);
				else
					System.out.println("Input is given in wrong format");

			}
			scanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
		}

	}

}
