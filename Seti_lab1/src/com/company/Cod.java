package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Cod {
    private int r;
    private int d;
    private int l;
    private double e;
    private int n;
    private int[] g;
    private int[] m;
    private int[] c;
    private int[] a;
    private int[] e_v;
    private int[] b;
    private int count_error = 0;
    private Random rand;
    ArrayList <int[]> codebook= new ArrayList();

    public Cod(int l, int d, int[] g, double e) {
        this.l = l;
        this.d = d;
        this.g = g;
        this.e = e;
        this.r = g.length - 1;
        rand = new Random();
        m = new int[l];
        c = new int[r];
        a = new int[l + r];
        e_v = new int[l + r];
        b = new int[l + r];
    }



    public double count_iter() {
        double tmp1 = Math.pow(e, 2);
        double tmp = 4 * tmp1;
        double N = (9 / tmp);
        n = (int) N;
        return n;
    }

    public int get_n() {
        return n;
    }

    public void create_m() {
        // while (code_word_weight(m) < d) {
        for (int i = 0; i < l; i++) {
            m[i] = rand.nextInt(2);
        }
        // }
        // print_vect(m, "m");
    }

    public void create_m(int[] m) {
        this.m = m;
    }

    public void create_c() {//создание многочлена с
        int[] x = new int[r + 1];
        x[0] = 1;
        int[] tmp = multiply(m, x);//метод для перемножения многочленов
        c = mod(tmp);//метод деления многочленов
        //print_vect(c, "c");
    }

    private int[] multiply(int[] m1, int[] m2) {//перемножение многочленов
        int[] res = new int[m1.length + m2.length - 1];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2.length; j++) {
                res[i + j] += m1[i] * m2[j];
            }
        }
        return res;
    }

    private int[] mod(int[] tmp) {//деление многочленов
        int tmp_deg = tmp.length - 1;
        int i = 0;
        int zero = 0;
        int[] tmp1 = new int[1];
        tmp1[0] = 0;
        while (tmp_deg >= r) {
            int deg = tmp_deg - r;//понижение степени многочлена
            int[] x = new int[deg + 1];
            x[0] = 1;
            int[] t = multiply(x, g);//перемножение многочленов
            tmp = minus(tmp, t);//метод вычитания многочленов
            zero = count_zero(tmp);//подсчет количества нулей в векторе
            if (zero == tmp.length) {//если многочлен стал нулевым то выходим из деления
                return tmp1;
            } else {
                tmp = cut0(tmp);//убираем все нули, которые стоят в начале
                tmp_deg = tmp.length - 1;
            }
        }
        return tmp;
    }

    private int[] minus(int[] m1, int[] m2) {
        int[] res = new int[m1.length];
        for (int i = 0; i < m1.length; i++) {
            res[i] = m1[i] - m2[i];
            if (res[i] == -1) res[i] = 1;
        }
        return res;
    }

    private int count_zero(int[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) count++;
        }
        return count;
    }

    private int[] cut0(int[] array) {
        int count = 0;
        int i = 0;
        if (array[i] != 1) {
            while (array[i] != 1) {
                count++;
                i++;
            }
        }
        int[] array1 = new int[array.length - count];
        for (int j = 0; j < array1.length; j++) {
            array1[j] = array[j + count];
        }
        return array1;
    }

    public void create_a() {
        int i = 0;
        int j = 0;
        int lenght = a.length - c.length - m.length;
        while (i < m.length) {
            a[i] = m[i];
            i++;
        }
        while (lenght > 0) {//если длина вектора а больше суммы с и m заполняем нулями
            a[i] = 0;
            i++;
            lenght--;
        }
        while (j < c.length) {
            a[i] = c[j];
            i++;
            j++;
        }
        //print_vect(a, "a");
    }

    public int[] get_a() {
        return a;
    }

    public int[] get_b() {
        return b;
    }

    private void print_vect(int[] array, String str) {
        System.out.print(str + " = (");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
        }
        System.out.println(")");
    }

    public void create_e(double p) {
        for (int i = 0; i < a.length; i++) {
            if (Math.random() * 10 > p * 10) {
                e_v[i] = 0;
            } else {
                e_v[i] = 1;
            }
        }
        // print_vect(e_v, "e");
    }

    public void create_b() {
        for (int i = 0; i < b.length; i++) {
            b[i] = (a[i] + e_v[i]) % 2;
        }
        //print_vect(b, "b");
    }

    public void sindrom() {
        int[] s = mod(b);
        int E;
        //   print_vect(s, "s");
        int count = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == 1) count++;
        }
        if (count == 0) {
            //System.out.println("E = 0 => No Error");
            E = 0;
            if (count_zero(e_v) != e_v.length) {
                count_error++;
            }
        } else {
            //System.out.println("E = 1 => Error");
            E = 1;
        }
    }


    public boolean sindrom(int [] input) {
        int[] s = mod(input);
        int E;
        //   print_vect(s, "s");
        int count = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == 1) count++;
        }
        if (count == 0) {
            //System.out.println("E = 0 => No Error");
            return false;
        } else {
            //System.out.println("E = 1 => Error");
            return true;
        }
    }

    public int get_err() {
        return count_error;
    }

    public double exact_value(ArrayList<int[]> list_v, double p) {//вычисление точного значения ошибки
        int[] A = new int[n + 1];
        int count = 0;
        double sum = 0;
        for (int i = 0; i < list_v.size(); i++) {
            count = code_word_weight(list_v.get(i));
            for (int j = 0; j < n; j++) {
                if (count == j) {
                    A[j]++;
                    break;
                }
            }
        }
        for (int i = d; i <= n; i++) {
            sum += (double) (A[i] * Math.pow(p, i) * Math.pow((1 - p), (n - i)));
        }
        return sum;
    }

    private int code_word_weight(int[] array) {//подсчет веса вектора
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) count++;
        }
        return count;
    }



}