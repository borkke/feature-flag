package is.symphony.feature;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.List;

class Feature {
    private FeatureId featureId;
    private FeatureState state;
    private List<String> whiteList;

    private Feature(FeatureId featureId, FeatureState state, List<String> whiteList) {
        this.featureId = featureId;
        this.state = state;
        this.whiteList = whiteList;
    }

    static Feature from(FeatureId featureId, FeatureState state, List<String> whiteList) {
        return new Feature(featureId, state, whiteList);
    }

    boolean isEnabledFor(String id) {
        if(state.equals(FeatureState.ENABLED)) return true;
        if(state.equals(FeatureState.DISABLED)) return false;

        return whiteList.contains(id);
    }

    public FeatureId getFeatureId() {
        return featureId;
    }

    public FeatureState getState(){
        return state;
    }

    public List<String> getWhiteList(){
        return whiteList;
    }
}
