import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.repository.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by christine on 19.01.17.
 */

/**
 * Main window from where the user starts
 */
public class MainWindow {
    private JButton addBookButton;
    private JButton searchBookButton;
    private JPanel mainView;
    private JButton showBooksButton;
    private JButton changeBookButton;
    private JButton switchUserButton;
    private static JFrame mainFrame;
    private static Model model;
    private static Repository repo;
    private static final String FILE_PATH = "src/main/resources/project.ttl";
    public static String reader;

    public MainWindow() {
        showBooksButton.addActionListener(new ShowBooksClicked());
        addBookButton.addActionListener(new AddBookBtnClicked());
        searchBookButton.addActionListener(new SearchBookButtonClicked());
        model = FileHandler.readModelFromFile(FILE_PATH);
        repo = FileHandler.readRepositoryFromFile(FILE_PATH);
        changeBookButton.addActionListener(new ChangeBookButtonClicked());
        switchUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reader = changeReader();
            }
        });
    }

    public static void main(String[] args) {
        mainFrame = new JFrame("MainWindow");
        mainFrame.setContentPane(new MainWindow().mainView);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();

        reader = changeReader();

        mainFrame.setVisible(true);
    }

    public static Model getModel() {
        return model;
    }

    public static Repository getRepo() {
        return repo;
    }

    public static void reloadRepo() {
        repo = FileHandler.readRepositoryFromFile(FILE_PATH);
    }

    public static void rewriteFile() {
        try {
            FileHandler.writeModelToFile(FILE_PATH, model);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String changeReader() {
        SelectReaderDialog srd = new SelectReaderDialog();
        return srd.showDialog();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainView = new JPanel();
        mainView.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 48));
        label1.setHorizontalTextPosition(0);
        label1.setText("Book Manager");
        mainView.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addBookButton = new JButton();
        addBookButton.setText("Add Book");
        mainView.add(addBookButton, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchBookButton = new JButton();
        searchBookButton.setText("Search Book");
        mainView.add(searchBookButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showBooksButton = new JButton();
        showBooksButton.setText("Show Books");
        mainView.add(showBooksButton, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changeBookButton = new JButton();
        changeBookButton.setText("Change Book");
        mainView.add(changeBookButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        switchUserButton = new JButton();
        switchUserButton.setText("Switch User");
        mainView.add(switchUserButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainView;
    }


    /**
     * ActionListener for the search book button
     */
    private class SearchBookButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //shows a dialog which contains the search book panel
            Object[] options = {"Back"}; //Button to return to the main window
            SearchBookDialog sbp = new SearchBookDialog("Show");
            //JOptionPane.showOptionDialog(null, sbp.getSearchBookView(), "Search Book", JOptionPane.CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        }
    }

    /**
     * Action listener for the add book button (manual addition)
     */
    private class AddBookBtnClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            //show the AddBookDialog, which returns the value of the button clicked
            AddBookDialog abp = new AddBookDialog(null);
            String o = abp.showDialog();

            if (o.equals("Save")) { // if the user wanted to save the book

                //get all the information for the AddBookDialog
                String isbn = abp.getIsbn();
                String title = abp.getTitle();
                String publicationYear = abp.getPublicationYear();
                String genre = abp.getGenre();
                String publisher = abp.getPublisher();
                String author = abp.getAuthor();

                try {
                    Book b;
                    if (publicationYear.equals("")) { //if the publicationYear is empty, replace it with null
                        b = new Book(isbn, author, title, publisher, genre, null);
                    } else { //if the publicationYear is not empty, parse it to an integer
                        b = new Book(isbn, author, title, publisher, genre, Integer.parseInt(publicationYear));
                    }
                    ModelHandler.addBook(b, MainWindow.reader, MainWindow.getModel());//add the book to the model
                    FileHandler.writeModelToFile(FILE_PATH, model);
                    reloadRepo();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private class ShowBooksClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ShowBooksDialog sbd = new ShowBooksDialog();
        }
    }

    private class ChangeBookButtonClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SearchBookDialog sbp = new SearchBookDialog("Change");
        }
    }
}



