package org.conjode.java;

import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;

/**
 * Created by smanvi on 9/5/17.
 */
public class TestFunction implements Function{
    @Override
    public boolean hasResult() {
        return true;
    }

    @Override
    public void execute(FunctionContext functionContext) {
        System.out.println("$$$$ Executing Function TestFunctionNoResult");
        System.out.println("$$$$ Function args for TestFunctionNoResult : "+functionContext.getArguments());

        functionContext.getResultSender().sendResult("Hello");
        functionContext.getResultSender().sendResult(" world.");
        functionContext.getResultSender().sendResult(" Have a good day!");

        functionContext.getResultSender().lastResult("true");
    }

    @Override
    public String getId() {
        return "TestFunction";
    }
}
