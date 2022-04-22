package dev.colin.utilities;

import java.util.HashMap;
import java.util.Map;

public class GlobalVariables {
    public static final Map <Integer,String> statusCode;

    private GlobalVariables() {}

    static {
        statusCode = new HashMap<>();
        statusCode.put(1, "Error updating database");
        statusCode.put(2, "No employee found");
        statusCode.put(3, "No expense found");
        statusCode.put(4, "Amount must be greater then 0");
        statusCode.put(5, "Can not alter expense that is approved or denied");
        statusCode.put(6, "invalid input");
        statusCode.put(7, "input mismatch");
    }

}
