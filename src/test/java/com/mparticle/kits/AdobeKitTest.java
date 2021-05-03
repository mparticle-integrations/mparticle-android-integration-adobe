package com.mparticle.kits;

import android.content.Context;

import com.mparticle.MParticle;
import com.mparticle.internal.KitManager;
import com.mparticle.internal.MPUtility;

import org.junit.Test;
import org.mockito.Mockito;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AdobeKitTest {

    private AdobeKit getKit() {
        return new AdobeKit();
    }

    @Test
    public void testGetName() throws Exception {
        String name = getKit().getName();
        assertTrue(name != null && name.length() > 0);
    }

    /**
     * Kit *should* throw an exception when they're initialized with the wrong settings.
     *
     */
    @Test
    public void testOnKitCreate() throws Exception{
        Exception e = null;
        try {
            KitIntegration kit = getKit();
            Map settings = new HashMap<>();
            settings.put("fake setting", "fake");
            kit.onKitCreate(settings, Mockito.mock(Context.class));
        }catch (Exception ex) {
            e = ex;
        }
        assertNotNull(e);
    }

    @Test
    public void testClassName() throws Exception {
        KitIntegrationFactory factory = new KitIntegrationFactory();
        Map<Integer, String> integrations = factory.getKnownIntegrations();
        String className = getKit().getClass().getName();
        for (Map.Entry<Integer, String> entry : integrations.entrySet()) {
            if (entry.getValue().equals(className)) {
                return;
            }
        }
        fail(className + " not found as a known integration.");
    }

    @Test
    public void testBuildUrl() throws Exception {
        String url = getKit().encodeIds("<MCID>", "<ORG ID>", "<BLOB>", "<REGION>", "<PUSH TOKEN>", "<GAID>", new HashMap<MParticle.IdentityType, String>());
        String testUrl1 = "d_mid=<MCID>&d_ver=2&d_orgid=<ORG ID>&d_cid=20914%01<GAID>&d_cid=20919%01<PUSH TOKEN>&dcs_region=<REGION>&d_blob=<BLOB>&d_ptfm=android";
        assertEqualUnorderedUrlParams(url, testUrl1);

        Map<MParticle.IdentityType, String> userIdentities = new HashMap<>();
        userIdentities.put(MParticle.IdentityType.CustomerId, "<CUSTOMER ID>");
        userIdentities.put(MParticle.IdentityType.Email, "<EMAIL>");
        String url2 = getKit().encodeIds("<MCID>", "<ORG ID>", "<BLOB>", "<REGION>", "<PUSH TOKEN>", "<GAID>", userIdentities);
        String testUrls2 = "d_mid=<MCID>&d_ver=2&d_orgid=<ORG ID>&d_cid=20914%01<GAID>&d_cid=20919%01<PUSH TOKEN>&dcs_region=<REGION>&d_blob=<BLOB>&d_ptfm=android&d_cid_ic=customerid%01<CUSTOMER ID>&d_cid_ic=email%01<EMAIL>";
        assertEqualUnorderedUrlParams(url2, testUrls2);
    }

    @Test
    public void testGetUrlInstance() throws Exception {
        AdobeKit kit = getKit();
        kit.setKitManager(Mockito.mock(KitManagerImpl.class));
        Map<String, String> integrationAttributes = new HashMap<>();
        integrationAttributes.put(AdobeKitBase.MARKETING_CLOUD_ID_KEY, "foo");
        Mockito.when(kit.getKitManager().getIntegrationAttributes(Mockito.any(KitIntegration.class))).thenReturn(integrationAttributes);

        Map settings = new HashMap<>();
        settings.put(AdobeKitBase.AUDIENCE_MANAGER_SERVER, "some.random.url");
        kit.onKitCreate(settings, Mockito.mock(Context.class));

        String url = kit.getUrl();
        assertEquals(url, "some.random.url");
    }

    private void assertEqualUnorderedUrlParams(String url1, String url2) {
        if (url1 == null && url2 == null) {
            return;
        }
        List<String> url1Split = Arrays.asList(url1.split("&"));
        List<String> url2Split = Arrays.asList(url2.split("&"));
        assertEquals(url1Split.size(), url2Split.size());
        Collections.sort(url1Split);
        Collections.sort(url2Split);
        for (int i = 0; i < url1Split.size(); i++) {
            if (!url1Split.get(i).equals(url2Split.get(i)))
            assertTrue(false);
        }
    }
}