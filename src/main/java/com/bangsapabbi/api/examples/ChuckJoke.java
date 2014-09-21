/*
 * Copyright 2014 Azazo
 *
 */
package com.bangsapabbi.api.examples;

/**
 * { "type": "success",
 * "value": {
 * "id": 58,
 *
 * "joke": "Crop circles are Chuck Norris' way of telling the world that sometimes corn needs to lie down.", "categories": [] } }
 */
public class ChuckJoke {

    private String joke;

    @Override
    public String toString() {
        return joke;
    }
}
