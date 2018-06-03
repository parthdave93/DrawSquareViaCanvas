package com.parthdave93.drawsquareviacanvas.ui.printvalues;

import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.parthdave93.drawsquareviacanvas.R;
import com.parthdave93.drawsquareviacanvas.model.FingerPath;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrintFormatsActivity extends AppCompatActivity {

    ArrayList<FingerPath> fingerPaths;

    public static void start(Context context, ArrayList<FingerPath> fingerPaths) {
        Intent starter = new Intent(context, PrintFormatsActivity.class);
        starter.putExtra("fingerPaths", fingerPaths);
        context.startActivity(starter);
    }

    @BindView(R.id.tvPrintFormats)
    TextView tvPrintFormats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_formats);
        ButterKnife.bind(this);

        fingerPaths = getIntent().getParcelableArrayListExtra("fingerPaths");

        StringBuilder stringBuilder = new StringBuilder();

        for (FingerPath fingerPath : fingerPaths) {
            for (RectF rectF : fingerPath.getmPath()) {
                String point1 = String.format("(%d, %d)", (int) rectF.left, (int) rectF.top);
                String point2 = String.format("(%d, %d)", (int) rectF.right, (int) rectF.top);
                String point3 = String.format("(%d, %d)", (int) rectF.left, (int) rectF.bottom);
                String point4 = String.format("(%d, %d)", (int) rectF.right, (int) rectF.bottom);
                stringBuilder.append(String.format("[%s, %s, %s, %s]", point1, point2, point3, point4));
                stringBuilder.append("\n\n");
            }
        }

        tvPrintFormats.setText(stringBuilder);
    }

}
