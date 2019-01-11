package tarundFBLA.Models;

public class Student {
    private int StudentID, Grade, RedemptionCode;
    private String FirstName, LastName, ClassRoom;

    public Student(String firstName, String lastName, int grade, String classRoom, int id){
        FirstName = firstName;
        LastName = lastName;
        Grade = grade;
        ClassRoom = classRoom;
        StudentID = id;
    }

    public Student(String firstName, String lastName, String grade, String classRoom, String id){
        FirstName = firstName;
        LastName = lastName;
        Grade = Integer.parseInt(grade);
        ClassRoom = classRoom;
        StudentID = Integer.parseInt(id);
    }

    public Student(String firstName, String lastName, String grade, String classRoom, String id, String redemptionCode){
        FirstName = firstName;
        LastName= lastName;
        Grade = Integer.parseInt(grade);
        ClassRoom = classRoom;
        StudentID = Integer.parseInt(id);
        RedemptionCode = Integer.parseInt(redemptionCode);
    }

    public String getID(){return String.valueOf(StudentID);}
    // public int getId(){return StudentID;}
    public String getFirstName(){return FirstName;}
    public String getLastName(){return LastName;}
    public String getGrade(){return String.valueOf(Grade);}
    public String getClassRoom(){return ClassRoom;}
    public String getRedemptioncode(){return String.valueOf(RedemptionCode);}
    public boolean getStatus(){
        if(RedemptionCode != (int)RedemptionCode){
            return false;
        }
        else return true;

    }
    public void setID(String studentID){this.StudentID = Integer.parseInt(studentID);}
    public void setFirstName(String FirstName){this.FirstName = FirstName;}
    public void setLastName(String LastName){this.LastName = LastName;}
    public void setGrade(String Grade){this.Grade = Integer.parseInt(Grade);}
    public void setClassRoom(String classRoom){this.ClassRoom = classRoom;}
    public void setRedemptionCode(String RedemptionCode){this.RedemptionCode = Integer.parseInt(RedemptionCode);}
    //public void setIssueStatus(boolean status){this.issueStatus =  status;}

}
