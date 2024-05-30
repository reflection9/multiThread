package org.example;

public class Employee {
    private String name;
    private int maxWorkHours;
    private int hoursWorked;

    public Employee(String name, int maxWorkHours) {
        this.name = name;
        this.maxWorkHours = maxWorkHours;
    }

    public String getName() {
        return name;
    }

    public int getMaxWorkHours() {
        return maxWorkHours;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void setMaxWorkHours(int maxWorkHours) {
        this.maxWorkHours = maxWorkHours;
    }
}