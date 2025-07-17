package retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Custom TestNG retry analyzer for re-executing failed test cases. Retries a
 * test up to a specified maximum number of times (default: 2) if it fails.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

	/** Keeps track of how many times a test has been retried */
	private int retryCount = 0;

	/** Maximum number of retry attempts */
	private static final int maxRetryCount = 2; // Customize retry limit

	/**
	 * This method is invoked by TestNG after a test method fails. If it returns
	 * true, TestNG will retry the test; if false, no retry will occur.
	 *
	 * @param result The result of the test execution
	 * @return boolean true if test should be retried, false otherwise
	 */
	@Override
	public boolean retry(ITestResult result) {
		if (!result.isSuccess() && retryCount < maxRetryCount) {
			retryCount++;
			System.out.println("🔁 Retrying failed scenario: " + result.getName() + " | Attempt #" + retryCount);
			return true;
		}
		return false;
	}
}
