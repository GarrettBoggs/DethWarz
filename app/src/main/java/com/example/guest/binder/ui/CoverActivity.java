package com.example.guest.binder.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;

import android.widget.TextView;


import com.example.guest.binder.R;
import com.example.guest.binder.models.Character;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoverActivity extends AppCompatActivity implements View.OnClickListener {

    private GestureDetectorCompat mDetector;

    @Bind(R.id.heroOneImage) ImageView mCharacterOneImage;
    @Bind(R.id.heroTwoImage) ImageView mCharacterTwoImage;
    @Bind(R.id.heroOneName) TextView mHeroOneName;
    @Bind(R.id.heroTwoName) TextView mHeroTwoName;
    @Bind(R.id.heroOneDescription) TextView mHeroOneDescription;
    @Bind(R.id.heroTwoDescription) TextView mHeroTwoDescription;

    boolean dead = false;

    private Context mContext;

    Random rn = new Random();

    private DatabaseReference mWinsReference;
    private DatabaseReference mWinsReferenceTwo;

    public Character mCharacterOne;
    public Character mCharacterTwo;

    public List<String> characterNames = Arrays.asList( "Shrek", "King Arthur", "Lancelot", "Goomba", "Hermione", "Dora", "Popeye" , "Rachet and Clank" , "Donkey Kong" , "Cookie Monster" , "Creeper", "Walter White", "Sora", "Ron Weasley", "Ezio Auditore", "Beethoven", "Godzilla" , "Commander Shepard", "Charizard", "Meat Boy" , "Wolverine", "The Joker","Sans", "Magikarp", "Kirby" , "Captain Falcon" , "The Terminator", "Pac Man", "Ronda Rousey", "Stormtrooper" , "Jar Jar" ,"Koopa","Bowser","Eugene Krabs","Elsa","Batman","Santa Claus", "Harry Potter", "Kirby", "Dory", "Michelangelo" ,"Goku" ,"Seabiscuit" ,"Al Capone", "John Wayne", "Usain Bolt", "Thrall", "Steven Hawkings", "Albert Einstein", "Abe Lincoln", "Ash Ketchum", "Banjo Kazooie", "Big Bird", "Bigfoot", "Bill Clinton", "Boo", "Bob Ross", "Britney Spears", "Bugs Bunny", "Chuck Norris", "Cloud", "Chewbacca", "Companion Cube", "Darth Vader", "Dracula", "Dumbledore", "Eragon", "Ernest Hemmingway", "Fred Flintstone", "Frodo", "Gandalf", "Han Solo", "Harley Quinn", "James Bond", "Link", "Luke Skywalker", "Mario", "Megaman", "Mr Mime", "Mr T", "Pikachu", "Rick Grimes", "Robin Hood", "Sonic", "Spiderman", "Spongebob", "Snoopy", "Snow White" ,"Superman", "The Flash","The Hulk", "Thrall","Tiger Woods", "Tigger", "Tracer", "Vegeta", "Wonder Woman", "Yoda", "Yoshi", "Zelda", "Zeus", "Naruto", "Beast Boy", "Conor Mcgregor", "Murloc" , "Thrall") ;

    Animation performAnimation, LoseAnimation, rightStrong, leftStrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getBaseContext();

        int guessSize = characterNames.size() - 1;
        int guess = rn.nextInt(guessSize);
        Log.d("Rar1" , "Which character1 is messing me up!!" + characterNames.get(guess));
        int guess2;

        mWinsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child(characterNames.get(guess));

        do{
            guess2 = rn.nextInt(guessSize);
        } while (guess == guess2);

        Log.d("Rar1" , "Which character1 is messing me up!!" + characterNames.get(guess));

        mWinsReferenceTwo = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child(characterNames.get(guess2));

        mWinsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    String name = (String) dataSnapshot.child("name").getValue();
                    String desc = (String)  dataSnapshot.child("description").getValue();
                    String picture = (String)  dataSnapshot.child("picture").getValue();
                    long wins = (long)  dataSnapshot.child("wins").getValue();
                    long losses = (long)  dataSnapshot.child("losses").getValue();

                    Character temp = new Character(name, picture, desc, wins, losses);

                    mCharacterOne = temp;
                    mHeroOneName.setText(mCharacterOne.getName());
                    mHeroOneDescription.setText(mCharacterOne.getDescription());

                    Picasso.with(mContext).load(mCharacterOne.getPicture()).into(mCharacterOneImage);

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mWinsReferenceTwo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = (String) dataSnapshot.child("name").getValue();
                String desc = (String)  dataSnapshot.child("description").getValue();
                String picture = (String)  dataSnapshot.child("picture").getValue();
                long wins = (long)  dataSnapshot.child("wins").getValue();
                long losses = (long)  dataSnapshot.child("losses").getValue();

                Character temp = new Character(name, picture, desc, wins, losses);

                mCharacterTwo = temp;
                mHeroTwoName.setText(mCharacterTwo.getName());
                mHeroTwoDescription.setText(mCharacterTwo.getDescription());

                Picasso.with(mContext).load(mCharacterTwo.getPicture()).into(mCharacterTwoImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        performAnimation = AnimationUtils.loadAnimation(this, R.anim.move_one);
        performAnimation.setRepeatCount(1);

        LoseAnimation = AnimationUtils.loadAnimation(this, R.anim.move_left);
        LoseAnimation.setRepeatCount(1);

        leftStrong = AnimationUtils.loadAnimation(this, R.anim.move_left_strong);
        leftStrong.setRepeatCount(1);

        rightStrong = AnimationUtils.loadAnimation(this, R.anim.move_right_strong);
        rightStrong.setRepeatCount(1);

        LoseAnimation.setFillAfter(true);
        leftStrong.setFillAfter(true);
        rightStrong.setFillAfter(true);
        performAnimation.setFillAfter(true);

        mCharacterOneImage.setOnClickListener(this);
        mCharacterTwoImage.setOnClickListener(this);
    }

    private static final int SWIPE_MIN_DISTANCE = 50;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;


        @Override
        public void onClick(View v){ {

            final Intent intent = new Intent(CoverActivity.this, StatsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            performAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCharacterOne.addWin();
                    mCharacterTwo.addLoss();

                    intent.putExtra("winner", mCharacterOne.getName());
                    intent.putExtra("winnerWins", mCharacterOne.getStringWins());
                    intent.putExtra("winnerLosses", mCharacterOne.getStringLosses());
                    intent.putExtra("winnerWinPercent", mCharacterOne.calculateWin());
                    intent.putExtra("loserWins", mCharacterTwo.getStringWins());
                    intent.putExtra("loserLosses", mCharacterTwo.getStringLosses());
                    intent.putExtra("loserWinPercent", mCharacterTwo.calculateWin());
                    intent.putExtra("loser", mCharacterTwo.getName());
                    intent.putExtra("winnerImage", mCharacterOne.getPicture());
                    intent.putExtra("loserImage", mCharacterTwo.getPicture());

                    mWinsReference.child("wins").setValue(mCharacterOne.getWins());
                    mWinsReferenceTwo.child("losses").setValue(mCharacterTwo.getLosses());
                    mWinsReference.child("winrate").setValue(mCharacterOne.calculateWin());
                    mWinsReferenceTwo.child("winrate").setValue(mCharacterTwo.calculateWin());

                    startActivity(intent);
                    finish();

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            LoseAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCharacterTwo.addWin();
                    mCharacterOne.addLoss();

                    mWinsReference.child("losses").setValue(mCharacterOne.getLosses());
                    mWinsReferenceTwo.child("wins").setValue(mCharacterTwo.getWins());

                    mWinsReference.child("winrate").setValue(mCharacterOne.calculateWin());
                    mWinsReferenceTwo.child("winrate").setValue(mCharacterTwo.calculateWin());

                    intent.putExtra("winner", mCharacterTwo.getName());
                    intent.putExtra("winnerWins", mCharacterTwo.getStringWins());
                    intent.putExtra("winnerLosses", mCharacterTwo.getStringLosses());
                    intent.putExtra("loserWins", mCharacterOne.getStringWins());
                    intent.putExtra("loserLosses", mCharacterOne.getStringLosses());
                    intent.putExtra("loser", mCharacterOne.getName());
                    intent.putExtra("loserWinPercent", mCharacterOne.calculateWin());
                    intent.putExtra("winnerWinPercent", mCharacterTwo.calculateWin());
                    intent.putExtra("winnerImage", mCharacterTwo.getPicture());
                    intent.putExtra("loserImage", mCharacterOne.getPicture());

                    startActivity(intent);
                    finish();

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


            if(v == mCharacterTwoImage) {
                mCharacterOneImage.startAnimation(leftStrong);
                mCharacterTwoImage.startAnimation(LoseAnimation);
            }

            if(v == mCharacterOneImage){

                    mCharacterOneImage.startAnimation(performAnimation);
                    mCharacterTwoImage.startAnimation(rightStrong);
                }


        }

    }

}
