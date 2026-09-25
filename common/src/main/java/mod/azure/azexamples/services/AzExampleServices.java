package mod.azure.azexamples.services;

import java.util.ServiceLoader;

public class AzExampleServices {

    public static final CommonRegistry COMMON_REGISTRY = load(CommonRegistry.class);

    private AzExampleServices() {
        throw new UnsupportedOperationException();
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz)
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
