package tarundFBLA.FormViews;

import tarundFBLA.Forms.SignInForm;

import java.util.ArrayList;
import java.util.List;

public class SignInFormView extends SignInForm {
    public List<String> creds = new ArrayList<>();
    public SignInFormView () {
        creds.add("foo:hello");
        creds.add("hello:foo");
    }
    public boolean isCred(String u, String p){
        return creds.contains(u + ":" + p);
    }

}
