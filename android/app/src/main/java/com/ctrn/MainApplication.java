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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.content.Intent;
import android.Manifest;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.app.Activity;


public class MainApplication extends Application implements ReactApplication {
  private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
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


    if (mCleverTapInstance != null) {
      // proceed only if cleverTap instance is not null
         checkPermissions();
         initCTGeofenceApi();
         System.out.println("CreatedCTGeofence");
       
      
  }



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
                    System.out.println("GeoFence Initialised");
                    Toast.makeText(con, "Geofence Initialised", Toast.LENGTH_SHORT).show();
                }
            });

    CTGeofenceAPI.getInstance(getApplicationContext())
            .setCtGeofenceEventsListener(new CTGeofenceEventsListener() {
                @Override
                public void onGeofenceEnteredEvent(JSONObject jsonObject) {
                  System.out.println("GeoFence Entered");
                    Toast.makeText(con, "Geofence Entered", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onGeofenceExitedEvent(JSONObject jsonObject) {
                  System.out.println("GeoFence exited");
                    Toast.makeText(con, "Geofence Exited", Toast.LENGTH_SHORT).show();
                }
            });

    CTGeofenceAPI.getInstance(getApplicationContext())
            .setCtLocationUpdatesListener(new CTLocationUpdatesListener() {
                @Override
                public void onLocationUpdates(Location location) {
                  System.out.println("Location Updated");
                    Toast.makeText(con, "Location updated", Toast.LENGTH_SHORT).show();
                }
            });
}

//###1Req
private boolean checkPermissions() {
  int fineLocationPermissionState = ContextCompat.checkSelfPermission(
          this, Manifest.permission.ACCESS_FINE_LOCATION);

  int backgroundLocationPermissionState = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ? ContextCompat.checkSelfPermission(
          this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) : PackageManager.PERMISSION_GRANTED;

  return (fineLocationPermissionState == PackageManager.PERMISSION_GRANTED) &&
          (backgroundLocationPermissionState == PackageManager.PERMISSION_GRANTED);
}

//###1Req
private void requestPermissions() {
  boolean permissionAccessFineLocationApproved =
          ActivityCompat.checkSelfPermission(
                  this, Manifest.permission.ACCESS_FINE_LOCATION)
                  == PackageManager.PERMISSION_GRANTED;

  boolean backgroundLocationPermissionApproved =
          ActivityCompat.checkSelfPermission(
                  this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                  == PackageManager.PERMISSION_GRANTED;

  boolean shouldProvideRationale =
          permissionAccessFineLocationApproved && backgroundLocationPermissionApproved;

  
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
