public class Account {

    String userName;
    int accountNumber;
    double accountBalance;
    int pinNumber;


    // default constructor
    public Account(){}

    // constructor which takes parameters namely username, accountnumber, pin number, accountbalance
    public Account(String userName, int accountNumber, int pinNumber, double accountBalance){
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.pinNumber = pinNumber;
        this.accountBalance = accountBalance;
    }

    // constructor which takes only 2 parameters named username and pinnumber
    public Account(String userName, int pinNumber){
        this.userName = userName;
        this.pinNumber = pinNumber;
    }
}





