package ru.nexterot.homework1;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NumberListFragment extends Fragment {
    public static final int SPAN_COUNT_PORTRAIT = 3;
    public static final int SPAN_COUNT_LANDSCAPE = 4;

    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 100;

    public int currentNumber = MAX_NUMBER;

    private MyAdapter adapter;

    interface ItemClickHandler {
        void onNumSelected(String num, int color);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        /*
        if (savedInstanceState != null) {
            currentNumber = savedInstanceState.getInt("maxNum", MAX_NUMBER);
        } else {
            Log.d("Fragment", "no savedInstanceState on create");
            currentNumber = MAX_NUMBER;
        }
        */

        ArrayList<String> strings = new ArrayList<>();
        fillList(strings);
        adapter = new MyAdapter(strings);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recycler_list);

        boolean isPortrait = (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int spanCount = (isPortrait) ? SPAN_COUNT_PORTRAIT : SPAN_COUNT_LANDSCAPE;
        final GridLayoutManager layout = new GridLayoutManager(this.getContext(), spanCount);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);

        v.findViewById(R.id.button_add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem();
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        /*
        outState.putInt("maxNum", currentNumber);
        */
    }

    void fillList(List<String> toFill) {
        for (int i = MIN_NUMBER; i <= currentNumber; i++) {
            toFill.add(i + "");
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = ((TextView) v).getText().toString();
                    int color = ((TextView) v).getCurrentTextColor();
                    MainActivity activity = (MainActivity) getActivity();
                    if (activity != null) {
                        activity.onNumSelected(num, color);
                    }
                }
            });
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
}
