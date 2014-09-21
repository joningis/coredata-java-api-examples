/*
 * Copyright 2014 Azazo
 *
 */
package com.bangsapabbi.api.examples;


import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.bangsapabbi.api.ClientBuilder;
import com.bangsapabbi.api.CoredataClient;
import com.bangsapabbi.api.comment.Comment;
import com.bangsapabbi.api.comment.CommentService;
import com.bangsapabbi.api.common.Insertable;
import com.bangsapabbi.api.common.Service;
import com.google.gson.GsonBuilder;


/**
 * Code that randomly inserts comment from the The Internet Chuck Norris Database.
 * Comments are inserted for projects, tasks and files.
 */
public class Chuck {

    protected final Client client;

    private static final double PROBABILITY = 0.9;

    public static void main(String[] args) {
        Chuck chuck = new Chuck();

        String username = "";
        String password = "";
        final CoredataClient client = ClientBuilder.newClient(
                "http://localhost:8100", username, password);

        final CommentService commentService = client.getCommentService();

        chuck.randomlyComment(commentService, client.getFileService());
        chuck.randomlyComment(commentService, client.getProjectService());
        chuck.randomlyComment(commentService, client.getTaskService());

    }

    private <T extends Insertable<T>> void randomlyComment(final CommentService commentService,
                                                           final Service<T> service) {
        for (T item : service) {
            if (shouldComment()) {
                System.out.println("Commented on document: " + item.getUUID());
                this.writeComment(commentService, item);
            }
        }
    }

    private <T extends Insertable<T>> void writeComment(final CommentService commentService,
                                                        final T item) {

        Comment comment = new Comment();
        comment.setText(this.get().getJoke().toString());
        comment.setParentUUID(item.getUUID());
        commentService.add(comment);
    }

    private static boolean shouldComment() {
        return Math.random() < PROBABILITY;
    }

    public Chuck() {
        this.client = javax.ws.rs.client.ClientBuilder.newClient();
    }

    public ChuckContainer get() {
        try {
            final WebTarget myResource = client.target("http://api.icndb.com/jokes/random");
            final String response = myResource.request(MediaType.TEXT_PLAIN).get(String.class);
            return new GsonBuilder().create().fromJson(response, ChuckContainer.class);
        } catch (NotFoundException e) {
            return null;
        }
    }

}
