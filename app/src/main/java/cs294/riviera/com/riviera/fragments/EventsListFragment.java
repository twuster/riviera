package cs294.riviera.com.riviera.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cs294.riviera.com.riviera.R;
import cs294.riviera.com.riviera.activity.NamesListActivity;

public class EventsListFragment extends Fragment{

    public EventsListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events_list, container, false);
        final EventsListAdapter eventsListAdapter = new EventsListAdapter();
        ListView eventsList = (ListView) view.findViewById(R.id.events_list);
        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event eventClicked = eventsListAdapter.getEvent(position);
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_events_list,new NamesListFragment())
//                        .commit();
                Intent intent = new Intent(getActivity(), NamesListActivity.class);
                startActivity(intent);
            }
        });
        eventsList.setAdapter(eventsListAdapter);

        return view;
    }

    class EventsListAdapter extends BaseAdapter {
        List<Event> events = getDataForListView();
        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public Event getItem(int arg0) {
            return events.get(arg0);
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
            Event event = events.get(arg0);
            eventDate.setText(event.date);
            eventName.setText(event.name);
            return arg1;
        }

        public Event getEvent(int position) {
            return events.get(position);
        }
    }

    class Event {
        String date;
        String name;
    }

    List<Event> getDataForListView() {
        List<Event> events = new ArrayList<Event>();
        for (int i = 0; i < 10; i ++) {
            Event event = new Event();
            event.name = "Event " + i;
            event.date = "11/" + i + "/15";
            events.add(event);
        }
        return events;
    }
}
