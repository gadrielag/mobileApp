package co.edu.unal.tictactoeadri;

import android.view.View;

class ButtonClickListener implements View.OnClickListener {
    int location;

    public ButtonClickListener(int location) {
        this.location = location;
    }

    @Override
    public void onClick(View v) {

    }

    /*public void onClick(View view) {
        if (mBoardButtons[location].isEnabled()) {
            setMove(TicTacToeGame.HUMAN_PLAYER, location);

            // If no winner yet, let the computer make a move
            int winner = mGame.checkForWinner();
            if (winner == 0) {
                mInfoTextView.setText("It's Android's turn.");
                int move = mGame.getComputerMove();
                setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                winner = mGame.checkForWinner();
            }

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
    */

}
