package web.Student;

public class StudentGrade {
    private String courseName;
    private double grade;
    private String instructorName;

    public StudentGrade(String courseName, double grade, String instructorName) {
        this.courseName = courseName;
        this.grade = grade;
        this.instructorName = instructorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getGrade() {
        return grade;
    }

    public String getInstructorName() {
        return instructorName;
    }
}
