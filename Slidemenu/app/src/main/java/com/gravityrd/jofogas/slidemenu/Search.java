package com.gravityrd.jofogas.slidemenu;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.gravityrd.jofogas.R;
import com.gravityrd.jofogas.util.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Search {
    private AutoCompleteTextView auto;

    public void create(Menu menu, final Activity activity) {
        auto = (AutoCompleteTextView) menu.findItem(R.id.menu_search).getActionView();
        auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String text = auto.getText().toString().toLowerCase(Locale.getDefault());
                if (text.length() < 3) return;
                add(text, activity);
            }

        });
        auto.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (b) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                } else {
                    imm.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
                }
            }
        });
        add(null, activity);

    }

    private void add(final String text, final Activity activity) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                List<String> res;
                if (text == null) {
                    res = new ArrayList<String>();
                } else {
                    res = Client.getTextSuggestion(text, null, null);
                }
                ArrayAdapter<String> adp = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, res);
                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                auto.setThreshold(1);
                auto.setAdapter(adp);
                return null;
            }
        }.execute();


    }

}
