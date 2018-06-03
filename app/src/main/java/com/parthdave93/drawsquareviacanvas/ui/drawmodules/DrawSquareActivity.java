package com.parthdave93.drawsquareviacanvas.ui.drawmodules;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.parthdave93.drawsquareviacanvas.R;
import com.parthdave93.drawsquareviacanvas.ui.customviews.DrawSquareView;
import com.parthdave93.drawsquareviacanvas.ui.printvalues.PrintFormatsActivity;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.parthdave93.drawsquareviacanvas.ui.customviews.DrawSquareView.DEFAULT_COLOR;
import static com.parthdave93.drawsquareviacanvas.ui.customviews.DrawSquareView.MAX_SQUARE_SIZE;
import static com.parthdave93.drawsquareviacanvas.ui.customviews.DrawSquareView.MAX_STROKE_WIDTH;

public class DrawSquareActivity extends AppCompatActivity {

    @BindView(R.id.sbSquareSize)
    SeekBar sbSquareSize;
    @BindView(R.id.cbDrawContinuousSquare)
    CheckBox cbDrawContinuousSquare;
    @BindView(R.id.drawSquareView)
    DrawSquareView drawSquareView;
    @BindView(R.id.cbSquareSize)
    CheckBox cbSquareSize;
    @BindView(R.id.cbStrokeWidth)
    CheckBox cbStrokeWidth;
    @BindView(R.id.ivDrawColor)
    ImageView ivDrawColor;

    private int defaultAlphaValue = 1;
    private int defaultColorR = 1;
    private int defaultColorG = 1;
    private int defaultColorB = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_square);
        ButterKnife.bind(this);

        ivDrawColor.setBackgroundColor(DEFAULT_COLOR);
        sbSquareSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(cbSquareSize.isChecked())
                    drawSquareView.setSquareWidth(progress);
                if(cbStrokeWidth.isChecked())
                    drawSquareView.setStrokeWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cbDrawContinuousSquare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                drawSquareView.setDrawNewSquareOnMove(isChecked);
            }
        });

        cbSquareSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbStrokeWidth.setChecked(!isChecked);
                sbSquareSize.setProgress(drawSquareView.getSquareWidth());
                sbSquareSize.setMax(MAX_SQUARE_SIZE);
            }
        });

        cbStrokeWidth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbSquareSize.setChecked(!isChecked);
                sbSquareSize.setProgress(drawSquareView.getStrokeWidth());
                sbSquareSize.setMax(MAX_STROKE_WIDTH);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                drawSquareView.clearDrawing();
                return true;
            case R.id.printFormats:
                PrintFormatsActivity.start(this,drawSquareView.getPaths());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pickColor(){
        int red = Color.red(drawSquareView.getColor());
        int green = Color.green(drawSquareView.getColor());
        int blue = Color.blue(drawSquareView.getColor());
        int alpha = Color.alpha(drawSquareView.getColor());
        final ColorPicker cp = new ColorPicker(this, alpha, red, green, blue);

        /* Show color picker dialog */
        cp.show();

        /* Set a new Listener called when user click "select" */
        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {
                cp.dismiss();
                drawSquareView.setColor(color);
                ivDrawColor.setBackgroundColor(color);
            }
        });
    }

    public void onColorPick(View view){
        pickColor();
    }
}
