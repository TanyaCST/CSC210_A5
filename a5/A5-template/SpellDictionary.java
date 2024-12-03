//import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SpellDictionary {
    // attributes
    HashSet <String> wordDict = new HashSet<String>();
    static char[] alphabet = {'a','b','c', 'd','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
    /**
     * 
     * @param filename the pathway for a local file
     * @return
     */
    public static Scanner readFile(String filePath){
        Scanner file = null;
        try{
            file = new Scanner(new File(filePath));
        }catch(FileNotFoundException e){
            System.err.println("Cannot locate file");
            System.exit( - 1);
        }

        return file;
    }

    /**
     * 
     * @param filename
     */
    public SpellDictionary(String filePath){
        // The constructor should meet the following specifications:
        // 1. Accepts a string as input, corresponding to a filename that contains a list of valid words. We have provided `words.txt` for you to use for this purpose.
        // 2. Opens the file
        // 3. Creates an empty `HashSet`
        // 4. Populates the HashSet by reading words one at a time from the file
        Scanner file = readFile(filePath);

        while(file.hasNextLine()){
            String line = file.nextLine();
            if(line.endsWith("'s")){
                continue;
            }
            else{
                this.wordDict.add(line.toLowerCase());
            }
        }
    }

    /**
    *  @param query the word to check
    *  @return true if the query word is in the dictionary.
    */
    public boolean containsWord(String query){
        // Make the query case-blinded -> default: lower case
        query = query.toLowerCase();

        if(this.wordDict.contains(query)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 
     * @param query the word with error
     * @return a HashSet containing the proper alternatives for mispelled query
     */
    public HashSet<String> deletion(String query){
        // **Deletions**: Delete one letter from the word. 
        // Example: **catttle** -> **cattle**
        // Number of Possibilities: *n* possibilities for a word of length *n*

        // Make the query case-blinded -> default: lower case
        query = query.toLowerCase();

        // Create a HashSet to store possible alternative words query might be
        HashSet<String> alternatives = new HashSet<String>();

        for(int i = 0; i<query.length()-1; i++){
            // Remove an element in index i
            StringBuilder wordAlt = new StringBuilder(query).delete(i, i+1);
            
            // If: word after revision is in dictionary, add it into HashSet
            if (wordDict.contains(wordAlt.toString())) {
                alternatives.add(wordAlt.toString());
            }
        }

        return alternatives;
    }

    /**
     * 
     * @param query the word with error
     * @return a HashSet containing the proper alternatives for mispelled query
     */
    public HashSet<String> insertion(String query){
        // **Insertions**: Insert one letter into the word at any point. 
        // Example: **catle** -> **cattle**
        // Number of Possibilities: 26*(*n*+1) possibilities for a word of length *n*

        // Make the query case-blinded -> default: lower case
        query = query.toLowerCase();

        // Create a HashSet to store possible alternative words query might be
        HashSet<String> alternatives = new HashSet<String>();

        for(int i = 0; i<query.length(); i++){ // i is the index where insertion happens
            for(int j = 0; j<alphabet.length; j++){
                // Add a letter from the alphabet into the original query at index i
                StringBuilder wordAlt = new StringBuilder(query).insert(i, alphabet[j]);
                
                // If: word after revision is in dictionary, add it into HashSet
                if (wordDict.contains(wordAlt.toString())) {
                    alternatives.add(wordAlt.toString());
                }
            }
        }
        return alternatives;
    }

    /**
     * Substitute one element with one of the letter in the alphabet
     * @param query the word with error
     * @return a HashSet containing the proper alternatives for mispelled query
     */
    public HashSet<String> substitution(String query){
        // **Substitutions**: Replace one character with another. 
        // Example: **caxtle** -> **cattle**
        // Number of Possibilities: 25**n* possibilities for a word of length *n*

        // Make the query case-blinded -> default: lower case
        query.toLowerCase();

        // Create a HashSet to store possible alternative words query might be
        HashSet<String> alternatives = new HashSet<String>();

        for(int i = 0; i<query.length()-1; i++){ // i is the index where substitution happens
            for(int j = 0; j<alphabet.length; j++){
                // Relace the element on index i with a letter
                StringBuilder wordAlt = new StringBuilder(query).replace(i, i+1, String.valueOf(alphabet[j]));
                
                // If: word after revision is in dictionary, add it into HashSet
                if (wordDict.contains(wordAlt.toString())) {
                    alternatives.add(wordAlt.toString());
                }
            }
        }
        return alternatives;
    }

    /**
     * 
     * @param query the word with error
     * @return a HashSet containing the proper alternatives for mispelled query
     */
    public HashSet<String> transposition(String query){
        // **Transpositions**: Swap two adjacent characters. 
        // Example: **cattel** -> **cattle**
        // Number of Possibilities: *n*-1 possibilities for a word of length *n* 

        // Make the query case-blinded -> default: lower case
        query = query.toLowerCase();
        char[] queryChar = query.toCharArray();

        // Create a HashSet to store possible alternative words query might be
        HashSet<String> alternatives = new HashSet<String>();

        // Create a StringBuilder to copy the incorrectly-spelled word
        StringBuilder wordAlt = new StringBuilder(query);
        
        // Loop through the characters to change the position of 2 elemennts
        for(int i = 0; i<queryChar.length-1; i++){ // representing the change of index to transposition
            // Declare a variable to store the character that will be swapped first
            char temp = wordAlt.charAt(i);

            // Swap the element between i and i+1
            wordAlt.setCharAt(i, wordAlt.charAt(i+1));
            wordAlt.setCharAt(i+1, temp);

            // If: word after revision is in dictionary, add it into HashSet
            if(wordDict.contains(wordAlt.toString())){
                alternatives.add(wordAlt.toString());
            }
        }

        return alternatives;

    }

    /**
     * Separate two words with space
     * @param query the word with error
     * @return a HashSet containing the proper alternatives for mispelled query
     */
    public HashSet<String> spliting(String query){
        // For this kind of near miss, 
        // the pair of words together should be recorded as a single entry, with a space between them. 
        // Example: **cattell** -> **cat tell**
        // Number of Possibilities: *n*-1 possibilities for a word of length *n*

        // Make the query case-blinded -> default: lower case
        query = query.toLowerCase();

        // Create a HashSet to store possible alternative words query might be
        HashSet<String> alternatives = new HashSet<String>();

        for(int i = 0; i<query.length(); i++){
            // Separate two words by making query into 2 substrings
            String firstPt = query.substring(0, i);
            String lastPt = query.substring(i);

            // If: word after revision is in dictionary, add it into HashSet
            if(wordDict.contains(firstPt) && wordDict.contains(lastPt)){
                // Add two words and space together
                String full = firstPt + " " + lastPt;
                alternatives.add(full);
            }

        }

        return alternatives;
        
    }


    /**
    *  @param query the word with error
    *  @return a list of all valid words that are one edit away from the query
    */
    public ArrayList<String> nearMisses(String query){
       // Create a HashSet to store possible alternative words query might be
       HashSet<String> alternatives = new HashSet<String>();

       // **Deletions**: Delete one letter from the word. 
       alternatives.addAll(deletion(query));

       // **Insertions**: Insert one letter into the word at any point. 
       alternatives.addAll(insertion(query));

       // **Substitutions**: Replace one character with another. 
       alternatives.addAll(substitution(query));

       // **Transpositions**: Swap two adjacent characters. 
       alternatives.addAll(transposition(query));

       // **Splits**: Divide the word into two legal words. 
       alternatives.addAll(spliting(query));

       // Convert alternatives from HashSet to arraylist
       ArrayList<String> altArrList = new ArrayList<String>(alternatives);
       return altArrList;
    }

    public static void main(String[] args) {
        // Write tests for the nearMisses method
        SpellDictionary newDict = new SpellDictionary("data/words.txt");
        System.out.println(newDict.deletion("Baark"));
        System.out.println(newDict.deletion("Taarget"));

        System.out.println(newDict.insertion("Lst"));

        System.out.println(newDict.substitution("Lest"));

        System.out.println(newDict.transposition("rset"));
        
        System.out.println(newDict.spliting("CurtCurie"));
        System.out.println(newDict.spliting("ab"));
    }

    
}
