package cs294.riviera.com.riviera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import cs294.riviera.com.riviera.R;

public class CandidateProfileActivity extends AppCompatActivity {

    private TextView mCandidateSummary;

    private CheckBox mMobileCheckbox;
    private CheckBox mFrontendCheckbox;
    private CheckBox mBackendCheckbox;
    private CheckBox mBizOps;
    private CheckBox mPM;

    private RadioGroup mjobType;

    private CheckBox mGPA;
    private CheckBox mExp;
    private CheckBox mCulture;
    private CheckBox mMission;

    private RadioGroup mRating;

    private EditText mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        mCandidateSummary = (TextView) findViewById(R.id.candidate_summary);
        mCandidateSummary.setText("John Doe\nEECS\n May 2016");

        mMobileCheckbox = (CheckBox) findViewById(R.id.mobile_checkbox);
        mFrontendCheckbox = (CheckBox) findViewById(R.id.frontend_checkbox);
        mBackendCheckbox = (CheckBox) findViewById(R.id.backend_checkbox);
        mBizOps = (CheckBox) findViewById(R.id.bizops_checkbox);
        mPM = (CheckBox) findViewById(R.id.pm_checkbox);

        mjobType = (RadioGroup) findViewById(R.id.jobtype_radio);

        mGPA = (CheckBox) findViewById(R.id.gpa_checkbox);
        mExp = (CheckBox) findViewById(R.id.exp_checkbox);
        mCulture = (CheckBox) findViewById(R.id.culture_checkbox);
        mMission = (CheckBox) findViewById(R.id.mission_checkbox);

        mRating = (RadioGroup) findViewById(R.id.rating_radio);

        mNotes = (EditText) findViewById(R.id.notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_candidate_profile, menu);
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

    public void handleSaveCandidate(View view) {

        String jobTypeString;
        String ratingString;
        String notesText;
        
        boolean mobile = mMobileCheckbox.isChecked();
        boolean frontend = mFrontendCheckbox.isChecked();
        boolean backend = mBackendCheckbox.isChecked();
        boolean bizops = mBizOps.isChecked();
        boolean pm = mPM.isChecked();

        boolean gpa = mGPA.isChecked();
        boolean exp = mExp.isChecked();
        boolean culture = mCulture.isChecked();
        boolean mission = mMission.isChecked();

        RadioButton jobType = (RadioButton) findViewById(mjobType.getCheckedRadioButtonId());
        RadioButton rating = (RadioButton) findViewById(mRating.getCheckedRadioButtonId());

        if (jobType != null) {
            jobTypeString = jobType.getText().toString();
        } else {
            jobTypeString = "";
        }

        if (rating != null) {
            ratingString = rating.getText().toString();
        } else {
            ratingString = "";
        }

        notesText = mNotes.getText().toString();

        saveSuccessful("Candidate Saved!");
    }

    public void saveSuccessful(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
