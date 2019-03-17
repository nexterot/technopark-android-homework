package ru.nexterot.homework1;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int SPAN_COUNT_PORTRAIT = 3;
    public static final int SPAN_COUNT_LANDSCAPE = 4;

    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 100;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> strings = new ArrayList<>();
        fillList(strings);

        RecyclerView recyclerView = findViewById(R.id.my_list);

        boolean isPortrait = (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int spanCount = (isPortrait) ? SPAN_COUNT_PORTRAIT : SPAN_COUNT_LANDSCAPE;
        final GridLayoutManager layout = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layout);

        adapter = new MyAdapter(strings);
        recyclerView.setAdapter(adapter);
    }

    void fillList(List<String> toFill) {
        for (int i = MIN_NUMBER; i <= MAX_NUMBER; i++) {
            toFill.add(i + "");
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<String> mData;

        MyAdapter(List<String> data) {
            mData = data;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v = inflater.inflate(R.layout.list_elem, viewGroup, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            String str = mData.get(i);
            myViewHolder.mTextView.setText(str);
            if (Integer.parseInt(str) % 2 == 0) {
                myViewHolder.mTextView.setTextColor(Color.RED);
            } else {
                myViewHolder.mTextView.setTextColor(Color.BLUE);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        void addItem() {
            if (mData.size() > 0) {
                String lastItem = mData.get(mData.size() - 1);
                String newItem = Integer.parseInt(lastItem) + 1 + "";
                mData.add(newItem);
            } else {
                mData.add(MIN_NUMBER + "");
            }
            adapter.notifyItemInserted(mData.size() - 1);
        }
    }

    public void onAddItemClick(View view) {
        adapter.addItem();
    }
}