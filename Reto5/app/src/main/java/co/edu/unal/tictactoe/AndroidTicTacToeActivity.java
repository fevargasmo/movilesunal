package co.edu.unal.tictactoe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.harding.tictactoe.BoardView;
import edu.harding.tictactoe.TicTacToeGame;

public class AndroidTicTacToeActivity extends AppCompatActivity {
    private TicTacToeGame mGame;

    private Button mBoardButtons[];
    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;

    // Various text displayed
    private TextView mInfoTextView;
    private BoardView mBoardView;
    private boolean mGameOver = false;
    private char mGoFirst = TicTacToeGame.HUMAN_PLAYER;
    private boolean mSoundOn = true;


    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;


    // Listen for touches on the board

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {
            // Determine which cell was touched
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;

            if (!mGameOver && setMove(TicTacToeGame.HUMAN_PLAYER, pos)) {
                Log.d("getComputerMove ", "getComputerMove ");
                mGoFirst = mGoFirst == TicTacToeGame.HUMAN_PLAYER ? TicTacToeGame.COMPUTER_PLAYER
                        : TicTacToeGame.HUMAN_PLAYER;
                if (mSoundOn) {
                    try {
                        mHumanMediaPlayer.start(); // Play the sound effect
                    } catch (Exception e) {

                    }
                }
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                  //  mInfoTextView.setText("Turno computador");
                    turnComputer();
                } else
                    endGame(winner);

            }

            // So we aren't notified of continued events when finger is moved
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new TicTacToeGame();

        mGame = new TicTacToeGame();

        mBoardView = (BoardView) findViewById(R.id.board);
        // Listen for touches on the board
        mBoardView.setOnTouchListener(mTouchListener);
        mBoardView.setGame(mGame);

        startNewGame();
        mGame.displayBoard();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.electricidad);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.fallo);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }
        return false;
    }

    private void startNewGame() {
        mGame.clearBoard();
        mBoardView.invalidate();
        mGameOver = false;

    }

    // Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }
        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled()) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                // If no winner yet, let the computer make a move
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText("It's Android's turn.");
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();

                if (winner == 0)
                    mInfoTextView.setText("It's your turn.");
                else if (winner == 1)
                    mInfoTextView.setText("It's a tie!");
                else if (winner == 2)
                    mInfoTextView.setText("You won!");
                else
                    mInfoTextView.setText("Android won!");
            }
        }
        }
    }

    private boolean setMove(char player, int location) {
        if (mGame.setMove(player, location)) {
            mBoardView.invalidate(); // Redraw the board
            return true;
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch(id) {
            case DIALOG_DIFFICULTY_ID:
                builder.setTitle(R.string.difficulty_choose);

                final CharSequence[] levels = {
                        getResources().getString(R.string.difficulty_easy),
                        getResources().getString(R.string.difficulty_harder),
                        getResources().getString(R.string.difficulty_expert)};

                final int selected = mGame.getDifficultyLevel().ordinal();
                builder.setSingleChoiceItems(levels, selected,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                dialog.dismiss();   // Close dialog
                                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.values()[item]);

                                // Display the selected difficulty level
                                Toast.makeText(getApplicationContext(), levels[item],
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog = builder.create();

                break;

            case DIALOG_QUIT_ID:
                // Create the quit confirmation dialog

                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AndroidTicTacToeActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();

                break;

        }

        return dialog;
    }

    private void endGame(int winner) {

        mGameOver = true;
    }

    private void turnComputer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                int move = mGame.getComputerMove();
                setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                mGoFirst = mGoFirst == TicTacToeGame.HUMAN_PLAYER ? TicTacToeGame.COMPUTER_PLAYER
                        : TicTacToeGame.HUMAN_PLAYER;
                mBoardView.invalidate();
                if (mSoundOn) {
                    try {
                        mComputerMediaPlayer.start(); // Play the sound effect
                    } catch (Exception e) {
                    }
                }
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                   // mInfoTextView.setText("Turno humano");
                } else
                    endGame(winner);
            }
        }, 1000);
    }


    }
