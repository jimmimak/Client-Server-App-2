import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try (Socket sock = new Socket("localhost", 5555)) {

            Scanner in = new Scanner(System.in);

            ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream()); //stream gia apostolh mhnumatwn ston ClientHandler
            ObjectInputStream instream = new ObjectInputStream(sock.getInputStream()); //stream gia anagnwsh mhnumatwn apo ton ClientHandler

            outstream.writeObject("BEGIN"); //o client stelnei to mhnuma "BEGIN" ston ClientHandler gia na jekinhsei thn epikoinwnia me ton server

            //an o ClientHandler apanthsei me "LISTENING" exei jekinhsei h epikoinwnia metaju tou server kai tou client
            if (instream.readObject().equals("LISTENING")) {

                String user; //metablhth sthn opoia apothikeuetai to eidos tou xrhsth pou sundethike sthn efarmogh

                //elegxos an o xrhsths eishgage kapoion egkuro rolo sthn efarmogh
                do {
                    System.out.print("Insert your role: ");
                    user = in.nextLine();
                } while (!user.equalsIgnoreCase("Customer") && !user.equalsIgnoreCase("Chef 1") && !user.equalsIgnoreCase("Chef 2") && !user.equalsIgnoreCase("Chef 3"));

                while (true) {
                    ArrayList<String> order = new ArrayList<String>(); //ArrayList sto opoio apothikeuontai ta faghta pou paraggelnei o pelaths

                    //an o xrhsths dhlwnei oti mpainei sthn efarmogh ws pelaths
                    if (user.equalsIgnoreCase("Customer")) {
                        int x = 0;

                        //epanelabe oso o xrhsths den exei pathsei thn epilogh "Submit your order and exit"
                        while (x != 5) {
                            //o client stelnei ston ClientHandler to mhnuma "Customer", etsi wste apo th meria tou ClientHandler,
                            //autos na mpei sthn katallhlh if kai na ektelestei to katallhlo senario gia ton pelath
                            outstream.writeObject("Customer");

                            x = menu();

                            //an o xrhsths pathsei thn epilogh 1
                            if (x == 1) {
                                //stelnetai ston ClientHandler to mhnuma "Grilled fish" gia na epistrafei apo ton ClientHandler
                                //to prwto psari sta karvouna pou brisketai sthn antistoixh lista
                                outstream.writeObject("Grilled fish");
                                order.add((String) instream.readObject()); //to piato pou epistrefetai apo ton ClientHandler mpainei sthn lista me ta piata pou perilambanei h paraggelia tou xrhsth
                            } //an o xrhsths pathsei thn epilogh 2
                            else if (x == 2) {
                                //stelnetai ston ClientHandler to mhnuma "Sea food appetizer" gia na epistrafei apo ton ClientHandler
                                //o prwtos thalassinos mezes pou brisketai sthn antistoixh lista
                                outstream.writeObject("Sea food appetizer");
                                order.add((String) instream.readObject()); //to piato pou epistrefetai apo ton ClientHandler mpainei sthn lista me ta piata pou perilambanei h paraggelia tou xrhsth
                            } //an o xrhsths pathsei thn epilogh 3
                            else if (x == 3) {
                                //stelnetai ston ClientHandler to mhnuma "Meat variety" gia na epistrafei apo ton ClientHandler
                                //h prwth poikilia kreatwn pou brisketai sthn antistoixh lista
                                outstream.writeObject("Meat variety");
                                order.add((String) instream.readObject()); //to piato pou epistrefetai apo ton ClientHandler mpainei sthn lista me ta piata pou perilambanei h paraggelia tou xrhsth
                            } //an o xrhsths pathsei thn epilogh 4
                            else if (x == 4) {
                                //stelnetai ston ClientHandler to mhnuma "Displaying customer's order"
                                //etsi wste apo th meria tou ClientHandler, autos na mpei sthn katallhlh if
                                outstream.writeObject("Displaying customer's order");

                                //an o pelaths den exei paraggeilei akoma kati
                                if (order.isEmpty()) {
                                    System.out.println();
                                    System.out.println("Your order doesn't contain any dishes");
                                    System.out.println("\n");
                                } 
                                //an h paraggelia tou pelath periexei toulaxiston ena piato
                                else {
                                    System.out.println();
                                    System.out.print("Your order contains the following dishes: ");
                                    //emfanish olwn twn piatwn pou periexontai sthn paraggelia tou xrhsth
                                    for (int i = 0; i < order.size(); i++) {
                                        System.out.print(order.get(i) + " | ");
                                    }
                                    System.out.println("\n");
                                }
                            }
                        }

                        //an o pelaths den exei paraggeilei akoma kati
                        if (order.isEmpty()) {
                            System.out.println();
                            System.out.println("You didn't order any dishes");
                            System.out.println("\n");
                            break;
                        } 
                        //an h paraggelia tou pelath periexei toulaxiston ena piato
                        else {
                            System.out.println();
                            System.out.print("Your order contains the following dishes: ");
                            //emfanish olwn twn piatwn pou periexontai sthn paraggelia tou xrhsth
                            for (int i = 0; i < order.size(); i++) {
                                System.out.print(order.get(i) + " | ");
                            }
                            System.out.println("\n");
                            break;
                        }
                    }

                    //an o xrhsths dhlwnei oti mpainei sthn efarmogh ws o prwtos chef, o opoios ftiaxnei psaria sta karvouna
                    if (user.equalsIgnoreCase("Chef 1")) {

                        String choice; //metablhth sthn opoia apothikeuetai h epilogh tou xrhsth (Yes h No)

                        while (true) {
                            //o client stelnei ston server to mhnuma "Chef 1", etsi wste apo th meria tou ClientHandler,
                            //autos na mpei sthn katallhlh if kai na ektelestei to katallhlo senario gia ton prwto chef
                            outstream.writeObject("Chef 1");

                            System.out.print("Insert dish: "); //emfanish mhnumatos pou protrepei ton chef na plhktrologhsei to onoma tou piatou pou thelei na eisagei
                            String dish = in.nextLine();
                            outstream.writeObject(dish); //to piato pou plhktrologhse o chef stelnetai ston server gia na eisaxthei sth lista
                            System.out.println(instream.readObject()); //emfanish tou mhnumatos "Dish added successfully!"

                            //erwthsh tou xrhsth an thelei na eisagei ki allo piato h na kleisei thn efarmogh
                            //kai epanemfanish tou katallhlou mhnumatos mexri o xrhsths na plhktrologhsei "Yes" h "No"
                            do {
                                System.out.print("Do you want to insert another dish? (Type Yes/No): ");
                                choice = in.nextLine();
                            } while (!choice.equalsIgnoreCase("Yes") && !choice.equalsIgnoreCase("No"));

                            //an o xrhsths plhktroghsei "Yes" tote h parapanw diadikasia janaginetai ap thn arxh
                            if (choice.equalsIgnoreCase("Yes")) {
                                continue;
                            } //an o xrhsths pathsei "No" tote h efarmogh kleinei
                            else if (choice.equalsIgnoreCase("No")) {
                                break;
                            }
                        }
                        break;
                    }

                    //an o xrhsths dhlwnei oti mpainei sthn efarmogh ws o deuteros chef pou ftiaxnei thalassinous mezedes
                    if (user.equalsIgnoreCase("Chef 2")) {

                        String choice; //metablhth sthn opoia apothikeuetai h epilogh tou xrhsth (Yes h No)

                        while (true) {
                            //o client stelnei ston server to mhnuma "Chef 2", etsi wste apo th meria tou ClientHandler,
                            //autos na mpei sthn katallhlh if kai na ektelestei to katallhlo senario gia ton deutero chef
                            outstream.writeObject("Chef 2");

                            System.out.print("Insert dish: "); //emfanish mhnumatos pou protrepei ton chef na plhktrologhsei to onoma tou piatou pou thelei na eisagei
                            String dish = in.nextLine();
                            outstream.writeObject(dish); //to piato pou plhktrologhse o chef stelnetai ston server gia na eisaxthei sth lista
                            System.out.println(instream.readObject()); //emfanish tou mhnumatos "Dish added successfully!"

                            //erwthsh tou xrhsth an thelei na eisagei ki allo piato h na kleisei thn efarmogh
                            //kai epanemfanish tou katallhlou mhnumatos mexri o xrhsths na plhktrologhsei "Yes" h "No"
                            do {
                                System.out.print("Do you want to insert another dish? (Type Yes/No): ");
                                choice = in.nextLine();
                            } while (!choice.equalsIgnoreCase("Yes") && !choice.equalsIgnoreCase("No"));

                            //an o xrhsths plhktroghsei "Yes" tote h parapanw diadikasia janaginetai ap thn arxh
                            if (choice.equalsIgnoreCase("Yes")) {
                                continue;
                            } //an o xrhsths pathsei "No" tote h efarmogh kleinei
                            else if (choice.equalsIgnoreCase("No")) {
                                break;
                            }
                        }
                        break;
                    }

                    //an o xrhsths dhlwnei oti mpainei sthn efarmogh ws o tritos chef pou ftiaxnei poikilies kreatikwn
                    if (user.equalsIgnoreCase("Chef 3")) {

                        String choice; //metablhth sthn opoia apothikeuetai h epilogh tou xrhsth (Yes h No)

                        while (true) {
                            //o client stelnei ston server to mhnuma "Chef 3", etsi wste apo th meria tou ClientHandler,
                            //autos na mpei sthn katallhlh if kai na ektelestei to katallhlo senario gia ton trito chef
                            outstream.writeObject("Chef 3");

                            System.out.print("Insert dish: "); //emfanish mhnumatos pou protrepei ton chef na plhktrologhsei to onoma tou piatou pou thelei na eisagei
                            String dish = in.nextLine();
                            outstream.writeObject(dish); //to piato pou plhktrologhse o chef stelnetai ston server gia na eisaxthei sth lista
                            System.out.println(instream.readObject()); //emfanish tou mhnumatos "Dish added successfully!"

                            //erwthsh tou xrhsth an thelei na eisagei ki allo piato h na kleisei thn efarmogh
                            //kai epanemfanish tou katallhlou mhnumatos mexri o xrhsths na plhktrologhsei "Yes" h "No"
                            do {
                                System.out.print("Do you want to insert another dish? (Type Yes/No): ");
                                choice = in.nextLine();
                            } while (!choice.equalsIgnoreCase("Yes") && !choice.equalsIgnoreCase("No"));

                            //an o xrhsths plhktroghsei "Yes" tote h parapanw diadikasia janaginetai ap thn arxh
                            if (choice.equalsIgnoreCase("Yes")) {
                                continue;
                            } //an o xrhsths pathsei "No" tote h efarmogh kleinei
                            else if (choice.equalsIgnoreCase("No")) {
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            //kleinoume tis roes pou xrhsimopoihsame gia thn epikoinwnia
            //metaju tou server kai tou client
            instream.close();
            outstream.close();
            System.out.println("\nConnection Closing...");

        } catch (IOException ex) {
            System.out.println("Connection Failed!");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found!");
        }
    }

    //sunarthsh emfanishs menu
    static void displayMenu() {
        System.out.println("\nWelcome! What would you like to order?");
        System.out.println("1. Grilled fish");
        System.out.println("2. Sea food appetizer");
        System.out.println("3. Meat variety");
        System.out.println("4. Review your order");
        System.out.println("5. Submit your order and exit");
        System.out.print("Choose an option between 1-5: ");
    }

    //sunarthsh epiloghs menu kai elegxou timhs
    static int menu() {
        int x;

        Scanner input = new Scanner(System.in);

        displayMenu();
        x = input.nextInt();
        while (x < 1 || x > 5) {
            System.out.println("\nInvalid option\n");
            displayMenu();
            x = input.nextInt();
        }
        return x;
    }
}
