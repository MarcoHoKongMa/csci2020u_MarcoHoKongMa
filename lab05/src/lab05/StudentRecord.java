package lab05;

public class StudentRecord {
    private String studentId;
    private float assignments;
    private float midterm;
    private float finalExam;
    private float finalMark;
    private char letterGrade;

    public StudentRecord(String studentId, float assignments, float midterm, float finalExam){
        this.studentId = studentId;
        this.assignments = assignments;
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.finalMark = midterm * .3f + assignments * .2f + finalExam * .5f;

        if(this.finalMark > 79){
            this.letterGrade = 'A';
        }
        else if (this.finalMark > 69){
            this.letterGrade = 'B';
        }
        else if (this.finalMark > 59){
            this.letterGrade = 'C';
        }
        else if (this.finalMark > 49){
            this.letterGrade = 'D';
        }
        else{
            this.letterGrade = 'F';
        }
    }

    public String getStudentId() {
        return studentId;
    }

    public float getAssignments() {
        return assignments;
    }

    public float getMidterm() {
        return midterm;
    }

    public float getFinalExam() {
        return finalExam;
    }

    public float getFinalMark() {
        return finalMark;
    }

    public char getLetterGrade() {
        return letterGrade;
    }
}
