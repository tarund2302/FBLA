package tarundFBLA.Forms;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.declarative.Design;

@DesignRoot
@SuppressWarnings("serial")
public class HelpForm extends AbsoluteLayout {
    protected Button student;
    protected Button eBook;
    protected Button report;
    protected Label desc;

/*    public HelpForm(){
        Design.read(this);}*/

    public HelpForm(){
        student = new Button();
        eBook = new Button();
        report = new Button();
        desc = new Label();
        //Design.read(this);
    }

}