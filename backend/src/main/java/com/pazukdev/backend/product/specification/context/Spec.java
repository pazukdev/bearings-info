package com.pazukdev.backend.product.specification.context;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class Spec {

    private final Map<String, List<String>> specification = new HashMap<>();

    public Spec() {
        final List<String> types = Arrays.asList("deepgroove", "cylindrical_roller", "tapered_roller", "new_one");
        specification.put("type", types);
    }

    public List<String> getSpecs(final String key) {
        return specification.get(key);
    }

}
