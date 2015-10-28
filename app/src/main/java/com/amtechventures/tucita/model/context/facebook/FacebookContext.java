package com.amtechventures.tucita.model.context.facebook;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.amtechventures.tucita.utils.strings.Strings;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.MessageDialog;
import com.parse.ParseUser;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.List;

public class FacebookContext {
    private Completion.BoolBoolUserCompletion loginCompletion;

    private Context context;

    private  String fields;

    private List<String> permissions;

    private String meFields;

    private String name;

    private String email;
    LoginManager loginManager;
    ParseUser parseUser;
    private CallbackManager callbackManager;
    public static FacebookContext context(FacebookContext facebookContext) {

        if (facebookContext == null) {

            facebookContext = new FacebookContext();

        }

        return facebookContext;

    }
    public FacebookContext(){
        callbackManager = CallbackManager.Factory.create();

        registerFacebookCallback(callbackManager);
        fields= Strings.FIELDS;

        name=Strings.NAME;

        String publicProfile=Strings.PUBLIC_PROFILE;

        email=Strings.EMAIL;

        permissions=Arrays.asList(publicProfile, email);

        meFields=name+","+email+","+Strings.PROFILE_PICTURE;
    }

    public CallbackManager getCallbackManager() {

        return callbackManager;

    }
    public static boolean logged() {

        return AccessToken.getCurrentAccessToken() != null;

    }

    public void loadMe(final Completion.DictionaryErrorCompletion completion) {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {

                AppError error = null;

                if (graphResponse.getError() != null) {

                    error = new AppError(graphResponse.getError().getErrorType(), graphResponse.getError().getErrorCode(), null);

                }

                completion.completion(jsonObject, error);

            }

        });

        Bundle parameters = new Bundle();

        parameters.putString(fields, meFields);

        request.setParameters(parameters);

        request.executeAsync();

    }

    public void login(Activity activity, Completion.BoolBoolUserCompletion completion) {

        loginCompletion = completion;


        LoginManager.getInstance().logInWithReadPermissions(activity,

                permissions);

        loginCompletion.completion(parseUser, true, false);

    }
    public void registerFacebookCallback(CallbackManager callbackManager) {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                loginCompletion.completion(parseUser,true, false);

            }

            @Override
            public void onCancel() {

                loginCompletion.completion(parseUser,false, true);

            }

            @Override
            public void onError(FacebookException e) {

                loginCompletion.completion(parseUser,false, false);

            }

        });

    }
    public void logout() {

        //..logOut();

    }

    public void loadFriends(final Long offset,final Completion.LongArrayErrorCompletion completion) {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMyFriendsRequest(accessToken, new GraphRequest.GraphJSONArrayCallback() {

            @Override
            public void onCompleted(JSONArray jsonArray, GraphResponse graphResponse) {

                AppError error = null;

                if (graphResponse.getError() != null) {

                    error = new AppError(graphResponse.getError().getErrorType(), graphResponse.getError().getErrorCode(), null);

                }
                long aLong = 0;

                if (jsonArray != null && jsonArray.length() > 0) {

                    aLong = offset + jsonArray.length() + 1;

                }
                completion.completion(aLong,jsonArray, error);

            }

        });

        Bundle parameters = new Bundle();

        //parameters.putString(fields, friendsFields);

        request.setParameters(parameters);

        request.executeAsync();

    }

    public void showMessageDialog(Activity activity, String title, Uri contentUrl) {

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentUrl(contentUrl)
                .build();

        MessageDialog.show(activity, content);

    }

    public void inviteApp(Activity activity, String appLinkUrl, String previewImageUrl) {

        if (AppInviteDialog.canShow()) {

            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();

            AppInviteDialog.show(activity, content);

        }

    }

}