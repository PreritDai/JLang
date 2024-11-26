package Lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;


public class LexicalAnalyser {
    static States state=States.NONE;
    static States tokenType=States.NONE;
    static DataType dataType=null;
    static String rawString="";
    static HashMap<String, String> tokenMapper=new HashMap<>();


    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner=new Scanner(new File("src/src/Test/1.j"));
        String line="";

        tokenMapper.put("=", "EQUALS");
        tokenMapper.put("(", "SMBRKTL");
        tokenMapper.put(")", "SMBRKTR");
        tokenMapper.put("{", "CBRKTL");
        tokenMapper.put("}", "WORKTREE");
        tokenMapper.put("[", "BGBRKTL");
        tokenMapper.put("]", "BACKTRACK");


        while(scanner.hasNextLine()){
            line=scanner.nextLine();
            dfall(line);
        }

    }

    public static void dfall(String line){

        Token rawToken=new Token("", DataType.NONE, "");

        for (char x : line.toCharArray()){
            if (state==States.QUOTATION) {
                if (x == '"') {
                    rawToken = new Token("", DataType.STRING, rawString);
                    pushToken(rawToken);
                    rawString = "";
                    state = States.NONE;
                }
            }
            else{
                    if (String.valueOf(x).equals(String.valueOf('"'))){
                        state=States.QUOTATION;
                    }

                    else{
                        if (tokenMapper.containsKey(String.valueOf(x))){
                            pushToken(new Token(rawString, null, ""));
                            pushToken(new Token(String.valueOf(x), null, ""));
                        }
                        else if (x=='$'){
                            //Identifier has ended
                            pushToken(new Token(rawString, null, ""));
                            pushToken(new Token("$", null, ""));


                        }else if (x=='&'){
                            //Value of Variable has ended
                            pushToken(new Token(rawString, dataType, ""));
                            pushToken(new Token("&", null, ""));
                            dataType=null;
                        }

                        else if (String.valueOf(x).replaceAll("\\s", "").isEmpty()){
                            switch (rawString){
                                case "int":
                                    dataType=DataType.INT;
                                    pushToken(new Token("int", dataType, ""));
                                    break;
                                case "String":
                                    dataType=DataType.STRING;
                                    pushToken(new Token("String", dataType, ""));
                                    break;
                                case "boolean":
                                    dataType=DataType.BOOLEAN;
                                    pushToken(new Token("boolean", dataType, ""));
                                    break;
                                case "char":
                                    dataType=DataType.CHAR;
                                    pushToken(new Token("char", dataType, ""));
                                    break;
                            }
                        }
                        else{
                            rawString+=x;
                        }
                    }
                }
            }
        }

    private static void pushToken(Token token) {
        if (!(token.name.isEmpty() && token.value.isEmpty())){
            System.out.println(token.name+" "+token.dataType+" "+token.value);

        }
        rawString="";
    }
}






