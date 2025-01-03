import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Map<String, String> map = new Hashtable<>(); // klucevi se dijaletknite zborovi (keySet), a values na tie klucevi se literaturni zborovi

        for(int i = 0; i < n; i++)
            map.put(sc.next(), sc.next());

        Set<String> zborovi = map.keySet(); // dijalektnite zborovi gi dobivame so keySet() vrakja set od site klucevi na mapata
        StringBuilder line = new StringBuilder();
        String word;

        while(sc.hasNext()) {
            word = sc.next();
            char end = ' '; // ako ima znak posle zborot, ke go cuvame tuka
            if(word.charAt(word.length()-1) == ',' || word.charAt(word.length()-1) == '.' || word.charAt(word.length()-1) == '!' || word.charAt(word.length()-1) == '?') {
                end = word.charAt(word.length()-1);
                word = word.substring(0, word.length() - 1);
            }
            // pravime lower, bidejki u treba da stane vo, a U treba da stane Vo
            String lower = word.toLowerCase();
            if(zborovi.contains(lower)) {
                if(word.charAt(0) == word.toUpperCase().charAt(0)) {
                    word = map.get(lower); // so get() dobivame value na daden key
                    // proveruvame golema bukva
                    word = word.toUpperCase().charAt(0) + word.substring(1);
                }
                else
                    word = map.get(lower);

            }
            // ako nema znak posle zborot, dodavame samo prazno mesto (end)
            if(end == ' ') line.append(word).append(end);
            else line.append(word).append(end).append(' '); // ako ima znak, posle znakot dodavame dodatno prazno mesto
        }

        System.out.println(line);
    }
}
