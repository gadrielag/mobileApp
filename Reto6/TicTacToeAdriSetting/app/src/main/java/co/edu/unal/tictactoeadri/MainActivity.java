package co.edu.unal.tictactoeadri;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{
    private TicTacToeGame mGame;
    private BoardView mBoardView;

    // Various text displayed
    private TextView mInfoTextView;
    boolean mGameOver;
    private int winner;


    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;

    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;
    Handler handler = new Handler();
    private CharSequence player_turn;

    @Override
    protected void onResume() {
        super.onResume();

        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_ice);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_dragon);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }



    private OnTouchListener mTouchListener = new OnTouchListener()
    {
        public boolean onTouch(View v, MotionEvent event) {
            // Determine which cell was touched
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;

            if ( !mGameOver  && setMove(TicTacToeGame.HUMAN_PLAYER, pos) ) {
                winner = mGame.checkForWinner();
                player_turn = mInfoTextView.getText();
                mHumanMediaPlayer.start();
                mInfoTextView.setText("It's your turn.");
                if (winner == 0)
                {
                    if (player_turn != "It's Android's turn.")
                    {

                        handler.postDelayed(new Runnable() {
                        public void run()
                        {
                            mInfoTextView.setText("It's Android's turn.");
                            mComputerMediaPlayer.start();
                            int move = mGame.getComputerMove();
                            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                            mBoardView.invalidate();
                            //mHumanMediaPlayer.stop();
                            winner = mGame.checkForWinner();
                        }
                        }, 4000);
                        mInfoTextView.setText("It's your turn.");
                    } else{
                        handler.postDelayed(new Runnable() {
                            public void run()
                            {
                                mInfoTextView.setText("It's Android's turn.");
                                mComputerMediaPlayer.start();
                                int move = mGame.getComputerMove();
                                setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                                mBoardView.invalidate();
                                //mHumanMediaPlayer.stop();
                                winner = mGame.checkForWinner();
                            }
                        }, 4000);
                        winner = mGame.checkForWinner();

                    }
                    // winner = mGame.checkForWinner();
                }
                else if (winner == 1)
                    mInfoTextView.setText("It's a tie!");
                else if (winner == 2) {
                    mInfoTextView.setText("You won!");
                    mGameOver = true;
                } else {
                    mInfoTextView.setText("Android won!");
                    mGameOver = true;
                }

            }
            return false;
        }

        private boolean setMove(char player, int location) {
            if (mGame.setMove(player, location)) {
                mBoardView.invalidate();   // Redraw the board
                return true;
            }
            return false;
        }
    };

    //@SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInfoTextView = (TextView) findViewById(R.id.information);

        mGame = new TicTacToeGame();
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setGame(mGame);
        mBoardView.setOnTouchListener(mTouchListener);

        mInfoTextView = (TextView) findViewById(R.id.information);

        //mGame = new TicTacToeGame();
        startNewGame();
        mGameOver = false;

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
                mGameOver = false;
                return true;
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                startNewGame();
                mGameOver = false;
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
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

                int selected= mGame.getDifficultyLevel().getValue();
                // selected is the radio button that should be selected.

                builder.setSingleChoiceItems(levels, selected,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                dialog.dismiss();   // Close dialog

                                switch(item){
                                    case 0:
                                        mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
                                        break;
                                    case 1:
                                        mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
                                        break;
                                    case 2:
                                        mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
                                        break;
                                }

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
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();

                break;
        }

        return dialog;
    }




    // Set up the game board.
    void startNewGame()
    {

        mGame.clearBoard();
        mBoardView.invalidate();

        // Human goes first
        mInfoTextView.setText(R.string.first_human);

    }
}
