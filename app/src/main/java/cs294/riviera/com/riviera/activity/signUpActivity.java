package cs294.riviera.com.riviera.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cs294.riviera.com.riviera.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText recruiterEmail;
    private EditText recruiterCompany;
    private EditText password;
    private EditText confirmedPassword;

    private String emailText;
    private String companyText;
    private String passwordText;
    private String confirmedPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        recruiterEmail = (EditText) findViewById(R.id.sign_up_email);
        recruiterCompany = (EditText) findViewById(R.id.company);
        password = (EditText) findViewById(R.id.sign_up_password);
        confirmedPassword = (EditText) findViewById(R.id.sign_up_confirm_password);

        confirmedPassword
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int id,
                                                  KeyEvent keyEvent) {
                        if (id == R.id.confirm_password || id == EditorInfo.IME_ACTION_DONE) {
                            handleSignUp(findViewById(android.R.id.content));
                            return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleSignUp(View view) {
        emailText = recruiterEmail.getText().toString();
        companyText = recruiterCompany.getText().toString();
        passwordText = password.getText().toString();
        confirmedPasswordText = confirmedPassword.getText().toString();

        Boolean cancel = signUpCheck();

        if (!cancel) {
            displayOk();
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean signUpCheck() {
        View focusView = null;
        Boolean cancel = false;
        if (TextUtils.isEmpty(emailText)) {
            recruiterEmail.setError(getString(R.string.error_field_required));
            focusView = recruiterEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(companyText)) {
            recruiterCompany.setError(getString(R.string.error_field_required));
            focusView = recruiterCompany;
            cancel = true;
        }

        if (TextUtils.isEmpty(passwordText)) {
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(confirmedPasswordText)) {
            confirmedPassword.setError(getString(R.string.error_field_required));
            focusView = confirmedPassword;
            cancel = true;
        }

        if (!TextUtils.equals(confirmedPasswordText, passwordText)) {
            confirmedPassword.setError(getString(R.string.error_different_passwords));
            password.setError(getString(R.string.error_different_passwords));
            cancel = true;
        }

        return cancel;
    }

    public void displayOk() {
        hideSoftKeyboard(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thanks for signing up!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recruiterEmail.setText("");
                        recruiterCompany.setText("");
                        password.setText("");
                        confirmedPassword.setText("");
                        SignUpActivity.this.finish();
                        Intent intent = new Intent(((Dialog)dialog).getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
