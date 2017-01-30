package com.example.guest.binder.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
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

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public List<String> characterNames = Arrays.asList( "Swiper", "Darth Maul", "Green Arrow", "Legolas", "Cat in the Hat", "Star Lord", "Katniss Everdeen", "Lara Croft", "Leia", "Princess Peach", "Mickey Mouse", "TR-8R", "Groot","Rey", "Grim Reaper", "Magneto", "Hamlet", "Venom", "Buzz Lightyear", "Droid", "Master Chief", "Gary", "Thor" , "Iron Man" , "Captain America", "Blue Knight" , "Smaug" , "Shadow", "Simon Belmont", "Dwight Schrute",  "Krillin", "Knuckles" , "Rick Roll", "John Cena", "Boba fett" , "Aang", "Robin", "Ratchet and Clank" , "Wario", "Toothless", "Samus", "Joel", "Geralt" , "Manny Pacquio" , "Muhammed Ali", "Turtle" , "Da Vinci", "Slash", "Seto Kaiba" , "Chun-li", "Zezima" , "Ryu" , "T-Rex" , "Luigi" , "Joan de Arc", "Shrek", "Deadpool", "Jak and Daxter", "Shrek", "King Arthur", "Lancelot", "Goomba", "Hermione", "Dora", "Popeye" , "Donkey Kong" , "Cookie Monster" , "Creeper", "Walter White", "Sora", "Ron Weasley", "Ezio Auditore", "Beethoven", "Godzilla" , "Cmd Shepard", "Charizard", "Meat Boy" , "Wolverine", "The Joker","Sans", "Magikarp", "Kirby" , "Captain Falcon" , "The Terminator", "Pac Man", "Ronda Rousey", "Stormtrooper" , "Jar Jar" ,"Koopa","Bowser","Eugene Krabs","Elsa","Batman","Santa Claus", "Harry Potter", "Kirby", "Dory", "Michelangelo" ,"Goku" ,"Seabiscuit" ,"Al Capone", "John Wayne", "Usain Bolt", "Thrall", "Steven Hawkings", "Albert Einstein", "Abe Lincoln", "Ash Ketchum", "Banjo Kazooie", "Big Bird", "Bigfoot", "Bill Clinton", "Boo", "Bob Ross", "Britney Spears", "Bugs Bunny", "Chuck Norris", "Cloud", "Chewbacca", "Companion Cube", "Darth Vader", "Dracula", "Dumbledore", "Eragon", "E Hemmingway", "Fred Flintstone", "Frodo", "Gandalf", "Han Solo", "Harley Quinn", "James Bond", "Link", "Luke Skywalker", "Mario", "Megaman", "Mr Mime", "Mr T", "Pikachu", "Rick Grimes", "Robin Hood", "Sonic", "Spiderman", "Spongebob", "Snoopy", "Snow White" ,"Superman", "The Flash","The Hulk", "Thrall","Tiger Woods", "Tigger", "Tracer", "Vegeta", "Wonder Woman", "Yoda", "Yoshi", "Zelda", "Zeus", "Naruto", "Beast Boy", "Conor Mcgregor", "Murloc" , "Thrall", "Prince Zuko") ;

    Animation performAnimation, LoseAnimation, rightStrong, leftStrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getBaseContext();

        int guessSize = characterNames.size();
        int guess = rn.nextInt(guessSize);
        Log.d("Rar1" , "Which character1 is messing me up!!" + characterNames.get(guess));
        int guess2 = guess;

        mWinsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child("Companion Cube");

        while (guess == guess2){
            guess2 = rn.nextInt(guessSize);
        }

        Log.d("Rar2" , "Which character2 is messing me up!!" + characterNames.get(guess2));

        mWinsReferenceTwo = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allCharacters")
                .child("Mario");

        mWinsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = (String) dataSnapshot.child("name").getValue();
                String desc = (String)  dataSnapshot.child("description").getValue();
                String picture = (String)  dataSnapshot.child("picture").getValue();
                int sounds = Integer.parseInt(dataSnapshot.child("sounds").getValue().toString());
                long wins = (long)  dataSnapshot.child("wins").getValue();
                long losses = (long)  dataSnapshot.child("losses").getValue();

                Character temp = new Character(name, picture, desc, wins, losses, sounds);

                mCharacterOne = temp;

                mHeroOneName.setText(mCharacterOne.getName());
                mHeroOneDescription.setText(mCharacterOne.getDescription());
                Picasso.with(mContext).load(mCharacterOne.getPicture()).into(mCharacterOneImage);

                mCharacterOne = temp;

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
                int sounds = Integer.parseInt(dataSnapshot.child("sounds").getValue().toString());
                long wins = (long)  dataSnapshot.child("wins").getValue();
                long losses = (long)  dataSnapshot.child("losses").getValue();

                Character temp = new Character(name, picture, desc, wins, losses, sounds);

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

        MediaPlayer mMediaPlayer = new MediaPlayer();

//        try {
//            getAudioTwo();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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


            if(v == mCharacterTwoImage && !dead) {
                dead = true;
                mCharacterOneImage.startAnimation(leftStrong);
                mCharacterTwoImage.startAnimation(LoseAnimation);


                   MediaPlayer mp = MediaPlayer.create(this, mCharacterTwo.getSound());
                   Log.d("What ??" , "?? int?" + R.raw.mario);
                   mp.start();


            }

            if(v == mCharacterOneImage && !dead){
                    dead = true;
                    mCharacterOneImage.startAnimation(performAnimation);
                    mCharacterTwoImage.startAnimation(rightStrong);

                MediaPlayer mp = MediaPlayer.create(this, mCharacterOne.getSound());
                Log.d("What ??" , "?? int?" + R.raw.mario);
                mp.start();

            }


        }

    }

    private void uploadAudio(){

    }

    private void getAudioTwo() throws IOException {
        final MediaPlayer mMediaPlayer;

        String url = "https://firebasestorage.googleapis.com/v0/b/dethbattle-44a6b.appspot.com/o/Audio%2Fyoshisound.wav?alt=media&token=d93ea1d9-988f-454f-bf89-c0358907d1e5";
        Log.v("the url looks like: ", url);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDataSource(url);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.prepare(); // might take long! (for buffering, etc)
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMediaPlayer.start();
            }
        }, 1000);

    }

}
