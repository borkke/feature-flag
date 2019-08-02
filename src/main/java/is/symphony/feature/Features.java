package is.symphony.feature;

import java.util.List;
import java.util.stream.Collectors;

class Features {
    private List<Feature> featureList;

    private Features(List<Feature> featureList){

        this.featureList = featureList;
    }

    public static Features from(List<Feature> featureList){
        return new Features(featureList);
    }

    List<Feature> toList(){
        return featureList;
    }

    Features getEnabledFeaturesFor(String id) {
        List<Feature> enabledFeatures = featureList
                .stream()
                .filter(feature -> feature.isEnabledFor(id))
                .collect(Collectors.toList());

        return new Features(enabledFeatures);
    }
}
