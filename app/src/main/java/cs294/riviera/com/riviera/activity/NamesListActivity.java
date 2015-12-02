package cs294.riviera.com.riviera.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import cs294.riviera.com.riviera.ParseWrapper;
import cs294.riviera.com.riviera.R;

public class NamesListActivity extends AppCompatActivity {

    public static final String EVENT_ID_EXTRA = "event_id";

    private ParseWrapper mParseWrapper;
    private NamesListAdapter mNamesListAdapter;
    private ListView mNamesList;
    private String mEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names_list);

        mParseWrapper = new ParseWrapper(this);

        if (getIntent() != null && getIntent().hasExtra(EVENT_ID_EXTRA)) {
            mEventId = getIntent().getStringExtra(EVENT_ID_EXTRA);
        }

        mNamesList = (ListView) findViewById(R.id.names_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<ParseObject> candidates = mParseWrapper.getCandidatesForEvent(mEventId);
        mNamesListAdapter = new NamesListAdapter(this, 0, candidates.toArray());
        mNamesList.setAdapter(mNamesListAdapter);
        mNamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_names_list, menu);
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

    class NamesListAdapter extends ArrayAdapter {
        Object[] candidates;

        public NamesListAdapter(Context context, int resource, Object[] objects) {
            super(context, resource, objects);
            candidates = objects;
        }

        @Override
        public int getCount() {
            return candidates.length;
        }

        @Override
        public Object getItem(int arg0) {
            return candidates[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            if (arg1 == null) {
                LayoutInflater inflater = getLayoutInflater();
                arg1 = inflater.inflate(R.layout.list_item, arg2, false);
            }

            TextView candidateName = (TextView) arg1.findViewById(R.id.list_left_item);
            TextView candidateStatus = (TextView) arg1.findViewById(R.id.list_right_item);
            ParseObject candidate = (ParseObject) candidates[arg0];
            candidateName.setText(candidate.get("name").toString());
            if (candidate.get("reviewed").equals(true)) {
                candidateStatus.setText("Reviewed");
            } else {
                candidateStatus.setText("Not Reviewed");
            }
            return arg1;
        }

        public Object getName(int position) {
            return candidates[position];
        }
    }

    class Candidate {
        String status;
        String name;
    }

    List<Candidate> getDataForListView() {
        List<Candidate> candidates = new ArrayList<Candidate>();
        for (int i = 0; i < 10; i ++) {
            Candidate candidate = new Candidate();
            candidate.name = "Candidate " + i;
            candidate.status = "Reviewed";
            candidates.add(candidate);
        }
        return candidates;
    }
}
