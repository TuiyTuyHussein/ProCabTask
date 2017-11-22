package dev.m.hussein.procabtask.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.m.hussein.procabtask.R;
import dev.m.hussein.procabtask.config.User;
import dev.m.hussein.procabtask.ui.fragment.FragmentRegister1;
import dev.m.hussein.procabtask.ui.fragment.FragmentRegister2;
import dev.m.hussein.procabtask.ui.fragment.FragmentRegister3;
import dev.m.hussein.procabtask.ui.interfaces.OnNextEnable;

public class RegisterActivity extends AppCompatActivity implements OnNextEnable {

    @BindView(R.id.tabDots) TabLayout tabLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.next) AppCompatButton nextButton;


    private int currentPosition = -1;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        setupTabs();
        setupToolbar();
        nextFragment();

    }

    private void setupToolbar() {
        toolbar.setNavigationOnClickListener(view -> {
            Toast.makeText(this , "Navigation Button" , Toast.LENGTH_SHORT).show();
        });

        nextButton.setOnClickListener(view -> {

            nextFragment();
        });
    }



    Fragment fragment = null;
    private void nextFragment() {
        currentPosition ++;

        switch (currentPosition){
            case 0:
                fragment = new FragmentRegister1();
                ((FragmentRegister1)fragment).addOnNextEnable(user, this);
                break;

            case 1:
                fragment = new FragmentRegister2();
                ((FragmentRegister2)fragment).addOnNextEnable(user , this);
                break;

            case 2:
                fragment = new FragmentRegister3();
                ((FragmentRegister3)fragment).addOnNextEnable(user ,this);
                break;
        }

        if (tabLayout != null && tabLayout.getTabAt(currentPosition) != null)
            tabLayout.getTabAt(currentPosition).select();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

        if (currentPosition > 0) {
            transaction.replace(R.id.container, fragment ,String.valueOf(currentPosition))
                    .addToBackStack(String.valueOf(currentPosition))
                    .commitAllowingStateLoss();
        }else{
            transaction.replace(R.id.container, fragment, String.valueOf(currentPosition))
                    .commitAllowingStateLoss();
        }
    }

    private void previousFragment(){
        currentPosition --;
        if (tabLayout != null && tabLayout.getTabAt(currentPosition) != null)
            tabLayout.getTabAt(currentPosition).select();
        getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (fragment != null) fragment.onRequestPermissionsResult(requestCode , permissions , grantResults);
    }

    private void setupTabs() {
        tabLayout.setClickable(false);
        tabLayout.setSelected(false);
        tabLayout.clearOnTabSelectedListeners();
        tabLayout.addTab(tabLayout.newTab() , true);
        tabLayout.addTab(tabLayout.newTab() , false);
        tabLayout.addTab(tabLayout.newTab() , false);



        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){

           previousFragment();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void setNextEnable(boolean visible, boolean enable) {
        nextButton.setEnabled(enable);
        nextButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }



}
