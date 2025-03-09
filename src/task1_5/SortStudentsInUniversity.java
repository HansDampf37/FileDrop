package task1_5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortStudentsInUniversity {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        boolean sortByName = true;

        students.add(new Student("0MI3400001", "Simeon", "Nikolov", 3.0f));
        students.add(new Student("12345", "Tobias", "Krick", 1.0f));
        students.add(new Student("12346", "Julian", "Brand", 2.0f));
        students.add(new Student("12347", "Lady", "Gaga", 4.0f));

        if (sortByName) {
            students.sort((Comparator.comparing(s -> (s.getFamilyName() + s.getFirstName()))));
        } else {
            students.sort(((s1, s2) -> Float.compare(s1.getPointAverage(), s2.getPointAverage())));
        }
        System.out.println(students);
    }
}
