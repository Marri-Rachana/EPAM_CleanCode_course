package com.epam.engx.cleancode.naming.task6;

public class Formatter {

    private static final String TABLE_CORNER = "+";
    private static final String CONTENT_ENCLOSER = "|";
    private static final String BORDER_DECORATOR = "-";
    private static final String KEY_VALUE_SEPARATOR = " _ ";
    private static final String NEW_LINE = "\n";


    public String formatContent(String key, String value) {
        String content = key + KEY_VALUE_SEPARATOR + value;
        String borderLine = formingBorderLine(BORDER_DECORATOR, content.length());
        return TABLE_CORNER +  borderLine + TABLE_CORNER + NEW_LINE
                + CONTENT_ENCLOSER + content + CONTENT_ENCLOSER + NEW_LINE
                + TABLE_CORNER + borderLine + TABLE_CORNER + NEW_LINE;
    }

    private String formingBorderLine(String borderDecorator, int times) {
        String resultString = "";
        for (int i = 0; i < times; i++)
            resultString += borderDecorator;
        return resultString;
    }
}
