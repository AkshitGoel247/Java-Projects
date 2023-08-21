public class Account {

    String userName;
    int accountNumber;
    double accountBalance;
    int pinNumber;


    // default constructor
    public Account(){}


    public Account(String userName, int accountNumber, int pinNumber, double accountBalance){
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.pinNumber = pinNumber;
        this.accountBalance = accountBalance;
    }

    public Account(String userName, int pinNumber){
        this.userName = userName;
        this.pinNumber = pinNumber;
    }
}









//public class Account {
//
//    // username
//    private String userName;
//
//    // account number
//    private int accountNumber;
//
//    // balance in account
//    private double accountBalance;
//
//    public Account(){
//        // default constructor
//    }
//
//    // constructor
//    public Account(int accountNumber, String userName){
//        this.accountNumber = accountNumber;
//        this.accountBalance = 0.00;
//        this.userName = userName;
//    }
//
//    // method for deposit
//    public void depositMoney(double depositAmount){
//        accountBalance = accountBalance + depositAmount;
//    }
//
//    // method for withdraw
//    public void withdrawMoney(double amountWithdraw){
//        if(amountWithdraw > accountBalance){
//            System.out.println("Insufficient Balance");
//        }
//        else{
//            accountBalance = accountBalance - amountWithdraw;
//        }
//    }
//
//    // method to get account number
//    public int getAccountNumber(){
//        return accountNumber;
//    }
//
//    // method to get account's username
//    public String getUserName(){
//        return userName;
//    }
//
//    // method to get account balance
//    public double getAccountBalance(){
//        return accountBalance;
//    }
//
//
//}
