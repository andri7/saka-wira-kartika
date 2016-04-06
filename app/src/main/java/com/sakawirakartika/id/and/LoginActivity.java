package com.sakawirakartika.id.and;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by cvglobalsolusindo on 4/4/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private static final  String TAG = "LoginActivity";
    private static final int RIGUEST_SIGNUP = 0;

    Databasehelper dbuser;
    @Bind(R.id.input_username)
    EditText _usernameText;
    @Bind(R.id.input_password)
    EditText _inputpassword;
    @Bind(R.id.show_password)
    CheckBox _showpassword;
    @Bind(R.id.button_login)
    Button _btnlogin;
    @Bind(R.id.buat_akun)
    TextView _buatakun;

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);

            _btnlogin.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            _showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        _inputpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        _inputpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                }
            });

            _buatakun.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Start the Signup activity
                    Intent homeIntent = new Intent(getApplicationContext(), RegistrasiActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    homeIntent.putExtra("Exit", true);
                    startActivity(homeIntent);
                    finish();
                }
            });
    }

    public void login(){
        Log.d(TAG, "login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _btnlogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _inputpassword.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            dbuser = new Databasehelper(LoginActivity.this);
                            SQLiteDatabase db = dbuser.getReadableDatabase();

                            String username = _usernameText.getText().toString();
                            String password = _inputpassword.getText().toString();
                            String acak = "ARhqu70j4TNa0sPUOQgvENaamRe_8GkJqPnSckY9dTP4ZP2ci-liBscwogXs ";
                            String message = "2B22cS3UC5s35WBihLBo8w==";

                            String encryptedMsg = AESCrypt.encrypt(acak, password);
                            Cursor mCursor = db.rawQuery("SELECT * FROM user WHERE username='" + username +
                                    "' AND password = '" + encryptedMsg + "'", null);
                            if (mCursor != null) {
                                if (mCursor.getCount() > 0 && mCursor.moveToFirst()) {
                                    Log.e("size : ", mCursor.getColumnCount() + "pass : " + encryptedMsg);
                                    onLoginSuccess(mCursor.getString(0).toString());
                                } else {
                                    onLoginFailed();
                                }
                            } else {
                                onLoginFailed();
                            }
                            // On complete call either onLoginSuccess or onLoginFailed
                        } catch (GeneralSecurityException e) {
                            onLoginFailed();
                        } catch (Exception e) {
                            onLoginFailed();
                            e.printStackTrace();
                        }
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RIGUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess( String id){
        _btnlogin.setEnabled(true);
        _usernameText.setText("");
        _inputpassword.setText("");
        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.putExtra("EXIT", true);
        home.putExtra("id_user",id);
        finish();
        startActivity(home);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Failed!", Toast.LENGTH_LONG).show();

        _btnlogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String user = _usernameText.getText().toString();
        String password = _inputpassword.getText().toString();

        if (user.isEmpty()) {
            _usernameText.setError("Enter a valid username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10){
            _inputpassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _inputpassword.setError(null);
        }
        return  valid;
    }
}
