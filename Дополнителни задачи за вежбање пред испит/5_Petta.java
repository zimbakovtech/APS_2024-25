import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Set<String> zborovi = new HashSet<>();
        boolean correct = true;

        // gi dodavame zborovite od recnikot vo set
        for(int i = 0; i < n; i++)
            zborovi.add(sc.next());

        while(sc.hasNext()) {
            String word = sc.next();
            // ako zborot sodrzi znak na kraj, go briseme
            if(word.charAt(word.length()-1) == ',' || word.charAt(word.length()-1) == '.' || word.charAt(word.length()-1) == '!' || word.charAt(word.length()-1) == '?')
                word = word.substring(0, word.length() - 1);

            String lower = word.toLowerCase();
            // zborot go pravime lower, ako ne se sodrzi vo recnikot i ako ne e prazen string, correct go pravime false i go pecatime nepravilniot zbor
            if(!zborovi.contains(lower) && !word.isEmpty()) {
                correct = false;
                System.out.println(word);
            }
        }

        if(correct)
            System.out.println("Bravo");
    }
}