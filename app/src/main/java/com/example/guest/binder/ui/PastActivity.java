package com.example.guest.binder.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.guest.binder.Constants;
import com.example.guest.binder.R;
import com.example.guest.binder.adapters.CharacterListAdapter;
import com.example.guest.binder.adapters.FirebaseCharacterViewHolder;
import com.example.guest.binder.models.Character;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 12/8/16.
 */
public class PastActivity {

    public class SavedRestaurantListActivity extends AppCompatActivity {
        private DatabaseReference mCharacterReference;
        private FirebaseRecyclerAdapter mFirebaseAdapter;

        @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_character);
            ButterKnife.bind(this);

            mCharacterReference = FirebaseDatabase.getInstance().getReference("allCharacters");
            setUpFirebaseAdapter();
        }

        private void setUpFirebaseAdapter() {
            mFirebaseAdapter = new FirebaseRecyclerAdapter<Character, FirebaseCharacterViewHolder>
                    (Character.class, R.layout.character_list_item, FirebaseCharacterViewHolder.class,
                            mCharacterReference) {

                @Override
                protected void populateViewHolder(FirebaseCharacterViewHolder viewHolder,
                                                  Character model, int position) {
                    viewHolder.bindCharacter(model);
                }
            };
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mFirebaseAdapter);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mFirebaseAdapter.cleanup();
        }
    }

}
