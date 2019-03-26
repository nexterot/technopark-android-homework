package ru.nexterot.homework1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements NumberListFragment.ItemClickHandler {

    public static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("FRAGMENT_DEBUG", "activity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_LIST_FRAGMENT);
        if (fragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new NumberListFragment(), TAG_LIST_FRAGMENT);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("FRAGMENT_DEBUG", "activity destroyed");
    }

    @Override
    public void onNumSelected(String num, int color) {
        Bundle bundle = new Bundle();
        bundle.putString("num", num);
        bundle.putInt("color", color);
        Fragment fragment = new NumberDisplayFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}