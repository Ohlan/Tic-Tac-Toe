package com.ohlanpushpender.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int activePlayer = 0;
    int gameActive = 1;
    int filledCells = 0;
    // Player's Representation
    // 0 = O
    // 1 = X
    int[][] winningState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9},
            {1, 4, 7}, {2, 5, 8}, {3, 6, 9},
            {1, 5, 9}, {3, 5, 7}};
    MediaPlayer player;

    public boolean isDraw() {
        if (filledCells >= 9) {
            player = MediaPlayer.create(this, R.raw.draw);
            player.start();
            return true;
        } else
            return false;
    }

    public boolean isWinningState() {
        for (int i = 0; i < 8; i++) {
            if ((gameState[winningState[i][0] - 1] == gameState[winningState[i][1] - 1]) && (gameState[winningState[i][1] - 1] == gameState[winningState[i][2] - 1])
                    && gameState[winningState[i][0] - 1] != 2) {
                player = MediaPlayer.create(this, R.raw.win);
                player.start();
                return true;
            }
        }
        return false;
    }

    public void resetGame(View view) {
        if (gameActive == 0) {
            for (int i = 0; i < 9; i++)
                gameState[i] = 2;
            activePlayer = 0;
            gameActive = 1;
            filledCells = 0;
            String s;
            for (int i = 1; i <= 9; i++) {
                s = "i";
                s += Integer.toString(i);
                ImageView img = (ImageView) findViewById(getResources().getIdentifier(s, "id", getPackageName()));
                img.setImageResource(0);
            }
            TextView status = (TextView) view;
            status.setText("First Player's turn");
        }
    }

    public void onTap(View view) {
        player = MediaPlayer.create(this, R.raw.tap);
        player.start();
        if (gameActive == 1) {
            ImageView img = (ImageView) view;
            int tappedImage = parseInt((String) img.getTag());
            TextView status = (TextView) findViewById(R.id.textViewTurn);
            if (gameState[tappedImage] == 2) {
                filledCells++;
                if (activePlayer == 0) {
                    activePlayer = 1;
                    gameState[tappedImage] = 0;
                    img.setImageResource(R.drawable.o);
                    if (isWinningState()) {
                        status.setText("First Player won!!!\nTap to play again.");
                        gameActive = 0;
                    } else {
                        if (isDraw()) {
                            status.setText("Oops! it's a draw.\nTap to play again.");
                            gameActive = 0;
                        } else {
                            status.setText("Second Player's turn");
                        }
                    }

                } else {
                    activePlayer = 0;
                    gameState[tappedImage] = 1;
                    img.setImageResource(R.drawable.x);
                    if (isWinningState()) {
                        status.setText("Second Player won!!!\nTap to play again.");
                        gameActive = 0;
                    } else {
                        if (isDraw()) {
                            status.setText("Oops! it's a draw\nTap to play again.");
                            gameActive = 0;
                        } else {
                            status.setText("First Player's turn");
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Invalid move! please tap on empty cell", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}