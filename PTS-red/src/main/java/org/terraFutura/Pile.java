package org.terraFutura;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

public class Pile {
    private List<Card> hiddenCards;
    private LinkedList<Card> visibleCards;
    private Random random;

    public Pile(List<Card> hiddenCards, List<Card> visibleCards) {
        this(hiddenCards, visibleCards,new Random());
    }

    public Pile(List<Card> hiddenCards, List<Card> visibleCards ,Random random) {
        this.hiddenCards = new ArrayList<>(hiddenCards);
        this.visibleCards = new LinkedList<>(visibleCards);
        this.random = random==null?new Random():random;
    }

    public Pile(){
        this(new ArrayList<>(),new LinkedList<>(),new Random());
    }

    public Card getCard(int index) {
        if(index < 0 || index >= 4) throw new IndexOutOfBoundsException();
        if(index >= visibleCards.size()) throw new IllegalStateException();
        return visibleCards.get(index);
    }

    public void takeCard(int index){
        if(index < 0 || index >= 4) throw new IndexOutOfBoundsException();
        if(index >= visibleCards.size()) throw new IllegalStateException();
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
            JSONObject cardJson = new JSONObject();
            cardJson.put("state", card.state());
            visible.put(cardJson);
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
