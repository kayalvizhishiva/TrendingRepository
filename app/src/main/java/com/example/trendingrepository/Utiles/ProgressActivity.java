package com.example.trendingrepository.Utiles;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trendingrepository.R;

public class ProgressActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private TextView progressText;

    public void  showProgress(String message) {
        if (alertDialog == null) {
            initLoadingDialog();
        }
        if (!TextUtils.isEmpty(message)) {
            progressText.setText(message);
        }
        if(!isFinishing()) {
            alertDialog.show();
        }
    }

    private void initLoadingDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.progressbar_view, null);
        alertDialogBuilder.setView(view);
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressText = (TextView) view.findViewById(R.id.status_text);
    }


    public void dismissProgress() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

}
