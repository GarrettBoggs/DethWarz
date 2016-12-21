package com.example.guest.binder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.winnerText) TextView mWinnerText;
    @Bind(R.id.loserText) TextView mLoserText;
    @Bind(R.id.nextButton) Button mNextButton;
    @Bind(R.id.contactClick) TextView mContactClick;
    @Bind(R.id.victorButton) Button mVictorButton;
    @Bind(R.id.loserlosses) TextView mLoserLosses;
    @Bind(R.id.loserWins) TextView mLoserWins;
    @Bind(R.id.winnerWins) TextView mWinnerWins;
    @Bind(R.id.winnerLosses) TextView mWinnerLosses;
    @Bind(R.id.winnerWinPercent) TextView mWinnerWinPercent;
    @Bind(R.id.loserWinPercent) TextView mLoserWinPercent;
    @Bind(R.id.winnerImage) ImageView mWinnerImage;
    @Bind(R.id.loserImage) ImageView mLoserImage;

    Context mContext;
    Random rn = new Random();

    private DatabaseReference mWinsReference;
    private DatabaseReference mWinsReferenceTwo;

    public Character mCharacterOne;
    public Character mCharacterTwo;

    public List<String> characterNames = Arrays.asList( "TR-8R", "Groot","Rey", "Grim Reaper", "Magneto", "Hamlet", "Venom", "Buzz Lightyear", "Droid", "Master Chief", "Gary", "Thor" , "Iron Man" , "Captain America", "Blue Knight" , "Smaug" , "Shadow", "Simon Belmont", "Dwight Schrute",  "Krillin", "Knuckles" , "Rick Roll", "John Cena", "Boba fett" , "Aang", "Robin", "Ratchet and Clank" , "Wario", "Toothless", "Samus", "Joel", "Geralt" , "Manny Pacquio" , "Muhammed Ali", "Turtle" , "Da Vinci", "Slash", "Seto Kaiba" , "Chun-li", "Zezima" , "Ryu" , "T-Rex" , "Luigi" , "Joan de Arc", "Shrek", "Deadpool", "Jak and Daxter", "Shrek", "King Arthur", "Lancelot", "Goomba", "Hermione", "Dora", "Popeye" , "Donkey Kong" , "Cookie Monster" , "Creeper", "Walter White", "Sora", "Ron Weasley", "Ezio Auditore", "Beethoven", "Godzilla" , "Cmd Shepard", "Charizard", "Meat Boy" , "Wolverine", "The Joker","Sans", "Magikarp", "Kirby" , "Captain Falcon" , "The Terminator", "Pac Man", "Ronda Rousey", "Stormtrooper" , "Jar Jar" ,"Koopa","Bowser","Eugene Krabs","Elsa","Batman","Santa Claus", "Harry Potter", "Kirby", "Dory", "Michelangelo" ,"Goku" ,"Seabiscuit" ,"Al Capone", "John Wayne", "Usain Bolt", "Thrall", "Steven Hawkings", "Albert Einstein", "Abe Lincoln", "Ash Ketchum", "Banjo Kazooie", "Big Bird", "Bigfoot", "Bill Clinton", "Boo", "Bob Ross", "Britney Spears", "Bugs Bunny", "Chuck Norris", "Cloud", "Chewbacca", "Companion Cube", "Darth Vader", "Dracula", "Dumbledore", "Eragon", "E Hemmingway", "Fred Flintstone", "Frodo", "Gandalf", "Han Solo", "Harley Quinn", "James Bond", "Link", "Luke Skywalker", "Mario", "Megaman", "Mr Mime", "Mr T", "Pikachu", "Rick Grimes", "Robin Hood", "Sonic", "Spiderman", "Spongebob", "Snoopy", "Snow White" ,"Superman", "The Flash","The Hulk", "Thrall","Tiger Woods", "Tigger", "Tracer", "Vegeta", "Wonder Woman", "Yoda", "Yoshi", "Zelda", "Zeus", "Naruto", "Beast Boy", "Conor Mcgregor", "Murloc" , "Thrall", "Prince Zuko") ;

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
                .child(characterNames.get(guess));

        while (guess == guess2){
            guess2 = rn.nextInt(guessSize);
        }

        Log.d("Rar2" , "Which character2 is messing me up!!" + characterNames.get(guess2));

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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        Context mContext = this.getBaseContext();

        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        String loser = intent.getStringExtra("loser");
        String winnerImage = intent.getStringExtra("winnerImage");
        String loserImage = intent.getStringExtra("loserImage");

        Picasso.with(mContext).load(winnerImage).into(mWinnerImage);
        Picasso.with(mContext).load(loserImage).into(mLoserImage);

        mWinnerText.setText(winner);
        mLoserText.setText(loser);

        mWinnerWins.setText(intent.getStringExtra("winnerWins"));
        mWinnerLosses.setText(intent.getStringExtra("winnerLosses"));
        mWinnerWinPercent.setText(intent.getStringExtra("winnerWinPercent") + "%");
        mLoserWins.setText(intent.getStringExtra("loserWins"));
        mLoserLosses.setText(intent.getStringExtra("loserLosses"));
        mLoserWinPercent.setText(intent.getStringExtra("loserWinPercent") + "%");

        mNextButton.setOnClickListener(this);
        mContactClick.setOnClickListener(this);
        mVictorButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == mNextButton) {
            Intent intent = new Intent(StatsActivity.this, CoverActivity.class);
            intent.putExtra("charOne" , Parcels.wrap(mCharacterOne));
            intent.putExtra("charTwo" , Parcels.wrap(mCharacterTwo));
            startActivity(intent);
        }

        else if (v == mContactClick) {
            Intent intent = new Intent(StatsActivity.this, ContactActivity.class);
            startActivity(intent);
        }

        else if (v == mVictorButton) {
            Intent intent = new Intent(StatsActivity.this, HistoryActivity.class);
            startActivity(intent);
        }


    }

}
