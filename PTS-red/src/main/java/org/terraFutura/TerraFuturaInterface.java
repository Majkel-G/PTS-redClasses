package org.terraFutura;
import java.util.*;

public interface TerraFuturaInterface {
    boolean takeCard(int playerId, CardSource source, GridPosition destination);
    boolean discardLastCardFromDeck(int playerId, Deck deck);
    void activateCard(int playerId, GridPosition card,
                      List<Pair<Resource, GridPosition>> inputs,
                      List<Pair<Resource, GridPosition>> outputs,
                      List<GridPosition> pollution,
                      Optional<Integer> otherPlayerId, Optional<GridPosition> otherCard);

    void selectReward(int playerId, Resource resource);
    boolean turnFinished(int playerId);
    boolean selectActivationPattern(int playerId, int card);
    boolean selectScoring(int playerId, int card);
}
