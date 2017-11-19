package dev.m.hussein.procabtask.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
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

public class FragmentRegister2 extends Fragment {

    Context context;
    private OnNextEnable onNextEnable;
    @BindView(R.id.nationality) AppCompatEditText nationality;
    @BindView(R.id.pass_number) AppCompatEditText passNumber;
    @BindView(R.id.place_of_birth) AppCompatEditText placeOfBirth;
    @BindView(R.id.date_of_birth) AppCompatEditText dateOfBirth;
    @BindView(R.id.place_of_issue) AppCompatEditText placeOfIssue;
    @BindView(R.id.pass_issue_date) AppCompatEditText passIssueDate;
    @BindView(R.id.cid_number) AppCompatEditText cidNumber;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy" , Locale.getDefault());
    private Calendar birthCalendar , passCalendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        return inflater.inflate(R.layout.fragment_register_2 , container , false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);


        if (onNextEnable != null) onNextEnable.setNextEnable(true  , false);
        setupViews();
    }



    private void setupViews() {
        birthCalendar = Calendar.getInstance();
        passCalendar = Calendar.getInstance();

        dateOfBirth.setText(dateFormat.format(birthCalendar.getTime()));
        passIssueDate.setText(dateFormat.format(passCalendar.getTime()));

        nationality.addTextChangedListener(new CustomTextWatcher());
        passNumber.addTextChangedListener(new CustomTextWatcher());
        placeOfBirth.addTextChangedListener(new CustomTextWatcher());
        dateOfBirth.addTextChangedListener(new CustomTextWatcher());
        dateOfBirth.setKeyListener(null);
        dateOfBirth.setOnClickListener(view -> {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.setOnDateSet(this.birthCalendar, (dialog, calendar) -> {
                this.birthCalendar = calendar;
                dateOfBirth.setText(dateFormat.format(this.birthCalendar.getTime()));
                dialog.dismiss();
            });
            datePickerDialogFragment.show(getChildFragmentManager() , "datePicker");
        });
        placeOfIssue.addTextChangedListener(new CustomTextWatcher());
        passIssueDate.addTextChangedListener(new CustomTextWatcher());
        passIssueDate.setKeyListener(null);
        passIssueDate.setOnClickListener(view -> {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.setOnDateSet(this.passCalendar, (dialog, calendar) -> {
                this.passCalendar = calendar;
                passIssueDate.setText(dateFormat.format(this.passCalendar.getTime()));
                dialog.dismiss();
            });
            datePickerDialogFragment.show(getChildFragmentManager() , "datePicker");
        });
        cidNumber.addTextChangedListener(new CustomTextWatcher());


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
            if (onNextEnable != null) onNextEnable.setNextEnable(true , isAllViewsNotEmpty());

        }
    }

    private boolean isAllViewsNotEmpty() {

        if (TextUtils.isEmpty(nationality.getText())) return false;
        if (TextUtils.isEmpty(passNumber.getText())) return false;
        if (TextUtils.isEmpty(placeOfBirth.getText())) return false;
        if (TextUtils.isEmpty(dateOfBirth.getText())) return false;
        if (TextUtils.isEmpty(placeOfIssue.getText())) return false;
        if (TextUtils.isEmpty(passIssueDate.getText())) return false;
        if (TextUtils.isEmpty(cidNumber.getText())) return false;


        return true;
    }


    public void addOnNextEnable(OnNextEnable onNextEnable) {
        this.onNextEnable = onNextEnable;
    }
}
