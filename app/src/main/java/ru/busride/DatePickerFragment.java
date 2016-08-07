package ru.busride;

/**
 * Created by Shcherbakov on 02.08.2016.
 */
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import com.roadtob.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jahid on 12/10/15.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        TextView tv1= (TextView) getActivity().findViewById(R.id.dateView);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM");
        String finalDate = outputFormat.format(cal.getTime());
        tv1.setText(finalDate);

        SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
        String dateReqq = date1.format(cal.getTime());
        TextView textView = (TextView) getActivity().findViewById(R.id.dateFind);
        textView.setText(dateReqq);

        Bundle bundle = new Bundle();
        bundle.putSerializable("date", cal);
        Intent i = new Intent();
        i.putExtra("date", bundle);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);

    }

}