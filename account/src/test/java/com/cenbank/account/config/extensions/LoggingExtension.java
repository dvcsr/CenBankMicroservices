package com.cenbank.account.config.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExtension implements BeforeEachCallback, AfterEachCallback {
    private static final Logger logger = LoggerFactory.getLogger(LoggingExtension.class);

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        logger.info("**TEST START**");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        logger.info("**TEST END**");
    }
}
