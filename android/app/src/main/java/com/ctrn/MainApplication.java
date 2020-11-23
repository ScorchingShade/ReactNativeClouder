package com.ctrn;

import android.app.Application;
import android.content.Context;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.react.CleverTapPackage;
import com.clevertap.android.sdk.CleverTapAPI;

import com.clevertap.android.geofence.CTGeofenceAPI;
import com.clevertap.android.geofence.CTGeofenceSettings;
import com.clevertap.android.geofence.Logger;
import com.clevertap.android.geofence.interfaces.CTGeofenceEventsListener;
import com.clevertap.android.geofence.interfaces.CTLocationUpdatesListener;
import android.widget.Toast;
import android.location.Location;
import org.json.JSONObject;


public class MainApplication extends Application implements ReactApplication {
  private CleverTapAPI mCleverTapInstance;
  private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for example:
          // packages.add(new MyReactNativePackage());
         // packages.add(new CleverTapPackage());// only needed when not auto-linking
          return packages;
        }

        @Override
        protected String getJSMainModuleName() {
          return "index";
        }
      };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    CleverTapAPI.setUIEditorConnectionEnabled(false);
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
    initializeFlipper(this, getReactNativeHost().getReactInstanceManager());

    //test
    mCleverTapInstance = CleverTapAPI.getDefaultInstance(this);
    CleverTapAPI.setDebugLevel(10);
    initCTGeofenceApi();



  }

  private void initCTGeofenceApi() {
    if (mCleverTapInstance == null)
        return;

    // proceed only if cleverTap instance is not null
    final Context con = this;
    CTGeofenceAPI.getInstance(getApplicationContext())
            .init(new CTGeofenceSettings.Builder()
                    .enableBackgroundLocationUpdates(true)
                    .setLogLevel(Logger.DEBUG)
                    .setLocationAccuracy(CTGeofenceSettings.ACCURACY_HIGH)
                    .setLocationFetchMode(CTGeofenceSettings.FETCH_CURRENT_LOCATION_PERIODIC)
                    .setGeofenceMonitoringCount(99)
                    .setInterval(3600000) // 1 hour
                    .setFastestInterval(1800000) // 30 minutes
                    .setSmallestDisplacement(1000)// 1 km
                    .build(), mCleverTapInstance);

    CTGeofenceAPI.getInstance(getApplicationContext())
            .setOnGeofenceApiInitializedListener(new CTGeofenceAPI.OnGeofenceApiInitializedListener() {
                @Override
                public void OnGeofenceApiInitialized() {
                    Toast.makeText(con, "Geofence API initialized", Toast.LENGTH_SHORT).show();
                }
            });

    CTGeofenceAPI.getInstance(getApplicationContext())
            .setCtGeofenceEventsListener(new CTGeofenceEventsListener() {
                @Override
                public void onGeofenceEnteredEvent(JSONObject jsonObject) {
                    Toast.makeText(con, "Geofence Entered", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onGeofenceExitedEvent(JSONObject jsonObject) {
                    Toast.makeText(con, "Geofence Exited", Toast.LENGTH_SHORT).show();
                }
            });

    CTGeofenceAPI.getInstance(getApplicationContext())
            .setCtLocationUpdatesListener(new CTLocationUpdatesListener() {
                @Override
                public void onLocationUpdates(Location location) {
                    Toast.makeText(con, "Location updated", Toast.LENGTH_SHORT).show();
                }
            });
}

  /**
   * Loads Flipper in React Native templates. Call this in the onCreate method with something like
   * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
   *
   * @param context
   * @param reactInstanceManager
   */
  private static void initializeFlipper(
      Context context, ReactInstanceManager reactInstanceManager) {
    if (BuildConfig.DEBUG) {
      try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
        Class<?> aClass = Class.forName("com.ctrn.ReactNativeFlipper");
        aClass
            .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
            .invoke(null, context, reactInstanceManager);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}
