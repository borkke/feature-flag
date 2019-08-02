package is.symphony.feature;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class FeatureServiceTest {

    @Test(expected = IllegalArgumentException.class)
    public void WhenOneOfTheFeaturesContainsSpecialCharacter_ThenItShouldThrowException(){
        List<Feature> features = new ArrayList<>();
        features.add(Feature.from(FeatureId.from("user@Page"), FeatureState.ENABLED, new ArrayList<String>()));
        features.add(Feature.from(FeatureId.from("customerPage"), FeatureState.ENABLED, new ArrayList<String>()));

        FeatureFlagService flagService = new FeatureFlagService(features);

        flagService.getEnabledFeaturesFor("USR123");
    }

    @Test
    public void WhenNullIsProvidedAsAConstructor_ThenItShouldReturnEmptyListOfFeatures(){
        FeatureFlagService flagService = new FeatureFlagService(null);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(0)
        );
    }

    @Test
    public void WhenAllFeaturesAreInEnabledState_ThenItShouldReturnListOfAllFeatures(){
        List<Feature> features = new ArrayList<>();
        features.add(Feature.from(FeatureId.from("userPage"), FeatureState.ENABLED, new ArrayList<String>()));
        features.add(Feature.from(FeatureId.from("customerPage"), FeatureState.ENABLED, new ArrayList<String>()));
        features.add(Feature.from(FeatureId.from("billingPage"), FeatureState.ENABLED, new ArrayList<String>()));

        FeatureFlagService flagService = new FeatureFlagService(features);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(3)
        );
    }

    @Test
    public void WhenAllFeaturesAreInDisabledState_ThenItShouldReturnEmptyListOfFeatures(){
        List<Feature> features = new ArrayList<>();
        features.add(Feature.from(FeatureId.from("userPage"), FeatureState.DISABLED, new ArrayList<String>()));
        features.add(Feature.from(FeatureId.from("customerPage"), FeatureState.DISABLED, new ArrayList<String>()));
        features.add(Feature.from(FeatureId.from("billingPage"), FeatureState.DISABLED, new ArrayList<String>()));

        FeatureFlagService flagService = new FeatureFlagService(features);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(0)
        );
    }

    @Test
    public void WhenThereAreEnabledAndFilteredFeaturesForTheId_ThenItShouldReturnEnabledAndFilteredFeatures(){
        List<Feature> features = new ArrayList<>();
        features.add(Feature.from(FeatureId.from("userPage"), FeatureState.DISABLED, new ArrayList<>()));
        features.add(Feature.from(FeatureId.from("customerPage"), FeatureState.FILTERED, Arrays.asList("USR123")));
        features.add(Feature.from(FeatureId.from("billingPage"), FeatureState.ENABLED, new ArrayList<>()));

        FeatureFlagService flagService = new FeatureFlagService(features);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(2)
        );
    }

    @Test
    public void WhenThereAreFilteredFeaturesButIdIsNotWhitelisted_ThenItShouldReturnEmptyList(){
        List<Feature> features = new ArrayList<>();
        features.add(Feature.from(FeatureId.from("userPage"), FeatureState.FILTERED, Arrays.asList("123", "145555", "98")));
        features.add(Feature.from(FeatureId.from("customerPage"), FeatureState.FILTERED, Arrays.asList("123")));
        features.add(Feature.from(FeatureId.from("billingPage"), FeatureState.FILTERED, Arrays.asList("44")));

        FeatureFlagService flagService = new FeatureFlagService(features);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(0)
        );
    }

}
