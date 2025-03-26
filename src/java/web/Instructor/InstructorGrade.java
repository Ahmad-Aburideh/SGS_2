package web.Instructor;

public class InstructorGrade {
    private int enrollmentId;
    private String courseName;
    private String studentName;
    private String section;
    private double grade;

    public InstructorGrade(int enrollmentId, String courseName, String studentName, String section, double grade) {
        this.enrollmentId = enrollmentId;
        this.courseName = courseName;
        this.studentName = studentName;
        this.section = section;
        this.grade = grade;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSection() {
        return section;
    }

    public double getGrade() {
        return grade;
    }
}
