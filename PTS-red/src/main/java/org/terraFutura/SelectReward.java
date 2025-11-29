package org.terraFutura;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class SelectReward {

    private Integer player=null;
    private List<Resource> selection=new ArrayList<>();
    private Card rewardCard;

    public void setReward(int player, Card card, Resource[] reward) {
        if(this.player!=null) throw new IllegalStateException();
        if(card==null || reward==null) throw new NullPointerException();

        this.player=player;
        this.rewardCard =card;
        this.selection=new ArrayList<>(Arrays.asList(reward));
    }

    public Optional<Integer> getPlayer() {
        return Optional.ofNullable(player);
    }

    public boolean canSelectReward(Resource resource) {
        if(resource==null) return false;
        return player!=null &&  selection.contains(resource);
    }

    public void selectReward(Resource resource) {
        if(!canSelectReward(resource)) throw new IllegalStateException();

        List<Resource> newSelection=new ArrayList<>();
        newSelection.add(resource);
        if(!rewardCard.canPutResources(newSelection)) throw new IllegalStateException();
        rewardCard.putResources(newSelection);

        this.player=null;
        this.selection.clear();
        this.rewardCard =null;
    }

    public String state(){
        JSONObject res=new JSONObject();
        if(player!=null) res.put("player",player);
        else res.put("player",JSONObject.NULL);

        JSONArray cards=new JSONArray();
        for(Resource r: selection){
            cards.put(r.name());
        }
        res.put("selection",cards);
        res.put("hasReward", rewardCard!=null);
        res.put("rewardCard",rewardCard);
        return res.toString();
    }
}
