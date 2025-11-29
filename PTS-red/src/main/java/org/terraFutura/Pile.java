package org.terraFutura;

import org.json.JSONObject;
import java.util.*;

public class Pile {
    private List<Card> hiddenCards;
    private List<Card> visibleCards;
    private Random random;

    public Pile(List<Card> hiddenCards, List<Card> visibleCards) {
        this(hiddenCards, visibleCards,new Random());
    }

    public Pile(List<Card> hiddenCards, List<Card> visibleCards ,Random random) {
        this.hiddenCards = new ArrayList<>(hiddenCards);
        this.visibleCards = new ArrayList<>(visibleCards);
        this.random = random;
    }

    public Optional<Card> getCard(int index) {
        if(index < 0 || index >= 4) throw new IndexOutOfBoundsException();
        return Optional.ofNullable(visibleCards.get(index));
    }

    public Card takeCard(int index){
        if(index < 0 || index >= 4) throw new IndexOutOfBoundsException();
        Card card = visibleCards.get(index);
        visibleCards.remove(index);
        visibleCards.addFirst(drawCards());
        return card;
    }

    private Card drawCards(){
        if(hiddenCards.isEmpty()) return null;
        int idx = random.nextInt(hiddenCards.size());
        return hiddenCards.remove(idx);
    }

    public void removeLastCard(){
        if(visibleCards.size()<4) throw new IllegalStateException();
        visibleCards.removeLast();
        visibleCards.addFirst(drawCards());
    }

    private String state(){
        JSONObject result = new JSONObject();
        result.put("visibleCards", visibleCards);
        result.put("hiddenCardsCount", hiddenCards.size());
        return result.toString();
    }


}
