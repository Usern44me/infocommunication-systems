package com.company;

public class Main {


    public static void main(String[] args) {
        int[] gx = {1, 0, 1, 1};
        double p = 0.1;
        int [] m = {1, 0, 0 , 1};
        Cod cod = new Cod(5, 3, gx, 0.01);
            for (int i = 0; i < cod.count_iter(); i++) {
                cod.create_m(); //m
                cod.create_c();
                cod.create_a();
                cod.create_e(p);
                cod.create_b();
                cod.sindrom();
            }
            System.out.println("P  = " + p);
            double pe = (double) cod.get_err() / cod.get_n();
            System.out.println("Pe  = " + pe);
            System.out.println(cod.get_err() + " ERROR");
    }

}
