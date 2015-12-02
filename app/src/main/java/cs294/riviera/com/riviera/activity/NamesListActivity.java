package cs294.riviera.com.riviera.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cs294.riviera.com.riviera.R;

public class NamesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names_list);

        final NamesListAdapter eventsListAdapter = new NamesListAdapter();
        ListView eventsList = (ListView) findViewById(R.id.names_list);
        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CandidateProfileActivity.class);
                startActivity(intent);
            }
        });
        eventsList.setAdapter(eventsListAdapter);
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

    class NamesListAdapter extends BaseAdapter {
        List<Candidate> candidates = getDataForListView();

        @Override
        public int getCount() {
            return candidates.size();
        }

        @Override
        public Candidate getItem(int arg0) {
            return candidates.get(arg0);
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
            Candidate candidate = candidates.get(arg0);
            candidateName.setText(candidate.name);
            candidateStatus.setText(candidate.status);
            return arg1;
        }

        public Candidate getEvent(int position) {
            return candidates.get(position);
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
