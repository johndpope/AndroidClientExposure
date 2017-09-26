package com.ebs.android.exposure.metadata.builders;

import com.ebs.android.exposure.clients.exposure.ExposureError;
import com.ebs.android.exposure.interfaces.IExposureCallback;
import com.ebs.android.exposure.metadata.IMetadataCallback;
import com.ebs.android.exposure.models.EmpAsset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Joao Coelho on 23/07/2017.
 */

public class AssetListBuilder extends EmpBaseBuilder implements IExposureCallback {

    public AssetListBuilder(IMetadataCallback<ArrayList<EmpAsset>> listener) {
        super(listener);
    }

    public ArrayList<EmpAsset> getMetadata(JSONObject payload) {
        ArrayList<EmpAsset> assets = new ArrayList<EmpAsset>();

        try {
            JSONArray allAssetsJson = payload.getJSONArray("items");

            for(int i = 0; i < allAssetsJson.length(); ++i) {
                try {
                    JSONObject assetJson = allAssetsJson.getJSONObject(i);
                    EmpAsset asset = new EmpAsset();
                    asset = getAsset(assetJson, asset, true);
                    if(asset != null) {
                        assets.add(asset);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return assets;
    }

    @Override
    public void onCallCompleted(JSONObject response, ExposureError error) {
        if (handleError(error)) {
            return;
        }
        this.listener.onMetadata(getMetadata(response));
    }
}
