package org.zhenchao.demo;

import org.jetlang.core.Callback;
import org.zhenchao.demo.message.Message;
import org.zhenchao.demo.message.Move;
import org.zhenchao.demo.message.PlayMessage;
import org.zhenchao.demo.message.StartMessage;
import org.zhenchao.demo.message.ThrowMessage;

public class Coordinator implements Callback<Message> {

    private final Actor<PlayMessage> player1;
    private final Actor<PlayMessage> player2;
    private Actor<Message> coordinator;

    private ThrowMessage pendingMessage;

    public Coordinator(Actor<PlayMessage> player1, Actor<PlayMessage> player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setCoordinator(Actor<Message> coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof StartMessage) {
            player1.send(new PlayMessage(coordinator));
            player2.send(new PlayMessage(coordinator));
        } else if (message instanceof ThrowMessage) {
            if (pendingMessage == null) {
                pendingMessage = (ThrowMessage) message;
            } else {
                this.announce(pendingMessage, (ThrowMessage) message);
                pendingMessage = null;
                coordinator.send(StartMessage.INSTANCE);
            }
        }
    }

    public void announce(ThrowMessage msg1, ThrowMessage msg2) {
        String winner = "tie";
        Move m1 = msg1.getMove();
        Move m2 = msg2.getMove();
        if (this.firstWins(m1, m2) && !this.firstWins(m2, m1)) {
            winner = msg1.getPlayer();
        } else if (this.firstWins(m2, m1) && !this.firstWins(m1, m2)) {
            winner = msg2.getPlayer();
        } // else tie

        if (msg1.getPlayer().compareTo(msg2.getPlayer()) < 0) {
            System.out.println(msg1 + ", " + msg2 + ", winner = " + winner);
        } else {
            System.out.println(msg2 + ", " + msg1 + ", winner = " + winner);
        }
    }

    private boolean firstWins(Move m1, Move m2) {
        return (m1 == Move.ROCK && m2 == Move.SCISSORS) ||
                (m1 == Move.PAPER && m2 == Move.ROCK) ||
                (m1 == Move.SCISSORS && m2 == Move.PAPER);
    }
}
