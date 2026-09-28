import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class HelloWorld {

    // Anki database location
    public static final String ANKI_DB_PATH = "C:collection.anki2";
    public static final int MAX_CARDS = 15; // Change this limit as needed
    public static final int WORD_LIMIT = 10;

    public static void main(String[] args) {

        //System.out.println("Hello, World!");
	    JFrame frame = new JFrame("Full Screen Java App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes if X is pressed
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Does nothing on close
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize
        frame.setUndecorated(true); // Remove title bar for true fullscreen
        frame.setVisible(true);
        //FONT - GlobalVars
        //frame.setLayout(new GridLayout(3, 1));
        Font customFont = new Font("Arial", Font.PLAIN, 16);
/*
        //JLabel WRITE TEXT IN JFrame
        String[] greeting = new String[]{"Hello!", "Add 10 Cards in English and 10 in French to continue!", "Hello, this is text in a JFrame!"};

*/
        JLabel label0 = new JLabel("Hello!, How are you?", SwingConstants.CENTER);
        frame.add(label0, BorderLayout.EAST);
        label0.setBorder(BorderFactory.createEmptyBorder(-350, 0, 0, 60));
        label0.setFont(customFont);

        try {
            // Launch Anki
            String command = "cmd";
            Runtime.getRuntime().exec(command);
            //ADD HERE Todo
            // Wait a few seconds for the app to open
            try {
                Thread.sleep(3000); // Wait 3 seconds for app to open
                //insert here while loop
            } catch (InterruptedException e) {
                System.out.println("Sleep was interrupted.");
                e.printStackTrace();
            }
            System.out.println("App launched successfully!");

            JLabel label = new JLabel("Add 10 Cards in English and 10 in French to continue!", SwingConstants.CENTER);
            frame.add(label, BorderLayout.EAST);
            label.setBorder(BorderFactory.createEmptyBorder(-300, 0, 0, 60));
            label.setFont(customFont);

            //maximizeWindow();
            //Trying to implement internetMethod
            String wifiName = "WLAN"; // correct Wi-Fi name

            runCommand("netsh interface set interface \"" + wifiName + "\" admin=disable"); // Turn off Wi-Fi
            System.out.println("Wi-Fi disabled. Waiting for Anki to close...");
        } catch (IOException e) {
            System.out.println("Error launching the app.");
            e.printStackTrace();
        }
        //
        //AnkiCardMonitor
        //Variables
        //LocalDate installDate = getAnkiInstallationDate(ANKI_DB_PATH);
            while (true) { // Infinite loop to keep checking
                try {
                    int cardCount = getCardCount(ANKI_DB_PATH);
                    System.out.println("Current card count: " + cardCount);
            //ATTENTION
                    // ATTENTION
                    //ATTENTION < INSTEAD OF >
                    if (cardCount >= MAX_CARDS) {
                        //NEW Cards Inserted
                        //Now Reviewing ALL DUE Cards
                        JLabel label = new JLabel("Now Review All Cards!", SwingConstants.CENTER);
                        frame.add(label, BorderLayout.EAST);
                        label.setBorder(BorderFactory.createEmptyBorder(-250, 0, 0, 30));
                        label.setFont(customFont);
                        //REVIEW
                        //Review();
                        label = new JLabel("Good! You've finished...Well Done", SwingConstants.CENTER);
                        frame.add(label, BorderLayout.EAST);
                        label.setBorder(BorderFactory.createEmptyBorder(-200, 0, 0, 0));
                        label.setFont(customFont);
                        //when REVIEW DONE close app
                        System.out.println("Card limit reached! Closing Anki...");
                        closeAnkiApp();
                        //Now Typing
                        Typing();
                        frame.dispose();
                        //AFTER ANKI CLOSE / OPEN NOTEPAD TO TYPE 10 Fast Fingers
                        //Runtime.getRuntime().exec("calc.exe");
                        break;
                    }
                    // Wait for 10 seconds before checking again
                    Thread.sleep(10000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
/*
        try{
            Process process = Runtime.getRuntime().exec("calc.exe");
        }catch (IOException e){
            e.printStackTrace();
        }
*/
    }
    //END of main method

    private static void runCommand(String command) {
        try { new ProcessBuilder("cmd.exe", "/c", command).start().waitFor(); }
        catch (IOException | InterruptedException ignored) {}
    }

    private static int getCardCount(String dbPath) {
        int Cardcount = 0;
        String url = "jdbc:sqlite:" + dbPath;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM cards")) {

            if (rs.next()) {
                Cardcount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Cardcount;
    }

    public static void maximizeWindow() {
        try {
            // 
            String psCommand = "powershell -command \"(new-object -com wscript.shell).SendKeys('^{UP}')\"";
            Runtime.getRuntime().exec(psCommand);
        } catch (IOException e) {
            System.out.println("Failed to maximize window.");
            e.printStackTrace();
        }
    }

    //CLOSEANKI
   
    private static void closeAnkiApp() {
        //  TURN ON WIFI AGAIN
        /*String wifi = "WLAN"; // correct Wi-Fi name
        runCommand("netsh interface set interface \"" + wifi + "\" admin=enable"); // Turn on Wi-Fi
        */
        //

         //Closing the JFrame

        //
        try {
            //
            String command = "powershell -command \"Get-Process | Where-Object { $_.MainWindowTitle -like '*Anki*' } | Stop-Process -Force\"";
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //ALL-REVIEW-METHODS
    /**
     * Review() finishes, when ALL DUE Cards are reviewed
     * Every 5 seconds getReviewCardCount is called to return remaining Review cards,to see if remainingReviewCards==0
     */
    private static void Review(){

        while(true) {
            //
            int remainingReviewCards = getReviewCardCount(ANKI_DB_PATH);

            System.out.println("Cards Remaining (New + Due Today): " + remainingReviewCards);
            if (remainingReviewCards == 0) {
                System.out.println("No more review cards");
                break;
            }
            try {
                System.out.println("Waiting for reviews to be finished...");
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static void Typing(){

        //TEST
        JFrame frame1 = new JFrame("Text Editor");
        frame1.setSize(600, 400);
        frame1.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame1.setLayout(new BorderLayout());
        frame1.setLayout(new BorderLayout());
        //Textarea
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textArea.setAlignmentY(Component.CENTER_ALIGNMENT);

        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(textArea);
        //scrollPane.setPreferredSize(new Dimension(400, 200));

        frame1.add(scrollPane, BorderLayout.CENTER);

        textArea.setLineWrap(true);

        frame1.setVisible(true);

        //TEST

        textArea.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                int wordCount = countWords(textArea.getText());

                System.out.println("Word Count: " + wordCount); // Debugging purpose

                // Close the application if word count exceeds the limit
                if (wordCount > WORD_LIMIT) {
                    JOptionPane.showMessageDialog(frame1, "Word limit exceeded! Closing...");
                    frame1.dispose(); // Close the window

                }
            }
        });

        //  TURN ON WIFI AGAIN
        String wifi = "WLAN"; // correct Wi-Fi name
        runCommand("netsh interface set interface \"" + wifi + "\" admin=enable"); // Turn on Wi-Fi

        //
    }
    private static int countWords(String text) {
        if (text.isBlank()) return 0; // No words if text is empty or whitespace
        String[] words = text.trim().split("\\s+"); // Split by spaces, tabs, or newlines
        return words.length;
    }

    /**
     * getReviewCardCount needs to return the COUNT OF the remaining/due Review Cards
     * for today
     * THIS method need to go through one time CALLING METHOD already uses LOOP
     */
    private static int getReviewCardCount(String dbPath) {
        String url = "jdbc:sqlite:" + dbPath;
        int count = 0;
        int count0 = 0;
        int count1 = 0;
        int count2 = 0;
        LocalDate installDate = getAnkiInstallationDate(dbPath);
        int ankiToday = (int) java.time.temporal.ChronoUnit.DAYS.between(installDate, LocalDate.now());

        //System.out.println("Anki Installation Date: " + installDate);
        System.out.println("Anki Today (Days since install): " + ankiToday);

        try {
            Class.forName("org.sqlite.JDBC");

            //Just IN (2) bc cards in 1 dont have a real date
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT COUNT(*) FROM cards WHERE queue IN (2) AND due = ? ORDER BY due ASC")) {

                stmt.setInt(1, ankiToday); // Only fetch cards due today
                ResultSet rs = stmt.executeQuery();

                System.out.println("Listing all due cards **for today**:");

                while (rs.next()) {
                    count2 = rs.getInt(1);

                    /*int cardId = rs.getInt("id");
                    int dueDays = rs.getInt("due");
                    int queue = rs.getInt("queue");

                    // Calculate real due date
                    LocalDate realDueDate = installDate.plusDays(dueDays);
                    String formattedDate = realDueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    System.out.println("Card ID: " + cardId + " | Due: " + formattedDate);*/
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving today's due cards!");
            e.printStackTrace();
        }
        //
         //
        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(

                         //"SELECT COUNT(*) FROM cards WHERE queue = 0 " + // New cards
                         // "OR (queue IN (1, 2) AND due <= " + today + ")" // Due today or earlier
                         "SELECT COUNT(*) FROM cards WHERE queue = 1"
                 )

            ) {
                if (rs.next()) {
                    count1 = rs.getInt(1);
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error connecting to Anki database!");
            e.printStackTrace();
        }

        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(

                         //"SELECT COUNT(*) FROM cards WHERE queue = 0 " + // New cards
                         // "OR (queue IN (1, 2) AND due <= " + today + ")" // Due today or earlier
                         "SELECT COUNT(*) FROM cards WHERE queue = 0"
                 )

            ) {
                if (rs.next()) {
                    count0 = rs.getInt(1);
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error connecting to Anki database!");
            e.printStackTrace();
        }

        count = count0+count1+count2;
        return count;

    }
    private static LocalDate getAnkiInstallationDate(String dbPath) {
        String url = "jdbc:sqlite:" + dbPath;
        LocalDate installDate = null;

        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement("SELECT crt FROM col")) {

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    long installTimestamp = rs.getLong("crt");
                    installDate = Instant.ofEpochSecond(installTimestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving Anki installation date!");
            e.printStackTrace();
        }

        return installDate;
    }
}
