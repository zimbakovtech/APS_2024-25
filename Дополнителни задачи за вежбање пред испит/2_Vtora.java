import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Map<String, String> map = new Hashtable<>();

        for(int i = 0; i < n; i++)
            map.put(sc.next(), sc.next());

        Set<String> zborovi = map.keySet();
        StringBuilder line = new StringBuilder();
        String word;

        while(sc.hasNext()) {
            word = sc.next();
            char end = ' ';
            if(word.charAt(word.length()-1) == ',' || word.charAt(word.length()-1) == '.' || word.charAt(word.length()-1) == '!' || word.charAt(word.length()-1) == '?') {
                end = word.charAt(word.length()-1);
                word = word.substring(0, word.length() - 1);
            }
            String lower = word.toLowerCase();
            if(zborovi.contains(lower)) {
                if(word.charAt(0) == word.toUpperCase().charAt(0)) {
                    word = map.get(lower);
                    word = word.toUpperCase().charAt(0) + word.substring(1);
                }
                else
                    word = map.get(lower);

            }
            if(end == ' ') line.append(word).append(end);
            else line.append(word).append(end).append(' ');
        }

        System.out.println(line);
    }
}