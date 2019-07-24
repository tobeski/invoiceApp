package com.eltobeski.invoicingapp.model;

public class StringResult {
    private String resultString;
    private String param2;

    public StringResult() {
    }

    public StringResult(String resultString) {
        this.resultString = resultString;
    }

   public StringResult(String resultString, String param) {
        this.resultString = resultString;
        this.param2 = param;
    }

    public String getResultString() {
        return resultString;

    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
}
