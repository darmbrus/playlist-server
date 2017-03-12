package com.davidarmbrust.spi.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Provides tests to establish secure properties can be read
 *
 * @author David Armbrust
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotifyPropertiesTest {

    @Autowired
    private SpotifyProperties spotifyProperties;

    @Test
    public void getClientId() throws Exception {
        assertNotNull(spotifyProperties.getClientId());
    }

    @Test
    public void getCallbackUrl() throws Exception {
        assertTrue(spotifyProperties.getCallbackUrl().contains("http://"));
    }
}