package tarundFBLA;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
@SuppressWarnings("serial")
public class HomeDesign extends AbsoluteLayout {

    public HomeDesign(){ Design.read(this);}
}