import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Game {
    ArrayList<Team> teamList=new ArrayList<>();

    Random random = new Random();

    Team[] finalMatchTeams=new Team[2];


    public void setTeams(String path){
        if (!path.isEmpty()) {
            File file = new File(path);
            String name = file.getName();
            try {
                FileInputStream fs = new FileInputStream(name);
                BufferedReader br = new BufferedReader(new InputStreamReader(fs, "UTF-8"));

                while (br.read() != -1) {
                    String context = br.readLine();
                    String[] teamInfos = context.split(",");

                    for (int i = 0; i < teamInfos.length; i++) {
                        String[] teamInfo = teamInfos[i].split(" ");
                        String teamName = teamInfo[0];
                        int ranking=Integer.parseInt(teamInfo[1]);

                        Player player1 = new Player();
                        Player player2 = new Player();
                        player1.setName(teamInfo[2]);
                        player2.setName(teamInfo[3]);
                        Player[] players = new Player[]{player1, player2};

                        Team team = new Team();
                        team.setName(teamName);
                        team.setPlayers(players);
                        team.setRanking(ranking);

                        teamList.add(team);
                    }
                }
            }catch (FileNotFoundException e){
                System.out.println("File doesn't exist, please retry!");
            }catch (IOException e){
                e.getMessage();
            }
        }
    }


    private void playGame(Team[] teams){
        if (teams[0].getRanking()> teams[1].getRanking()){
            teams[0].setGoals(random.nextInt(8));
            teams[1].setGoals(random.nextInt(6));
        }else {
            teams[1].setGoals(random.nextInt(8));
            teams[0].setGoals(random.nextInt(6));
        }

        teams[0].totalGoals+=teams[0].getGoals();
        teams[1].totalGoals+=teams[1].getGoals();

        if (teams[0].getGoals()> teams[1].getGoals()){
            teams[0].score+=3;
            teams[0].finalMatchScore=3;
            teams[0].won++;
            teams[1].lost++;

        }else if (teams[0].getGoals()== teams[1].getGoals()){
            teams[0].score+=1;
            teams[1].score+=1;
            teams[0].finalMatchScore=1;
            teams[1].finalMatchScore=1;
            teams[0].drawn++;
            teams[1].drawn++;

        }else {
            teams[1].score+=3;
            teams[1].finalMatchScore=3;
            teams[1].won++;
            teams[0].lost++;
        }
        teams[0].played++;
        teams[1].played++;
    }

    public ArrayList<Team> playPreliminaryGame(){

        int count=0;
        int times=0;
        ArrayList<Team[]> matchs=new ArrayList<>();
        boolean flag = true;

        //the number of match
        for (int i = 1; i < teamList.size(); i++) {
            times+=i;
        }

        while(count<times){
            int index1=random.nextInt(teamList.size());
            int index2=random.nextInt(teamList.size());
            //Team can't play match with itself
            while (index1==index2){
                index1=random.nextInt(teamList.size());
                index2=random.nextInt(teamList.size());
            }

            Team[] match=new Team[2];
            match[0]=teamList.get(index1);
            match[1]=teamList.get(index2);

            //Can't have repeat match
            if (count>0){
                for (int i = 0; i < count; i++) {
                    if (match[0].getName().equals((matchs.get(i))[0].getName()) && match[1].getName().equals((matchs.get(i))[1].getName())){
                        flag=false;
                        break;
                    }else if (match[0].getName().equals((matchs.get(i))[1].getName()) && match[1].getName().equals((matchs.get(i))[0].getName())){
                        flag=false;
                        break;
                    }else {
                        flag=true;
                    }
                }
            }

            if (flag){
                playGame(match);
                matchs.add(match);
                count++;
            }
        }
        //Compare the score, and choose teams who will play the final
        Collections.sort(teamList, (o1, o2) -> {
            int result=0;
            if (o1.score> o2.score){
                result=-1;
            }else {
                result=1;
            }
            return result;
        });
        finalMatchTeams[0]=teamList.get(0);
        finalMatchTeams[1]=teamList.get(1);

        return teamList;
    }

    public int playPenaltyShootOut(Team team){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                int a=random.nextInt(100);
                int b;
                if (a<20){
                    b=0;
                }else {
                    b=1;
                }
                team.penaltyShootoutGoals+=b;
            }
        }
        return team.penaltyShootoutGoals;
    }

    public Team playFinals() {
        Team winner=new Team();
        boolean ticket=false;

        for (int i = 0; i < teamList.size(); i++) {
            if (teamList.get(i).played==3){
                ticket=true;
            }
        }  //check if Preliminaries have been held

        if (ticket==false){
            System.out.println("Preliminary round has not yet been held, please do the preliminary round first!");
        }else{
            playGame(finalMatchTeams);
            if (finalMatchTeams[0].finalMatchScore!=finalMatchTeams[1].finalMatchScore){
                if (finalMatchTeams[0].finalMatchScore>finalMatchTeams[1].finalMatchScore){
                    winner=finalMatchTeams[0];
                }else {
                    winner=finalMatchTeams[1];
                }
            }else {
                for (int i = 0; i < finalMatchTeams.length; i++) {
                    playPenaltyShootOut(finalMatchTeams[i]);
                }
                if (finalMatchTeams[0].penaltyShootoutGoals!=finalMatchTeams[1].penaltyShootoutGoals){
                    if (finalMatchTeams[0].penaltyShootoutGoals>finalMatchTeams[1].penaltyShootoutGoals){
                        winner=finalMatchTeams[0];
                    }else {
                        winner=finalMatchTeams[1];
                    }
                }else {
                    winner = finalMatchTeams[random.nextInt(2)];
                }
            }
        }
        return winner;
    }
}