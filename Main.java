package org.example;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            Map<String, Employee> employees = loadEmployeesFromFile("employees.xlsx");

            TaskManager taskManager = new TaskManager(employees);
            taskManager.assignTasks();

            saveTasksToFile(taskManager.getTasks(), "tasks_completed.xlsx");

            taskManager.printStatistics();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Employee> loadEmployeesFromFile(String filePath) throws IOException {
        Map<String, Employee> employees = new HashMap<>();
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String name = row.getCell(0).getStringCellValue();
            Cell maxWorkHoursCell = row.getCell(1);
            int maxWorkHours;
            if (maxWorkHoursCell.getCellType() == CellType.NUMERIC) {
                maxWorkHours = (int) maxWorkHoursCell.getNumericCellValue();
            } else if (maxWorkHoursCell.getCellType() == CellType.STRING) {
                maxWorkHours = Integer.parseInt(maxWorkHoursCell.getStringCellValue());
            } else {
                throw new IllegalArgumentException("Invalid cell type for max work hours");
            }
            Employee employee = new Employee(name, maxWorkHours);
            employees.put(name, employee);
        }

        workbook.close();
        file.close();
        return employees;
    }

    private static void saveTasksToFile(Map<String, Task> tasks, String filePath) throws IOException {
        Workbook workbook = WorkbookFactory.create(true);
        Sheet sheet = workbook.createSheet("Tasks");

        int rowNum = 0;
        for (Map.Entry<String, Task> entry : tasks.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey()); // Employee name
            row.createCell(1).setCellValue(entry.getValue().getHoursWorked()); // Hours worked
        }

        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        workbook.close();
        fileOut.close();
    }
}
