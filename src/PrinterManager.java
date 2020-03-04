public class PrinterManager {
    Printer[] printers;
    PrinterManager(int number_of_printers){
        printers = new Printer[number_of_printers];
        for(int i = 0; i < number_of_printers; i++){
            printers[i] = new Printer((i+1));
        }
    }
}