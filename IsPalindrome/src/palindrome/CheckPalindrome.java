package palindrome;

import java.util.Scanner;

public class CheckPalindrome {
   public static void IsPalindromeCheck(){

        String original, reverse = ""; // Objects of String class
        Scanner sc = new Scanner(System.in);
        System.out.println("Въведи число и текст за да провериш дали е палиндром");
        original = sc.nextLine();
        int length = original.length();
        for ( int i = length - 1; i >= 0; i-- )
            reverse = reverse + original.charAt(i);

        if (original.equals(reverse))
            System.out.println("Текста или числото е палиндром.");
        else
            System.out.println("Въведения текст/число не е палиндром.");
    }

}
