package is.symphony.feature;

import java.util.ArrayList;
import java.util.List;

class FeatureFlagService {
    private final Features features;

    FeatureFlagService(Features features){
        if(features == null){
            features = Features.from(new ArrayList<>());
        }

        this.features = features;
    }

    List<Feature> getEnabledFeaturesFor (String id){
        return features
                .getEnabledFeaturesFor(id)
                .toList();
    }
}