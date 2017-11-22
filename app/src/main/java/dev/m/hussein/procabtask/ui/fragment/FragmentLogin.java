package dev.m.hussein.procabtask.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.m.hussein.procabtask.R;
import dev.m.hussein.procabtask.config.Invalidation;
import dev.m.hussein.procabtask.ui.activity.MainActivity;
import dev.m.hussein.procabtask.ui.activity.ProfileActivity;
import dev.m.hussein.procabtask.ui.activity.RegisterActivity;
import dev.m.hussein.procabtask.ui.interfaces.OnLoginClick;

/**
 * Created by Dev. M. Hussein on 11/18/2017.
 */

public class FragmentLogin extends Fragment {

    Context context;

    @BindView(R.id.close) ImageButton close;
    @BindView(R.id.register) AppCompatTextView register;
    @BindView(R.id.email) AppCompatEditText email;
    @BindView(R.id.password) AppCompatEditText password;
    @BindView(R.id.login) AppCompatButton login;

    private OnLoginClick onLoginClick;

    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        return inflater.inflate(R.layout.fragment_login , container , false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        enableLogin(false);
        setupViews();
    }

    private void enableLogin(boolean enable) {
        login.setEnabled(enable);
        login.setClickable(enable);
    }


    private void setupViews() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Login ...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        close.setOnClickListener(view -> {
            if (onLoginClick != null) onLoginClick.onCloseClick();
        });

        login.setOnClickListener(view -> {
            dialog.show();
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.getText().toString() , password.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            startActivity(new Intent(context , MainActivity.class));
                            getActivity().supportFinishAfterTransition();
                        }else {
                            Toast.makeText(context , "Couldn't login , please check login info and try again" , Toast.LENGTH_LONG).show();
                        }
                    });

        });

        register.setOnClickListener(view -> startActivity(new Intent(context , RegisterActivity.class)));

        password.addTextChangedListener(new CustomTextWatcher());
        email.addTextChangedListener(new CustomTextWatcher());
    }



    private class CustomTextWatcher implements TextWatcher {



        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            enableLogin(isAllViewsNotEmpty());

        }
    }


    private boolean isAllViewsNotEmpty() {

        if (TextUtils.isEmpty(email.getText()) || !Invalidation.isValidEmail(email.getText().toString())) return false;
        if (TextUtils.isEmpty(password.getText())) return false;


        return true;
    }

    public void addOnRegisterClick(OnLoginClick onLoginClick) {
        this.onLoginClick = onLoginClick;
    }
}
