package is.symphony.feature;

import is.symphony.feature.model.Feature;
import is.symphony.feature.model.FeatureId;

import java.util.List;

public interface ReadOnlyFeatureFlag {
    List<Feature> getEnabledFeaturesFor(String id);
    Boolean isFeatureEnabledFor(FeatureId featureId, String id);
}
