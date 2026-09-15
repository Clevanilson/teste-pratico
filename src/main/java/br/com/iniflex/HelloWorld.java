package br.com.iniflex;

public class HelloWorld {

    public String mensagem() {
        return "Hello World";
    }

    public static void main(String[] args) {
        System.out.println(new HelloWorld().mensagem());
    }
}
