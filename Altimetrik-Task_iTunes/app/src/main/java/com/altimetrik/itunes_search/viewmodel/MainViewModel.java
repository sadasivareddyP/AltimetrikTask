package com.altimetrik.itunes_search.viewmodel;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hbb20.CountryCodePicker;

public class MainViewModel extends ViewModel {

    /**
     * Observable value whether to show refreshing state of {@link SwipeRefreshLayout}
     */
    private ObservableField<Boolean> isRefreshing = new ObservableField<>();

       /**
     * Observable value to determine if api call result returns with empty data
     */
    private ObservableField<Boolean> isResultEmpty = new ObservableField<>();

    /**
     * Observable value of last search date which is binded on a {@link android.widget.TextView} widget
     */
    private ObservableField<String> lastSearch = new ObservableField<>();

    /**
     * Observable value of term which is binded on a {@link android.widget.EditText} widget
     */
    private ObservableField<String> searchTerm = new ObservableField<>();


    public MainViewModel(){

    }

    @BindingAdapter({"refreshVisibility"})
    public static void setRefreshVisibility(SwipeRefreshLayout view, boolean isRefreshing) {
        view.setRefreshing(isRefreshing);
    }

    @BindingAdapter({"animatedVisibility"})
    public static void setAnimatedVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"listVisibility"})
    public static void setListVisibility(View view, boolean isEmpty) {
        view.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter({"emptyViewVisibility"})
    public static void setEmptyViewVisibility(View view, boolean isEmpty) {
        view.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }


    public ObservableField<Boolean> isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean progressVisible) {
        this.isRefreshing.set(progressVisible);
    }


    public ObservableField<String> getLastSearch() {
        return lastSearch;
    }

    public void setLastSearch(String lastSearch) {
        this.lastSearch.set(lastSearch);
    }

    public ObservableField<String> getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm.set(searchTerm);
    }


    public ObservableField<Boolean> getIsResultEmpty() {
        return isResultEmpty;
    }

    public void setIsResultEmpty(boolean isResultEmpty) {
        this.isResultEmpty.set(isResultEmpty);
    }
}
