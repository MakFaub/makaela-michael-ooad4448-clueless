package clueless;

import clueless.board.Board;
import clueless.cards.Deck;
import clueless.observers.EventType;
import clueless.observers.IEventsObserver;

import java.util.List;
import java.util.logging.Logger;

public class ObservableClueless extends Clueless implements IEventsObserver {
    private static final Logger logger = Logger.getLogger(ObservableClueless.class.getName());


    public ObservableClueless(Board board, Deck deck, List<Player> players){
        super(board, deck, players);
    }

    @Override
    public void update(EventType eventType, Object eventObject) {
        switch (eventType) {
            case SUSPECT_MOVED -> logger.info("Suspect moved: " + eventObject);
            case WEAPON_MOVED -> logger.info("Weapon moved: " + eventObject);
            case ADD_PLAYER -> logger.info("Player added: " + eventObject);
            case SUGGESTION_OCCURRED -> logger.info("Suggestion occurred: " + eventObject);
            case ACCUSATION_OCCURRED -> logger.info("Accusation occurred: " + eventObject);
            case ARTIFACT_USED -> logger.info("Artifact used: " + eventObject);
            case TURN_ENDED -> logger.info("Turn ended: " + eventObject);
        }
    }

    public void playTurn() {
        super.playTurn();
        EventBus.getInstance().postEvent(EventType.TURN_ENDED, "Finished turn");    }
}