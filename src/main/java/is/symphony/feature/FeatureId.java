package is.symphony.feature;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeatureId {
    private String id;

    private FeatureId(String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("id must not be null or empty string.");
        }

        if (20 < id.length()) {
            throw new IllegalArgumentException("id can not be longer than 20 characters.");
        }

        String regex = "[^&%$#@!~]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(id);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("id can not contain special characters.");
        }

        this.id = id;
    }

    public static FeatureId from(String featureId) {
        return new FeatureId(featureId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureId featureId = (FeatureId) o;

        return Objects.equals(featureId, featureId.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
