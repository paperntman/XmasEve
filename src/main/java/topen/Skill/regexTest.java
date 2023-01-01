package topen.Skill;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final String input = scanner.nextLine();
        Pattern pattern = Pattern.compile("^[A-Z]급\\s스킬\\s(.*?)을[(]를[)] 배울 수 있는 책입니다[.]");
        Matcher matcher = pattern.matcher(input);

        if(matcher.find()) System.out.println(matcher.group(1));
    }
}
