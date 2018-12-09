

package me.mingz.cordova.plugin.applovin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.util.Log;

import java.util.Map;

import com.applovin.adview.AppLovinAdView;
import com.applovin.adview.AppLovinAdViewDisplayErrorCode;
import com.applovin.adview.AppLovinAdViewEventListener;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdRewardListener;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.adview.AppLovinIncentivizedInterstitial;

public class AppLovinPlugin extends CordovaPlugin {
    private static final String TAG = "AppLovinPlugin";


    public static final String ACTION_GET_AD_SETTINGS = "getAdSettings";
    public static final String ACTION_SET_OPTIONS = "setOptions";
    public static final String ACTION_CREATE_BANNER = "createBanner";
    public static final String ACTION_REMOVE_BANNER = "removeBanner";
    public static final String ACTION_HIDE_BANNER = "hideBanner";
    public static final String ACTION_SHOW_BANNER = "showBanner";
    public static final String ACTION_SHOW_BANNER_AT_XY = "showBannerAtXY";
    public static final String ACTION_PREPARE_INTERSTITIAL = "prepareInterstitial";
    public static final String ACTION_SHOW_INTERSTITIAL = "showInterstitial";
    public static final String ACTION_IS_INTERSTITIAL_READY = "isInterstitialReady";
    public static final String ACTION_PREPARE_REWARD_VIDEO_AD = "prepareRewardVideoAd";
    public static final String ACTION_SHOW_REWARD_VIDEO_AD = "showRewardVideoAd";
    public static final String ACTION_CREATE_NATIVE_AD = "createNativeAd";
    public static final String ACTION_REMOVE_NATIVE_AD = "removeNativeAd";
    public static final String ACTION_SET_NATIVE_AD_CLICK_AREA = "setNativeAdClickArea";
    //    static final String TRACK_EVENT = "trackEvent";


    public static final String ADTYPE_BANNER = "banner";
    public static final String ADTYPE_INTERSTITIAL = "interstitial";
    public static final String ADTYPE_NATIVE = "native";
    public static final String ADTYPE_REWARDVIDEO = "rewardvideo";

    public static final String EVENT_AD_LOADED = "onAdLoaded";
    public static final String EVENT_AD_FAILLOAD = "onAdFailLoad";
    public static final String EVENT_AD_PRESENT = "onAdPresent";
    public static final String EVENT_AD_LEAVEAPP = "onAdLeaveApp";
    public static final String EVENT_AD_DISMISS = "onAdDismiss";
    public static final String EVENT_AD_WILLPRESENT = "onAdWillPresent";
    public static final String EVENT_AD_WILLDISMISS = "onAdWillDismiss";

    private AppLovinIncentivizedInterstitial interstitialAds = null;
    private AppLovinIncentivizedInterstitial rewardVideoAds = null;
    //    protected TextView adStatusTextView;
    private AppLovinAdView bannerView = null;

    protected void log(final String message) {
//        if ( adStatusTextView != null )
//        {
//            runOnUiThread( new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    adStatusTextView.setText( message );
//                }
//            } );
//        }
//        System.out.println( message );


        Log.d(TAG, message);

    }


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        AppLovinSdk.initializeSdk(cordova.getActivity().getApplicationContext());

        AppLovinSdk.getInstance(cordova.getActivity().getApplicationContext()).getSettings().setTestAdsEnabled(true);

        // Warn user if SDK key is invalid
//        boolean isLegitSdkKey = checkSdkKey();

        // Prompt to add config flags if SDK key is legit
//        if ( isLegitSdkKey )
//        {
//            maybePromptConfigFlags();
//        }


        log("SDK Initialized");


    }


//    private boolean checkSdkKey()
//    {
//        final String sdkKey = AppLovinSdk.getInstance( getApplicationContext() ).getSdkKey();
//        if ( "YOUR_SDK_KEY".equalsIgnoreCase( sdkKey ) )
//        {
//            new AlertDialog.Builder( this )
//                    .setTitle( "ERROR" )
//                    .setMessage( "Please update your sdk key in the manifest file." )
//                    .setCancelable( false )
//                    .setNeutralButton( "OK", null )
//                    .show();
//
//            return false;
//        }
//
//        return true;
//    }


    @Override
    public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext) throws JSONException {
        PluginResult result = null;


//        if (Actions.TRACK_EVENT.equals(action)) {
//            String event = inputs.getString(0);
//            JSONArray parameters = inputs.optJSONArray(1);
//            result = trackEvent(event, parameters, callbackContext);
//
//        }else
        if (ACTION_SET_OPTIONS.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = setOptions(callbackContext);

        } else if (ACTION_CREATE_BANNER.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = createBanner(callbackContext);

        } else if (ACTION_REMOVE_BANNER.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = removeBanner(callbackContext);

        } else if (ACTION_HIDE_BANNER.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = hideBanner(callbackContext);

        } else if (ACTION_SHOW_BANNER.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = showBanner(callbackContext);

        } else if (ACTION_SHOW_BANNER_AT_XY.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = showBannerAtXY(callbackContext);

        } else if (ACTION_PREPARE_INTERSTITIAL.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = prepareInterstitial(callbackContext);

        } else if (ACTION_IS_INTERSTITIAL_READY.equals(action)) {

            result = isInterstitialReady(callbackContext);


        } else if (ACTION_SHOW_INTERSTITIAL.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = showInterstitial(callbackContext);

        } else if (ACTION_PREPARE_REWARD_VIDEO_AD.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = prepareRewardVideoAd(callbackContext);

        } else if (ACTION_SHOW_REWARD_VIDEO_AD.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = showRewardVideoAd(callbackContext);

        } else if (ACTION_CREATE_NATIVE_AD.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = createNativeAd(callbackContext);

        } else if (ACTION_REMOVE_NATIVE_AD.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = removeNativeAd(callbackContext);

        } else if (ACTION_SET_NATIVE_AD_CLICK_AREA.equals(action)) {

            String event = inputs.getString(0);
            JSONArray parameters = inputs.optJSONArray(1);
            result = setNativeAdClickArea(callbackContext);


        } else {
            log(String.format("Invalid action passed: %s", action));
            result = new PluginResult(Status.INVALID_ACTION);
        }

        if (result != null) {
            callbackContext.sendPluginResult(result);
        }

        return true;
    }


    private PluginResult setOptions(final CallbackContext callbackContext) {
        return null;
    }

    private PluginResult createBanner(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                bannerView = new AppLovinAdView(AppLovinAdSize.BANNER, cordova.getActivity());

                //
                // Optional: Set listeners
                //
                bannerView.setAdLoadListener(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(final AppLovinAd ad) {
                        log("adReceived: Banner loaded");
                    }

                    @Override
                    public void failedToReceiveAd(final int errorCode) {
                        // Look at AppLovinErrorCodes.java for list of error codes
                        log("failedToReceiveAd: Banner failed to load with error code " + errorCode);
                    }
                });

                bannerView.setAdDisplayListener(new AppLovinAdDisplayListener() {
                    @Override
                    public void adDisplayed(final AppLovinAd ad) {
                        log("adDisplayed: Banner Displayed");
                    }

                    @Override
                    public void adHidden(final AppLovinAd ad) {
                        log("adHidden: Banner Hidden");
                    }
                });

                bannerView.setAdClickListener(new AppLovinAdClickListener() {
                    @Override
                    public void adClicked(final AppLovinAd ad) {
                        log("adClicked: Banner Clicked");
                    }
                });

                bannerView.setAdViewEventListener(new AppLovinAdViewEventListener() {
                    @Override
                    public void adOpenedFullscreen(final AppLovinAd ad, final AppLovinAdView adView) {
                        log("adOpenedFullscreen: Banner opened fullscreen");
                    }

                    @Override
                    public void adClosedFullscreen(final AppLovinAd ad, final AppLovinAdView adView) {
                        log("adClosedFullscreen: Banner closed fullscreen");
                    }

                    @Override
                    public void adLeftApplication(final AppLovinAd ad, final AppLovinAdView adView) {
                        log("adLeftApplication: Banner left application");
                    }

                    @Override
                    public void adFailedToDisplay(final AppLovinAd ad, final AppLovinAdView adView, final AppLovinAdViewDisplayErrorCode code) {
                        log("adFailedToDisplay: Banner failed to display with error code " + code);
                    }
                });
//                bannerView.loadNextAd();
            }
        });

        return null;
    }

    private PluginResult removeBanner(final CallbackContext callbackContext) {
        bannerView.pause();
        return null;
    }

    private PluginResult hideBanner(final CallbackContext callbackContext) {
        bannerView.pause();
        return null;
    }

    private PluginResult showBanner(final CallbackContext callbackContext) {
        bannerView.loadNextAd();
        return null;
    }

    private PluginResult showBannerAtXY(final CallbackContext callbackContext) {
        bannerView.loadNextAd();
        return null;
    }

    private PluginResult prepareInterstitial(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _prepareInterstitial();
            }
        });
        callbackContext.success();
        return null;
    }

    private PluginResult showInterstitial(final CallbackContext callbackContext) {
        final CallbackContext delayCallback = callbackContext;
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean result = _showInterstitial();
                if (result) {
                    delayCallback.success();
                } else {
                    delayCallback.error("Ad is not ready!");
                }
            }
        });
        return null;
    }

    private PluginResult prepareRewardVideoAd(final CallbackContext callbackContext) {
        log("prepareRewardVideoAd");
        rewardVideoAds = AppLovinIncentivizedInterstitial.create(cordova.getActivity().getApplicationContext());
        rewardVideoAds.setUserIdentifier("DEMO_USER_IDENTIFIER"); // Set an optional user identifier used for S2S callbacks
        rewardVideoAds.preload(new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd appLovinAd) {
                log("adReceived: Rewarded video loaded.");
//                showButton.setEnabled( true );
            }

            @Override
            public void failedToReceiveAd(int errorCode) {
                log("failedToReceiveAd: Rewarded video failed to load with error code " + errorCode);
            }
        });
        return null;
    }

    private PluginResult showRewardVideoAd(final CallbackContext callbackContext) {
        //
        // OPTIONAL: Create listeners
        //

        // Reward Listener
        AppLovinAdRewardListener adRewardListener = new AppLovinAdRewardListener() {
            @Override
            public void userRewardVerified(AppLovinAd appLovinAd, Map map) {
                // AppLovin servers validated the reward. Refresh user balance from your server.  We will also pass the number of coins
                // awarded and the name of the currency.  However, ideally, you should verify this with your server before granting it.

                // i.e. - "Coins", "Gold", whatever you set in the dashboard.
                String currencyName = (String) map.get("currency");

                // For example, "5" or "5.00" if you've specified an amount in the UI.
                String amountGivenString = (String) map.get("amount");

                log("userRewardVerified: Rewarded " + amountGivenString + " " + currencyName);

                // By default we'll show a alert informing your user of the currency & amount earned.
                // If you don't want this, you can turn it off in the Manage Apps UI.
            }

            @Override
            public void userOverQuota(AppLovinAd appLovinAd, Map map) {
                // Your user has already earned the max amount you allowed for the day at this point, so
                // don't give them any more money. By default we'll show them a alert explaining this,
                // though you can change that from the AppLovin dashboard.

                log("userOverQuota: Reward validation request exceeded quota with response: " + map);
            }

            @Override
            public void userRewardRejected(AppLovinAd appLovinAd, Map map) {
                // Your user couldn't be granted a reward for this view. This could happen if you've blacklisted
                // them, for example. Don't grant them any currency. By default we'll show them an alert explaining this,
                // though you can change that from the AppLovin dashboard.

                log("userRewardRejected: Reward validation request was rejected with response: " + map);
            }

            @Override
            public void validationRequestFailed(AppLovinAd appLovinAd, int responseCode) {
                if (responseCode == AppLovinErrorCodes.INCENTIVIZED_USER_CLOSED_VIDEO) {
                    // Your user exited the video prematurely. It's up to you if you'd still like to grant
                    // a reward in this case. Most developers choose not to. Note that this case can occur
                    // after a reward was initially granted (since reward validation happens as soon as a
                    // video is launched).
                } else if (responseCode == AppLovinErrorCodes.INCENTIVIZED_SERVER_TIMEOUT || responseCode == AppLovinErrorCodes.INCENTIVIZED_UNKNOWN_SERVER_ERROR) {
                    // Some server issue happened here. Don't grant a reward. By default we'll show the user
                    // a alert telling them to try again later, but you can change this in the
                    // AppLovin dashboard.
                } else if (responseCode == AppLovinErrorCodes.INCENTIVIZED_NO_AD_PRELOADED) {
                    // Indicates that the developer called for a rewarded video before one was available.
                    // Note: This code is only possible when working with rewarded videos.
                }

                log("validationRequestFailed: Reward validation request failed with error code: " + responseCode);
            }

            @Override
            public void userDeclinedToViewAd(AppLovinAd appLovinAd) {
                // This method will be invoked if the user selected "no" when asked if they want to view an ad.
                // If you've disabled the pre-video prompt in the "Manage Apps" UI on our website, then this method won't be called.

                log("userDeclinedToViewAd: User declined to view ad");
            }
        };

        // Video Playback Listener
        AppLovinAdVideoPlaybackListener adVideoPlaybackListener = new AppLovinAdVideoPlaybackListener() {
            @Override
            public void videoPlaybackBegan(AppLovinAd appLovinAd) {
                log("videoPlaybackBegan: Video Started");
            }

            @Override
            public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean fullyWatched) {
                log("videoPlaybackEnded: Video Ended");
            }
        };

        // Ad Dispaly Listener
        AppLovinAdDisplayListener adDisplayListener = new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd appLovinAd) {
                log("adDisplayed: Ad Displayed");
            }

            @Override
            public void adHidden(AppLovinAd appLovinAd) {
                log("adHidden: Ad Dismissed");
            }
        };

        // Ad Click Listener
        AppLovinAdClickListener adClickListener = new AppLovinAdClickListener() {
            @Override
            public void adClicked(AppLovinAd appLovinAd) {
                log("Ad Click");
            }
        };

        rewardVideoAds.show(cordova.getActivity(), adRewardListener, adVideoPlaybackListener, adDisplayListener, adClickListener);
        return null;
    }

    private PluginResult createNativeAd(final CallbackContext callbackContext) {
        return null;
    }

    private PluginResult removeNativeAd(final CallbackContext callbackContext) {
        return null;
    }

    private PluginResult setNativeAdClickArea(final CallbackContext callbackContext) {
        return null;
    }

    private PluginResult isInterstitialReady(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean result = interstitialAds != null && interstitialAds.isAdReadyToDisplay();
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
            }
        });

        return null;
    }


    private void _prepareInterstitial() {
        log("_prepareInterstitial");
        interstitialAds = AppLovinIncentivizedInterstitial.create(cordova.getActivity().getApplicationContext());
        log("AppLovinIncentivizedInterstitial.create");
        interstitialAds.preload(new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd ad) {
                log("adReceived");

                JSONObject data = new JSONObject();
                try {
                    data.put("id", ad.getAdIdNumber());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                fireEvent("applovin.load", data);
            }

            @Override
            public void failedToReceiveAd(int errorCode) {
                log(String.format("failedToReceiveAd: %d", errorCode));

                JSONObject data = new JSONObject();
                try {
                    data.put("error", errorCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                fireEvent("applovin.load_error", data);
            }
        });
    }

    private boolean _showInterstitial() {
        if (interstitialAds == null || !interstitialAds.isAdReadyToDisplay()) {
            return false;
        }

        interstitialAds.show(
                cordova.getActivity(),
                new AppLovinAdRewardListener() {
                    @Override
                    public void userDeclinedToViewAd(final AppLovinAd appLovinAd) {
                        log("User declined to view rewarded video");
                        fireEvent("applovin.close", null);
                    }

                    @Override
                    public void userRewardVerified(final AppLovinAd ad, final Map map) {
                        final String currency = (String) map.get("currency");
                        final String amountStr = (String) map.get("amount");
                        final int amount = (int) Double.parseDouble(amountStr); // AppLovin returns amount as double

                        log("Verified " + amount + " " + currency);

                        JSONObject data = new JSONObject();
                        try {
                            data.put("amount", amount);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        fireEvent("applovin.reward", data);
                    }

                    @Override
                    public void userOverQuota(final AppLovinAd appLovinAd, final Map map) {
                        log("Rewarded video validation request for ad did exceed quota with response: " + map);
                    }

                    @Override
                    public void validationRequestFailed(final AppLovinAd appLovinAd, final int errorCode) {
                        log("Rewarded video validation request for ad failed with error code: " + errorCode);
                    }

                    @Override
                    public void userRewardRejected(final AppLovinAd appLovinAd, final Map map) {
                        log("Rewarded video validation request was rejected with response: " + map);
                    }
                },
                new AppLovinAdVideoPlaybackListener() {
                    @Override
                    public void videoPlaybackBegan(AppLovinAd ad) {
                        log("Rewarded video playback began");
                    }

                    @Override
                    public void videoPlaybackEnded(AppLovinAd ad, double percentViewed, boolean fullyWatched) {
                        if (fullyWatched) {
                            fireEvent("applovin.completed", null);
                        }
                    }
                },
                new AppLovinAdDisplayListener() {
                    @Override
                    public void adDisplayed(AppLovinAd appLovinAd) {
                        fireEvent("applovin.start", null);
                    }

                    @Override
                    public void adHidden(AppLovinAd appLovinAd) {
                        fireEvent("applovin.close", null);
                    }
                }
        );

        return true;
    }

//    private PluginResult trackEvent(final String event, final JSONArray data, CallbackContext callbackContext) {
//        final AppLovinEventService eventService = AppLovinSdk.getInstance(cordova.getActivity()).getEventService();
//
//        Map<String, String> parameters = new HashMap<String, String>();
//        String eventName;
//        if (event.equalsIgnoreCase("USER_COMPLETED_LEVEL")) {
//            eventName = AppLovinEventTypes.USER_COMPLETED_LEVEL;
//            parameters.put(AppLovinEventParameters.COMPLETED_LEVEL_IDENTIFIER, data.optString(0, "0"));
//
//        } else if (event.equalsIgnoreCase("USER_COMPLETED_IN_APP_PURCHASE")) {
//            eventName = AppLovinEventTypes.USER_COMPLETED_IN_APP_PURCHASE;
//            parameters.put(AppLovinEventParameters.REVENUE_AMOUNT, data.optString(0, "0"));
//            parameters.put(AppLovinEventParameters.REVENUE_CURRENCY, data.optString(1, "USD"));
//            parameters.put(AppLovinEventParameters.IN_APP_PURCHASE_TRANSACTION_IDENTIFIER, data.optString(2, ""));
//
//        } else if (event.equalsIgnoreCase("USER_SPENT_VIRTUAL_CURRENCY")) {
//            eventName = AppLovinEventTypes.USER_SPENT_VIRTUAL_CURRENCY;
//            parameters.put(AppLovinEventParameters.VIRTUAL_CURRENCY_AMOUNT, data.optString(0, "0"));
//            parameters.put(AppLovinEventParameters.VIRTUAL_CURRENCY_NAME, data.optString(1, ""));
//
//        } else if (event.equalsIgnoreCase("USER_COMPLETED_TUTORIAL")) {
//            eventName = AppLovinEventTypes.USER_COMPLETED_TUTORIAL;
//
//        } else if (event.equalsIgnoreCase("USER_VIEWED_PRODUCT")) {
//            eventName = AppLovinEventTypes.USER_VIEWED_PRODUCT;
//            parameters.put(AppLovinEventParameters.PRODUCT_IDENTIFIER, data.optString(0, ""));
//
//        } else if (event.equalsIgnoreCase("USER_LOGGED_IN")) {
//            eventName = AppLovinEventTypes.USER_LOGGED_IN;
//
//        } else {
//            return null;
//        }
//
//        eventService.trackEvent(eventName, parameters);
//
//        return null;
//    }

    private void fireEvent(String eventName, JSONObject jsonObj) {
        String data = "";
        if (jsonObj != null) {
            data = jsonObj.toString();
        }
//
        StringBuilder js = new StringBuilder();
        js.append("javascript:cordova.fireDocumentEvent('");
        js.append(eventName);
        js.append("'");
        if (data != null && !"".equals(data)) {
            js.append(",");
            js.append(data);
        }
        js.append(");");
//
        final String code = js.toString();

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(code);
            }
        });


    }
}