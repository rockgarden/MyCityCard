package com.eastcom.mycitycard.activitys;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.eastcom.mycitycard.R;

/**
 * Created by rockgarden on 15/11/16.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mEmailLoginFormView;
    private View mSignOutButtons;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find the Google+ sign in button.
//        mPlusSignInButton = (SignInButton) findViewById(R.id.plus_sign_in_button);
//        mPlusSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        mPlusSignInButton.setVisibility(View.GONE);
        return;
    }

    public void showSnackbarMessage(View v) {

        final TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        textInputLayout.setHint("请输入用户名");
        EditText et_snackbar = (EditText) findViewById(R.id.et_UserName);
        View view = findViewById(R.id.coordinator_layout);
        if (et_snackbar.getText().toString().isEmpty()) {
            textInputLayout.setError(getString(R.string.alert_text));
        } else {
            textInputLayout.setErrorEnabled(false);
            et_snackbar.onEditorAction(EditorInfo.IME_ACTION_DONE);
            Snackbar.make(view, et_snackbar.getText().toString(), Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(android.R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Do nothing
                        }
                    })
                    .show();
        }

    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    /**
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}



