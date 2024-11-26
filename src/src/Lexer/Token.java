package Lexer;

public class Token {
    String name;
    DataType dataType;
    String value;

    public Token(String name, DataType dataType, String value){
        this.name=name;
        this.dataType=dataType;
        this.value=value;
    }
}
