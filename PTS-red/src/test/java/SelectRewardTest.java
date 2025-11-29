import org.terraFutura.*;

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class SelectRewardTest {
    @Test
    public void testInitState() {
        SelectReward sr= new SelectReward();
        assertFalse(sr.getPlayer().isPresent());
        String st= sr.state();
        assertNotNull(st);
        assertTrue(st.contains("\"player\":null") || st.contains("\"player\":"));
    }

    @Test
    public void testSetReward() {
        Card c= new Card(null,null,2);
        SelectReward sr= new SelectReward();

        Resource[] rewards= new Resource[]{Resource.Green,Resource.Red};
        sr.setReward(2, c, rewards);

        Optional<Integer> current= sr.getPlayer();

        assertTrue(current.isPresent());
        assertEquals(2, (int) current.get());
        assertFalse(sr.canSelectReward(Resource.Bulb));
        assertTrue(sr.canSelectReward(Resource.Red));
    }

    @Test
    public void testCannotDoDouble() {
        Card card = new Card(null, null, 1);
        SelectReward sr= new SelectReward();
        Resource[] rewards =new Resource[]{Resource.Bulb};
        sr.setReward(1,card,rewards);
        sr.selectReward(Resource.Bulb);

        try {
            sr.selectReward(Resource.Bulb);
            fail("Should throw exception on second");
        } catch (IllegalStateException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testRewardReset() {
        Card card= new Card(null, null, 2);
        SelectReward sr= new SelectReward();

        Resource[] rewards= new Resource[]{Resource.Gear};
        sr.setReward(2, card, rewards);

        assertTrue(sr.getPlayer().isPresent());
        assertTrue(sr.canSelectReward(Resource.Gear));

        sr.selectReward(Resource.Gear);

        assertTrue(card.getResourcesSnapshot().contains(Resource.Gear));
        assertFalse(sr.getPlayer().isPresent());
        assertFalse(sr.canSelectReward(Resource.Gear));
        assertTrue(sr.state().contains("\"player\":null") || sr.state().contains("\"player\":null"));
    }

    @Test
    public void testJson() {
        Card card= new Card(null, null, 1);
        SelectReward sr =new SelectReward();
        sr.setReward(2, card, new Resource[]{Resource.Car,Resource.Money});

        String st = sr.state();
        assertNotNull(st);
        assertTrue(st.startsWith("{"));
        assertTrue(st.contains("Car"));
        assertTrue(st.contains("Money"));
    }
}
