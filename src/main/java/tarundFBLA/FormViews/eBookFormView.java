package tarundFBLA.FormViews;

import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import tarundFBLA.Forms.eBookForm;
import tarundFBLA.Models.eBook;
import tarundFBLA.SQL;

import java.util.ArrayList;
import java.util.List;

public class eBookFormView extends eBookForm {
    private Binder<eBook> binder = new Binder<>(eBook.class);
    private boolean addClicked = false;
    private boolean saveClicked = false;
    public StudentFormView studentFormView;
    private static SQL sql;
    //  private SQL.E
    private Grid<eBook> grid;
    public eBookFormView(){
        cancel.setComponentError(null);
        id.setVisible(false);
        binder.forField(this.id)
                .withNullRepresentation("")
                .bind(eBook::getID,eBook::setID);
        binder.forField(this.author)
                .withNullRepresentation("")
                .bind(eBook::getAuthor,eBook::setAuthor);
        binder.forField(this.bookName)
                .withNullRepresentation("")
                .bind(eBook::getBookName,eBook::setBookName);
        binder.forField(this.redemptionCode)
                .withNullRepresentation("")
                .bind(eBook::getRedemptionCode,eBook::setRedemptionCode);
        binder.bindInstanceFields(this);

        save.addClickListener((Button.ClickListener) click ->{
            if(saveClicked){
                if(Integer.valueOf(getStudentData(Integer.parseInt(this.studentId.getValue()))[2])
                        >= Integer.valueOf(getStudentData(Integer.parseInt(this.studentId.getValue()))[3])) {
                    new Notification("This student has a redemption code.","",
                            Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
                }
                else {
                    int id = Integer.parseInt(studentId.getValue().replaceAll(",", ""));
                    studentId.setValue("");
                    sql.editStudent(SQL.Table.STUDENT, "redemptionCode", String.valueOf(Integer.parseInt(getStudentData(id)[2]) + 1), id);
                    sql.edit(SQL.Table.EBOOK, "id", true, Integer.parseInt(this.id.getValue().replaceAll(",", "")));
                    if (!StudentFormView.getStudentById(id).getStatus())
                        new Notification("This student does not has a redemption code.","",
                                Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
                    else new Notification("This student has a redemption code.","",
                            Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
                    sql.commit();
                    refresh();
                    studentFormView.refresh();
                    studentId.setVisible(false);
                }
            }
        });
        add.addClickListener((Button.ClickListener) click ->{
            if(addClicked) new Notification("Click Save. ", "",
                    Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
            addClicked = true;
            id.setValue(String.valueOf(genID()));
            id.setReadOnly(true);
            studentId.setValue("");
            studentId.setVisible(false);
        });
        cancel.addClickListener((Button.ClickListener) clickListener -> {
            add.setVisible(true);
            addClicked = false;
            // checkOutClicked = false;
            cancel.setVisible(false);
            studentId.setValue("");
            studentId.setVisible(false);
            studentId.setCaption("Student id");
            studentId.setVisible(true);
            grid.deselectAll();
            new Notification("Operation Canceled. ", "",
                    Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
        });
        delete.addClickListener((Button.ClickListener) click ->{
            if (addClicked) new Notification("Click Save. ", "",
                    Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
            else {
                add.setVisible(true);
                sql.delete(this.id.getValue().replace(",", ""), SQL.Table.EBOOK);
                refresh();
                new Notification("Delete Successful. ", "",
                        Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
            }
        });


    }
    public void setEBook(eBook value){binder.setBean(value);}
    public void setGrid(Grid<eBook> grid){this.grid = grid;}
    public void setSql(SQL sql){this.sql = sql;}
    public static String[] getStudentData(int id) {
        String[] returnString = new String[]{"","","","","",""};
        returnString[0] = String.valueOf(id);
        int index = 0;
        for (Integer integer: sql.getIntegerList(SQL.Table.STUDENT, "id")) {
            if (integer == id){
                returnString[1] = String.valueOf(sql.getList(SQL.Table.STUDENT, "firstName").get(index));
                returnString[2] = String.valueOf(sql.getList(SQL.Table.STUDENT, "lastName").get(index));
                returnString[3] = String.valueOf(sql.getList(SQL.Table.STUDENT, "grade").get(index));
                returnString[4] = String.valueOf(sql.getList(SQL.Table.STUDENT, "classRoom").get(index));
                returnString[5] = String.valueOf(sql.getList(SQL.Table.STUDENT, "id").get(index));
            }
            index++;
        }
        for (String s: returnString) {
        }
        return returnString;
    }

    public static String[] getBookData(int id) {
        String[] returnString = new String[]{"","","",""};
        returnString[0] = String.valueOf(id);
        int index = 0;
        for (Integer integer: sql.getIntegerList(SQL.Table.EBOOK, "id")) {
            if (integer == id){
                returnString[1] = String.valueOf(sql.getList(SQL.Table.EBOOK  , "redemptionCode").get(index));
                returnString[2] = String.valueOf(sql.getList(SQL.Table.EBOOK, "bookName").get(index));
                returnString[3] = String.valueOf(sql.getList(SQL.Table.EBOOK, "author").get(index));
            }
            index++;
        }
        for (String s: returnString) {
        }
        return returnString;
    }

    private int genID (){
        return sql.genID(SQL.Table.EBOOK) + 2;
    }
    public void refresh() {
        List<eBook> books = new ArrayList<>();
        int loopIteration = 0;
        String checkedOut = "";
        while (loopIteration < sql.getList(SQL.Table.EBOOK, "id").size()) {
            if (sql.getList(SQL.Table.EBOOK, "checkOut").get(loopIteration).toString().equals("1"))
                checkedOut = "CHECKED OUT";
            else checkedOut = "AVAILABLE";
            books.add(new eBook(
                    Integer.parseInt(sql.getList(SQL.Table.EBOOK, "id").get(loopIteration).toString()),
                    sql.getList(SQL.Table.EBOOK, "bookName").get(loopIteration).toString(),
                    sql.getList(SQL.Table.EBOOK, "author").get(loopIteration).toString(),
                    sql.getList(SQL.Table.EBOOK, "redemptionCode").get(loopIteration).toString(),
                    checkedOut));
            loopIteration++;
        }
        grid.setItems(books);
        books = null;
    }

    public void setStudentFormView(StudentFormView studentFormView) {
        this.studentFormView = studentFormView;
    }


}

