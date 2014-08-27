package com.gravityrd.jofogas.slidemenu;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.gravityrd.jofogas.R;
import com.gravityrd.jofogas.activities.SingleItemActivity;
import com.gravityrd.jofogas.activities.StaggeredListActivity;
import com.gravityrd.jofogas.model.GravityProduct;
import com.gravityrd.jofogas.util.Client;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Search {
    private final Activity activity;
    private AutoCompleteTextView auto;
    private List<GravityProduct> items;
    private boolean isActive = false;

    public Search(Activity activity) {
        this.activity = activity;
    }

    public void focus() {
        auto.requestFocus();
    }

    private static class SearchSuggestions {
        public GravityProduct item;

        public SearchSuggestions(GravityProduct i) {
            this.item = i;
        }

        @Override
        public String toString() {
            return item.getProductTitle();
        }
    }

    public void create(Menu menu, final Activity activity) {
        auto = (AutoCompleteTextView) menu.findItem(R.id.menu_search).getActionView();
        auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String text = s.toString();

                if (text.length() < 3) {
                    add(null, activity);
                    return;
                }
                add(text, activity);
            }
        });
        auto.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    final String text = v.getEditableText().toString();
                    Client.addSearchAsync(text);
                    StaggeredListActivity.startStaggered(activity, items);

                    isActive = false;
                }
                return false;
            }
        });
        auto.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                handleKeyboard(view, b);
            }
        });
        auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                SearchSuggestions suggestions = (SearchSuggestions) arg0.getItemAtPosition(arg2);
                handleKeyboard(arg1, false);
                SingleItemActivity.startSingleView(activity, suggestions.item);
            }
        });

        add(null, activity);
    }

    void handleKeyboard(View view, boolean show) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (show) {
            if (!isActive) isActive = true;
            else return;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            if (!isActive) return;
            isActive = false;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void add(final String text, final Activity activity) {
        if (!isActive) return;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                final List<SearchSuggestions> res;
                if (text == null) {
                    res = new ArrayList<SearchSuggestions>();
                    items = new ArrayList<GravityProduct>();
                } else {
                    items = Client.getTextSuggestion(text, null, null);
                    res = suggestions(items);
                }
                Log.i("text", "got items " + text + " " + items.size());
                Log.i("change suggestions", "is" + res.toString());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!isActive) return;
                        ArrayAdapter<SearchSuggestions> adp = new ArrayAdapter<SearchSuggestions>(activity, android.R.layout.simple_dropdown_item_1line, res);
                        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        auto.setThreshold(1);
                        auto.setAdapter(adp);
                        auto.showDropDown();
                    }
                });
                return null;
            }
        }.execute();


    }

    private List<SearchSuggestions> suggestions(List<GravityProduct> items) {
        List<SearchSuggestions> suggestions = new ArrayList<SearchSuggestions>();
        Set<String> recommends = new LinkedHashSet<String>();
        final int limit = 6;

        for (GravityProduct i : items) {
            SearchSuggestions s = new SearchSuggestions(i);
            if (recommends.contains(s.toString())) continue;
            recommends.add(s.toString());
            suggestions.add(s);
            if (suggestions.size() >= limit) break;
        }

        return suggestions;
    }

}
