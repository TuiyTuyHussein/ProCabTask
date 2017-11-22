package dev.m.hussein.procabtask.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.m.hussein.procabtask.R;
import dev.m.hussein.procabtask.ui.fragment.FragmentLogin;
import dev.m.hussein.procabtask.ui.fragment.FragmentMyProfile;
import dev.m.hussein.procabtask.ui.interfaces.OnLoginClick;

public class ProfileActivity extends AppCompatActivity implements OnLoginClick {




    private int currentPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);



        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this  , MainActivity.class));
            supportFinishAfterTransition();
            return;
        }
        nextFragment();
    }




    private void nextFragment() {
        currentPosition ++;
        Fragment fragment = null;
        switch (currentPosition){
            case 0:
                fragment = new FragmentMyProfile();
                ((FragmentMyProfile)fragment).addOnRegisterClick(this);
                break;

            case 1:
                fragment = new FragmentLogin();
                ((FragmentLogin)fragment).addOnRegisterClick(this);
                break;


        }



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
        getSupportFragmentManager().popBackStack();
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
    public void onLoginClick() {
        nextFragment();
    }

    @Override
    public void onCloseClick() {
        previousFragment();
    }
}
