package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.LoginPresenter;

public class LoginFragment extends Fragment implements ILoginActivity {

    EditText emailInput;
    EditText passInput;
    TextView errorText;

    Button loginButton;

    LoginPresenter loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_login);

        // TODO: Make accountManagerActivity (To be created) assign loginPresenter and registerPresenter
        loginPresenter = new LoginPresenter(this);

        emailInput = getView().findViewById(R.id.emailLoginInput);
        passInput = getView().findViewById(R.id.passLoginInput);
        errorText = getView().findViewById(R.id.errorView);

        loginButton = getView().findViewById(R.id.loginButton);

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                hideErrorMessage();
            }
        });
        passInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hideErrorMessage();
            }
        });


    }

    /**
     * Method reads info in email and password fields
     *
     * @return a String[] in the form of {email, password}.
     */
    public String[] fetchLoginFields() {
        // TODO Send information in a better way :S
        return new String[]{emailInput.getText().toString(), passInput.getText().toString()};

    }

    public EditText getEmailInput() {
        return emailInput;
    }

    public EditText getPassInput() {
        return passInput;
    }

    public TextView getErrorText() {
        return errorText;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public LoginPresenter getLoginPresenter() {
        return loginPresenter;
    }

    public void setLoginPresenter(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    /**
     * @param view
     */
    public void loginAccount(View view) {


        // Remove focus from activity
        emailInput.clearFocus();
        passInput.clearFocus();

        if (passInput.length() < 6) {
            showErrorMessage();
            return;
        }

        //        String[] temp = fetchLoginFields();

        loginPresenter.login(emailInput.getText().toString(), passInput.getText().toString());


        /*BoardbookSingleton.getInstance().getAuthHandler().login(temp[0], temp[1]).thenAccept(u -> {
            // access logged in user from "u"
            progress.dismiss();
            finish();

        }).exceptionally(e -> {
            // Handle error ("e")

            progress.dismiss();
            showErrorMessage();
            e.printStackTrace();

            return null;
        });*/

    }
      /*
    @Override
    public void finish() {
        super.finish();
    } */


    // TODO: Unchecked loading goes on forever, better implementation requiered


    /**
     * Reveals error message on wrong email/password combination
     */
    public void showErrorMessage() {
        errorText.setAlpha(1);
    }

    public void hideErrorMessage() {
        errorText.setAlpha(0);
    }


    /**
     * Starts a new activity of registry page and finishes this one.
     *
     * @param view is the visual object (ex a button) the method is bound to (I think)
     */
   public void showRegisterPage(View view) {
        //Intent intent = new Intent(this, RegisterFragment.class);
        //startActivity(intent);
        //finish();

    }

}
