package br.com.srdoutorandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.srdoutorandroid.activities.CustomizedListView;
import br.com.srdoutorandroid.components.ProgressDialogAsyncTask;
import br.com.srdoutorandroid.exception.AcessoNegadoException;
import br.com.srdoutorandroid.util.ConexaoUtil;
import br.com.srdoutorandroid.util.ToastUtil;
import br.com.srdoutorandroid.util.ValidaUtil;
import br.com.srdoutorandroid.webservice.endpoint.ExecutorMetodoService;
import br.com.srdoutorandroid.webservice.endpoint.LoginService;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListaMedicoActivity extends AppCompatActivity implements ProgressDialogAsyncTask.IProgressActivity, GoogleApiClient.OnConnectionFailedListener{

    @Bind(R.id.email_login)
    EditText login;
    @Bind(R.id.senha_login)
    EditText senha;
    @Bind(R.id.dialog_progress)
    RelativeLayout layoutProgress;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "ListaMedicoActivity";
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN_GOOGLE = 7859;
    private static final int RC_SIGN_IN_FACEBOOK = 64206;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medico);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               // .requestIdToken("1030419986033-hro947juqboabffbqfh0bf0l7lsbdm16.apps.googleusercontent.com")
                .requestIdToken("1030419986033-4d7akan3oo8l421h3re2e1e0vv29qtv2.apps.googleusercontent.com")
               // .requestIdToken("1030419986033-hlovcvu42cvnj3s7imcqbu1s28ejhrhm.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.sign_in_button) {
                    signIn();
                }
            }
        };
        signInButton.setOnClickListener(listener);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    String uid = user.getUid();
                    // User is signed in
                    ToastUtil.show(ListaMedicoActivity.this,  user.getDisplayName(), ToastUtil.INFORMATION);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getDisplayName());
                } else {

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
/**
 try {
 PackageInfo info = getPackageManager().getPackageInfo(
 "br.com.srdoutorandroid",
 PackageManager.GET_SIGNATURES);
 for (Signature signature : info.signatures) {
 MessageDigest md = MessageDigest.getInstance("SHA");
 md.update(signature.toByteArray());
 Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
 }
 } catch (NameNotFoundException e) {

 } catch (NoSuchAlgorithmException e) {

 }
 **/


    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            ToastUtil.show(ListaMedicoActivity.this, "Facebook login falhou!", ToastUtil.WARNING);
                        }else{
                            redirect();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthUserCollisionException) {
                    ToastUtil.show(ListaMedicoActivity.this, e.toString(), ToastUtil.WARNING);

                } else if (e instanceof FirebaseAuthInvalidUserException) {
                    ToastUtil.show(ListaMedicoActivity.this, e.toString(), ToastUtil.WARNING);
                }
            }
            });
    }


    @Override
    public void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.

        mAuth.addAuthStateListener(mAuthListener);
      //  mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // mGoogleApiClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // callbackManager.onActivityResult( requestCode, resultCode, data );
        if( requestCode == RC_SIGN_IN_GOOGLE ){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                ToastUtil.show(ListaMedicoActivity.this, "Google login: " + account.getIdToken(), ToastUtil.INFORMATION);
                firebaseAuthWithGoogle(account);
            }else{
                ToastUtil.show(ListaMedicoActivity.this, "Google login falhou, tente novamente", ToastUtil.WARNING);
            }
        }
        else if (requestCode == RC_SIGN_IN_FACEBOOK){
            callbackManager.onActivityResult( requestCode, resultCode, data );
        //   ToastUtil.show(ListaMedicoActivity.this, "Login facebook " , ToastUtil.INFORMATION);
       }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            ToastUtil.show(ListaMedicoActivity.this, "Google login falhou!", ToastUtil.WARNING);
                        }else{
                            redirect();
                        }
                        // ...
                    }
                });
    }

    public void logar(View view) {
        ProgressDialogAsyncTask task = new ProgressDialogAsyncTask(this, layoutProgress, this);
        if (!ConexaoUtil.isConexao(getApplicationContext())) {
            ToastUtil.show(this, getResources().getString(R.string.error_conexao_internet), ToastUtil.ERROR);
        } else if (ValidaUtil.validaCampoEditText(this.login) && ValidaUtil.validaCampoEditText(senha)) {
            task.execute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }

    public void redirect(){
        Intent intent = new Intent(this, CustomizedListView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void executaProgressoDialog() {
        try {
            ExecutorMetodoService.invoke(new LoginService(this), "login", login.getText().toString(), senha.getText().toString());
            //registrarIdGcm();
            redirect();
        } catch (RetrofitError error) {
            final Response resp = error.getResponse();
            ToastUtil.showErro(this, resp);
        } catch (AcessoNegadoException erro) {
            ToastUtil.show(ListaMedicoActivity.this, getResources().getString(R.string.error_acesso_negado), ToastUtil.WARNING);
        } catch (RuntimeException erro) {
            ToastUtil.show(ListaMedicoActivity.this, getResources().getString(R.string.error_dados_invalidos), ToastUtil.WARNING);
        }
    }

    @Override
    public boolean isAddedValidation() {
        return true;
    }

    @Override
    public void onPostExecute() {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ToastUtil.show(ListaMedicoActivity.this, connectionResult.getErrorMessage(), ToastUtil.WARNING);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ListaMedico Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}
