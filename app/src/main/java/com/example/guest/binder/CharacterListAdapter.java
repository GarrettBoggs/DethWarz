package com.example.guest.binder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 12/2/16.
 */
public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder> {
    private ArrayList<Character> mCharacters = new ArrayList<>();
    private Context mContext;

    public CharacterListAdapter(Context context, ArrayList<Character> characters) {
        mContext = context;
        mCharacters = characters;
    }

    @Override
    public CharacterListAdapter.CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_list_item, parent, false);
        CharacterViewHolder viewHolder = new CharacterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CharacterListAdapter.CharacterViewHolder holder, int position){
        holder.bindCharacter(mCharacters.get(position));
    }

    @Override
    public int getItemCount(){
        return mCharacters.size();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.characterImageView) ImageView mCharacterImageView;
        @Bind(R.id.characterNameTextView) TextView mCharacterTextView;

        private Context mContext;

        public CharacterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
    }

        public void bindCharacter(Character character) {
            mCharacterTextView.setText(character.getName());

        }
    }
}
