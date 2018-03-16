package com.angelmsger.cslaboratory.login;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.angelmsger.cslaboratory.CSLaboratory;
import com.angelmsger.cslaboratory.source.DataSource;
import com.angelmsger.cslaboratory.source.UnrealDataSource;
import com.angelmsger.cslaboratory.R;
import com.dd.CircularProgressButton;

/**
 * 一个登录屏幕以提供了电子邮箱地址及密码登录方式。
 */
public class LoginActivity extends AppCompatActivity {
    private CircularProgressButton mEmailSignInButton;

    private Handler mHandler;
    private Runnable mResetButton = new Runnable() {
        @Override
        public void run() {
            mEmailSignInButton.setProgress(0);
        }
    };
    private Runnable mLoginSuccessfully = new Runnable() {
        @Override
        public void run() {
            setResult(LOGIN_SUCCESSFULLY);
            finish();
        }
    };

    private DataSource mDataSource;

    public static final int LOGIN_SUCCESSFULLY = 0;

    /**
     * 持续跟踪登录任务以保证当需要时可以取消。
     */
    private UserLoginTask mAuthTask = null;

    // UI 实现。
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 建立登录表格。
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (CircularProgressButton) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mHandler = new Handler();
        mDataSource = new UnrealDataSource();
    }

    /**
     * 来自登录表格的注册或登录请求。
     * 如果存在非法输入 (无效的电子邮件地址，缺少填写的内容等。), 错误将被跳过并且不会发出登录请求。
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // 重置错误。
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // 存储发出登录请求时的值。
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 确保的到了有效的密码输入。
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 确保得到了有效的电子邮箱地址输入。
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // 出现错误，取消登录请求并使第一个输入框重新获取焦点。
            focusView.requestFocus();
        } else {
            // 显示一个进度条，并开始一个后台任务进行用户登录请求。
            mEmailSignInButton.setProgress(1);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: 替换为自定义处理逻辑。
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: 替换为自定义处理逻辑。
        return password.length() > 4;
    }

    /**
     * 创建一个异步登录或注册任务用以认证用户。
     */
    public class UserLoginTask extends AsyncTask<Void, Integer, Boolean> {
        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            publishProgress(50);
            // 通过网络服务以请求认证。
            Login.Request request = new Login.Request(mEmail, mPassword);
            Login.Response response = mDataSource.attemptLogin(request);
            if (response.getCode() == Login.Response.SUCCESS) {
                ((CSLaboratory)getApplication()).mGlobal.put("User", response.getUser());
            }
            else if (response.getCode() == Login.Response.FORBIDDEN) {
                return false;
            }
            else {
                // TODO: 在此注册新用户。
            }
            publishProgress(99);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                mEmailSignInButton.setProgress(100);
                mHandler.postDelayed(mLoginSuccessfully, 500);
            } else {
                mEmailSignInButton.setProgress(-1);
                mHandler.postDelayed(mResetButton, 500);
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // 结束前一次动画效果
            mEmailSignInButton.setProgress(values[0]);
        }

        @Override
        protected void onCancelled() {
            // 设置按钮状态
            mEmailSignInButton.setProgress(-1);
            mHandler.postDelayed(mResetButton, 500);

            // 取消任务
            mAuthTask = null;
        }
    }


}

