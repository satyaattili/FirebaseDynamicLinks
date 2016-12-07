package in.mobileappdev.firebasedynamiclinks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

public class DynamicLinkActivity extends AppCompatActivity implements
    GoogleApiClient.OnConnectionFailedListener{

  private String TAG = "MainActivity";
  private TextView dynamicLinkTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_depplink_data);
    dynamicLinkTextView = (TextView) findViewById(R.id.dynamiclink);

    // Build GoogleApiClient with AppInvite API for receiving deep links
    GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this, this)
        .addApi(AppInvite.API)
        .build();

    // Check if this app was launched from a deep link. Setting autoLaunchDeepLink to true
    // would automatically launch the deep link if one is found.
    boolean autoLaunchDeepLink = false;
    AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
        .setResultCallback(
            new ResultCallback<AppInviteInvitationResult>() {
              @Override
              public void onResult(@NonNull AppInviteInvitationResult result) {
                if (result.getStatus().isSuccess()) {
                  // Extract deep link from Intent
                  Intent intent = result.getInvitationIntent();
                  String deepLink = AppInviteReferral.getDeepLink(intent);
                  if(deepLink != null){
                    dynamicLinkTextView.setText(deepLink);
                  }

                  Log.d(TAG, "getInvitation: "+deepLink);
                  // Handle the deep link. For example, open the linked
                  // content, or apply promotional credit to the user's
                  // account.

                  // ...
                } else {
                  Log.d(TAG, "getInvitation: no deep link found.");
                }
              }
            });
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.d(TAG, "onConnectionFailed");
  }

  public void goNext(View view) {
  }
}
