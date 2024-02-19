package com.oliveskies.sous_chef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Half;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.oliveskies.sous_chef.fragments.AddRecipeFragment;
import com.oliveskies.sous_chef.fragments.CookbookFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navBar;

    CookbookFragment cookbookFragment = new CookbookFragment();
    AddRecipeFragment addRecipeFragment = new AddRecipeFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentView, cookbookFragment);
        fragmentTransaction.commit();

        navBar = findViewById(R.id.nav_view);
        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_cookbook) {
                    fragmentManager.beginTransaction().replace(R.id.fragmentView, cookbookFragment).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.navigation_history) {
                    return true;
                } else if (itemId == R.id.navigation_add_recipe) {
                    fragmentManager.beginTransaction().replace(R.id.fragmentView, addRecipeFragment).addToBackStack(null).commit();
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}