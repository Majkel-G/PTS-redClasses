import org.terraFutura.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PileTest {

    @Test
    public void emptyPileCardsCount() {
        Pile pile = new Pile();
        assertEquals(0, pile.getVisibleCardsCount());
        assertEquals(0, pile.getHiddenCardsCount());
    }

    @Test
    public void emptyTakeCard() {
        Pile pile = new Pile();
        try {
            pile.takeCard(0);
            fail("takeCard on empty visible slot should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // Ok, ocakavame vynimku
        }
    }

    @Test
    public void takeCardRemovesVisibleAndAddsRandomHidden() {
        Card h1 = new Card(null, null, 1);
        Card h2 = new Card(null, null, 1);
        Card v1 = new Card(null, null, 1);
        Card v2 = new Card(null, null, 1);
        Card v3 = new Card(null, null, 1);
        Card v4 = new Card(null, null, 1);

        List<Card> hidden = new ArrayList<>();
        hidden.add(h1);
        hidden.add(h2);

        LinkedList<Card> visible = new LinkedList<>();
        visible.add(v1);
        visible.add(v2);
        visible.add(v3);
        visible.add(v4);
        Pile pile = new Pile(hidden, visible);
        pile.takeCard(2);
        assertEquals(4, pile.getVisibleCardsCount());
        assertEquals(1, pile.getHiddenCardsCount());
    }

    @Test
    public void removeLastNeedsAtLeast4VisibleCards() {
        Card h= new Card(null, null, 1);
        LinkedList<Card> visible= new LinkedList<>();
        visible.add(h);
        visible.add(h);
        visible.add(h);
        Pile pile= new Pile(List.of(h), visible, new Random(2));
        assertThrows(IllegalStateException.class, pile::removeLastCard);
    }

    @Test
    public void removeLastAddsRandomNewest() {
        Card h= new Card(null, null, 1);
        LinkedList<Card> visible = new LinkedList<>();
        visible.add(h);
        visible.add(h);
        visible.add(h);
        visible.add(h);
        Random myRand = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Pile pile = new Pile(List.of(h), visible, myRand);
        pile.removeLastCard();
        assertEquals(4, pile.getVisibleCardsCount());
        assertNotNull(pile.getCard(0));
    }

    @Test
    public void testJson() {
        LinkedList<Card> visible = new LinkedList<>();
        visible.add(new Card(null, null, 1));
        Pile pile = new Pile(List.of(), visible, new Random(5));
        String json = pile.state();
        assertNotNull(json);
        assertTrue(json.contains("visible"));
    }
}
