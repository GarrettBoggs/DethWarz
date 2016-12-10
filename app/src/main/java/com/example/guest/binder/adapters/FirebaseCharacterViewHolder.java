package com.example.guest.binder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.binder.R;
import com.example.guest.binder.models.Character;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by Guest on 12/8/16.
 */
    public class FirebaseCharacterViewHolder extends RecyclerView.ViewHolder {
        private static final int MAX_WIDTH = 300;
        private static final int MAX_HEIGHT = 300;

        View mView;
        Context mContext;

        public FirebaseCharacterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mContext = itemView.getContext();
        }

        public void bindCharacter(Character character) {
            TextView mCharacterTextView = (TextView) mView.findViewById(R.id.characterNameTextView);
            TextView mCharacterDescTextView = (TextView) mView.findViewById(R.id.descriptionTextView);
            ImageView mCharacterImageView = (ImageView) mView.findViewById(R.id.characterImageView);


            Picasso.with(mContext)
                    .load(character.getPicture())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerInside()
                    .into(mCharacterImageView);

            mCharacterTextView.setText(character.getName());
            mCharacterDescTextView.setText(character.getDescription());

        }

}
