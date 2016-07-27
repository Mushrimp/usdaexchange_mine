package com.mahoneydev.usdafmexchange;

/**
 * Created by mahoneydev on 7/14/2016.
 */
public class SpinnerElement {
    private String name;
    private String value;

    public SpinnerElement(String cName, String cValue)
    {
        name = cName;
        value = cValue;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public String toString()
    {
        return name;
    }
}
