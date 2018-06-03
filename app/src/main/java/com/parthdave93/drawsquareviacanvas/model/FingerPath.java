package com.parthdave93.drawsquareviacanvas.model;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FingerPath implements Parcelable {

    private int color;
    private int strokeWidth;
    private ArrayList<RectF> mPath;
    private int squareWidth;

    public FingerPath(int color, int strokeWidth, int squareWidth, ArrayList<RectF> mPath) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.squareWidth = squareWidth;
        this.mPath = mPath;
    }

    protected FingerPath(Parcel in) {
        color = in.readInt();
        strokeWidth = in.readInt();
        mPath = in.createTypedArrayList(RectF.CREATOR);
        squareWidth = in.readInt();
    }

    public static final Creator<FingerPath> CREATOR = new Creator<FingerPath>() {
        @Override
        public FingerPath createFromParcel(Parcel in) {
            return new FingerPath(in);
        }

        @Override
        public FingerPath[] newArray(int size) {
            return new FingerPath[size];
        }
    };

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public ArrayList<RectF> getmPath() {
        return mPath;
    }

    public void setmPath(ArrayList<RectF> mPath) {
        this.mPath = mPath;
    }

    public int getSquareWidth() {
        return squareWidth;
    }

    public void setSquareWidth(int squareWidth) {
        this.squareWidth = squareWidth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(color);
        dest.writeInt(strokeWidth);
        dest.writeTypedList(mPath);
        dest.writeInt(squareWidth);
    }
}
