package org.zhenchao.demo;

import org.zhenchao.demo.message.Message;
import org.zhenchao.demo.message.PlayMessage;
import org.zhenchao.demo.message.StartMessage;

/**
 * 剪刀石头布游戏
 */
public class RockPaperScissors {

    public static void main(String[] args) throws Exception {
        ActorFactory actorFactory = new ActorFactory();

        // create actors
        Actor<PlayMessage> player1 = actorFactory.createActor(new Player("Player1"));
        Actor<PlayMessage> player2 = actorFactory.createActor(new Player("Player2"));

        Coordinator coordinatorFunction = new Coordinator(player1, player2);
        Actor<Message> coordinator = actorFactory.createActor(coordinatorFunction);
        coordinatorFunction.setCoordinator(coordinator);

        // start to play
        coordinator.send(StartMessage.INSTANCE);

        Thread.sleep(100000);
    }

}
