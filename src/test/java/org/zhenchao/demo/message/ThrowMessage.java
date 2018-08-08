package org.zhenchao.demo.message;

public class ThrowMessage implements Message {

    private final String player;
    private final Move move;

    public ThrowMessage(String player, Move move) {
        this.player = player;
        this.move = move;
    }

    public Move getMove() {
        return this.move;
    }

    public String getPlayer() {
        return this.player;
    }

    @Override
    public String toString() {
        return player + " (" + move + ")";
    }
}
