package com.bankteller.ui;

import java.util.Scanner;

public class ValidationHelper
{
    
	public static String getString(final Scanner scanner, final String prompt)
    {
        System.out.print(prompt);
        final String string = scanner.next();        // read the first string on the line
        scanner.nextLine();               // discard any other data entered on the line
        return string;
    }

    public static String getLine(final Scanner scanner, final String prompt)
    {
        System.out.print(prompt);
        final String string = scanner.nextLine();        // read the whole line
        return string;
    }

    public static int getInt(final Scanner scanner, final String prompt)
    {
        boolean isValid = true;
        int selection = 0;
        while (isValid)
        {
            System.out.print(prompt);
            if (scanner.hasNextInt())
            {
                selection = scanner.nextInt();
                isValid = false;
            }
            else
            {
                System.out.println("Error! Invalid integer value. Try again.");
            }
            scanner.nextLine();  // discard any other data entered on the line
        }
        return selection;
    }
    
    public static int getAccountNumber(final Scanner scanner, final String prompt)
    {
        boolean isValid = true;
        int selection = 0;
        while (isValid)
        {
            System.out.print(prompt);
            if (scanner.hasNextInt())
            {
                selection = scanner.nextInt();
                
                if(selection/10000000 > 0 && selection/10000000 < 10) {
                    isValid = false;
                }else {
                    System.out.println("Error! Enter an 8 digit value.");
                }
            }
            else
            {
                System.out.println("Error! Enter an 8 digit value.");
            }
            scanner.nextLine();  // discard any other data entered on the line
        }
        return selection;
    }

    
    public static String getPPSNumber(final Scanner scanner, final String prompt)
    {
        boolean isValid = true;
        int selection = 0;
        while (isValid)
        {
            System.out.print(prompt);
            if (scanner.hasNextInt())
            {
                selection = scanner.nextInt();
                
                if(selection/1000000 > 0 && selection/1000000 < 10) {
                    isValid = false;
                }else {
                    System.out.println("Error! Enter a 7 digit value.");
                }
            }
            else
            {
                System.out.println("Error! Enter a 7 digit value.");
            }
            scanner.nextLine();  // discard any other data entered on the line
        }
        return String.valueOf(selection);
    }
    
    

    
	public static double getDouble(final Scanner scanner, final String prompt) {
        boolean isInvalid = true;
        double selection = 0;
        
        while (isInvalid)
        {
            System.out.print(prompt);
            if (scanner.hasNextDouble())
            {
                selection = scanner.nextDouble();
  
                isInvalid = false;

            }
            else
            {
                System.out.println("Error! Invalid decimal value. Try again.");
            }
            scanner.nextLine();  // discard any other data entered on the line
        }
        return selection;
	}

}
