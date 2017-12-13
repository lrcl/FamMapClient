package cs240.fammapclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


public class LoginFragment extends Fragment implements View.OnClickListener{
    private EditText port;
    private EditText host;
    private EditText userName;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioButton female;
    private RadioButton male;
    private Button login;
    private Button register;

    private String hostname;
    private String portname;
    private String username;
    private String pword;
    private String fname;
    private String lname;
    private String emailIn;
    private String genderIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        host = (EditText) view.findViewById(R.id.hostInput);
        port = (EditText) view.findViewById(R.id.portInput);
        userName = (EditText) view.findViewById(R.id.userNameInput);
        password = (EditText) view.findViewById(R.id.passwordInput);
        firstName = (EditText) view.findViewById(R.id.fNameInput);
        lastName = (EditText) view.findViewById(R.id.lNameInput);
        email = (EditText) view.findViewById(R.id.emailInput);
        female = (RadioButton) view.findViewById(R.id.femaleButton);
        male = (RadioButton) view.findViewById(R.id.maleButton);

        login = (Button) view.findViewById(R.id.loginButton);
        login.setOnClickListener(this);

        register = (Button) view.findViewById(R.id.registerButton);
        register.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        this.hostname = host.getText().toString();
        this.portname = port.getText().toString();
        this.username = userName.getText().toString();
        this.pword = password.getText().toString();
        this.fname = firstName.getText().toString();
        this.lname = lastName.getText().toString();
        this.emailIn = email.getText().toString();
        if (female.isChecked()) {
            this.genderIn = "f";
        }
        if (male.isChecked()) {
            this.genderIn = "m";
        }
        if(((Button)v).getText().toString().equals("SIGN IN")) {
            System.out.println("login button clicked");
            String[] userInput = {hostname, portname, username, pword, fname, lname, emailIn, genderIn};
            LoginTask loginTask = new LoginTask(getActivity().getApplicationContext(), getActivity());
            loginTask.execute(userInput);
        }
        if (((Button)v).getText().toString().equals("REGISTER")) {
            System.out.println("REGISTER BUTTON CLICKED");
            String[] userInput = {hostname, portname, username, pword, fname, lname, emailIn, genderIn};
            RegisterTask registerTask = new RegisterTask(getActivity().getApplicationContext(), getActivity());
            registerTask.execute(userInput);
        }
//        System.out.println("end method ----------------------");
    }
}