package com.dev.binil.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;

/**
 * @author binil on 11/28/17.
 */

public class ViewPagerRCV extends RecyclerView {
    public static final int SCROLL_TO_DOWN = 0;
    public static final int SCROLL_TO_UP = 1;
    private int allPixels;
    private int itemLength;
    private int orientation;
    private int mOffset;
    private int itemMargin;
    private int onScrollPosition = Integer.MAX_VALUE;
    private int onScrollEndPosition = Integer.MAX_VALUE;
    private OnScrollChangeListener mScrollChangeListener;

    private OnScrollListener mListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                scrollListToPosition(recyclerView, calculatePosition(recyclerView));
            }
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            allPixels += orientation == LinearLayoutManager.VERTICAL ? dy : dx;

            if (itemLength == 0) {
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    orientation = ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation();

                    itemLength = orientation == LinearLayoutManager.VERTICAL ?
                            recyclerView.getChildAt(0).getHeight() : recyclerView.getChildAt(0).getWidth();

                    LayoutParams layoutParams = ((LayoutParams) recyclerView.getChildAt(0).getLayoutParams());
                    itemMargin = orientation == LinearLayoutManager.VERTICAL ?
                            (layoutParams.topMargin + layoutParams.bottomMargin) :
                            (layoutParams.leftMargin + layoutParams.rightMargin);

                    itemLength += itemMargin;

                } else {
                    throw new RuntimeException(recyclerView.getContext().toString()
                            + " LayoutManager must be LinearLayoutManager.");
                }
            }


            if (mScrollChangeListener !=  null) {
                int currentPosition = calculatePosition(recyclerView);
                if (currentPosition == onScrollPosition){
                    return;
                }
                ViewPagerRCV viewPagerRCV = ViewPagerRCV.this;

                mScrollChangeListener.onScroll(
                        currentPosition,
                        ((LinearLayoutManager) viewPagerRCV.getLayoutManager()).findLastVisibleItemPosition()-currentPosition,
                        viewPagerRCV.getAdapter().getItemCount(),
                        onScrollPosition<currentPosition?   SCROLL_TO_DOWN:SCROLL_TO_UP);
                onScrollPosition = currentPosition;
            }
        }
    };

    public ViewPagerRCV(Context context) {
        super(context);
        this.addOnScrollListener(mListener);
    }

    public ViewPagerRCV(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addOnScrollListener(mListener);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewPagerRCV, 0, 0);
        setStartOffSet(a.getDimensionPixelSize(R.styleable.ViewPagerRCV_startOffset, 0));
    }

    public ViewPagerRCV(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.addOnScrollListener(mListener);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewPagerRCV, 0, 0);
        setStartOffSet(a.getDimensionPixelSize(R.styleable.ViewPagerRCV_startOffset, 0));
    }

    private int calculatePosition(RecyclerView recyclerView) {

        int expectedPosition = Math.round((allPixels) / itemLength);

        if (expectedPosition == -1) {
            expectedPosition = 0;
        } else if (expectedPosition >= recyclerView.getAdapter().getItemCount() - 2) {
            expectedPosition--;
        }

        if (allPixels > itemLength * expectedPosition && (allPixels - itemLength * expectedPosition) > (itemLength / 2)) {
            expectedPosition++;
        }
        return expectedPosition;
    }

    private void scrollListToPosition(RecyclerView recyclerView, int expectedPosition) {
        float targetScrollPos = expectedPosition * itemLength;
        float missingPx = targetScrollPos - allPixels;

        if (mOffset != 0) {
            missingPx -= ((mOffset - itemMargin) / 2);
        }

        if (missingPx != 0) {
            recyclerView.smoothScrollBy(
                    orientation == LinearLayoutManager.VERTICAL ? 0 : (int) missingPx,
                    orientation == LinearLayoutManager.VERTICAL ? (int) missingPx : 0, new DecelerateInterpolator());
        }

        if (mScrollChangeListener !=  null) {
            if (expectedPosition == onScrollEndPosition){
                return;
            }
            onScrollEndPosition = expectedPosition;
            mScrollChangeListener.onScrollEnd(expectedPosition);
        }
    }

    public void setStartOffSet(int offset) {
        mOffset = offset;
    }

    public void addOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mScrollChangeListener = onScrollChangeListener;
    }

    public void addOneItemTop() {
        allPixels += itemLength;
    }

    public interface OnScrollChangeListener {
        void onScrollEnd(int topPosition);
        void onScroll(int topPosition, int visibleItemsCount, int ItemsCount, int direction);
    }
}
