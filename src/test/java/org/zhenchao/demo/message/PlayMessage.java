package org.zhenchao.demo.message;

import org.zhenchao.demo.Actor;

public class PlayMessage implements Message {

    private final Actor<Message> responseActor;

    public PlayMessage(Actor<Message> responseActor) {
        this.responseActor = responseActor;
    }

    public Actor<Message> getResponseActor() {
        return this.responseActor;
    }

}
