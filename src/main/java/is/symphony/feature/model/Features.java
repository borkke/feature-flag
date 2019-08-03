package is.symphony.feature.model;

import java.util.List;
import java.util.stream.Collectors;

public class Features {
    private List<Feature> featureList;

    private Features(List<Feature> featureList){

        this.featureList = featureList;
    }

    public static Features from(List<Feature> featureList){
        return new Features(featureList);
    }

    public List<Feature> toList(){
        return featureList;
    }

    public Features getEnabledFeaturesFor(String id) {
        List<Feature> enabledFeatures = featureList
                .stream()
                .filter(feature -> feature.isEnabledFor(id))
                .collect(Collectors.toList());

        return new Features(enabledFeatures);
    }

    public Boolean isEnabledFor(FeatureId featureId, String id) {
        return featureList
                .stream()
                .anyMatch(feature ->
                        feature.getFeatureId().equals(featureId) &&
                        feature.isEnabledFor(id));
    }
}
