package dev.m.hussein.procabtask.ui.fragment;

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

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.m.hussein.procabtask.R;
import dev.m.hussein.procabtask.config.Invalidation;
import dev.m.hussein.procabtask.ui.activity.MainActivity;
import dev.m.hussein.procabtask.ui.activity.RegisterActivity;
import dev.m.hussein.procabtask.ui.dialog.DatePickerDialogFragment;
import dev.m.hussein.procabtask.ui.interfaces.OnNextEnable;

/**
 * Created by Dev. M. Hussein on 11/18/2017.
 */

public class FragmentRegister3 extends Fragment {

    Context context;
    private OnNextEnable onNextEnable;
    @BindView(R.id.password) AppCompatEditText password;
    @BindView(R.id.password2) AppCompatEditText confirmPassword;
    @BindView(R.id.finish) AppCompatButton finish;

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


        password.addTextChangedListener(new CustomTextWatcher());
        confirmPassword.addTextChangedListener(new CustomTextWatcher());

        finish.setOnClickListener(view -> {
            startActivity(new Intent(context , MainActivity.class));
            getActivity().supportFinishAfterTransition();
        });

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


    private boolean isAllViewsNotEmpty() {

        if (TextUtils.isEmpty(password.getText())) return false;
        if (TextUtils.isEmpty(confirmPassword.getText())) return false;
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) return false;

        return true;
    }
    public void addOnNextEnable(OnNextEnable onNextEnable) {
        this.onNextEnable = onNextEnable;
    }
}
