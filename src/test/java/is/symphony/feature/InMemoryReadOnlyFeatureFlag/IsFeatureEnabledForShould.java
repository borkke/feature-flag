package is.symphony.feature.InMemoryReadOnlyFeatureFlag;

import is.symphony.feature.inmemory.InMemoryReadOnlyFeatureFlag;
import is.symphony.feature.model.Feature;
import is.symphony.feature.model.FeatureId;
import is.symphony.feature.model.FeatureState;
import is.symphony.feature.model.Features;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class IsFeatureEnabledForShould {

    @Test
    public void ReturnFalse_WhenThereAreNoFeatureWithGivenId(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("userPage"), FeatureState.FILTERED, Collections.singletonList("98")));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        Boolean actualIsEnabled = flagService.isFeatureEnabledFor(FeatureId.from("billingPage"), "98");

        Assert.assertThat(
                actualIsEnabled,
                is(false)
        );
    }

    @Test
    public void ReturnFalse_WhenFeatureIsInDisabledState(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("userPage"), FeatureState.DISABLED, Collections.singletonList("98")));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        Boolean actualIsEnabled = flagService.isFeatureEnabledFor(FeatureId.from("userPage"), "98");

        Assert.assertThat(
                actualIsEnabled,
                is(false)
        );
    }

    @Test
    public void ReturnFalse_WhenThereIsAFeatureWithFilteredState_AndAnIdIsNotWhitelisted(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("userPage"), FeatureState.FILTERED, Collections.singletonList("98")));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        Boolean actualIsEnabled = flagService.isFeatureEnabledFor(FeatureId.from("userPage"), "123");

        Assert.assertThat(
                actualIsEnabled,
                is(false)
        );
    }

    @Test
    public void ReturnTrue_WhenFeatureIsInEnabledState(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("userPage"), FeatureState.ENABLED, new ArrayList<>()));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        Boolean actualIsEnabled = flagService.isFeatureEnabledFor(FeatureId.from("userPage"), "98");

        Assert.assertThat(
                actualIsEnabled,
                is(true)
        );
    }

    @Test
    public void ReturnTrue_WhenThereIsAFeatureWithFilteredState_AndAnIdIsWhitelisted(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("userPage"), FeatureState.FILTERED, Collections.singletonList("98")));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        Boolean actualIsEnabled = flagService.isFeatureEnabledFor(FeatureId.from("userPage"), "98");

        Assert.assertThat(
                actualIsEnabled,
                is(true)
        );
    }
}
