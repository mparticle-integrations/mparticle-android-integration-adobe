package com.mparticle.kits;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mparticle.MParticle;
import com.mparticle.internal.MPUtility;
import com.mparticle.kits.KitIntegration.AttributeListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class AdobeKit extends AdobeKitBase {

    @Override
    public String getName() {
        return "Adobe";
    }
}