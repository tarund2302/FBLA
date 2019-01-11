package tarundFBLA.Forms;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.*;

@DesignRoot
@SuppressWarnings("serial")
public class SignInForm extends AbsoluteLayout {
    public Button signinButton;
    public PasswordField passwordField;
    public TextField username;
    public Label welcomeLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label signInError;

}
