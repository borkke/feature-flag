package is.symphony.feature.InMemoryReadOnlyFeatureFlag;

import is.symphony.feature.inmemory.InMemoryReadOnlyFeatureFlag;
import is.symphony.feature.model.Feature;
import is.symphony.feature.model.FeatureId;
import is.symphony.feature.model.FeatureState;
import is.symphony.feature.model.Features;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class GetEnabledFeaturesForShould {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    public void ThrowException_WhenOneOfTheFeaturesContainsSpecialCharacter(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("user@Page"), FeatureState.ENABLED, new ArrayList<>()));
        featureList.add(Feature.from(FeatureId.from("customerPage"), FeatureState.ENABLED, new ArrayList<>()));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        flagService.getEnabledFeaturesFor("USR123");

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("id can not be longer than 20 characters.");
    }

    @Test
    public void ReturnEmptyListOfFeatures_WhenNullIsProvidedAsAConstructor(){
        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(null);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(0)
        );
    }

    @Test
    public void ReturnEmptyListOfFeatures_WhenAllFeaturesAreInDisabledState(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("userPage"), FeatureState.DISABLED, new ArrayList<>()));
        featureList.add(Feature.from(FeatureId.from("customerPage"), FeatureState.DISABLED, new ArrayList<>()));
        featureList.add(Feature.from(FeatureId.from("billingPage"), FeatureState.DISABLED, new ArrayList<>()));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(0)
        );
    }

    @Test
    public void ReturnEmptyListOfFeatures_WhenThereAreFilteredFeaturesButIdIsNotWhitelisted(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("userPage"), FeatureState.FILTERED, Arrays.asList("123", "145555", "98")));
        featureList.add(Feature.from(FeatureId.from("customerPage"), FeatureState.FILTERED, Arrays.asList("123")));
        featureList.add(Feature.from(FeatureId.from("billingPage"), FeatureState.FILTERED, Arrays.asList("44")));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(0)
        );
    }

    @Test
    public void ReturnListOfAllFeatures_WhenAllFeaturesAreInEnabledState(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("userPage"), FeatureState.ENABLED, new ArrayList<>()));
        featureList.add(Feature.from(FeatureId.from("customerPage"), FeatureState.ENABLED, new ArrayList<>()));
        featureList.add(Feature.from(FeatureId.from("billingPage"), FeatureState.ENABLED, new ArrayList<>()));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(3)
        );
    }

    @Test
    public void ReturnEnabledAndFilteredFeatures_WhenThereAreEnabledAndFilteredFeaturesForTheId(){
        List<Feature> featureList = new ArrayList<>();
        featureList.add(Feature.from(FeatureId.from("userPage"), FeatureState.DISABLED, new ArrayList<>()));
        featureList.add(Feature.from(FeatureId.from("customerPage"), FeatureState.FILTERED, Arrays.asList("USR123")));
        featureList.add(Feature.from(FeatureId.from("billingPage"), FeatureState.ENABLED, new ArrayList<>()));
        Features features = Features.from(featureList);

        InMemoryReadOnlyFeatureFlag flagService = new InMemoryReadOnlyFeatureFlag(features);

        List<Feature> actualEnabledFeatures = flagService.getEnabledFeaturesFor("USR123");

        Assert.assertThat(
                actualEnabledFeatures.size(),
                is(2)
        );
    }
}
