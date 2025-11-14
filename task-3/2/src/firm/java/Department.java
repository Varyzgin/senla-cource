package firm.java;

import java.util.ArrayList;
import java.util.List;

public class Department {
    public static List<Employee> findDevs() {
        List<Employee> guys = new ArrayList<>();
        guys.add(new Frontender("John", Interests.NATURE, 250000.00F));
        guys.add(new Backender("Jack", Interests.TECH, 300000.00F));
        guys.add(new DataScientist("Jane", Interests.PEOPLE, 400000.00F));
        guys.add(new Backender("Jacob", Interests.THEORY, 350000.00F));
        guys.add(new Frontender("Jerry", Interests.PEOPLE, 320000.00F));
        return guys;
    }

    public static List<Employee> makeProject(List<Employee> guys) {
        List<Employee> project = new ArrayList<>();
        for (Employee guy : guys) {
            if (guy.interests.equals(Interests.PEOPLE)) {
                project.add(guy);
            }
        }
        return project;
    }

    public static void main(String[] args) {
        List<Employee> guys = findDevs();
        List<Employee> project = makeProject(guys);

        float sum_of_salaries = 0;
        System.out.println("Состав сотрудников:");
        for (Employee guy : project) {
            System.out.println(guy.name);
            sum_of_salaries += guy.salary;
        }
        System.out.println("----------------------------" );
        System.out.println(sum_of_salaries + " - Расход на з/п в месяц" );
    }
}