package org.zhenchao.demo;

import org.jetlang.core.Callback;
import org.zhenchao.demo.message.Move;
import org.zhenchao.demo.message.PlayMessage;
import org.zhenchao.demo.message.ThrowMessage;

import java.util.Random;

public class Player implements Callback<PlayMessage> {

    private static final Random RANDOM = new Random();

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(PlayMessage message) {
        message.getResponseActor().send(new ThrowMessage(name, this.randomMove()));
    }

    /**
     * 随机出剪刀石头布
     *
     * @return
     */
    private Move randomMove() {
        return Move.values()[RANDOM.nextInt(Move.values().length)];
    }
}
