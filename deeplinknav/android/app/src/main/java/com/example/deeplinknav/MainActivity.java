package com.yourcompany.deeplink;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterView;

public class MainActivity extends FlutterActivity {
  public final String channel = "geturi.com/route";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);
    new MethodChannel(getFlutterView(),channel).setMethodCallHandler(new MethodChannel.MethodCallHandler(){
      @Override
      public void onMethodCall(MethodCall methodCall,MethodChannel.Result result){
        if(methodCall.method.equals("getUri")){
          String message = "hi";
          result.success(message);
        }
      }
    });
  }


  @Override
  public FlutterView createFlutterView(Context context) {
    final FlutterView view = new FlutterView(this);
    view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    setContentView(view);
    final String route = getRouteFromIntent();
    if (route != null) {
      view.setInitialRoute(route);
    }
    return view;
  }

  private String getRouteFromIntent() {
    final Intent intent = getIntent();
    if (Intent.ACTION_VIEW.equals(intent.getAction()) && intent.getData() != null) {
      return intent.getData().getPath();
    }
    return null;
  }
}