package com.example.colormatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private CardAdapter cardAdapter;
    private List<Character> cardCharacters =new ArrayList<>();
    private int firstCardPosition = -1;
    private boolean isFlipping = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        initializeGame();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (isFlipping || cardAdapter.isCardFlipped(position)) {
                    return;
                }

                cardAdapter.flipCard(position);

                if (firstCardPosition == -1) {
                    firstCardPosition = position;
                } else {
                    isFlipping = true;
                    final int previousPosition = firstCardPosition;
                    firstCardPosition = -1;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (cardCharacters.get(previousPosition).equals(cardCharacters.get(position))) {
                                cardAdapter.addMatchedCard(previousPosition);
                                cardAdapter.addMatchedCard(position);
                            } else {
                                cardAdapter.flipCard(previousPosition);
                                cardAdapter.flipCard(position);
                            }

                            // Check if all pairs are matched after the card flip operation
                            if (cardAdapter.isAllMatched()) {
                                showWinDialog();
                            }
                            isFlipping = false;
                        }
                    }, 1000);
                }
            }
        });
    }

    private void initializeGame() {
        // Create a list of characters for the game
        cardCharacters.clear();
        char[] characters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        for (char c : characters) {
            cardCharacters.add(c);
        }
        Collections.shuffle(cardCharacters);

        // Initialize the adapter with shuffled characters
        cardAdapter = new CardAdapter(this, cardCharacters.toArray(new Character[0]));
        gridView.setAdapter(cardAdapter);
    }

    private void showWinDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("You Win! All pairs found.")
                .setCancelable(false)
                .create();

        dialog.show();

        // Automatically dismiss the dialog after 3 seconds and restart the game
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                initializeGame();
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}
