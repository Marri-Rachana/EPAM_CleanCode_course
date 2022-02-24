package com.epam.engx.cleancode.finaltask.task1;

import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.Command;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DataSet;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.View;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class TablePrinter implements Command {

    private enum LevelBoundary {
        UPPER("╔", "╦", "╗"), MIDDLE("╠", "╬", "╣"), BOTTOM("╚", "╩", "╝");

        private final String leftBoundary;
        private final String middleBoundary;
        private final String rightBoundary;

        LevelBoundary(String leftBoundary, String middleBoundary, String rightBoundary) {
            this.leftBoundary = leftBoundary;
            this.middleBoundary = middleBoundary;
            this.rightBoundary = rightBoundary;
        }
    }

    private static final int TABLE_NAME_INDEX = 1;
    private static final int VALID_PARAMETER_COUNT = 2;
    private static final int EVEN_NUMBER_OF_COLUMNS = 2;
    private static final int ODD_NUMBER_OF_COLUMNS = 3;
    private static final String COMMAND_PREFIX = "print ";
    private static final String UNIT_BORDER_LINE = "═";
    private static final String NEW_LINE = "\n";
    private static final String WHITE_SPACE = " ";
    private static final String COLUMN_SEPERATOR = "║";
    private static final String EMPTY_TABLE_TEMPLATE = "╔%s╗\n%s\n╚%s╝\n";
    private static final String EMPTY_TABLE_TEXT = "║ Table '%s' is empty or does not exist ║";

    private final View view;
    private final DatabaseManager manager;

    public TablePrinter(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith(COMMAND_PREFIX);
    }

    @Override
    public void process(String command) {
        String[] commandParameters = command.split(WHITE_SPACE);
        validateCommand(commandParameters);
        String tableName = getTableName(commandParameters);
        List<DataSet> dataSets = manager.getTableData(tableName);
        view.write(getTableString(tableName, dataSets));
    }

    private void validateCommand(String[] commandParameters) {
        int numberOfParameters = commandParameters.length;
        if (numberOfParameters != VALID_PARAMETER_COUNT) {
            throw new IllegalArgumentException(
                    "incorrect number of parameters. Expected 1, but is " + (numberOfParameters - 1));
        }
    }

    private String getTableName(String[] commandParameters) {
        return commandParameters[TABLE_NAME_INDEX];
    }

    private String getTableString(String tableName, List<DataSet> dataSets) {
        int maxColumnSize = getMaxColumnSize(dataSets);
        return maxColumnSize == 0 ? getEmptyTable(tableName)
                : getHeaderOfTheTable(dataSets) + getStringTableData(dataSets);
    }

    private int getMaxColumnSize(List<DataSet> dataSets) {
        if (dataSets.isEmpty()) {
            return 0;
        }
        List<String> columnNames = dataSets.get(0).getColumnNames();
        int maxColumnNameLength = getMaxColumnNameLength(columnNames);
        int maxValueLength = getMaxValueLength(dataSets);
        return Math.max(maxColumnNameLength, maxValueLength);
    }

    private int getMaxColumnNameLength(List<String> values) {
        int maxLength = 0;
        for (String value : values) {
            maxLength = Math.max(maxLength, value.length());
        }
        return maxLength;
    }

    private int getMaxValueLength(List<DataSet> dataSets) {
        int maxLength = 0;
        for (DataSet dataSet : dataSets) {
            List<String> values = convertIntoStrings(dataSet.getValues());
            maxLength = Math.max(maxLength, getMaxColumnNameLength(values));
        }
        return maxLength;
    }

    private List<String> convertIntoStrings(List<Object> values) {
        List<String> stringValues = new ArrayList<>();
        for (Object value : values) {
            stringValues.add(String.valueOf(value));
        }
        return stringValues;
    }

    private String getEmptyTable(String tableName) {
        String textEmptyTable = String.format(EMPTY_TABLE_TEXT, tableName);
        String borderLine = repeatString(UNIT_BORDER_LINE,textEmptyTable.length() - 2);
        return String.format(EMPTY_TABLE_TEMPLATE, borderLine, textEmptyTable, borderLine);
    }

    private String getHeaderOfTheTable(List<DataSet> dataSets) {
        int columnCount = getColumnCount(dataSets);
        int maxColumnSize = getMaxColumnSize(dataSets);
        int columnSize = getColumnWidth(maxColumnSize);
        maxColumnSize = columnSize;

        StringBuilder header = new StringBuilder();
        List<String> columnNames = dataSets.get(0).getColumnNames();
        return header.append(getTopBorder(maxColumnSize, columnCount)).append(getHeaderData(columnNames, maxColumnSize, columnCount)).append(getHeaderBottom(dataSets, columnCount, maxColumnSize)).toString();
    }

    private int getColumnCount(List<DataSet> dataSets) {
        return dataSets.isEmpty() ? 0 : dataSets.get(0).getColumnNames().size();
    }

    private int getColumnWidth(int maxColumnSize) {
        return maxColumnSize + (isEven(maxColumnSize) ? EVEN_NUMBER_OF_COLUMNS : ODD_NUMBER_OF_COLUMNS);
    }

    private boolean isEven(int length) {
        return length % 2 == 0;
    }

    private String getTopBorder(int maxColumnSize, int columnCount) {
        return getBorder(columnCount, maxColumnSize, LevelBoundary.UPPER);
    }

    private String getBorder(int columnCount, int maxColumnSize, LevelBoundary levelBoundary) {
        StringBuilder border = new StringBuilder();
        String borderLine = repeatString(UNIT_BORDER_LINE,maxColumnSize);
        return border.append(levelBoundary.leftBoundary)
                .append(repeatString(borderLine + levelBoundary.middleBoundary,columnCount - 1)).append(borderLine).append(levelBoundary.rightBoundary).append(NEW_LINE).toString();
    }

    private String getHeaderData(List<String> columnNames, int maxColumnSize, int columnCount) {
        return getRow(columnNames, maxColumnSize, columnCount);
    }

    private String getRow(List<String> columnNames, int maxColumnSize, int columnCount) {
        StringBuilder row = new StringBuilder();
        for (int column = 0; column < columnCount; column++) {
            row.append(COLUMN_SEPERATOR);
            String columnName = columnNames.get(column);
            row.append(getRowElementData(columnName, maxColumnSize));
        }
        return row.append(COLUMN_SEPERATOR).append(NEW_LINE).toString();
    }

    private String getRowElementData(String columnName, int maxColumnSize) {
        StringBuilder rowElement = new StringBuilder();
        int columnNamesLength = columnName.length();
        int totalWhiteSpaceCount = (maxColumnSize - columnNamesLength) / 2;

        rowElement.append(repeatString(WHITE_SPACE,totalWhiteSpaceCount));
        rowElement.append(columnName);
        rowElement.append(repeatString(WHITE_SPACE,isEven(columnNamesLength) ? totalWhiteSpaceCount : totalWhiteSpaceCount + 1));
        return rowElement.toString();
    }

    private String getHeaderBottom(List<DataSet> dataSets, int columnCount, int maxColumnSize) {
        return dataSets.isEmpty() ? getBottomBorder(columnCount, maxColumnSize)
                : getMiddleBorder(columnCount, maxColumnSize);
    }

    private String getBottomBorder(int columnCount, int maxColumnSize) {
        return getBorder(columnCount, maxColumnSize, LevelBoundary.BOTTOM);
    }

    private String getMiddleBorder(int columnCount, int maxColumnSize) {
        return getBorder(columnCount, maxColumnSize, LevelBoundary.MIDDLE);
    }

    private String getStringTableData(List<DataSet> dataSets) {
        int rowsCount = dataSets.size();
        int maxColumnSize = getMaxColumnSize(dataSets);
        int rowSize = getColumnWidth(maxColumnSize);
        maxColumnSize = rowSize;
        int columnCount = getColumnCount(dataSets);
        StringBuilder table = new StringBuilder();

        for (int row = 0; row < rowsCount - 1; row++) {
            List<String> values = convertIntoStrings(dataSets.get(row).getValues());
            table.append(getRow(values, maxColumnSize, columnCount)).append(getMiddleBorder(columnCount, maxColumnSize));
        }
        List<String> lastRowValues = convertIntoStrings(dataSets.get(rowsCount - 1).getValues());
        return table.append(getRow(lastRowValues, maxColumnSize, columnCount)).append(getBottomBorder(columnCount, maxColumnSize)).toString();
    }

    private String repeatString(String stringToBeRepeated,int numberOfTimes)
    {
        String repeatedString = new String(new char[numberOfTimes]).replace("\0", stringToBeRepeated);
        return repeatedString;
    }
}
