package retry;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * RetryListener is a TestNG annotation transformer. It automatically applies
 * the {@link RetryAnalyzer} retry logic to every test method. This eliminates
 * the need to manually specify retryAnalyzer on each @Test annotation.
 *
 * Usage: Register this class as a listener in your testng.xml or via @Listeners
 * annotation.
 */
public class RetryListener implements IAnnotationTransformer {

	/**
	 * Intercepts test method annotations and assigns RetryAnalyzer to enable
	 * retries.
	 *
	 * @param annotation  The ITestAnnotation instance to modify
	 * @param testClass   The test class being executed
	 * @param constructor Constructor reference (can be null)
	 * @param method      The method being executed
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor constructor, Method method) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
