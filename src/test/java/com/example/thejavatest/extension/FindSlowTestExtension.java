package com.example.thejavatest.extension;

import com.example.thejavatest.customtag.SlowTest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private long THRESHOLD;
    public FindSlowTestExtension(long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        long startTime = store.remove("START_TIME", long.class);
        Method requiredTestMethod = context.getRequiredTestMethod();
        SlowTest slowTest = requiredTestMethod.getAnnotation(SlowTest.class);
        if(System.currentTimeMillis() - startTime > THRESHOLD && slowTest == null){
            System.out.printf("Please consider mark method [%s] with @SlowTest.\n", requiredTestMethod.getName());
        }
    }
    private static ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
        return store;
    }
}
