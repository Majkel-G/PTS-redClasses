package org.terraFutura;

import org.json.JSONArray;
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
        this.random = random==null?new Random():random;
    }

    public Pile(){
        this(Collections.emptyList(),Collections.emptyList(),new Random());
    }

    public Card getCard(int index) {
        if(index < 0 || index >= 4) throw new IndexOutOfBoundsException();
        return hiddenCards.get(index);
    }

    public void takeCard(int index){
        if(index < 0 || index >= 4) throw new IndexOutOfBoundsException();
        visibleCards.remove(index);
        visibleCards.addFirst(drawCards());
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

    public String state(){
        JSONObject result = new JSONObject();
        JSONArray visible = new JSONArray();
        for(Card card : visibleCards){
            visible.put(new JSONObject(card.state()));
        }
        result.put("visible", visible);
        result.put("hiddenCardsCount", hiddenCards.size());
        return result.toString();
    }

    public int getVisibleCardsCount() {
        return visibleCards.size();
    }
    public int getHiddenCardsCount() {
        return hiddenCards.size();
    }


}
