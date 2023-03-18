package uqam.npc;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //use a cache if big data?

        if(args.length>0){
            System.out.println(args[0]);
        }
        var test = Util.randomArray(10);
        Arrays.stream(test).forEach(e-> System.out.print(e+" "));

        System.out.println("\n");

        Arrays.stream(Util.quickSort(test)).forEach(e-> System.out.print(e+" "));
    }
}