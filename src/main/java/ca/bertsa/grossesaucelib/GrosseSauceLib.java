package ca.bertsa.grossesaucelib;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrosseSauceLib implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(LibConstants.MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("{} is initializing!", LibConstants.MOD_NAME);
    }
}
