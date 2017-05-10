package edu.utexas.cs.nn.util.stats;

import edu.utexas.cs.nn.parameters.CommonConstants;
import edu.utexas.cs.nn.util.datastructures.Pair;
import edu.utexas.cs.nn.util.random.RandomNumbers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * Contains important utility functions for statistical purposes
 *
 * @author Jacob Schrum
 */
public class StatisticsUtilities {

	/**
	 * TODO
	 * 
	 * @param values Array of input values
	 * @param p Percentile to compute
	 * @return Percentile value
	 * @throws IllegalArgumentException
	 *             if the parameters are not valid or the input array is null
	 */
	public static double percentile(final double[] values, final double p) {
		if ((p > 100) || (p <= 0)) {
			throw new IllegalArgumentException("invalid quantile value: " + p);
		}
		double n = (double) values.length;
		if (n == 0) {
			return Double.NaN;
		}
		if (n == 1) {
			return values[0]; // always return single value for n = 1
		}
		double pos = p * (n + 1) / 100;
		double fpos = Math.floor(pos);
		int intPos = (int) fpos;
		double dif = pos - fpos;
		double[] sorted = new double[values.length];
		System.arraycopy(values, 0, sorted, 0, values.length);
		Arrays.sort(sorted);

		if (pos < 1) {
			return sorted[0];
		}
		if (pos >= n) {
			return sorted[values.length - 1];
		}
		double lower = sorted[intPos - 1];
		double upper = sorted[intPos];
		return lower + dif * (upper - lower);
	}

	/**
	 * Returns the median in a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @return Median in xs
	 */
	public static double median(double[] xs) {
		Arrays.sort(xs);
		if (xs.length % 2 == 0) { // even
			return (xs[(xs.length / 2) - 1] + xs[xs.length / 2]) / 2.0;
		} else { // odd
			return xs[xs.length / 2];
		}
	}

	/**
	 * The values in xs are checked for the max value, and the array index
	 * corresponding to the max is returned. If there is a tie, then the
	 * randomArgMaxTieBreak setting determines whether a random max is chosen or
	 * the first of the maxes is chosen.
	 * 
	 * @param xs
	 *            values to check for max
	 * @return array index corresponding to max
	 */
	public static int argmax(double[] xs) {
		double max = -Double.MAX_VALUE;
		ArrayList<Integer> equalMaxIndexes = new ArrayList<Integer>(xs.length);
		for (int i = 0; i < xs.length; i++) {
			if (xs[i] == max) {
				equalMaxIndexes.add(i);
			} else if (xs[i] > max) {
				max = xs[i];
				equalMaxIndexes.clear();
				equalMaxIndexes.add(i);
			}
		}
		return equalMaxIndexes.get(CommonConstants.randomArgMaxTieBreak
				? RandomNumbers.randomGenerator.nextInt(equalMaxIndexes.size()) : 0);
	}

	/**
	 * The values in xs are checked for the max value, and the array index
	 * corresponding to the max is returned. If there is a tie, then the
	 * randomArgMaxTieBreak setting determines whether a random max is chosen or
	 * the first of the maxes is chosen.
	 * 
	 * @param xs
	 * @return array index corresponding to max
	 */
	public static int argmax(int[] xs) {
		double max = -Double.MAX_VALUE;
		ArrayList<Integer> equalMaxIndexes = new ArrayList<Integer>(xs.length);
		for (int i = 0; i < xs.length; i++) {
			if (xs[i] == max) {
				equalMaxIndexes.add(i);
			} else if (xs[i] > max) {
				max = xs[i];
				equalMaxIndexes.clear();
				equalMaxIndexes.add(i);
			}
		}
		return equalMaxIndexes.get(CommonConstants.randomArgMaxTieBreak
				? RandomNumbers.randomGenerator.nextInt(equalMaxIndexes.size()) : 0);
	}

	/**
	 * The values in xs are checked for the max value, and the array index
	 * corresponding to the max is returned. If there is a tie, then the
	 * randomArgMaxTieBreak setting determines whether a random max is chosen or
	 * the first of the maxes is chosen.
	 * 
	 * Rank can make the result more specific, to get the max (rank = 0), next
	 * highest (rank = 1), or next (rank = 2), etc.
	 * 
	 * @param xs
	 * @param rank
	 * @return array index corresponding to max
	 */
	@SuppressWarnings("unchecked")
	public static int argmax(int[] xs, int rank) {
		Pair<Integer, Integer>[] indexValuePairs = new Pair[xs.length];
		for (int i = 0; i < xs.length; i++) {
			indexValuePairs[i] = new Pair<Integer, Integer>(i, xs[i]);
		}
		Arrays.sort(indexValuePairs, new Comparator<Pair<Integer, Integer>>() {
			public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
				// second values are actual usages to be sorted by
				return (int) Math.signum(o2.t2 - o1.t2); // descending order
															// sort
			}
		});
		return indexValuePairs[rank].t1;
	}

	/**
	 * The values in xs are checked for the min value, and the array index
	 * corresponding to the min is returned. If there is a tie, then the
	 * randomArgMaxTieBreak setting determines whether a random min is chosen or
	 * the first of the mins is chosen.
	 * 
	 * @param xs
	 *            values to check for min
	 * @return array index corresponding to min
	 */
	public static int argmin(double[] xs) {
		double min = Double.MAX_VALUE;
		ArrayList<Integer> equalMinIndexes = new ArrayList<Integer>(xs.length);
		for (int i = 0; i < xs.length; i++) {
			if (xs[i] == min) {
				equalMinIndexes.add(i);
			} else if (xs[i] < min) {
				min = xs[i];
				equalMinIndexes.clear();
				equalMinIndexes.add(i);
			}
		}
		return equalMinIndexes.get(CommonConstants.randomArgMaxTieBreak
				? RandomNumbers.randomGenerator.nextInt(equalMinIndexes.size()) : 0);
	}

	/**
	 * TODO
	 * 
	 * @param xs
	 * @return
	 */
	public static int argmin(int[] xs) {
		double min = Double.MAX_VALUE;
		ArrayList<Integer> equalMinIndexes = new ArrayList<Integer>(xs.length);
		for (int i = 0; i < xs.length; i++) {
			if (xs[i] == min) {
				equalMinIndexes.add(i);
			} else if (xs[i] < min) {
				min = xs[i];
				equalMinIndexes.clear();
				equalMinIndexes.add(i);
			}
		}
		return equalMinIndexes.get(CommonConstants.randomArgMaxTieBreak
				? RandomNumbers.randomGenerator.nextInt(equalMinIndexes.size()) : 0);
	}

	/**
	 * TODO
	 * 
	 * @param ps
	 * @param temperature
	 * @return
	 */
	public static int softmax(double[] ps, double temperature) {
		double[] posExps = new double[ps.length];
		for (int i = 0; i < posExps.length; i++) {
			posExps[i] = Math.exp(ps[i] / temperature);
		}
		posExps = distribution(posExps);
		return RandomNumbers.probabilisticSelection(posExps);
	}

	/**
	 * Returns an Integer as calculated by the probabilisticSelection method in the RandomNumbers Class
	 * 
	 * @param ps Array of Doubles
	 * @return 
	 */
	public static int probabilistic(double[] ps) {
		for (int i = 0; i < ps.length; i++) { // Cycles through the given Array to make all values positive
			ps[i] += 1; // Initial range is [-1,1], so now all are positive
		}
		double[] dist = distribution(ps); // Creates a new Array with the distribution of the given Array 
		return RandomNumbers.probabilisticSelection(dist);
	}

	/**
	 * Calculates the distribution values in a given Array of Doubles, then returns an Array of Doubles with the distribution values
	 * Values in raw must be non-negative
	 * 
	 * @param raw Array of Doubles
	 * @return An Array of Doubles with the distribution values of the numbers in raw
	 */
	public static double[] distribution(double[] raw) {
		double[] dist = new double[raw.length]; // Creates an Array of Doubles to be returned later
		double sum = 0; // Stores the sum of the values in raw[]
		for (int i = 0; i < raw.length; i++) { // Cycles through the given Array and stores the sum
			if (raw[i] < 0) { // If a negative value is encountered, an Error message is printed, and the method stops
				System.out.println(
						"Cannot create distribution from negative values: " + Arrays.toString(raw) + ":" + raw[i]);
				System.exit(1);
			}
			sum += raw[i]; // Adds the values from every Index
		}
		if (sum == 0) { // If the sum == 0, an Error will be printed TODO
			return new double[0];
			// System.out.println("Cannot create distribution across nothing!");
			// throw new IllegalArgumentException("Array only adds to 0: " +
			// Arrays.toString(raw));
		}
		for (int i = 0; i < raw.length; i++) { // Cycles through the given and created Arrays and stores the distribution values of raw
			dist[i] = raw[i] / sum; // Stores the distribution values of raw in dist at the proper Index
		}
		return dist; // Returns the Array of distribution values
	}

	/**
	 * Calculates the distribution values in a given Array of Integers, then returns an Array of Doubles with the distribution values
	 * 
	 * @param raw Array of Integers
	 * @return An Array of Doubles with the distribution values of the numbers in raw
	 */
	public static double[] distribution(int[] raw) {
		assert raw != null : "Can't work on null array"; // Asserts that the given Array is not null
		double[] ds = new double[raw.length]; // Creates a new Array of Doubles to store the values in the given Array of Integers
		for (int i = 0; i < raw.length; i++) { // Stores the values in the given Array of Integers in the Array of Doubles at the same Index
			ds[i] = raw[i];
		}
		return distribution(ds); // Calls the above method with the new Array of Doubles with the given values in the Array of Integers
	}

	/**
	 * Statistical mode calculation. Larger values are favored for tie-breaking
	 * purposes.
	 *
	 * @param xs Array of doubles
	 * @return Mode of values in xs
	 */
	public static double mode(double[] xs) {
		return mode(xs, 0.001); // Calls the method below to calculate and return the mode in the given Array of Doubles
	}

	/**
	 * Calculates the mode of a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @param epsilon The epsilon value
	 * @return The mode of the values in xs
	 */
	public static double mode(double[] xs, double epsilon) {
		assert xs.length > 0 : "Array empty!"; // Asserts that the given Array is not empty
		// System.out.println(Arrays.toString(xs));
		Arrays.sort(xs); // Sorts the given Array
		// System.out.println(Arrays.toString(xs));
		int maxCount = 0; // Stores the maximum number of times a given number is present
		double mode = xs[0]; // Stores the current Mode of the given Array
		int currentCount = 0; // Stores the current number of times a given number is present
		double currentValue = xs[0]; // Stores the value currently being counted
		for (int i = 0; i < xs.length; i++) { // Cycles through the given Array of Doubles and count how many times each given value appears
			if (Math.abs(xs[i] - currentValue) < epsilon) { // If the number at the current Index is equal to the currentValue within epsilon, increment the currentCount
				currentCount++;
				// System.out.println("\tCount " + currentValue + " " +
				// currentCount + " times");
			} else { // The value at the current Index is different from the current value
				if (currentCount >= maxCount) { // If the currentValue is encountered more times than the previously most encountered value, update the mode and the maxCount
					mode = currentValue;
					maxCount = currentCount;
					// System.out.println("\tMode = " + mode + " with " +
					// maxCount + " times");
				}
				currentValue = xs[i]; // Updates the currentValue to the newly encountered value
				currentCount = 1; // Updates the currentCount
			}
		}
		// Final check
		if (currentCount >= maxCount) { // If the final value is encountered more times than the previously most encountered value, update the mode
			mode = currentValue;
			// maxCount = currentCount;
			// System.out.println("\tMode = " + mode + " with " + maxCount + "
			// times");
		}

		return mode; // Returns the mode of the given Array of Doubles
	}

	/**
	 * Returns the maximum value in a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @return Maximum value in xs
	 */
	public static double maximum(double[] xs) {
		double max = xs[0]; // Stores the maximum value to be returned later; is assumed to be the value at the first Index
		for (int i = 1; i < xs.length; i++) { // Cycles through the Array to search for the maximum value
			max = Math.max(max, xs[i]); // If the value at the current Index is greater than the previously greatest encountered value, update max
		}
		return max; // Returns the maximum value in the given Array
	}

	/**
	 * Returns the maximum number in a given Collection of Integers
	 * 
	 * @param xs Collection of Integers
	 * @return Maximum value in xs
	 */
	public static int maximum(Collection<Integer> xs) {
		int max = Integer.MIN_VALUE; // Stores the maximum value to be returned later; is assumed to be the the smallest possible Integer value (to allow for comparison)
		for (Integer x : xs) { // Cycles through the Collection to search for the maximum value
			max = Math.max(max, x);  // If the value at the current Index is greater than the previously greatest encountered value, update max
		}
		return max; // Returns the maximum value in the given Collection
	}

	/**
	 * Returns the maximum number in a given Array of Long values
	 * 
	 * @param xs Array of Long values
	 * @return Maximum number in xs
	 */
	public static long maximum(long[] xs) {
		long temp = xs[0]; // Stores the maximum value to be returned later; is assumed to be the value at the first Index
		for (int i = 0; i < xs.length; i++) { // Cycles through the Array to search for the maximum value
			temp = Math.max(temp, xs[i]); // If the value at the current Index is greater than the previously greatest encountered value, update max
		}
		return temp; // Returns the maximum value in the given Array
	}

	/**
	 * Returns the minimum of a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @return The minimum number in xs
	 */
	public static double minimum(double[] xs) {
		double min = xs[0]; // Stores the minimum value to be returned later; is assumed to be the value at the first Index
		for (int i = 1; i < xs.length; i++) { // Cycles through the Array to search for the minimum value
			min = Math.min(min, xs[i]); // If the value at the current Index is less than the previously smallest encountered value, update min
		}
		return min;// Returns the minimum value in the given Array
	}

	/**
	 * Returns the average of the numbers in a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @return The average of the numbers in xs
	 */
	public static double average(double[] xs) {
		double avg = 0; // Stores the value of the average to be returned later
		for (int i = 0; i < xs.length; i++) { // Cycles through the given Array to calculate the average of the given values
			assert!Double.isNaN(xs[i]) : "xs[" + i + "] is NaN!"; // Asserts that the value at a given Index is a number
			avg += (xs[i] - avg) / (i + 1); // Calculates the average of the Array up to the given Index and stores the value to be returned later
			assert!Double.isNaN(avg) : "avg is NaN!"; // Asserts that the calculated average is a number
		}
		return avg; // Returns the value of the average of the values in the given Array
	}

	/**
	 * Returns the sum of the numbers in a given Array of Integers
	 *
	 * @param vals Array of Integers to add
	 * @return Sum of values in vals
	 */
	public static int sum(int[] vals) {
		int sum = 0; // Stores the sum to be returned later
		for (int i = 0; i < vals.length; i++) { // Cycles through the given Array to calculate the sum of all values
			sum += vals[i]; // Stores the overall sum of the values in the given Array
		}
		return sum; // Returns the sum of all the values in the given Array
	}

	/**
	 * Returns the sum of the numbers in a given Array of Doubles
	 * 
	 * @param vals Array of Doubles
	 * @return Sum of values in vals
	 */
	public static double sum(double[] vals) {
		double sum = 0; // Stores the sum to be returned later
		for (int i = 0; i < vals.length; i++) { // Cycles through the given Array to calculate the sum of all values
			sum += vals[i]; // Stores the overall sum of the values in the given Array
		}
		return sum; // Returns the sum of all the values in the given Array
	}

	/**
	 * Returns the sum of numbers in a given Collection of Integers
	 * 
	 * @param xs A Collection of Integers
	 * @return Sum of the values in xs
	 */
	public static int sum(Collection<Integer> xs) {
		int result = 0; // Stores the sum to be returned later
		for (Integer x : xs) { // Cycles through the given Collection to calculate the sum of all values
			result += x; // Stores the overall sum of the values in the given Array
		}
		return result; // Returns the sum of all the values in the given Array
	}

	/**
	 * Calculates the Population Standard Deviation in a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @return The square root of the Population Variance of xs
	 */
	public static double populationStandardDeviation(double[] xs) {
		return Math.sqrt(populationVariance(xs)); // Returns the square root of the Population Variance of the given Array
	}

	/**
	 * Calculates the Sample Standard Deviation in a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @return The square root of the Sample Variance in xs
	 */
	public static double sampleStandardDeviation(double[] xs) {
		return Math.sqrt(sampleVariance(xs)); // Returns the square root of the Sample Variance in the given Array
	}

	/**
	 * Calculates the Population Variance in a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @return The sumOfSquares in xs divided by the Length of xs
	 */
	public static double populationVariance(double[] xs) {
		return sumOfSquares(xs) / xs.length; // Returns the sumOfSquares in the given Array divided by the Length of the given Array
	}

	/**
	 * Calculates the Sample Varience in a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @return The sumOfSquares in xs divided by the Length of xs minus 1
	 */
	public static double sampleVariance(double[] xs) {
		return sumOfSquares(xs) / (xs.length - 1.0); // Returns the sumOfSquares in the given Array divided by the Length of the given Array minus 1
	}

	/**
	 * Calculate statistical sum of squares in a given Array of Doubles
	 * 
	 * @param xs Array of Doubles
	 * @return The Statistical Sum of Squares of the values in xs
	 */
	public static double sumOfSquares(double[] xs) {
		double average = average(xs); // Stores the average of the given Array
		double[] squares = new double[xs.length]; // Creates a new Array of Doubles with the same Length of the given Array
		for (int i = 0; i < squares.length; i++) { // Cycles through the newly created Array to store values at each Index
			squares[i] = xs[i] - average; // Stores the value of the given Array at the current Index minus the average of the given Array
			squares[i] *= squares[i]; // Multiplies the newly stored value by itself to store the Square of that value at the same Index
		}
		return sum(squares); // Returns the sum of the newly created Array
	}

	/**
	 * Compute "instantaneous error energy" as described on page 161 of Neural
	 * Networks by Haykin.
	 * 
	 * @param desired
	 *            what output should be
	 * @param output
	 *            actual output of one neuron
	 * @return instantaneous error
	 */
	public static double instantaneousErrorEnergy(double desired, double output) {
		assert!Double.isNaN(output) : "output was NaN!";
		assert!Double.isNaN(desired) : "desired was NaN!";
		double e = desired - output;
		if (CommonConstants.watch) {
			System.out.println("Error: " + e);
		}
		// return Math.abs(e);
		assert!Double.isNaN(e) : "Error was NaN!";
		return (e * e) / 2.0;
	}

	/**
	 * Compute "TOTAL instantaneous error energy" as described on page 161 of
	 * Neural Networks by Haykin. COULD BE SLIGHTLY OPTIMIZED
	 * 
	 * @param pairs
	 *            each pair is desired/actual values for one output neuron
	 * @return total error across whole output layer
	 */
	public static double instantaneousTotalErrorEnergy(ArrayList<Pair<Double, Double>> pairs) {
		double sum = 0;
		for (Pair<Double, Double> p : pairs) {
			sum += instantaneousErrorEnergy(p.t1, p.t2);
			assert!Double.isNaN(sum) : "sum was NaN!";
		}
		return sum;
	}

	/**
	 * Compute "average squared error energy" as described on page 161 of Neural
	 * Networks by Haykin.
	 * 
	 * @param samples
	 *            each member corresponds to a collection of desired/actual
	 *            outputs for a network
	 * @return
	 */
	public static double averageSquaredErrorEnergy(ArrayList<ArrayList<Pair<Double, Double>>> samples) {
		double[] totalErrors = new double[samples.size()];
		for (int i = 0; i < totalErrors.length; i++) {
			ArrayList<Pair<Double, Double>> pairs = samples.get(i);
			totalErrors[i] = instantaneousTotalErrorEnergy(pairs);
			assert!Double.isNaN(totalErrors[i]) : "totalErrors[" + i + "] is NaN!";
		}
		return average(totalErrors);
	}

    /**
     * For a given number of experimental runs (N), return the critical t-value
     * that would need to be surpassed in a two-tailed t-test for p=0.05
     * 
     * TODO: Make p be an additional parameter
     * TODO: Generalize for more values of N
     * 
     * @param runs N, the number of runs, which is the sample size
     * @return critical t-value for two-tailed t-test
     */
    public static double tValue(int runs) {
                if(runs == 1) throw new IllegalArgumentException("A t-value with 0 degrees of freedom cannot be computed");
                else if(runs == 2) return 12.706;//df= 1, p=0.05, two-tailed
                else if(runs == 3) return 4.303;// df=2, p=0.05, two-tailed
                else if(runs == 4) return 3.182;// df=3, p=0.05, two-tailed
                else if(runs == 5) return 2.776;// df=4, p=0.05, two-tailed
                else if(runs == 6) return 2.571;// df=5, p=0.05, two-tailed
                else if(runs == 7) return 2.447;// df=6, p=0.05, two-tailed
                else if(runs == 8) return 2.365;// df=7, p=0.05, two-tailed
                else if(runs == 9) return 2.306;// df=8, p=0.05, two-tailed
                else if(runs == 10) return 2.262;// df=9, p=0.05, two-tailed
                else if(runs == 11) return 2.228;// df=10, p=0.05, two-tailed
                else if(runs == 12) return 2.201;// df=11, p=0.05, two-tailed
                else if(runs == 13) return 2.179;// df=12, p=0.05, two-tailed
                else if(runs == 14) return 2.160;// df=13, p=0.05, two-tailed
                else if(runs == 15) return 2.145;// df=14, p=0.05, two-tailed
                else if(runs == 16) return 2.131;// df=15, p=0.05, two-tailed
                else if(runs == 17) return 2.120;// df=16, p=0.05, two-tailed
                else if(runs == 18) return 2.110;// df=17, p=0.05, two-tailed
                else if(runs == 19) return 2.101;// df=18, p=0.05, two-tailed
                else if(runs == 20) return 2.093;// df=19, p=0.05, two-tailed
                else if(runs == 21) return 2.086;// df=20, p=0.05, two-tailed
                else if(runs == 22) return 2.080;// df=21, p=0.05, two-tailed
                else if(runs == 23) return 2.074;// df=22, p=0.05, two-tailed
                else if(runs == 24) return 2.069;// df=23, p=0.05, two-tailed
                else if(runs == 25) return 2.064;// df=24, p=0.05, two-tailed
                else if(runs == 26) return 2.060;// df=25, p=0.05, two-tailed
                else if(runs == 27) return 2.056;// df=26, p=0.05, two-tailed
                else if(runs == 28) return 2.052;// df=27, p=0.05, two-tailed
                else if(runs == 29) return 2.048;// df=28, p=0.05, two-tailed
                else if(runs == 30) return 2.045;// df=29, p=0.05, two-tailed
                else throw new UnsupportedOperationException("Still need to expand tValue method to support different values of N: " + runs);
    }
}
