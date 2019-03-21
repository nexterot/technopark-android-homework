package ru.nexterot.homework1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NumberDisplayFragment extends Fragment {
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_number, container, false);
        if (getArguments() != null) {
            int color = getArguments().getInt("color", R.color.colorPrimary);
            String number = getArguments().getString("num", "-1");
            TextView textView = v.findViewById(R.id.text);
            textView.setTextColor(color);
            textView.setText(number);
        }
        return v;
    }
}
