package tarundFBLA.Forms;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.*;

@DesignRoot
@SuppressWarnings("serial")
public class eBookForm  extends HorizontalLayout {
    public FormLayout layout;
    public TextField redemptionCode;
    public TextField id;
    public TextField bookName;
    public TextField author;
    public TextField studentId;
    public Button delete;
    public Button save;
    public Button cancel;
    public Label nSave;
    public Button add;
    /*    public Button returnBook;*/

    public eBookForm(){
        layout = new FormLayout();
        cancel = new Button();
        delete = new Button();
        save = new Button();
        add = new Button();
        id = new TextField();
        author = new TextField();
        bookName = new TextField();
        redemptionCode = new TextField();
        studentId = new TextField();
    }

}
