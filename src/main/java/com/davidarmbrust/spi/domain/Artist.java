package com.davidarmbrust.spi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Provides container for Artist information
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                '}';
    }
}
