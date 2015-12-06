package cs294.riviera.com.riviera.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cs294.riviera.com.riviera.ParseWrapper;
import cs294.riviera.com.riviera.R;
import cs294.riviera.com.riviera.SharedPreferenceKeys;
import cs294.riviera.com.riviera.activity.AddEventActivity;
import cs294.riviera.com.riviera.activity.NamesListActivity;
import cs294.riviera.com.riviera.activity.SignUpActivity;

public class EventsListFragment extends Fragment{

    private ListView mEventsList;
    private EventsListAdapter mEventsListAdapter;

    private ParseWrapper mParseWrapper;

    public EventsListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParseWrapper = new ParseWrapper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events_list, container, false);


        mEventsList = (ListView) view.findViewById(R.id.events_list);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = getActivity().getSharedPreferences(SharedPreferenceKeys.USER_PREFS_KEY, Context.MODE_PRIVATE);
        if (prefs.contains(SharedPreferenceKeys.CURRENT_RECRUITER_ID_KEY)) {
            String recruiterId = prefs.getString(SharedPreferenceKeys.CURRENT_RECRUITER_ID_KEY, null);
            ArrayList<ParseObject> events = mParseWrapper.getEventsForRecruiter(recruiterId);

            mEventsListAdapter = new EventsListAdapter(getActivity(), 0, events.toArray());
            mEventsList.setAdapter(mEventsListAdapter);
            mEventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ParseObject eventClicked = mEventsListAdapter.getEvent(position);

                    SharedPreferences prefs = getActivity().getSharedPreferences(SharedPreferenceKeys.USER_PREFS_KEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(SharedPreferenceKeys.CURRENT_EVENT_ID_KEY, eventClicked.getObjectId());
                    editor.apply();

                    mParseWrapper.addStudentToRecruiterEvent(false, "testname", null, "testsite", 4, eventClicked.getObjectId(), "testurl");

                    Intent intent = new Intent(getActivity(), NamesListActivity.class);
                    intent.putExtra(NamesListActivity.EVENT_ID_EXTRA, eventClicked.getObjectId());
                    startActivity(intent);
                }
            });
        }
    }

    class EventsListAdapter extends ArrayAdapter {
        Object[] events;

        public EventsListAdapter(Context context, int resource, Object[] objects) {
            super(context, resource, objects);
            events = objects;
        }

        @Override
        public int getCount() {
            return events.length;
        }

        @Override
        public Object getItem(int arg0) {
            return events[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            if (arg1 == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                arg1 = inflater.inflate(R.layout.list_item, arg2, false);
            }

            TextView eventDate = (TextView) arg1.findViewById(R.id.list_left_item);
            TextView eventName = (TextView) arg1.findViewById(R.id.list_right_item);
//            ParseObject event = (ParseObject) events[arg0];
            ParseObject event = (ParseObject) events[0];
            String name = (String) event.get("name");
            eventDate.setText((String) event.get("name"));
            eventName.setText(((String) event.get("date")).substring(0,10));
            return arg1;
        }

        public ParseObject getEvent(int position) {
            return (ParseObject) events[position];
        }
    }

    class Event {
        String date;
        String name;
    }



//    List<Event> getDataForListView() {
//        List<Event> events = new ArrayList<Event>();
//        for (int i = 0; i < 10; i ++) {
//            Event event = new Event();
//            event.name = "Event " + i;
//            event.date = "11/" + i + "/15";
//            events.add(event);
//        }
//        return events;
//    }
}
