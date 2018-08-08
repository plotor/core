package org.zhenchao.demo;

import org.zhenchao.demo.message.Message;
import org.zhenchao.demo.message.PlayMessage;
import org.zhenchao.demo.message.StartMessage;

public class RockPaperScissors {

    public static void main(String[] args) throws Exception {
        ActorFactory actors = new ActorFactory();

        // Create actors
        Actor<PlayMessage> player1 = actors.createActor(new Player("Player1"));
        Actor<PlayMessage> player2 = actors.createActor(new Player("Player2"));

        Coordinator coordinatorFunction = new Coordinator(player1, player2);
        Actor<Message> coordinator = actors.createActor(coordinatorFunction);
        coordinatorFunction.setCoordinator(coordinator);

        // Start work
        coordinator.send(StartMessage.INSTANCE);

        Thread.sleep(100000);
    }

}
