package dev.m.hussein.procabtask.ui.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.m.hussein.procabtask.R;
import dev.m.hussein.procabtask.config.User;
import dev.m.hussein.procabtask.ui.dialog.DatePickerDialogFragment;
import dev.m.hussein.procabtask.ui.interfaces.OnNextEnable;

/**
 * Created by Dev. M. Hussein on 11/18/2017.
 */

public class FragmentRegister2 extends Fragment {

    private static final int CIVIL_ID_COPY = 10;
    private static final int PASSPORT_COPY = 11;
    private static final int RESIDENCE_COPY = 12;
    private static final int OTHER_DOCUMENTS = 13;
    Context context;
    @BindView(R.id.nationality) AppCompatEditText nationality;
    @BindView(R.id.pass_number) AppCompatEditText passNumber;
    @BindView(R.id.place_of_birth) AppCompatEditText placeOfBirth;
    @BindView(R.id.date_of_birth) AppCompatEditText dateOfBirth;
    @BindView(R.id.place_of_issue) AppCompatEditText placeOfIssue;
    @BindView(R.id.pass_issue_date) AppCompatEditText passIssueDate;
    @BindView(R.id.cid_number) AppCompatEditText cidNumber;
    @BindView(R.id.passportCopyCard) CardView passportCopyCard;
    @BindView(R.id.civilIDCopyCard) CardView civilIDCopyCard;
    @BindView(R.id.residenceCopyCard) CardView residenceCopyCard;
    @BindView(R.id.otherDocumentsCard) CardView otherDocumentsCard;
    @BindView(R.id.passportCopyPath) AppCompatTextView passportCopyText;
    @BindView(R.id.civilIDCopyPath) AppCompatTextView civilIDCopyText;
    @BindView(R.id.residenceCopyPath) AppCompatTextView residenceCopyText;
    @BindView(R.id.otherDocumentsPath) AppCompatTextView otherDocumentsText;
    DialogProperties properties ;
    private User user;
    private OnNextEnable onNextEnable;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy" , Locale.getDefault());
    private Calendar birthCalendar , passCalendar;
    private FilePickerDialog dialog;
    private int currentDialogId = -1;

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

        setupAttachmentProperties();
    }

    private void setupAttachmentProperties() {
        properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;
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



        civilIDCopyCard.setOnClickListener(view -> {
            currentDialogId = CIVIL_ID_COPY;
           showPickerDialog();
        });

        passportCopyCard.setOnClickListener(view -> {
            currentDialogId = PASSPORT_COPY;
            showPickerDialog();
        });

        residenceCopyCard.setOnClickListener(view -> {
            currentDialogId = RESIDENCE_COPY;
            showPickerDialog();
        });

        otherDocumentsCard.setOnClickListener(view -> {
            currentDialogId = OTHER_DOCUMENTS;
            showPickerDialog();
        });
    }

    private void showPickerDialog(){
        dialog = new FilePickerDialog(context,properties);
        dialog.setTitle("Select a File");
        dialog.show();
        dialog.setDialogSelectionListener(files -> {
            //files is the array of the paths of files selected by the Application User.

            dialog.dismiss();
            dialog = null;
            if (files == null || files.length == 0) return;
            File file = new File(files[0]);
            switch (currentDialogId){
                case CIVIL_ID_COPY:
                    civilIDCopyText.setText(file.getName());
                    break;

                case PASSPORT_COPY:
                    passportCopyText.setText(file.getName());
                    break;

                case RESIDENCE_COPY:
                    residenceCopyText.setText(file.getName());
                    break;

                case OTHER_DOCUMENTS:
                    otherDocumentsText.setText(file.getName());
                    break;

            }

            if (onNextEnable != null) onNextEnable.setNextEnable(true , isAllViewsNotEmpty());


        });
    }

    //Add this method to show Dialog when the required permission has been granted to the app.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(dialog!=null)
                    {   //Show dialog if the read permission has been granted.
                        dialog.show();
                    }
                }
                else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(context,"Permission is Required for getting list of files",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isAllViewsNotEmpty() {


        if (TextUtils.isEmpty(nationality.getText())) return false;
        else user.nationality = nationality.getText().toString();
        if (TextUtils.isEmpty(passNumber.getText())) return false;
        else user.passNumber = passNumber.getText().toString();
        if (TextUtils.isEmpty(placeOfBirth.getText())) return false;
        else user.placeOfBirth = placeOfBirth.getText().toString();
        if (TextUtils.isEmpty(dateOfBirth.getText())) return false;
        else user.birthDate = birthCalendar.getTime();
        if (TextUtils.isEmpty(placeOfIssue.getText())) return false;
        else user.placeOfIssue = placeOfIssue.getText().toString();
        if (TextUtils.isEmpty(passIssueDate.getText())) return false;
        else user.passIssueDate = passCalendar.getTime();
        if (TextUtils.isEmpty(cidNumber.getText())) return false;
        else user.cidNumber = cidNumber.getText().toString();
        if (TextUtils.isEmpty(passportCopyText.getText())) return false;
        if (TextUtils.isEmpty(civilIDCopyText.getText())) return false;
        if (TextUtils.isEmpty(residenceCopyText.getText())) return false;


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
            if (onNextEnable != null) onNextEnable.setNextEnable(true, isAllViewsNotEmpty());

        }
    }
}
