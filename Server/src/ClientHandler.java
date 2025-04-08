import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread {

    private final Socket sock;
    private final CubbyHole ch;

    public ClientHandler(Socket sock, CubbyHole ch) {
        this.sock = sock;
        this.ch = ch;
    }

    @Override
    public void run() {

        try (
                ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream()); //stream gia apostolh mhnumatwn ston client
                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream()); //stream gia anagnwsh mhnumatwn apo ton client
                ) {

            Scanner in = new Scanner(System.in);
            
            while (true) {

                try (sock) {
                    Object strin = instream.readObject(); //anagnwsh mhnumatos apo ton client

                    //an o client eipe ston server "BEGIN"
                    if (strin.equals("BEGIN")) {
                        outstream.writeObject("LISTENING"); //tote o server apantaei me "LISTENING" kai jekinaei h epikoinwnia metaju tous
                        outstream.flush();

                        do {
                            strin = instream.readObject(); //anagnwsh mhnumatos apo ton client (To mhnuma auto tha prosdiorizei ton rolo tou xrhsth pou xrhsimopoiei thn efarmogh)

                            //an o client esteile ston server "Customer"
                            if (strin.equals("Customer")) {
                                // anagnwsh mhnumatos apo ton client (To mhnuma auto prosdiorizei to eidos tou faghtou pou tha
                                //epistrepsei o ClientHandler ston client sumfwna me to ti epeleje o pelaths)
                                strin = instream.readObject();
                                
                                //an o pelaths epeleje oti thelei na paraggeilei psari sta karvouna
                                if (strin.equals("Grilled fish")) {
                                    //epistrefetai ston client to prwto faghto pou uparxei apothikeumeno
                                    //sthn lista me ta psaria sta karvouna. An h lista einai adeia
                                    //tote o client perimenei mexri na prostethei sth lista ena toulaxiston psari
                                    outstream.writeObject(ch.getGrilledFish());
                                } 
                                //an o pelaths epeleje oti thelei na paraggeilei thalassino meze
                                else if(strin.equals("Sea food appetizer")){
                                    //epistrefetai ston client to prwto faghto pou uparxei apothikeumeno
                                    //sthn lista me tous thalassinous mezedes. An h lista einai adeia
                                    //tote o client perimenei mexri na prostethei sth lista enas toulaxiston thalassinos mezes
                                    outstream.writeObject(ch.getSeafoodDelicacy());
                                } 
                                //an o pelaths epeleje oti thelei na paraggeilei poikilia kreatwn
                                else if(strin.equals("Meat variety")){
                                    //epistrefetai ston client to prwto faghto pou uparxei apothikeumeno
                                    //sthn lista me tis poikiliews kreatwn. An h lista einai adeia
                                    //tote o client perimenei mexri na prostethei sth lista mia toulaxiston poikilia kreatwn
                                    outstream.writeObject(ch.getMeatVariety());
                                } 
                                //an o pelaths epeleje oti thelei na tou emfanistoun ta periexomena ths paraggelias tou
                                //tote o ClientHandler den kanei tipota. O logos pou uparxei auth h else if einai giati
                                //o ClientHandler perimenei na labei kapoio mhnuma apo ton client afou o xrhsths exei sundethei
                                //me ton rolo tou pelath
                                else if(strin.equals("Displaying customer's order")){}
                            }
                            //an o client esteile ston server "Chef 1"
                            else if (strin.equals("Chef 1")) {
                                ch.putGrilledFish((String) instream.readObject()); //to piato pou tha plhktrologhsei o xrhsths (Chef 1) ston client tha eisaxthei sthn katallhlh lista
                                outstream.writeObject("Dish added successfully!"); //stelnetai ston client to mhnuma "Dish added successfully!" gia na emfanistei sthn othoni tou xrhsth (Chef 1)
                            } 
                            //an o client esteile ston server "Chef 2"
                            else if (strin.equals("Chef 2")) {
                                ch.putSeafoodDelicacy((String) instream.readObject()); //to piato pou tha plhktrologhsei o xrhsths (Chef 2) ston client tha eisaxthei sthn katallhlh lista
                                outstream.writeObject("Dish added successfully!"); //stelnetai ston client to mhnuma "Dish added successfully!" gia na emfanistei sthn othoni tou xrhsth (Chef 2)
                            } 
                            //an o client esteile ston server "Chef 3"
                            else if (strin.equals("Chef 3")) {
                                ch.putMeatVariety((String) instream.readObject()); //to piato pou tha plhktrologhsei o xrhsths (Chef 3) ston client tha eisaxthei sthn katallhlh lista
                                outstream.writeObject("Dish added successfully!"); //stelnetai ston client to mhnuma "Dish added successfully!" gia na emfanistei sthn othoni tou xrhsth (Chef 3)
                            }
                        } while (true);
                    } else {
                        outstream.writeObject("Not welcomed...");
                        outstream.flush();
                    }
                } catch (IOException ex) {
                    System.out.println("Connection Closing...\n");
                    break;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            System.out.println("Connection Closing...");
        }
    }
}
