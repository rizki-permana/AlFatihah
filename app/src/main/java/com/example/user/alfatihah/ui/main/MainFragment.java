package com.example.user.alfatihah.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.alfatihah.R;
import com.example.user.alfatihah.adapter.ListAdapter;
import com.example.user.alfatihah.model.DataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class MainFragment extends Fragment {

    private ListView listView;
    private TextView number, name, englishName, translation, revealType, numberAyahs;
    private List<DataModel> results;
    private ListAdapter listAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        number = view.findViewById(R.id.number);
        name = view.findViewById(R.id.name);
        englishName = view.findViewById(R.id.englishName);
        translation = view.findViewById(R.id.translation);
        revealType = view.findViewById(R.id.revealType);
        numberAyahs = view.findViewById(R.id.numberAyahs);

        listView = view.findViewById(R.id.list);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String URL = "http://api.alquran.cloud/v1/surah/1?offset=1";

        fetchData(URL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                AyahFragment ayahFragment = new AyahFragment();
                Bundle args = new Bundle();
                args.putSerializable("ayah", results.get(position));
                ayahFragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ayahFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }


    public void fetchData(String URL) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Cache cache = queue.getCache();
        Cache.Entry entry = cache.get(URL);

        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                parseJsonFeed(new JSONObject(data));

            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }

            return;
        }

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());

                parseJsonFeed(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        queue.add(jsonReq);
    }


    public void parseJsonFeed( JSONObject response) {
        try {
            if (response.getString("status").equals("OK")) {

                JSONObject header = response.getJSONObject("data");

                number.setText(header.getString("number"));
                name.setText(header.getString("name"));
                englishName.setText(header.getString("englishName"));
                translation.setText(header.getString("englishNameTranslation"));
                revealType.setText(header.getString("revelationType"));
                numberAyahs.setText(header.getString("numberOfAyahs"));

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                results = new ArrayList<>();
                Type listType = new TypeToken<ArrayList<DataModel>>() {
                }.getType();
                results = gson.fromJson(header.getJSONArray("ayahs").toString(), listType);

                listAdapter = new ListAdapter(getActivity(), results);
                listView.setAdapter(listAdapter);
            }

            else {
                Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
            }

        }

        catch (JSONException je) {
            Log.d(TAG, "Error: " + je.getMessage());
        }
    }

}
