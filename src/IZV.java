import com.sun.security.jgss.GSSUtil;

import java.security.KeyStore;
import java.sql.SQLOutput;
import java.util.Scanner;

public class IZV {

    private int i;
    private float[] C;
    private float[] PDK;
    private float Qsr;
    private float Qfac;
    private float K;

    public IZV(int i, float[] C, float[] PDK, float Qsr, float Qfac) {
        this.i = i;
        this.C = C;
        this.PDK = PDK;
        this.Qsr = Qsr;
        this.Qfac = Qfac;
        this.K = (float)Qfac/Qsr;
    }

    public IZV() {
    }

    public float estimationWithK(int i, float[] C, float[] PDK, float K){
        int sum = 0;
        System.out.println(i);
        float con = (float) 1%i * K;
        for (int j = 0; j < i; j++){
            sum +=(float)(C[j]/PDK[j]);
        }

        return con * sum;

    }

    public float estimation(int i, float[] C, float[] PDK){
        int sum = 0;

        float con = (float) 1%i;
        for ( int j = 0; j < i; j++){
            sum += sum + (float)(C[j]/PDK[j]);
        }

        return con * sum;

    }

    public String check(float ivzK, float ivz){
        String s = "";
        String k = "";
        if(ivzK <= 0.2){
            s= "Характеристика: I - очень чистая, Изменение величины ИЗВ в %: 100";
        } else if(ivzK > 0.2 && ivzK <= 1){
            s= "Характеристика: II - чистая, Изменение величины ИЗВ в %: >50";
        } else if(ivzK > 1 && ivzK <= 2){
            s =  "Характеристика: III - умеренно загрязненная, Изменение величины ИЗВ в %: >30";
        } else if(ivzK > 2 && ivzK <= 4){
            s= "Характеристика: IV - загрязненная, Изменение величины ИЗВ в %: >25";
        } else if(ivzK > 4 && ivzK <= 6){
            s= "Характеристика: V - грязная, Изменение величины ИЗВ в %: >20";
        } else if(ivzK > 6 && ivzK <= 10){
            s= "Характеристика: VI - грязная, Изменение величины ИЗВ в %: >15";
        }else if(ivzK > 10){
            s= "Характеристика: VII - черезвыяайно грязная, Изменение величины ИЗВ в %: >10";
        }

        if(ivz <= 0.3){
            k= "Характеристика: I - очень чистая, Изменение величины ИЗВ в %: 100";
        } else if(ivz > 0.3 && ivz <= 1){
            k= "Характеристика: II - чистая, Изменение величины ИЗВ в %: >50";
        } else if(ivz > 1 && ivz <= 2.5){
            k= "Характеристика: III - умеренно загрязненная, Изменение величины ИЗВ в %: >30";
        } else if(ivz > 2.5 && ivz <= 4){
            k= "Характеристика: IV - загрязненная, Изменение величины ИЗВ в %: >25";
        } else if(ivz > 4 && ivz <= 6){
            k= "Характеристика: V - грязная, Изменение величины ИЗВ в %: >20";
        } else if(ivz > 6 && ivz <= 10){
            k= "Характеристика: VI - грязная, Изменение величины ИЗВ в %: >15";
        }else if(ivz > 10){
            k= "Характеристика: VII - черезвыяайно грязная, Изменение величины ИЗВ в %: >10";
        }
        return s + " Проверка для значения с учетом K " +"\n" + k + " Проверка для значения без учета K";
    }


    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public float[] getC() {
        return C;
    }

    public void setC(float[] c) {
        C = c;
    }

    public float[] getPDK() {
        return PDK;
    }

    public void setPDK(float[] PDK) {
        this.PDK = PDK;
    }

    public float getQsr() {
        return Qsr;
    }

    public void setQsr(float qsr) {
        Qsr = qsr;
    }

    public float getQfac() {
        return Qfac;
    }

    public void setQfac(float qfac) {
        Qfac = qfac;
    }

    public float getK() {
        return K;
    }

    public void setK(float k) {
        K = k;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Расчет Индекса Загрязнения Воды");
        System.out.println("Введите число показателей");
        int temp = in.nextInt(); //Число показателей i
        float[] C = new float[temp];
        float[] PDK = new float[temp];
        float Qf = 0;
        float Qs = 0;

        while(true){
            for (int i = 0; i < temp; i++){
                int v = i+1;
                System.out.println("Введите " + v + " концентрацию компонента (C)");
                C[i] = in.nextFloat();

                System.out.println("Введите " + v + " установленную величину норматива для соответствующего типа водного объекта (ПДК)");
                PDK[i] = in.nextFloat();
            }
            System.out.println("Введите расход воды в водотоке за период определения ИЗВ и среднемноголетний соответственно, в м3/с через пробел");

            Qf = in.nextFloat();
            Qs = in.nextFloat();

            IZV izv = new IZV();

            System.out.println("Индекс загрязнения с учетом коэффициента стока загрязняющих веществ K");
            float x = izv.estimationWithK(temp,C,PDK,(float)(Qs/Qf));
            System.out.println(x);

            System.out.println("Индекс загрязнения без учета коэффициента стока загрязняющих веществ K");
            float c = izv.estimation(temp,C,PDK);
            System.out.println(c);
            System.out.println(izv.check(x,c));

        }
    }
}
