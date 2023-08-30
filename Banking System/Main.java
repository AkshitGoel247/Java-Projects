import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        This is reference to database
//        Create your own database and change the following values with yours        
        String url = "jdbc:mysql://127.0.0.1:3306/Java_BankingSystem";
        String username = "root";
        String password = "";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed.");
        }

//         Initializing scanner class
        Scanner scan = new Scanner(System.in);

//         creating object for main class
        Main mainClass = new Main();

        boolean cont = true;

        while(cont) {
            System.out.println("1. CREATE A NEW ACCOUNT");
            System.out.println("2. LOGIN TO ACCOUNT");
            System.out.println("3. EXIT");
            System.out.println("Enter your choice number: ");
            int userChoice = scan.nextInt();

            if(userChoice == 1){
//                start registeration method
                mainClass.Registeration();
            }
            else if(userChoice == 2){
//                start login method
                mainClass.Login();
            }
            else{
                System.out.println("Thank you");
                break;
            }
        }
    }



//    HashMap<Integer, Account> AccountsData = new HashMap<Integer, Account>();




    public void Registeration() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter your user name");
        String userName = scan.nextLine();

        System.out.println("Enter your desired 8 digit account number");
        int accountNumber = scan.nextInt();

        if (accountNumber >= 10000000 && accountNumber <= 99999999) {
            System.out.println("Set a 4 digit pin code for your account");
            int accountPin = scan.nextInt();

            if (Integer.toString(accountPin).length() == 4) {
                System.out.println("Re-enter your pin");
                int confirmPin = scan.nextInt();

                if (confirmPin != accountPin) {
                    System.out.println("Pin does not match. Try again please");
                } else {
                    double accountBalance = 0.0;

                    InsertUserIntoDatabase(accountNumber, userName, confirmPin, accountBalance);

                    System.out.println();
                    System.out.println("Do you want to make another account. Type yes or no");
                    String ans = scan.next();

                    if(ans.equals("yes")){
                        System.out.println();
                        Registeration();
                    }
                }
            } else {
                System.out.println("Enter a valid account pin");
            }
        } else {
            System.out.println("Enter a valid account number");
        }
    }



    public void Login() {

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter your account number");
        int accountNumber = scan.nextInt();

        Account account = getAccountByAccountNumber(accountNumber);

        if (account != null) {
            System.out.println("Enter the pin");
            int accountPin = scan.nextInt();

            if (account.pinNumber == accountPin) {
                System.out.println("Logged in successfully!");
                ModifyAccount(accountNumber);
            } else {
                System.out.println("Invalid PIN.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    boolean accountExists = true;
    public void ModifyAccount(int accountNumber){

            Scanner scan = new Scanner(System.in);


            Account account = getAccountByAccountNumber(accountNumber);

            System.out.println("********** Account Info **********");
            System.out.println("Account Number: " + account.accountNumber);
            System.out.println("Account Holder Name: " + account.userName);
            System.out.println("Account Balance: " + account.accountBalance);
            System.out.println();



            System.out.println("1. DEPOSIT");
            System.out.println("2. WITHDRAW");
            System.out.println("3. EXIT");
            System.out.println("Type your choice");
            int userChoiceAccountModify = scan.nextInt();


        while(accountExists) {
            if (userChoiceAccountModify == 1) {

                System.out.println("Current Balance: " + account.accountBalance);
                System.out.println("Enter deposit amount");
                int depositAmount = scan.nextInt();

                account.accountBalance = account.accountBalance + depositAmount;

                UpdateAccountBalanceInDatabase(accountNumber, account.accountBalance);

                System.out.println("Account Balance updated");
                DisplayAccountInfo(account.accountNumber);
                ModifyAccount(account.accountNumber);

            } else if (userChoiceAccountModify == 2) {

                System.out.println("Current Balance: " + account.accountBalance);
                System.out.println("Enter amount to withdraw");
                int withdrawlAmount = scan.nextInt();

                if (withdrawlAmount > account.accountBalance) {
                    System.out.println("Insufficient Balance in Account");
                } else {
                    account.accountBalance = account.accountBalance - withdrawlAmount;
                    UpdateAccountBalanceInDatabase(accountNumber, account.accountBalance);
                    System.out.println("Account Balance updated");
                    ModifyAccount(account.accountNumber);
                }
            } else {
                System.out.println("Thank you");
                accountExists = false;
            }

        }
    }





    public void DisplayAccountInfo(int accountNumber){

            Account account = getAccountByAccountNumber(accountNumber);

            System.out.println("********** Account Info **********");
            System.out.println("Account Number: " + account.accountNumber);
            System.out.println("Account Holder Name: " + account.userName);
            System.out.println("Account Balance: " + account.accountBalance);
            System.out.println();

        }



    String DB_url = "jdbc:mysql://127.0.0.1:3306/Java_BankingSystem";
    String DB_username = "root";
    String DB_password = "";
        public void InsertUserIntoDatabase(int accountNumber, String userName, int pinNumber, double accountBalance){
            try (Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password)) {
                String insertQuery = "INSERT INTO UserInformation (accountNumber, userName, pinNumber) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, accountNumber);
                    preparedStatement.setString(2, userName);
                    preparedStatement.setInt(3, pinNumber);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public Account getAccountByAccountNumber(int accountNumber) {
        try (Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password)) {
            String selectQuery = "SELECT * FROM UserInformation WHERE accountNumber = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, accountNumber);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String userName = resultSet.getString("userName");
                    int pinNumber = resultSet.getInt("pinNumber");
                    double accountBalance = resultSet.getDouble("accountBalance");

                    return new Account(userName, accountNumber, pinNumber, accountBalance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Account not found
    }




    public void UpdateAccountBalanceInDatabase(int accountNumber, double newBalance) {
        try (Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password)) {
            String updateQuery = "UPDATE UserInformation SET accountBalance = ? WHERE accountNumber = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setDouble(1, newBalance);
                preparedStatement.setInt(2, accountNumber);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
