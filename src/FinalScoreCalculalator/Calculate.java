package FinalScoreCalculalator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// X X X X X X X X X X XXX
//

// To calculate FRAME AND FINAL scores of an INDIVIDUAL bowling game given the game is FINISHED and all scores are already RECORDED
// Currently NO VALIDATION of input

public class Calculate {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
        File file = new File("src/FinalScoreCalculalator/scores.txt");
        ArrayList<ArrayList<Integer>> arrayOfScores = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        String data;
        List<Integer> temp;
        while (scanner.hasNextLine()){
            data = scanner.nextLine();
            temp = Arrays.stream(data.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            arrayOfScores.add(new ArrayList<>(temp));
        }
        // All Arrays have been added
        ArrayList<Integer> finalScores = new ArrayList<>();
        for(ArrayList<Integer> game: arrayOfScores){
            game.remove(0);
            finalScores.add(calculateScore(game));
        }
        System.out.println(finalScores);
    }

    public static int calculateScore(ArrayList<Integer> scores){
        int finalScore = 0;
        int strikeCarry = 0;
        int frameScore = 0;
        int next = 0, nextTwo = 0;
        int currentFrame = 1;

        for(int i = 0; i < scores.size(); i++){
            // index out of bounds check
            next = (i==scores.size()-1)?0:scores.get(i+1);
            nextTwo = (i==scores.size()-1||i==scores.size()-2)?0:scores.get(i+2);
            if(currentFrame==10){
                finalScore += scores.get(i) + next + nextTwo;
                break;
            }

            finalScore += scores.get(i);
            frameScore += scores.get(i);
            if((i+strikeCarry)%2==0) {
                if(frameScore==10){
//                    System.out.println("Strike at "+currentFrame);
                    finalScore += next + nextTwo;
                    strikeCarry++;
//                    System.out.println("Score rn "+finalScore+" at currentFrame " + currentFrame);
                    frameScore=0;
                    currentFrame++;
                }
            } else if((i+strikeCarry)%2==1){
                if(frameScore==10){
                    //Its a spare
                    finalScore += next;
//                    System.out.println("Spare! at "+currentFrame);
                }
                frameScore = 0;
//                System.out.println("Score rn "+finalScore+" at currentFrame " + currentFrame);
                currentFrame++;
            }

        } // end main for
        return finalScore;
    } // end method calculateScores()

} // end class
