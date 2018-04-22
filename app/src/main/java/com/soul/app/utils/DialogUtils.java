package com.soul.app.utils;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.soul.app.R;

import java.util.Calendar;
import java.util.List;


public class DialogUtils {

    public static String TAG = "DialogUtils";

    private static int parentCode;

    public int getParentCode() {
        return parentCode;
    }

    public void setParentCode(int parentCode) {
        this.parentCode = parentCode;
    }

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

//    public static void showOkCancelDialog(Context context, String title, String message, final DialogListener dialogListener) {
//
//        final AlertDialog.Builder builder =
//                new AlertDialog.Builder(context);
//        if (!TextUtils.isEmpty(title))
//            builder.setTitle(title);
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getString(R.string.lbl_ok), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//                dialogListener.onOkClick();
//            }
//        });
//        builder.setNegativeButton(context.getString(R.string.lbl_cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//                dialogListener.onCancelClick();
//            }
//        });
//        builder.show();
//    }

    public static void showOkCancelDialog(Context context, String message, String ok, String cancel, final DialogListener dialogListener) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dialogListener.onOkClick();
            }
        });
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dialogListener.onCancelClick();
            }
        });
        builder.show();
    }


    public static void showCustomButtonDialog(Context context, String message, String ok, String cancel, final DialogListener dialogListener) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dialogListener.onOkClick();
            }
        });
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dialogListener.onCancelClick();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    public static void showOkDialog(Context context, int layout, String okText, DialogInterface.OnClickListener onClickListener) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setView(layout);
        builder.setPositiveButton(okText, onClickListener);
        builder.show();
    }

    public static void showOkDialog(Context context, String title, String message) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);

        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.lbl_ok), null);

        builder.show();
    }

    public static void showOkDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.lbl_ok), onClickListener);

        builder.show();
    }




 /*   public static void showListDialog(Activity activity, String title, List<?> list, final ListDialogItemClickListener listDialogClickListener) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(activity.getApplicationContext());
        final ArrayAdapter arrayAdapter = new ArrayAdapter(activity.getApplicationContext(),
                R.layout.row_text, list);
        builder.setTitle(title);
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Object string = arrayAdapter.getItem(i);
                listDialogClickListener.onItemClick(i, string);
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton(activity.getApplicationContext().getString(R.string.lbl_cancel), null);

        builder.show();

    }*/


    public static void showListDialog(Context context, final TextView editText, String title, List<?> list) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(context,
                R.layout.row_text, list);
        builder.setTitle(title);
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Object string = arrayAdapter.getItem(i);
                editText.setText(string.toString());
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton(context.getString(R.string.lbl_cancel), null);

        builder.show();
    }


    public static void showListDialog(Context context, final TextView editText, String title, List<?> list,final int code,final DialogListener dl) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(context,
                R.layout.row_text, list);
        builder.setTitle(title);
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Object string = arrayAdapter.getItem(i);
                editText.setText(string.toString());
                dl.onClick(code, i);
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton(context.getString(R.string.lbl_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dl.onCancelClick();
            }
        });
        builder.show();


    }




    public static ProgressDialog getProgressDialog(Context mContext) {
        if (mContext != null) {
            ProgressDialog mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(mContext.getString(R.string.lbl_please_wait));
            return mProgressDialog;
        }

        return null;
    }



    public interface DialogListener {

       void onClick(int parentCode, int postion);

         void onOkClick();

         void onCancelClick();
    }



    public static void showTimePickerDialog(Context context,final EditText editText)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = 1;
        int minute = 0;
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                int minut = (selectedHour*60)+selectedMinute;
                editText.setText(""+minut);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Minutes");
        mTimePicker.show();
    }

}