package com.sakawirakartika.id.and;

/**
 * Created by cvglobalsolusindo on 4/4/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegistrasiActivity extends AppCompatActivity {
    private static final String TAG = "RegistrasiActivity";
    Databasehelper dbregistrasiakun;
    @Bind(R.id.tulis_username)
    EditText _buatusername;
    @Bind(R.id.tulis_email)
    EditText _tulisemail;
    @Bind(R.id.tulis_password)
    EditText _buatpassword;
    @Bind(R.id.tulis_konfirmasi_password)
    EditText _konfirmasipassword;
    @Bind(R.id.button_simpan)
    Button _btnsimpan;
    @Bind(R.id.button_kembalikelogin)
    EditText _backlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registrasi);
        ButterKnife.bind(this);

        _btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), LoginActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                homeIntent.putExtra("EXIT", true);
                startActivity(homeIntent);
                finish();
            }
        });

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }
        _btnsimpan.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegistrasiActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Menyimpan Akun...");
        progressDialog.show();
        String username = _buatusername.getText().toString();
        String email = _tulisemail.getText().toString();
        String password = _buatpassword.getText().toString();
        final String konfirmpassword = _konfirmasipassword.getText().toString();
        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        try {

                            String username = _buatusername.getText().toString();
                            String email = _tulisemail.getText().toString();
                            String password = _buatpassword.getText().toString();
                            String konfirmpassword = _konfirmasipassword.getText().toString();
                            SQLiteDatabase db = dbregistrasiakun.getWritableDatabase();
                            String acak = "ARhqu70j4TNa0sPUOQgvENaamRe_8GkJqPnSckY9dTP4ZP2ci-liBscwogXs ";
                            String message = "2B22cS3UC5s35WBihLBo8w==";

                            String encryptedMsg = AESCrypt.encrypt(acak, password);

                            db.execSQL("insert into user (username, email, password,  konfirmasipassword) " +
                                    "VALUES ('" + username + "'," +
                                    "'" + email + "'," +
                                    "'" + encryptedMsg + "'," +
                                    "'" + password + "','" + konfirmpassword + "')");
                            onSignupSuccess();
                        } catch (GeneralSecurityException e) {
                            //Handle error
                        } catch (Exception e) {
                            onSignupFailed();
                            e.printStackTrace();
                        }
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success

                        // onSignupFailed();
                        progressDialog.dismiss();

                    }
                }, 3000);

    }

    public void onSignupSuccess() {
        _btnsimpan.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Account has been created!", Toast.LENGTH_LONG).show();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _btnsimpan.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String username = _buatusername.getText().toString();
        String email = _tulisemail.getText().toString();
        String password = _buatpassword.getText().toString();
        String konfirmpassword = _konfirmasipassword.getText().toString();

        dbregistrasiakun = new Databasehelper(this);
        SQLiteDatabase db = dbregistrasiakun.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM user WHERE username='" + username + "'", null);
        if (username.isEmpty() || username.length() < 3) {
            _buatusername.setError("at least 3 characters");
            valid = false;
        } else {
            _buatusername.setError(null);
        }

        if (username.isEmpty() || username.length() < 3) {
            _buatusername.setError("at least 3 characters");
            valid = false;
        } else {
            if (mCursor != null) {
                if (mCursor.getCount() > 0) {
                    Log.e("user", username + mCursor.getCount());
                    _buatusername.setError("has been used");
                    valid = false;
                }
            } else {
                _buatusername.setError(null);
            }
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _tulisemail.setError("enter a valid email address");
            valid = false;
        } else {
            _tulisemail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _buatpassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _buatpassword.setError(null);
        }


        if (konfirmpassword.isEmpty() || konfirmpassword.length() < 4 || konfirmpassword.length() > 10) {
            _konfirmasipassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            if (!(konfirmpassword.equals(password))) {
                _konfirmasipassword.setError("Password Harus Sama!");
            } else {
                _konfirmasipassword.setError(null);
            }
        }

        return valid;
    }
}
