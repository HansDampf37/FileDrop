package homework.task1_5;

public class Student {
    private String facultyNumber;
    private String firstName;
    private String familyName;
    private float pointAverage;

    public Student(String facultyNumber, String firstName, String familyName, float pointAverage) {
        this.facultyNumber = facultyNumber;
        this.firstName = firstName;
        this.familyName = familyName;
        this.pointAverage = pointAverage;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public float getPointAverage() {
        return pointAverage;
    }

    public void setPointAverage(float pointAverage) {
        this.pointAverage = pointAverage;
    }

    @Override
    public String toString() {
        return firstName + " " + familyName + ", " + facultyNumber + ", " + pointAverage;
    }

    public static boolean validateFacultyNumber(String facultyNumber) {
        if (facultyNumber.matches("[0-9]+") && (facultyNumber.length() == 5 || facultyNumber.length() == 6)) return true;
        else if (facultyNumber.toLowerCase().matches("[0-9a-z]+") && facultyNumber.length() == 10) {
            int firstCharInt;
            if (Character.isDigit(facultyNumber.charAt(0))) firstCharInt = Integer.parseInt(facultyNumber.charAt(0) + "");
            else return false;

            int sum = 0;
            for (int i = 1; i < facultyNumber.length(); i++) {
                char c = facultyNumber.toLowerCase().charAt(i);
                if (Character.isDigit(c)) {
                    sum += Integer.parseInt(facultyNumber.charAt(i) + "");
                } else {
                    sum += c - 'a' + 1;
                }
            }
            return sum % 11 == firstCharInt;
        }
        return false;
    }
}
