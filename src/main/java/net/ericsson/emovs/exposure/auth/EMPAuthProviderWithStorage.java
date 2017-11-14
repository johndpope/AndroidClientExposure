package net.ericsson.emovs.exposure.auth;


import android.util.Log;

import net.ericsson.emovs.exposure.clients.exposure.ExposureClient;
import net.ericsson.emovs.exposure.clients.exposure.ExposureError;
import net.ericsson.emovs.exposure.interfaces.IAuthenticationListener;
import net.ericsson.emovs.exposure.interfaces.IExposureCallback;
import net.ericsson.emovs.utilities.EMPRegistry;

import org.json.JSONObject;

/**
 * Created by Joao Coelho on 2017-09-25.
 */

public class EMPAuthProviderWithStorage extends EMPAuthProvider {

    private EMPAuthProviderWithStorage() {
        super();
    }

    private static class EMPAuthProviderWithStorageHolder {
        private final static EMPAuthProviderWithStorage sInstance = new EMPAuthProviderWithStorage();
    }

    public static EMPAuthProvider getInstance() {
        SharedPropertiesICredentialsStorage sharedStorage = SharedPropertiesICredentialsStorage.getInstance();
        sharedStorage.storeCredentials(EMPRegistry.apiUrl(), EMPRegistry.customer(), EMPRegistry.businessUnit(), null);
        EMPAuthProviderWithStorage.EMPAuthProviderWithStorageHolder.sInstance.setApplicationContext(EMPRegistry.applicationContext());
        EMPAuthProviderWithStorage.EMPAuthProviderWithStorageHolder.sInstance.setCredentialsStore(sharedStorage);
        return EMPAuthProviderWithStorage.EMPAuthProviderWithStorageHolder.sInstance;
    }

    @Override
    public Boolean isAuthenticated() {
        if(mCredentials == null) {
            mCredentials = SharedPropertiesICredentialsStorage.getInstance().getCredentials();
        }
        return null != mCredentials;
    }

    @Override
    public void logout() {
        if (this.isAuthenticated()) {
            super.logout();
        }
    }

    @Override
    public void checkAuth(final IAuthenticationListener listener) {
        if(mCredentials == null) {
            mCredentials = SharedPropertiesICredentialsStorage.getInstance().getCredentials();
        }
        super.checkAuth(new IAuthenticationListener() {
            @Override
            public void onAuthSuccess(String sessionToken) {
                listener.onAuthSuccess(sessionToken);
            }

            @Override
            public void onAuthError(ExposureError error) {
                SharedPropertiesICredentialsStorage.getInstance().deleteCredentials();
                listener.onAuthError(error);
            }
        });
    }
}
