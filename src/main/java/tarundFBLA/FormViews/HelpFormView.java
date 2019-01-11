package tarundFBLA.FormViews;

import tarundFBLA.Forms.HelpForm;
import tarundFBLA.Models.Strings;

public class HelpFormView extends HelpForm {
    public HelpFormView(){
        desc.setSizeFull();
        desc.setValue(Strings.users);
        student.addClickListener(clickEvent -> {
            desc.setValue(Strings.users);
        });
        eBook.addClickListener(clickEvent -> {
            desc.setValue(Strings.books);
        });
        report.addClickListener(clickEvent -> {
            desc.setValue(Strings.reports);
        });
    }
}
