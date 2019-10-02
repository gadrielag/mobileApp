package co.edu.unal.tictactoeadri;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

public class BoardView extends View {
    // Width of the board grid lines
    public static final int GRID_WIDTH = 6;

    private Bitmap mHumanBitmap;
    private Bitmap mComputerBitmap;


    public BoardView(Context context) {
        super(context);
    }
}
