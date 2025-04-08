//Dimitrios Makris, 3212019119

import java.util.ArrayList;

public class CubbyHole {

    private ArrayList<String> grilledfish; // ArrayList pou perilambanei psaria sta karvouna
    private ArrayList<String> seafooddelicacies; // ArrayList pou perilambanei thalassinous mezedes
    private ArrayList<String> meatvarieties; // ArrayList pou perilambanei poikilies kreatikwn

    public CubbyHole() {
        //arxikopoihsh twn listwn
        grilledfish = new ArrayList<String>();
        seafooddelicacies = new ArrayList<String>();
        meatvarieties = new ArrayList<String>();
    }

    public synchronized String getGrilledFish() {
        //an h lista grilledfish einai adeia tote o client perimenei
        //mexri na eisaxthei kati se authn
        while (grilledfish.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        notifyAll();
        return grilledfish.remove(0); //epistrefetai auto pou brisketai sthn prwth thesi ths listas grilledfish
    }

    public synchronized String getSeafoodDelicacy() {
        //an h lista seafooddelicacies einai adeia tote o client perimenei
        //mexri na eisaxthei kati se authn
        while (seafooddelicacies.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        notifyAll();
        return seafooddelicacies.remove(0); //epistrefetai auto pou brisketai sthn prwth thesi ths listas seafooddelicacies
    }

    public synchronized String getMeatVariety() {
        //an h lista meatvarieties einai adeia tote o client perimenei
        //mexri na eisaxthei kati se authn
        while (meatvarieties.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        notifyAll();
        return meatvarieties.remove(0); //epistrefetai auto pou brisketai sthn prwth thesi ths listas meatvarieties
    }

    public synchronized void putGrilledFish(String dish) {
        //an h lista grilledfish einai gemath tote o client perimenei
        //mexri na adeiasei mia thesi gia na eisaxthei auto pou plhktrologhse o xrhsths (Chef 1)
        while (grilledfish.size() == 5) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        grilledfish.add(dish); //to piato pou plhktrologhse o xrhsths (Chef 1) eisagetai sthn katallhlh lista
        notifyAll();
    }

    public synchronized void putSeafoodDelicacy(String dish) {
        //an h lista seafooddelicacies einai gemath tote o client perimenei
        //mexri na adeiasei mia thesi gia na eisaxthei auto pou plhktrologhse o xrhsths (Chef 2)
        while (seafooddelicacies.size() == 5) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        seafooddelicacies.add(dish); //to piato pou plhktrologhse o xrhsths (Chef 2) eisagetai sthn katallhlh lista
        notifyAll();
    }

    public synchronized void putMeatVariety(String dish) {
        //an h lista meatvarieties einai gemath tote o client perimenei
        //mexri na adeiasei mia thesi gia na eisaxthei auto pou plhktrologhse o xrhsths (Chef 3)
        while (meatvarieties.size() == 5) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        meatvarieties.add(dish); //to piato pou plhktrologhse o xrhsths (Chef 3) eisagetai sthn katallhlh lista
        notifyAll();
    }
}
