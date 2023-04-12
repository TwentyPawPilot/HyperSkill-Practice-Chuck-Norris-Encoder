package chucknorris;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean runProgram = true;
        while(runProgram) {
            System.out.println("Please input operation (encode/decode/exit):");
            String choice = input.nextLine();
            if(choice.equals("encode")){
                System.out.println("Input string:");
                String stringToEncode = input.nextLine();
                String encoded = encodeFullString(stringToEncode);
                System.out.println("Encoded string\n" + encoded);
                continue;
            }else if(choice.equals("decode")){
                System.out.println("Input encoded string:");
                String stringToDecode = input.nextLine();
                boolean onlyZeros = Pattern.matches("[*^0 ]+", stringToDecode);
                boolean validFormat = validZeroes(stringToDecode);
                if (onlyZeros && validFormat) {
                    String turnedToBinary = chuckDecode(stringToDecode);
                    //TODO Verify multiple of 7
                    if (turnedToBinary.length() % 7 == 0) {
                        String decoded = decodeSevenDigitBinary(turnedToBinary);
                        System.out.println("Decoded string:\n" + decoded);
                    } else {
                        System.out.println("Encode string is not valid.");
                    }
                } else {
                    System.out.println("Encode string is not valid.");
                }
                continue;
            }else if(choice.equals("exit")) {
                runProgram = false;
                System.out.println("Bye!");
                break;
            }else{
                System.out.printf("There is no '%s' operation\n", choice);
            }
        }
    }


    public static String toSevenDigitBinary(char c){
        String binaryRep = Integer.toBinaryString(c);
        binaryRep = String.format("%7s", binaryRep).replace(" ", "0");
        return binaryRep;
    }

    public static String chuckEncode(String binaryChar){
        String codedString = "";
        char currentChar = ' ';
        for(int i = 0; i < binaryChar.length(); i++){
            if(currentChar == binaryChar.charAt(i)){
                codedString += "0";
            }else if(binaryChar.charAt(i) == '0'){
                codedString += " 00 0";
                currentChar = binaryChar.charAt(i);
            }else if(binaryChar.charAt(i) == '1'){
                codedString += " 0 0";
                currentChar = binaryChar.charAt(i);
            }
        }
        return codedString.strip();
    }

    public static String encodeFullString(String stringToEncode){
        String encodedString = "";
        for(int i = 0; i < stringToEncode.length(); i++){
            encodedString += toSevenDigitBinary(stringToEncode.charAt(i));
        }
        encodedString = chuckEncode(encodedString);
        //System.out.println("Encoded string:");
        return encodedString;
    }

    public static String chuckDecode(String zeroString){
        String[] zeroArray = zeroString.split(" ");
        String binaryReturn = "";
        String currentValue = "";
        for(int i = 0; i < zeroArray.length; i++){
            //Even indexes set the number, odd indicate repetition
            if(i % 2 == 0){
                currentValue = zeroArray[i].equals("00") ? "0" : "1";
            }else{
                binaryReturn += currentValue.repeat(zeroArray[i].length());
            }
        }
        return binaryReturn;
    }

    public static String decodeSevenDigitBinary(String binaryString){
        String returnString = "";
        String tempString = "";
        for(int i = 0; i < binaryString.length(); i++){
            if(tempString.length() == 7){
                returnString += (char)Integer.parseInt(tempString, 2);
                tempString = "";
            }
            tempString += binaryString.charAt(i);
        }
        returnString += (char)Integer.parseInt(tempString, 2);
        return returnString;
    }

    public static boolean validZeroes(String zeroString){
        String[] zeroArray = zeroString.split(" ");
        if(zeroArray.length % 2 != 0){
            return false;
        }
        for(int i = 0; i < zeroArray.length; i = i + 2){
            if(!zeroArray[i].equals("0") && !zeroArray[i].equals("00")){
                return false;
            }
        }
        return true;
    }
}