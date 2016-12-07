package com.example.guest.binder.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.guest.binder.Constants;
import com.example.guest.binder.R;
import com.example.guest.binder.adapters.CharacterListAdapter;
import com.example.guest.binder.models.Character;
import com.example.guest.binder.services.BombService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CharacterActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private CharacterListAdapter mAdapter;

    private String mRecentCharacter;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public ArrayList<Character> mCharacters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        ButterKnife.bind(this);

        getCharacters("koopa");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentCharacter = mSharedPreferences.getString(Constants.PREFERENCES_CHARACTER_KEY, null);

        if (mRecentCharacter != null) {
            getCharacters(mRecentCharacter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getCharacters(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getCharacters(String name) {
        final BombService bombService = new BombService();
        bombService.findAllCharacters(name, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                        mCharacters = bombService.proccessAllResults(response);

                CharacterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                            public void run() {
                    mAdapter = new CharacterListAdapter(getApplicationContext(), mCharacters);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(CharacterActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                    }
                });

            }
        });
    }

    private void addToSharedPreferences(String character) {
        mEditor.putString(Constants.PREFERENCES_CHARACTER_KEY, character).apply();
    }
}
