package tarundFBLA;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import tarundFBLA.FormViews.HelpFormView;
import tarundFBLA.FormViews.SignInFormView;
import tarundFBLA.FormViews.StudentFormView;
import tarundFBLA.FormViews.eBookFormView;
import tarundFBLA.Models.Student;
import tarundFBLA.Models.eBook;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */

/*
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
*/




@Theme("mytheme")
public class MyUI extends UI {
    private eBookFormView eBookEditor = new eBookFormView();
    private StudentFormView studentEditor = new StudentFormView();
    private HelpFormView helpView = new HelpFormView();
    private SignInFormView sign = new SignInFormView();
    private HomeDesign home = new HomeDesign();
    private HorizontalSplitPanel eBookSplitPanel = new HorizontalSplitPanel();
    private HorizontalSplitPanel studentSplitPanel = new HorizontalSplitPanel();
    //private SQL sql = new SQL();
    private TabSheet tabSheet = new TabSheet();
    private String p, u;
    private Grid<Student> studentGrid = new Grid<>(Student.class);
    private Grid<eBook> eBookGrid = new Grid<>(eBook.class);
    private boolean currentState = false;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
/*        //initializeSQL();
       sign.signinButton.addClickListener((Button.ClickListener) clickEvent ->{
            p = sign.passwordField.getValue();
            u = sign.username.getValue();
            if(sign.isCred(u,p)){

            }
            else{
                sign.signInError.setVisible(true);
            }

        });*/
        initializeSQL();
        tabSheet.setSizeFull();
        sign.setVisible(true);
/*        studentEditor.refresh();
        eBookEditor.refresh();*/
        createStudentPanel();
        createEBookPanel();
        createSplitPanels();
        home.setCaption("Home");
        eBookGrid.setCaption("E-Books");
        helpView.setCaption("Help");
        tabSheet.addComponent(home);
        tabSheet.addComponent(studentSplitPanel);
        tabSheet.addComponent(eBookSplitPanel);
        tabSheet.addComponent(eBookGrid);
        tabSheet.addComponent(helpView);
        helpView.setSizeFull();
        tabSheet.addSelectedTabChangeListener(selectedTabChangeEvent -> {
            if (selectedTabChangeEvent.getComponent() == helpView) {
                System.out.println();
                System.out.println("Got Here");
                new Notification("Hello", Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
            }
        });
        setContent(tabSheet);
    }

    private void createSplitPanels (){
        eBookSplitPanel.setFirstComponent(eBookGrid);
        eBookSplitPanel.setSecondComponent(eBookEditor);
        eBookSplitPanel.setCaption("E-Books");
        studentSplitPanel.setFirstComponent(studentGrid);
        studentSplitPanel.setSecondComponent(studentEditor);
        studentSplitPanel.setCaption("Student Data");
    }

    private void initializeSQL() {
        studentEditor.delete.setVisible(currentState);
        eBookEditor.cancel.setVisible(currentState);
        studentEditor.setGrid(studentGrid);
        eBookEditor.setGrid(eBookGrid);
        eBookEditor.setStudentFormView(studentEditor);
        //sql.connect(SQL.Database.EBOOK);
        //eBookEditor.setSql(sql);
        //studentEditor.setSQL(sql);
        setContent(sign);
    }

    private void createStudentPanel(){
        studentEditor.setSizeFull();
        studentGrid.setSizeFull();
        studentGrid.removeAllColumns();
        studentGrid.addColumn(Student::getID).setCaption("USER ID");
        studentGrid.addColumn(Student::getGrade).setCaption("GRADE");
        studentGrid.addColumn(Student::getFirstName).setCaption("FIRST NAME");
        studentGrid.addColumn(Student::getLastName).setCaption("LAST NAME");
        studentGrid.addColumn(Student::getClassRoom).setCaption("CLASS ROOM");
        studentGrid.addColumn(Student::getRedemptioncode).setCaption("REDEMPTION CODE");
        studentGrid.asSingleSelect().addValueChangeListener(evt -> studentEditor.setStudent(evt.getValue()));
        studentGrid.addSelectionListener(event -> {
            studentEditor.delete.setVisible(true);
            studentEditor.cancel.setVisible(true);
            studentEditor.add.setVisible(true);
            studentEditor.firstName.setReadOnly(true);
            studentEditor.lastName.setReadOnly(true);
        });
    }

    private void createEBookPanel(){
        eBookEditor.setSizeFull();
        eBookGrid.setSizeFull();
        eBookGrid.removeAllColumns();
        eBookGrid.addColumn(eBook::getID).setCaption("Book ID");
        eBookGrid.addColumn(eBook::getBookName).setCaption("Book Name");
        eBookGrid.addColumn(eBook::getAuthor).setCaption("Author");
        eBookGrid.addColumn(eBook::getRedemptionCode).setCaption("Redemption Code");
        eBookGrid.asSingleSelect().addValueChangeListener(evt -> {
            eBookEditor.setEBook(evt.getValue());
            eBookEditor.studentId.setVisible(false);
            eBookEditor.cancel.setVisible(true);
            eBookEditor.add.setVisible(true);
            eBookEditor.redemptionCode.setVisible(true);
        });
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {}

}


