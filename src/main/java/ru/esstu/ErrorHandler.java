package ru.esstu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorHandler {
    private static final String LOGS_FOLDER = "logs";
    private static final String LOG_FILE_NAME = "ErrorLogger.log";
    private static final String ERROR_SEPARATOR = "—".repeat(200);

    public static void handleException(Exception e, String errorMessage) {
        String logFilePath = LOGS_FOLDER + File.separator + LOG_FILE_NAME;

        try (FileWriter logWriter = new FileWriter(logFilePath, true)) {
            logWriter.write(ERROR_SEPARATOR + "\n");
            logWriter.write("ВОЗНИКЛА ОШИБКА\n");
            logWriter.write("Детали:\n");
            logWriter.write("Временная метка: " + getCurrentTimestamp() + "\n");
            logWriter.write("Сигнатура ошибки: " + e.getClass().getName() + "\n");
            logWriter.write("Сообщение исключения: " + e.getMessage() + "\n");
            logWriter.write("Сообщение об ошибке: " + errorMessage + "\n");
            logWriter.write("Причина возникновения ошибки: метод " + getCallingMethodName() + " класса " + getCallingClassName() + "\n\n");
            logWriter.write("Стек вызовов:\n");
            e.printStackTrace(new java.io.PrintWriter(logWriter));
            logWriter.write("\n");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd числа MM месяца в HH:mm");
        return dateFormat.format(new Date());
    }

    private static String getCallingClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return (stackTrace.length >= 4) ? stackTrace[3].getClassName() : "UnknownClass";
    }

    private static String getCallingMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return (stackTrace.length >= 4) ? stackTrace[3].getMethodName() : "unknownMethod";
    }
}
