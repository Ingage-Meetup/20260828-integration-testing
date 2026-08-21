package com.example.integrationtesting;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;

public class DistanceUtilsTest {

    @Test
    public void test_calculateDistance() {
        double actual = DistanceUtils.calculateDistance(-37.3159, 81.1496, -31.8129, 63.5342);
        assertThat(actual, equalTo(1069.9115796400772));
    }

}
