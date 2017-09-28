package com.ebs.android.exposure.metadata.builders;

import com.ebs.android.exposure.clients.exposure.ExposureError;
import com.ebs.android.exposure.interfaces.IExposureCallback;
import com.ebs.android.exposure.metadata.IMetadataCallback;
import com.ebs.android.exposure.models.EmpChannel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Joao Coelho on 2017-07-18.
 */
public class ChannelsBuilder extends EmpBaseBuilder implements IExposureCallback {

    public ChannelsBuilder(IMetadataCallback<ArrayList<EmpChannel>> listener) {
        super(listener);
    }

    public ArrayList<EmpChannel> getMetadata(JSONObject payload) {
        ArrayList<EmpChannel> channels = new ArrayList<EmpChannel>();

        try {
            JSONArray items = payload.getJSONArray("items");

            for(int i = 0; i < items.length(); ++i) {
                try {
                    EmpChannel channel = new EmpChannel();

                    JSONObject channelJson = items.getJSONObject(i);
                    String channelName = this.getLocalized(channelJson, "en", "title");
                    String logoUrl = this.getLocalizedImages(channelJson, "en", "thumbnail");
                    String channelId = channelJson.getString("assetId");

                    channel.name = channelName;
                    channel.logoUrl = logoUrl;
                    channel.channelId = channelId;

                    channels.add(channel);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return channels;
    }


    @Override
    public void onCallCompleted(JSONObject response, ExposureError error) {
        if (handleError(error)) {
            return;
        }
        this.listener.onMetadata(getMetadata(response));
    }

}