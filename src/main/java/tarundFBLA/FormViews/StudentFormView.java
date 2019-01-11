package tarundFBLA.FormViews;

import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import tarundFBLA.Forms.StudentForm;
import tarundFBLA.Models.Student;
import tarundFBLA.SQL;

import java.util.ArrayList;
import java.util.List;

public class StudentFormView extends StudentForm{
    private static SQL sql = new SQL();
    private Binder<Student> binder = new Binder<>(Student.class);
    private Grid<Student> grid = new Grid<>();
    private boolean addPressed = false;
    public StudentFormView(){
        binder.forField (this.firstName)
                .withNullRepresentation("")
                .bind(Student::getFirstName,Student::setFirstName);
        binder.forField (this.lastName)
                .withNullRepresentation("")
                .bind(Student::getLastName,Student::setLastName);
        binder.forField (this.grade)
                .withNullRepresentation("")
                .bind(Student::getGrade,Student::setGrade);
        binder.forField (this.classRoom)
                .withNullRepresentation("")
                .bind(Student::getClassRoom,Student::setClassRoom);
        binder.forField (this.studentId)
                .withNullRepresentation("")
                .bind(Student::getID,Student::setID);
        binder.bindInstanceFields(this);

        save.addClickListener((Button.ClickListener) clickListener ->{
            if (!addPressed){
                sql.refresh(this);
                refresh();
            }
            else{
                if(!firstName.getValue().equalsIgnoreCase("") && !lastName.getValue().equalsIgnoreCase("")){
                    Student student = new Student(firstName.getValue(),lastName.getValue(),grade.getValue(),classRoom.getValue(), studentId.getValue());
                    sql.addStudent(student);
                    refresh();
                    addPressed = false;
                    firstName.setReadOnly(true);
                    lastName.setReadOnly(true);
                }
                else nSave.setVisible(true);
            }
            new Notification("Save Success. ","",Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
        });
        delete.addClickListener((Button.ClickListener) clickListener ->{
            sql.delete(studentId.getValue().replaceAll(",",""),SQL.Table.STUDENT);
            sql.refresh(this);
            refresh();
            nSave.setVisible(false);
            cancel.setVisible(false);
            delete.setVisible(false);
            add.setVisible(false);
            new Notification("Delete Successful. ","",
                    Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
        });
        cancel.addClickListener((Button.ClickListener) clickListener -> {
            grid.deselectAll();
            nSave.setVisible(false);
            cancel.setVisible(false);
            delete.setVisible(false);
            add.setVisible(false);
            new Notification("Operation Canceled. ","",
                    Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
        });
        add.addClickListener((Button.ClickListener) clickListener -> {
            studentId.setValue(String.valueOf(genID()));
            firstName.setValue("");
            addPressed = true;
            lastName.setReadOnly(false);
            lastName.setValue("");
            grade.setReadOnly(false);
            grade.setValue("0");
            classRoom.setReadOnly(false);
            classRoom.setValue("");
            nSave.setVisible(false);
            new Notification("Student Added. ","",
                    Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
        });
    }

    public void setSQL(SQL sql){StudentFormView.sql = sql;}
    public void setStudent(Student value){binder.setBean(value);}
    public void setGrid(Grid<Student> studentGrid){this.grid = studentGrid;}
    public static Student getStudentById(int id){
        int idOfStudent = 0;
        int index = 0;
        //String status = "";
        for(Integer integer: sql.getIntegerList(SQL.Table.STUDENT,"id")){
            if (integer == id) break;
            index++;
        }
        return new Student(
                sql.getList(SQL.Table.STUDENT, "firstName").get(index).toString(),
                sql.getList(SQL.Table.STUDENT, "lastName").get(index).toString(),
                sql.getList(SQL.Table.STUDENT, "grade").get(index).toString(),
                sql.getList(SQL.Table.STUDENT, "classRoom").get(index).toString(),
                sql.getList(SQL.Table.STUDENT, "id").get(index).toString());
    }


    private int genID (){
        return sql.genID(SQL.Table.STUDENT) + 2;
    }
    public void refresh() {
        List<Student> students = new ArrayList<>();
        int loopIteration = 0;

        while (loopIteration < sql.getList(SQL.Table.STUDENT, "id").size()) {
            students.add(new Student(sql.getList(SQL.Table.STUDENT, "firstName").get(loopIteration).toString(),
                    sql.getList(SQL.Table.STUDENT, "lastName").get(loopIteration).toString(),
                    sql.getList(SQL.Table.STUDENT, "grade").get(loopIteration).toString(),
                    sql.getList(SQL.Table.STUDENT, "classRoom").get(loopIteration).toString(),
                    sql.getList(SQL.Table.STUDENT, "id").get(loopIteration).toString()));
            loopIteration++;
        }
        grid.setItems(students);
    }




}
