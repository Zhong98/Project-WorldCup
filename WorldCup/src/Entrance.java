import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Entrance {
    public static void main(String[] args) {
        ArrayList<Team> teamList=new ArrayList<>();
        Team winner=new Team();
        Game game=new Game();
        System.out.println("Please enter the path of file: (ex.F:\\JAVA\\...\\team.txt)");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();//Let the user enter the path of file
        //String path="F:\\JAVA\\WorldCup\\Team.txt";
        game.setTeams(path);

        ArrayList<Player> players=new ArrayList<>();//playerList，to get the MVP

        String champion="";
        String MVP="";

        System.out.println("Enter your choice:\n" +
                "A.Play preliminaries\n"+
                "B.Play the final\n"+
                "C.Show the record of each team\n"+
                "D.Show player goals\n"+
                "E.Show the result of WorldCup\n"+
                "F.Show teams of final\n"+
                "G.Close and output the result into a txt file\n");


        System.out.println("Enter your choice:");

        while(sc.hasNextLine()){
            String choose=sc.nextLine();
            if (choose.equals("A")){
                teamList=game.playPreliminaryGame();
            }

            else if (choose.equals("B")){
                winner=game.playFinals();
            }

            else if (choose.equals("C")){
                for (int i = 0; i < teamList.size(); i++) {
                    System.out.println(teamList.get(i).getName()+" - played "+teamList.get(i).played+" - won "+teamList.get(i).won+" - lost "+teamList.get(i).lost+" - goal "+teamList.get(i).totalGoals+" - points "+teamList.get(i).score);
                }

            }else if(choose.equals("D")){
                for (int i = 0; i < teamList.size(); i++) {
                    for (int j = 0; j < 2; j++) {
                        teamList.get(i).setPlayerGoal();
                        System.out.println(teamList.get(i).getPlayers()[j].getName()+" - "+teamList.get(i).getName()+" - "+teamList.get(i).getPlayers()[j].getGoals());
                    }
                }

            }else if(choose.equals("E")){
                champion="Champion - "+winner.getName();
                System.out.println(champion);
                for (int i = 0; i < teamList.size(); i++) {
                    teamList.get(i).setPlayerCountry();
                    for (int j = 0; j < 2; j++) {
                        players.add(teamList.get(i).getPlayers()[j]);
                    }
                }//Put all info of players into the list 'players'
                Collections.sort(players,(p1,p2)->{
                    int result;
                    if (p1.getGoals()> p2.getGoals()){
                        result=-1;
                    }else {
                        result=1;
                    }
                    return result;
                });
                MVP="MVP - "+players.get(0).getName()+"("+players.get(0).getCountry()+")";
                System.out.println(MVP);

            }else if(choose.equals("F")){
                System.out.println("The final match："+ game.finalMatchTeams[0].getName()+" VS "+ game.finalMatchTeams[1].getName());

            }else if(choose.equals("G")){
                FileOutputStream fos=null;
                try{
                    String result=champion+"     "+MVP;
                    fos=new FileOutputStream("Result.txt");
                    byte bytes[];
                    bytes=result.getBytes(StandardCharsets.UTF_8);
                    int b=bytes.length;
                    fos.write(bytes,0,b);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }finally {
                    try{
                        if (fos!=null){
                            fos.close();
                        }
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }else{
                break;
            }
        }
    }
}
