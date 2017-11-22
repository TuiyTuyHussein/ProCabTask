package dev.m.hussein.procabtask.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.m.hussein.procabtask.R;
import dev.m.hussein.procabtask.config.User;
import dev.m.hussein.procabtask.ui.activity.MainActivity;
import dev.m.hussein.procabtask.ui.interfaces.OnNextEnable;

/**
 * Created by Dev. M. Hussein on 11/18/2017.
 */

public class FragmentRegister3 extends Fragment {

    Context context;
    @BindView(R.id.password) AppCompatEditText password;
    @BindView(R.id.password2) AppCompatEditText confirmPassword;
    @BindView(R.id.finish) AppCompatButton finish;
    ProgressDialog dialog;
    private User user;
    private OnNextEnable onNextEnable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        return inflater.inflate(R.layout.fragment_register_3 , container , false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);

        if (onNextEnable != null) onNextEnable.setNextEnable(false  , true);
        enableFinish(false);
        setupViews();
    }

    private void enableFinish(boolean enable) {
        finish.setEnabled(enable);
        finish.setClickable(enable);
    }


    private void setupViews() {


        dialog = new ProgressDialog(context);
        dialog.setMessage("Registering ...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        password.addTextChangedListener(new CustomTextWatcher());
        confirmPassword.addTextChangedListener(new CustomTextWatcher());

        finish.setOnClickListener(view -> {
            dialog.show();
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(user.email , password.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            user.id = task.getResult().getUser().getUid();
                            Toast.makeText(context , "Register Successfully" , Toast.LENGTH_LONG).show();
                            addUserToDataBase();
                        }else{
                            dialog.dismiss();
                            Toast.makeText(context , task.getException() == null ?
                                    "Couldn't register , please try again" : task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    });

        });

    }

    private void addUserToDataBase() {
        FirebaseDatabase.getInstance().getReference().child("USERS")
                .child(user.id).setValue(user)
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()){
                        Toast.makeText(context , "User Added To Database Successfully" , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(context , MainActivity.class));
                        getActivity().supportFinishAfterTransition();
                    }else{
                        Toast.makeText(context , "Couldn't Add user to database , please try again" , Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean isAllViewsNotEmpty() {

        if (TextUtils.isEmpty(password.getText())) return false;
        if (TextUtils.isEmpty(confirmPassword.getText())) return false;
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) return false;



        return true;
    }

    public void addOnNextEnable(User user, OnNextEnable onNextEnable) {
        this.user = user;
        this.onNextEnable = onNextEnable;
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
            enableFinish(isAllViewsNotEmpty());
        }
    }
}
