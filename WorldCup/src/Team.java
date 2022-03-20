import java.util.Random;

public class Team {
    private String name;
    private int ranking;
    private Player[] players = new Player[2];
    private int goals=0;
    int score=0;

    int penaltyShootoutGoals=0;
    int totalGoals=0;
    int finalMatchScore=0;

    int played=0;
    int won=0;
    int lost=0;
    int drawn=0;

    Random random=new Random();
    public void setPlayerGoal(){
        for (int i = 0; i < 2; i++) {
            if (totalGoals%2==0){
                players[i].setGoals(totalGoals/2);
            }else {
                int index=random.nextInt(2);
                if (i==1){
                    players[index].setGoals(totalGoals-players[i-1].getGoals());
                }else{
                    players[index].setGoals(totalGoals/2+1);
                }
            }
        }
    }

    public void setPlayerCountry(){
        for (int i = 0; i < 2; i++) {
            players[i].setCountry(name);
        }
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
