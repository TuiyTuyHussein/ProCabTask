package dev.m.hussein.procabtask.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.m.hussein.procabtask.R;
import dev.m.hussein.procabtask.config.Invalidation;
import dev.m.hussein.procabtask.ui.activity.RegisterActivity;
import dev.m.hussein.procabtask.ui.dialog.DatePickerDialogFragment;
import dev.m.hussein.procabtask.ui.interfaces.OnNextEnable;

/**
 * Created by Dev. M. Hussein on 11/18/2017.
 */

public class FragmentRegister1 extends Fragment  {

    Context context;
    private OnNextEnable onNextEnable;

    @BindView(R.id.firstName) AppCompatEditText firstName;
    @BindView(R.id.lastName) AppCompatEditText lastName;
    @BindView(R.id.email) AppCompatEditText email;
    @BindView(R.id.contactNumber) AppCompatEditText contactNumber;
    @BindView(R.id.currentAddress) AppCompatEditText currentAddress;
    @BindView(R.id.maritalStatus) AppCompatEditText maritalStatus;
    @BindView(R.id.motherNationality) AppCompatEditText motherNationality;
    @BindView(R.id.employerName) AppCompatEditText employerName;
    @BindView(R.id.engagementDate) AppCompatEditText engagementDate;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy" , Locale.getDefault());
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        return inflater.inflate(R.layout.fragment_register_1 , container , false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);

        if (onNextEnable != null) onNextEnable.setNextEnable(true  , false);

        setupViews();
    }

    private void setupViews() {
        calendar = Calendar.getInstance();
        engagementDate.setText(dateFormat.format(calendar.getTime()));

        firstName.addTextChangedListener(new CustomTextWatcher());
        lastName.addTextChangedListener(new CustomTextWatcher());
        email.addTextChangedListener(new CustomTextWatcher());
        contactNumber.addTextChangedListener(new CustomTextWatcher());
        currentAddress.addTextChangedListener(new CustomTextWatcher());
        maritalStatus.addTextChangedListener(new CustomTextWatcher());
        motherNationality.addTextChangedListener(new CustomTextWatcher());
        employerName.addTextChangedListener(new CustomTextWatcher());
        engagementDate.addTextChangedListener(new CustomTextWatcher());
        engagementDate.setKeyListener(null);
        engagementDate.setOnClickListener(view -> {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.setOnDateSet(this.calendar, (dialog, calendar) -> {
                this.calendar = calendar;
                engagementDate.setText(dateFormat.format(this.calendar.getTime()));
                dialog.dismiss();
            });
            datePickerDialogFragment.show(getChildFragmentManager() , "datePicker");
        });
    }

    public void addOnNextEnable(OnNextEnable onNextEnable) {
        this.onNextEnable = onNextEnable;
    }



    private class CustomTextWatcher implements TextWatcher{



        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (onNextEnable != null) onNextEnable.setNextEnable(true , isAllViewsNotEmpty());

        }
    }

    private boolean isAllViewsNotEmpty() {

        if (TextUtils.isEmpty(firstName.getText())) return false;
        if (TextUtils.isEmpty(lastName.getText())) return false;
        if (TextUtils.isEmpty(email.getText()) || !Invalidation.isValidEmail(email.getText().toString())) return false;
        if (TextUtils.isEmpty(contactNumber.getText())) return false;
        if (TextUtils.isEmpty(currentAddress.getText())) return false;
        if (TextUtils.isEmpty(maritalStatus.getText())) return false;
        if (TextUtils.isEmpty(motherNationality.getText())) return false;
        if (TextUtils.isEmpty(employerName.getText())) return false;
        if (TextUtils.isEmpty(engagementDate.getText())) return false;


        return true;
    }
}
