import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker {
    static String[] punctuations = {",", ".", "'","?","!",":"};

    public static void main(String[] args) {
        // Words Provided as Argument
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input a word you want to look up");
        String userinput = scanner.nextLine();
        System.out.println("We've received your input. Checking is this word spelled correctly...");
        SpellDictionary newDict = new SpellDictionary("data/words.txt");
        boolean tOrF = newDict.containsWord(userinput);
        
        if (tOrF == false) {
            ArrayList<String> alt = newDict.nearMisses(userinput);
            System.out.println("Here is some alternative words you might want to put in:");
            for(int i = 0; i<alt.size(); i++){
                System.out.print(alt.get(i) + " ");
            }
        }
        else{
            System.out.println("Congradulations! This word is accurate!");
        }
        
        scanner.close();

        // Words Read from a File
        Scanner file = SpellDictionary.readFile("data/sonnet2.txt");
        ArrayList<String> wordsFile = new ArrayList<String>();
        while(file.hasNext()){
            String word = file.next();
            for(String punctuation:punctuations){
                if(word.endsWith(punctuation)){
                    continue;
                }
                else{
                    wordsFile.add(word);
                }
            }
        }
        
        System.out.println("Scanning the file...");
        int error = 0;
        for(int i = 0; i<wordsFile.size(); i++){
            if(error != 0){
                break;
            }
            boolean check = newDict.containsWord(wordsFile.get(i));
            if (check == false) {
                System.out.println("There are mistakes in your spelling");
                ArrayList<String> alt = newDict.nearMisses(wordsFile.get(i));
                System.out.println("Here is some alternative words you might want to put in:");
                System.out.println(alt);
                
                error++;
            }
            else{
                continue;
            }
        }
        

    }
}
