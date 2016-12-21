package com.example.guest.binder.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guest.binder.R;
import com.example.guest.binder.models.Character;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.loginButton) Button mLoginButton;

    Context mContext;
    Random rn = new Random();

    private DatabaseReference mWinsReference;
    private DatabaseReference mWinsReferenceTwo;

    public Character mCharacterOne;
    public Character mCharacterTwo;

    public List<String> characterNames = Arrays.asList( "Droid", "Master Chief", "Gary", "Thor" , "Iron Man" , "Captain America", "Blue Knight" , "Smaug" , "Shadow", "Simon Belmont", "Dwight Schrute",  "Krillin", "Knuckles" , "Rick Roll", "John Cena", "Boba fett" , "Aang", "Robin", "Ratchet and Clank" , "Wario", "Toothless", "Samus", "Joel", "Geralt" , "Manny Pacquio" , "Muhammed Ali", "Turtle" , "Leonardo Da Vinci", "Slash", "Seto Kaiba" , "Chun-li", "Zezima" , "Ryu" , "T-Rex" , "Luigi" , "Joan de Arc", "Shrek", "Deadpool", "Jak and Daxter", "Shrek", "King Arthur", "Lancelot", "Goomba", "Hermione", "Dora", "Popeye" , "Donkey Kong" , "Cookie Monster" , "Creeper", "Walter White", "Sora", "Ron Weasley", "Ezio Auditore", "Beethoven", "Godzilla" , "Commander Shepard", "Charizard", "Meat Boy" , "Wolverine", "The Joker","Sans", "Magikarp", "Kirby" , "Captain Falcon" , "The Terminator", "Pac Man", "Ronda Rousey", "Stormtrooper" , "Jar Jar" ,"Koopa","Bowser","Eugene Krabs","Elsa","Batman","Santa Claus", "Harry Potter", "Kirby", "Dory", "Michelangelo" ,"Goku" ,"Seabiscuit" ,"Al Capone", "John Wayne", "Usain Bolt", "Thrall", "Steven Hawkings", "Albert Einstein", "Abe Lincoln", "Ash Ketchum", "Banjo Kazooie", "Big Bird", "Bigfoot", "Bill Clinton", "Boo", "Bob Ross", "Britney Spears", "Bugs Bunny", "Chuck Norris", "Cloud", "Chewbacca", "Companion Cube", "Darth Vader", "Dracula", "Dumbledore", "Eragon", "Ernest Hemmingway", "Fred Flintstone", "Frodo", "Gandalf", "Han Solo", "Harley Quinn", "James Bond", "Link", "Luke Skywalker", "Mario", "Megaman", "Mr Mime", "Mr T", "Pikachu", "Rick Grimes", "Robin Hood", "Sonic", "Spiderman", "Spongebob", "Snoopy", "Snow White" ,"Superman", "The Flash","The Hulk", "Thrall","Tiger Woods", "Tigger", "Tracer", "Vegeta", "Wonder Woman", "Yoda", "Yoshi", "Zelda", "Zeus", "Naruto", "Beast Boy", "Conor Mcgregor", "Murloc" , "Thrall", "Prince Zuko") ;


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

            while(guess == guess2) {
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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(MainActivity.this, CoverActivity.class);

        if(v == mLoginButton) {
            intent.putExtra("charOne" , Parcels.wrap(mCharacterOne));
            intent.putExtra("charTwo" , Parcels.wrap(mCharacterTwo));

            startActivity(intent);
            Toast.makeText(MainActivity.this, "Just tap to vote!", Toast.LENGTH_LONG).show();
        }
    }

}
