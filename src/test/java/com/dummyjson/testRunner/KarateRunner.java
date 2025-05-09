package com.dummyjson.testRunner;

import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.Test;

public class KarateRunner {
    @Karate.Test
    Karate testHello() {
        return Karate.run("classpath:karate/features/hello.feature")
                .relativeTo(getClass());
    }
}