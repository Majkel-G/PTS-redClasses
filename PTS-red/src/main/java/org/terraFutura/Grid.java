package org.terraFutura;
import org.json.JSONObject;

import javax.smartcardio.Card;
import java.util.*;
public class Grid {

    // state() not finished yet

    private Map<GridPosition, Card> board = new HashMap<>();
    private Set<GridPosition> activated=new HashSet<>();
    private List<GridPosition> activationPattern;

    public Optional<Card> getCard(GridPosition coordinate){
        return Optional.ofNullable(board.get(coordinate));
    }

    public boolean canPutCard(GridPosition coordinate){
        if(coordinate.getX()<-2 || coordinate.getX()>2
           || coordinate.getY()<-2|| coordinate.getY()>2) return false;
        return !board.containsKey(coordinate);
    }

    public void putCard(GridPosition coordinate, Card card){
        if(!canPutCard(coordinate)) throw new IllegalArgumentException("Can't put card");
        board.put(coordinate, card);
    }

    public boolean canBeActivated(GridPosition coordinate){
        return board.containsKey(coordinate) && !activated.contains(coordinate);
    }

    public void setActivated(GridPosition coordinate){
        if(!canBeActivated(coordinate)) throw new IllegalArgumentException("Can't activate card");
        activated.add(coordinate);
    }

    public void setActivationPattern(List<GridPosition> pattern){
        activationPattern = pattern;
    }

    public List<GridPosition> getActivationPattern(){
        return activationPattern;
    }

    public void endTurn(){
        activated.clear();
    }


    public String state(){
        JSONObject result = new JSONObject();
        result.put("activated", activated);
        result.put("activationPattern", activationPattern);
        return result.toString();
    }

}