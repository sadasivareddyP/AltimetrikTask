package com.altimetrik.itunes_search.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hbb20.CountryCodePicker;
import com.altimetrik.itunes_search.R;
import com.altimetrik.itunes_search.databinding.ActivityMainBinding;
import com.altimetrik.itunes_search.model.Track;
import com.altimetrik.itunes_search.model.TrackResponse;
import com.altimetrik.itunes_search.utility.Constants;
import com.altimetrik.itunes_search.utility.Utility;
import com.altimetrik.itunes_search.view.adapter.TrackRecyclerViewAdapter;
import com.altimetrik.itunes_search.base.BaseActivity;
import com.altimetrik.itunes_search.viewmodel.MainViewModel;
import com.altimetrik.itunes_search.viewmodel.TrackViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnEditorAction;
import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;

public class MainActivity extends BaseActivity {


    ActivityMainBinding activityMainBinding;

    @BindView(R.id.recyclerViewTrack)
    RecyclerView rvTrackList;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout srlLoading;

    @BindView(R.id.editTextTerm)
    EditText etTerm;

    @BindView(R.id.spinner)
    Spinner filterOptionSpinner;

    @BindView(R.id.cartbtn)
    Button cartbtn;

    String term = Constants.DEFAULT_TERM;

    ArrayList<Track> trackArrayList = new ArrayList<>();
    TrackRecyclerViewAdapter trackRecyclerViewAdapter;
    TrackViewModel trackViewModel;
    MainViewModel mainViewModel;

    @Override
    public void onResume() {
        getSharedPreferenceManager().setLastActivity(getClass().getName());
        super.onResume();
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activityMainBinding = (ActivityMainBinding) getActivityMainBinding();

        setViewModel();

        setUI();

        callSearch();

        handleFilterSelection();

        cartSelection();

    }

    private void cartSelection() {

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,CartActivty.class);
                startActivity(i);
            }
        });
    }

    private void handleFilterSelection() {

        filterOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedPosition, long l) {

                if (selectedPosition == 0) {

                    Collections.sort(trackArrayList, new ReleaseDateComparator());
                    trackRecyclerViewAdapter.notifyDataSetChanged();
                }
               else if (selectedPosition == 1) {

                    Collections.sort(trackArrayList, new TrackNameComparator());
                    trackRecyclerViewAdapter.notifyDataSetChanged();
                }
                else if (selectedPosition == 2) {

                    Collections.sort(trackArrayList, new ArtistNameComparator());
                    trackRecyclerViewAdapter.notifyDataSetChanged();
                }
                else if (selectedPosition == 3) {

                    Collections.sort(trackArrayList, new CollectionPriceComparator());
                    trackRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
    }

    public void setViewModel() {

        trackViewModel = ViewModelProviders.of(this).get(TrackViewModel.class);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        activityMainBinding.setViewModel(mainViewModel);

    }

    public void setUI() {

        // Set term value and bind it to the search EditText widget
        mainViewModel.setSearchTerm(getSharedPreferenceManager().getTerm());

        // Set last search date and bind it to the date TextView widget
        mainViewModel.setLastSearch(Utility.formatDate(getSharedPreferenceManager().getLastSearchDate()));


        // Calling attemptSearch method when user swipes the track list to attempt calling Search API
        srlLoading.setOnRefreshListener(this::attemptSearch);

        setupRecyclerView();
    }


    private void setupRecyclerView() {
        if (trackRecyclerViewAdapter == null) {
            trackRecyclerViewAdapter = new TrackRecyclerViewAdapter(MainActivity.this, trackArrayList);
            rvTrackList.setLayoutManager(new LinearLayoutManager(this));
            rvTrackList.setAdapter(trackRecyclerViewAdapter);
            rvTrackList.setItemAnimator(new DefaultItemAnimator());
            rvTrackList.setNestedScrollingEnabled(true);
        } else {
            trackRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @OnEditorAction(R.id.editTextTerm)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            attemptSearch();
            return true;
        }
        return false;
    }

    private void attemptSearch() {
        Utility.hideKeyboard(MainActivity.this, etTerm);
        term = etTerm.getText().toString();

        if (TextUtils.isEmpty(term)) {
            Utility.setEditTextError(etTerm, getString(R.string.required));
            return;
        }

        saveData();

        mainViewModel.setLastSearch(Utility.formatDate(getSharedPreferenceManager().getLastSearchDate()));

        callSearch();

    }

    public void saveData() {


        // Save new value of search term locally
        getSharedPreferenceManager().setTerm(term);

        // Save new value of last search date locally
        getSharedPreferenceManager().setLastSearchDate(Utility.getCurrentDateTime());
    }

    public void callSearch() {
        trackArrayList.clear();
        trackViewModel.searchTrack(getTrackRepository(), getSharedPreferenceManager().getTerm());

        observeTrackDataResponse();
    }

    /**
     * Observe API call response and update {@link RecyclerView} list for data changes
     */
    private void observeTrackDataResponse() {
        mainViewModel.setRefreshing(true);
        trackViewModel.getTrackRepository().observe(this, trackResponseResult -> {
            if (trackResponseResult.getResponseBody().isSuccessful()) {
                List<Track> tracks = ((TrackResponse) trackResponseResult.getResponse()).getResults();
                Collections.sort(tracks,new ReleaseDateComparator());

                mainViewModel.setIsResultEmpty(tracks.size() <= 0);
                trackArrayList.addAll(tracks);
            } else {
                // Show a message feedback to user when api response is not successful due to network error
                if (!Utility.isNetworkAvailable(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, getString(R.string.no_network_available), Toast.LENGTH_SHORT).show();
                    trackArrayList.clear();
                }
            }

            trackRecyclerViewAdapter.notifyDataSetChanged();
            mainViewModel.setRefreshing(false);
        });

    }


    class ReleaseDateComparator implements Comparator<Track>
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        public int compare(Track lhs, Track rhs)
        {
            try {
                return dateFormat.parse(lhs.getReleaseDate()).compareTo(dateFormat.parse(rhs.getReleaseDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return 0;

        }
    }

    class TrackNameComparator implements Comparator<Track>
    {
        public int compare(Track lhs, Track rhs)
        {
            if(lhs.getTrackName()!=null && rhs.getTrackName()!=null)
            return lhs.getTrackName().compareTo(rhs.getTrackName());
            return 0;

        }
    }

    class ArtistNameComparator implements Comparator<Track>
    {
        public int compare(Track lhs, Track rhs)
        {
            return lhs.getArtistName().compareTo(rhs.getArtistName());

        }
    }

    class CollectionPriceComparator implements Comparator<Track>
    {
        public int compare(Track lhs, Track rhs)
        {
            return lhs.getCollectionPrice().compareTo(rhs.getCollectionPrice());

        }
    }
}
