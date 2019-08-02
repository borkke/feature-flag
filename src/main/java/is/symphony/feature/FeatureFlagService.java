package is.symphony.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class FeatureFlagService {
    private final List<Feature> features;

    FeatureFlagService(List<Feature> features){
        if(features == null) {
            features = new ArrayList<>();
        }

        this.features = features;
    }

    List<Feature> getEnabledFeaturesFor (String id){
        return features
                .stream()
                .filter(feature -> feature.isEnabledFor(id))
                .collect(Collectors.toList());
    }
}