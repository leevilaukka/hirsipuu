import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hirsipuu {
    /**
     * Lista yksinpelin sanoista.
     */
    private static String[] sanat = {"laurea", "ammattikorkeakoulu", "tradenomi", "opiskelija", "java", "ohjelmointi", "tikkurila", "kampus"};

    // Main-metodi
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Boolean started = false;

        System.out.println("Tervetuloa Leevin Hirsipuuhun. Pelaatko yksin (1) vai kaverin kanssa (2)?");
   
        while(!started) {
            String gameMode = scan.nextLine();
            switch(gameMode) {
                case "1":
                    // Haetaan satunnainen sana listasta ja käynnistetään peli tällä sanalla
                    startGame(getRandomWord().toLowerCase());
                    started = true;
                    break;
                case "2": 
                    // Kysytään toiselta pelaajalta sanaa ja käynnistetään peli annetulla sanalla
                    System.out.print("Anna arvattava sana: ");
                    String word = scan.nextLine().toLowerCase();

                    // Yritetään piilottaa toisen pelaajan antama sana :D
                    for(int i = 0; i <= 50; i++) {
                        System.out.println();
                    }

                    // Käynnistetään peli annetulla sanalla
                    startGame(word);
                    started = true;
                    break;
                default:
                    // Väärän vaihtoehdon kohdalla kysytään uutta vaihtoehtoa
                    System.out.println("Anna kelpaava vaihtoehto! (1 tai 2)");
                    break;
            }
        }
        
        scan.close();
    }

    /**
     * Haetaan satunnainen sana listasta
     * @return Satunnainen sana sanat-listasta
     */
    static String getRandomWord() {
        int rnd = new Random().nextInt(sanat.length);
        return sanat[rnd];
    }

    /**
     * Käynnistetään varsinainen peli.
     * @param word Sana, jolla pelataan
     */
    static void startGame(String word) {
        ArrayList<String> wrongAns = new ArrayList<String>();
        Scanner scan = new Scanner(System.in);
        Boolean running = true;
        int count = 7;
        String censored = new String(new char[word.length()]).replace("\0", "*");

        while(running) {
            System.out.println(censored);
            System.out.print("Anna arvaus: ");
            String guess = scan.next().toLowerCase();

            // Sana arvattu kerralla oikein
            if(guess.equals(word)) {
                running = false;
                endGame("win", word, count);
                scan.close();
            }

            // Arvaus väärin
            if(guess.length() > 1 && !guess.equals(word) || !word.contains(guess)) {
                count--;
                // Samaa kirjainta / sanaa ehdotettu jo.
                if(wrongAns.contains(guess)) {
                    System.out.println("Ehdotit tätä jo! Arvauksia jäljellä: " + count);
                } else {
                    wrongAns.add(guess);
                    System.out.println("Arvasit väärin! Arvauksia jäljellä " + count);
                }

                System.out.println("Väärät vastaukset: " + wrongAns.toString());

            } else {
                // Arvaus oikein
                String nextCensored = "";

                // Tarkistetaan kaikki kirjaimet, jos arvaus oli tietty kirjain, paljastetaan kirjain piilotetusta sanasta, muuten piilotetaan kirjain.
                for(int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == guess.charAt(0)) {
                        nextCensored += guess.charAt(0);
                    } else if (censored.charAt(i) != '*') {
                        nextCensored += word.charAt(i);
                    } else {
                        nextCensored += "*";
                    }
                }
                
                censored = nextCensored;

                // Kirjain kirjaimelta arvattu sana oikein
                if(censored.equals(word)) {
                    running = false;
                    endGame("win", word, count);
                    scan.close();
                }
            }

            // Yritykset loppu, päätetään peli häviöön.
            if(count <= 0) {
                running = false;
                endGame("lose", word, count);
                scan.close();
            }
        }
    }

    /**
     * Lopetetaan peli ja tulostetaan lopputuloksen mukainen vastaus
     * @param x - win / lose
     * @param word  - pelattu sana
     * @param count - yritysten määrä
     */
    static void endGame(String x, String word, int count) {
        switch(x) {
            // Voitto
            case "win":
                System.out.println("Voitit! Sana oli \"" + word + "\" Sinulle jäi " + count + " yritystä jäljelle" );
                break;

            // Häviö
            case "lose": 
                System.out.println("Hävisit! Sana olisi ollut \"" + word + "\"");
                break;
            default: 
                throw new IllegalArgumentException("Muuttujan x täytyy olla 'win' tai 'lose'. Annettiin \"" + x + "\"");
        }
    }
}