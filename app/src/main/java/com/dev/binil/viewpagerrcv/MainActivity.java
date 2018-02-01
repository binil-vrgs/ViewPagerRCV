package com.dev.binil.viewpagerrcv;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.dev.binil.viewpager.ViewPagerRCV;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setList();
        setList2();
    }

    private void setList() {
        ViewPagerRCV recyclerView = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RCVAdapter());

        recyclerView.addOnScrollChangeListener(new ViewPagerRCV.OnScrollChangeListener() {
            @Override
            public void onScrollEnd(int topPosition) {
                System.out.println("ViewPagerRCVFragment.onScrollEnd." + topPosition);
            }

            @Override
            public void onScroll(int topPosition, int visibleItemsCount, int ItemsCount, int direction) {
                System.out.println("ViewPagerRCVFragment.onScroll" +
                        "_Top:" + topPosition +
                        "_VisibleItemCount:" + visibleItemsCount +
                        "_itemsCount:" + ItemsCount +
                        "_Direction:" + direction
                );
            }
        });
    }

    private void setList2() {
        ViewPagerRCV recyclerView = findViewById(R.id.list2);
//        recyclerView.setStartOffSet(Offset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RCVAdapter2());

        recyclerView.addOnScrollChangeListener(new ViewPagerRCV.OnScrollChangeListener() {
            @Override
            public void onScrollEnd(int topPosition) {
                System.out.println("ViewPagerRCVFragment.onScrollEnd." + topPosition);
            }

            @Override
            public void onScroll(int topPosition, int visibleItemsCount, int ItemsCount, int direction) {
                System.out.println("ViewPagerRCVFragment.onScroll" +
                        "_Top:" + topPosition +
                        "_VisibleItemCount:" + visibleItemsCount +
                        "_itemsCount:" + ItemsCount +
                        "_Direction:" + direction
                );
            }
        });
    }

    private class RCVAdapter extends RecyclerView.Adapter<RCVAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.mName.setText(R.string.item);
            holder.mCount.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 200;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView mName, mCount;

            ViewHolder(View itemView) {
                super(itemView);

                mName = itemView.findViewById(R.id.name);
                mCount = itemView.findViewById(R.id.count);
            }
        }
    }

    private class RCVAdapter2 extends RecyclerView.Adapter<RCVAdapter2.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.mName.setText(R.string.item);
            holder.mCount.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 200;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView mName, mCount;

            ViewHolder(View itemView) {
                super(itemView);

//                Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
//                int offset = 200;
//                layoutParams.width = display.getWidth() - offset;
//                itemView.setLayoutParams(layoutParams);

                mName = itemView.findViewById(R.id.name);
                mCount = itemView.findViewById(R.id.count);
            }
        }
    }
}
