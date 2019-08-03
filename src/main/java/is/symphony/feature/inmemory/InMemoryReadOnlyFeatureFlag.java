package is.symphony.feature.inmemory;

import is.symphony.feature.ReadOnlyFeatureFlag;
import is.symphony.feature.model.Feature;
import is.symphony.feature.model.FeatureId;
import is.symphony.feature.model.Features;

import java.util.ArrayList;
import java.util.List;

public class InMemoryReadOnlyFeatureFlag implements ReadOnlyFeatureFlag {
    private final Features features;

    public InMemoryReadOnlyFeatureFlag(Features features){
        if(features == null){
            features = Features.from(new ArrayList<>());
        }

        this.features = features;
    }

    public List<Feature> getEnabledFeaturesFor(String id){
        return features
                .getEnabledFeaturesFor(id)
                .toList();
    }

    public Boolean isFeatureEnabledFor(FeatureId featureId, String id) {
        return features
                .isEnabledFor(featureId, id);
    }
}