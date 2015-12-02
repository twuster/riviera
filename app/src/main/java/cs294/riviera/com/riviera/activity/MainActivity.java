package cs294.riviera.com.riviera.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import cs294.riviera.com.riviera.NavigationDrawerFragment;
import cs294.riviera.com.riviera.ParseWrapper;
import cs294.riviera.com.riviera.R;
import cs294.riviera.com.riviera.SharedPreferenceKeys;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private NfcAdapter mNfcAdapter;
    private Button loginButton;
    private Button signUpButton;

    private EditText loginEmail;
    private EditText loginPassword;
    private ImageView mLogo;

    private String emailText;
    private String passwordText;

    private ParseWrapper mParseWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mParseWrapper = new ParseWrapper(this);

        loginButton = (Button) findViewById(R.id.login_button);
        signUpButton = (Button) findViewById(R.id.sign_up_button);

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);

        mLogo = (ImageView) findViewById(R.id.logo);
        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Note: THIS IS ONLY FOR DEBUG PURPOSES
//                mParseWrapper.getEventsForRecruiter()
            }
        });

        loginPassword
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int id,
                                                  KeyEvent keyEvent) {
                        if (id == R.id.login || id == EditorInfo.IME_ACTION_DONE) {
                            handleLoginButton(findViewById(android.R.id.content));
                            return true;
                        }
                        return false;
                    }
                });


        // NFC Code
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC not enabled. Please enable NFC.", Toast.LENGTH_SHORT).show();
        }

        handleIntent(getIntent());

    }

    private void handleIntent(Intent intent) {
        // Handle NFC Uri
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Log.d("DEBUG", intent.getDataString());
            String url =  intent.getDataString();
        }
    }

    public void handleLoginButton(View view) {
        String recruiterId = mParseWrapper.logInRecruiter(loginEmail.getText().toString(), loginPassword.getText().toString());

        if (recruiterId != null) {
            SharedPreferences prefs = this.getSharedPreferences(SharedPreferenceKeys.USER_PREFS_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(SharedPreferenceKeys.CURRENT_RECRUITER_ID_KEY, recruiterId);
            editor.apply();

            Intent intent = new Intent(this, EventsActivity.class);
            startActivity(intent);
        }
    }

    public void handleSignUpButton(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        /* update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit(); */
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
