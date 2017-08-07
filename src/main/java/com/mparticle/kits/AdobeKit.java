package com.mparticle.kits;

import android.content.Context;

import java.util.List;
import java.util.Map;

public class AdobeKit extends KitIntegration {
    @Override
    public String getName() {
        return "Adobe";
    }

    @Override
    protected List<ReportingMessage> onKitCreate(Map<String, String> map, Context context) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<ReportingMessage> setOptOut(boolean optedOut) {
        return null;
    }
}
