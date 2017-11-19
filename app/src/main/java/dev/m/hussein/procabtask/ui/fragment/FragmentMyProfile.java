package dev.m.hussein.procabtask.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.m.hussein.procabtask.R;
import dev.m.hussein.procabtask.ui.interfaces.OnLoginClick;

/**
 * Created by Dev. M. Hussein on 11/18/2017.
 */

public class FragmentMyProfile extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Context context;
    private OnLoginClick onLoginClick;

    @BindView(R.id.loginRegisterButton) AppCompatButton loginRegisterButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        return inflater.inflate(R.layout.fragment_my_profile , container , false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        setupToolbar();
        loginRegisterButton.setOnClickListener(view1 -> {
            if (onLoginClick != null) onLoginClick.onLoginClick();
        });

    }

    private void setupToolbar() {
        toolbar.setNavigationOnClickListener(view -> {
            Toast.makeText(context , "Navigation Button" , Toast.LENGTH_SHORT).show();
        });

        toolbar.inflateMenu(R.menu.profile_menu);

    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);
    }

    public void addOnRegisterClick(OnLoginClick onLoginClick) {
        this.onLoginClick = onLoginClick;
    }
}
