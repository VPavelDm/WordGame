package com.vpaveldm.wordgame.dagger.module;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class GoogleAuthModule {

    private FragmentActivity mActivity;

    public GoogleAuthModule(FragmentActivity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    public GoogleSignInOptions provideGoogleSignInOptions(@Named("Application") Context context) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @Provides
    @ActivityScope
    public GoogleApiClient provideGoogleSignInClient(@Named("Application") Context context, GoogleSignInOptions options) {
        try {
            return new GoogleApiClient.Builder(context)
                    .enableAutoManage(mActivity, connectionResult -> {
                        throw new NullPointerException();
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                    .build();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
