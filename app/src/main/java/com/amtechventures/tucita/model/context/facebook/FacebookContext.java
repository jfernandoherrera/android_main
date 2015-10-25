package com.amtechventures.tucita.model.context.facebook;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;


import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.utils.blocks.Completion;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.MessageDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class FacebookContext {

    private final static String fields = "fields";
    private final static String friendsFields = "fields=name,picture.type(large)";
    private final static List<String> permissions = Arrays.asList("public_profile", "user_friends", "email");
    private final static String meFields = "name,email,picture.type(large),friends.fields(name,picture.type(large))";

    public static boolean logged() {

        return AccessToken.getCurrentAccessToken() != null;

    }

    public static boolean usersFriendEnable() {

        boolean usersFriendEnable = false;

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null && accessToken.getDeclinedPermissions() != null) {

            usersFriendEnable = accessToken.getDeclinedPermissions().contains("user_friends") == false;

        }

        return usersFriendEnable;

    }

    public void login(Activity activity, Completion.BoolBoolCompletion boolBoolCompletion) {

        LoginManager.getInstance().logInWithReadPermissions(activity, permissions);

    }

    public void logout() {

        LoginManager.getInstance().logOut();

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

        parameters.putString(fields, friendsFields);

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