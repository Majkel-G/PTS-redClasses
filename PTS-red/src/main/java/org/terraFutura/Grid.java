package org.terraFutura;
import org.json.*;

import java.util.*;
public class Grid {

    private Map<GridPosition, Card> board = new HashMap<>();
    private Set<GridPosition> activated=new HashSet<>();
    private List<GridPosition> activationPattern;

    public Grid() {}

    public Grid(Card card) {
        putCard(GridPosition.START_P33,card);
    }
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
        activationPattern = new ArrayList<>(pattern);
    }

    public List<GridPosition> getActivationPattern(){
        return activationPattern;
    }

    public void endTurn(){
        activated.clear();
    }


    public String state(){
        JSONObject result= new JSONObject();
        JSONArray activatedJson= new JSONArray();
        GridPosition[] activatedArr= activated.toArray(new GridPosition[0]);
        for (GridPosition position : activatedArr) {
            JSONObject o=new JSONObject();
            o.put("x", position.getX());
            o.put("y", position.getY());
            activatedJson.put(o);
        }
        result.put("activated", activatedJson);

        JSONArray patternJson= new JSONArray();
        for (GridPosition gridPosition : activationPattern) {
            JSONObject o= new JSONObject();
            o.put("x", gridPosition.getX());
            o.put("y", gridPosition.getY());
            patternJson.put(o);
        }
        result.put("activationPattern", patternJson);

        JSONArray boardJson= new JSONArray();
        GridPosition[] keys= board.keySet().toArray(new GridPosition[0]);
        for (GridPosition p : keys) {
            JSONObject entry= new JSONObject();
            entry.put("position", p.name());
            entry.put("x", p.getX());
            entry.put("y", p.getY());
            boardJson.put(entry);
        }
        result.put("board", boardJson);
        return result.toString();
    }
}