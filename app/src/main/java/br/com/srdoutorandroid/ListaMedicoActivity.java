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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.crash.FirebaseCrash;

import br.com.srdoutorandroid.activities.CustomizedListView;
import br.com.srdoutorandroid.components.ProgressDialogAsyncTask;
import br.com.srdoutorandroid.exception.AcessoNegadoException;
import br.com.srdoutorandroid.model.User;
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
    private User user;
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
        // FACEBOOK
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                accessFacebookLoginData(loginResult.getAccessToken());
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

        // GOOGLE SIGN IN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1030419986033-4d7akan3oo8l421h3re2e1e0vv29qtv2.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
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
        mAuthListener = getFirebaseAuthResultHandler();
        initUser();
    }
    protected void initUser(){
        user = new User();
        user.setEmail( login.getText().toString() );
        user.setPassword( senha.getText().toString() );
    }

    private FirebaseAuth.AuthStateListener getFirebaseAuthResultHandler(){
        FirebaseAuth.AuthStateListener callback = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser userFirebase = firebaseAuth.getCurrentUser();

                if( userFirebase == null ){
                    return;
                }

                if( user.getId() == null
                        && isNameOk( user, userFirebase ) ){

                    user.setId( userFirebase.getUid() );
                    user.setNameIfNull( userFirebase.getDisplayName() );
                    user.setEmailIfNull( userFirebase.getEmail() );
                    user.saveDB();
                }

                redirect();
            }
        };
        return( callback );
    }

    private boolean isNameOk( User user, FirebaseUser firebaseUser ){
        return(
                user.getName() != null
                        || firebaseUser.getDisplayName() != null
        );
    }


    private void accessFacebookLoginData(AccessToken accessToken){
        accessLoginData(
                "facebook",
                (accessToken != null ? accessToken.getToken() : null)
        );
    }


    private void accessLoginData( String provider, String... tokens ){
        if( tokens != null
                && tokens.length > 0
                && tokens[0] != null ){

            AuthCredential credential = FacebookAuthProvider.getCredential( tokens[0]);
            credential = provider.equalsIgnoreCase("google") ? GoogleAuthProvider.getCredential( tokens[0], null) : credential;

            user.saveProviderSP( ListaMedicoActivity.this, provider );
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if( !task.isSuccessful() ){
                                ToastUtil.show(ListaMedicoActivity.this, "Login falhou!", ToastUtil.WARNING);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            FirebaseCrash.report( e );
                        }
                    });
        }
        else{
            mAuth.signOut();
        }
    }



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        verifyLogged();
    }

    private void verifyLogged(){
        if( mAuth.getCurrentUser() != null ){
            //callMainActivity();
        }
        else{
            mAuth.addAuthStateListener( mAuthListener );
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if( mAuthListener != null ){
            mAuth.removeAuthStateListener( mAuthListener );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == RC_SIGN_IN_GOOGLE ){

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent( data );
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();

            if( account == null ){
                ToastUtil.show(ListaMedicoActivity.this, "Google login falhou, tente novamente", ToastUtil.WARNING);
                return;
            }

            accessGoogleLoginData( account.getIdToken() );
        }
        else{
            callbackManager.onActivityResult( requestCode, resultCode, data );
        }
    }

    private void accessGoogleLoginData(String accessToken){
        accessLoginData(
                "google",
                accessToken
        );
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
