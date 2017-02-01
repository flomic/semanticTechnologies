import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

public class ChangeBookDetailsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField titleTextField;
    private JTextField publicationYearTextField;
    private JTextField genreTextField;
    private JComboBox publisherComboBox;
    private JComboBox authorComboBox;
    private JTextField isbnTextField;
    private JPanel changeBookView;
    private JOptionPane optionPane;
    private Book book;

    public ChangeBookDetailsDialog(String isbn) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        LinkedList<String> authors = RepoHandler.getAll(MainWindow.getRepo(), "Author"); //get all authors in the repo
        LinkedList<String> publisher = RepoHandler.getAll(MainWindow.getRepo(), "Publisher"); //get all publisher in the repo
        book = RepoHandler.searchBookWithFilter(MainWindow.getRepo(), "FILTER(?b = ex:" + isbn + ")").getFirst();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        authorComboBox.addItem("Please select an author"); //Default item that should be shown in the comboBox
        authorComboBox.addItem("New Author"); //Used to allow the user to add a new author
        for (String a : authors) {
            authorComboBox.addItem(a); //add all authors of the repo to the comboBox
        }

        //add itemListener to react when the user wants to add a new author
        authorComboBox.addItemListener(new AuthorSelected());


        publisherComboBox.addItem("Please select a publisher");//First item that should be shown in the comboBox
        publisherComboBox.addItem("New Publisher"); //Used to allow the user to add a new publisher
        for (String p : publisher) {
            publisherComboBox.addItem(p); //add all publishers of the repo to the comboBox
        }

        //add itemListener to react when the user wants to add a new publisher
        publisherComboBox.addItemListener(new PublisherSelected());

        isbnTextField.setText(book.getIsbn());
        isbnTextField.setEditable(false); // ISBN should not be changed

        titleTextField.setText(book.getTitle());

        if (book.getAuthor() != null && !book.getAuthor().equals("")) {
            authorComboBox.setSelectedItem(book.getAuthor());
        }

        if (book.getPublisher() != null && !book.getPublisher().equals("")) {
            publisherComboBox.setSelectedItem(book.getPublisher());
        }

        genreTextField.setText(book.getGenre());
        if (book.getPublicationYear() != null) {
            publicationYearTextField.setText(book.getPublicationYear() + "");
        }

        Object[] options = {"Save", "Cancel"}; // the two options the user can select in the optionPane
        optionPane = new JOptionPane(changeBookView, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
        setContentPane(optionPane);
        optionPane.addPropertyChangeListener(new MyPropertyChangeListener());
        pack();
    }

    public String showDialog() {
        setVisible(true);
        return optionPane.getValue().toString();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public Book getNewBook() {
        Book newBook;
        if (publicationYearTextField.getText().equals("")) {
            newBook = new Book(isbnTextField.getText(), authorComboBox.getSelectedItem().toString(), titleTextField.getText(), publisherComboBox.getSelectedItem().toString(), genreTextField.getText(), null);
        } else {
            newBook = new Book(isbnTextField.getText(), authorComboBox.getSelectedItem().toString(), titleTextField.getText(),  publisherComboBox.getSelectedItem().toString(), genreTextField.getText(), Integer.parseInt(publicationYearTextField.getText()));
        }
        return newBook;
    }

    public boolean changed(Book newBook) {
        boolean result = false;
        if ((book.getAuthor() != null && newBook.getAuthor() != null && !book.getAuthor().equals(newBook.getAuthor())) ||
                (book.getAuthor() == null && newBook.getAuthor() != null)) {
            result = true;
        }

        if ((book.getTitle() != null && newBook.getTitle() != null && !book.getTitle().equals(newBook.getTitle())) ||
                (book.getTitle() == null && newBook.getTitle() != null)) {
            result = true;
        }

        if ((book.getPublisher() != null && newBook.getPublisher() != null && !book.getPublisher().equals(newBook.getPublisher())) ||
                (book.getPublisher() == null && newBook.getPublisher() != null)) {
            result = true;
        }

        if ((book.getGenre() != null && newBook.getGenre() != null && !book.getGenre().equals(newBook.getGenre())) ||
                (book.getGenre() == null && newBook.getGenre() != null)) {
            result = true;
        }

        if ((book.getPublicationYear() != null && newBook.getPublicationYear() != null && !book.getPublicationYear().equals(newBook.getPublicationYear())) ||
                (book.getPublicationYear() == null && newBook.getPublicationYear() != null)) {
            result = true;
        }

        return result;
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        changeBookView = new JPanel();
        changeBookView.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(changeBookView, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        changeBookView.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Title");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleTextField = new JTextField();
        panel1.add(titleTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        changeBookView.add(panel2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Publication Year");
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        publicationYearTextField = new JTextField();
        panel2.add(publicationYearTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        changeBookView.add(panel3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Genre");
        panel3.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        genreTextField = new JTextField();
        panel3.add(genreTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        changeBookView.add(panel4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Publisher");
        panel4.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        publisherComboBox = new JComboBox();
        panel4.add(publisherComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        changeBookView.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Author");
        panel5.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        authorComboBox = new JComboBox();
        panel5.add(authorComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        changeBookView.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("ISBN*");
        panel6.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isbnTextField = new JTextField();
        isbnTextField.setColumns(13);
        isbnTextField.setEditable(true);
        isbnTextField.setEnabled(true);
        panel6.add(isbnTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel7.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel7.add(panel8, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel8.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel8.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setFont(new Font(label7.getFont().getName(), label7.getFont().getStyle(), 48));
        label7.setHorizontalTextPosition(0);
        label7.setText("Book Manager");
        contentPane.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    private class AuthorSelected implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED  //if a new item is selected and it is "New Author"
                    && authorComboBox.getSelectedItem().toString().equals("New Author")) {

                //Create an new Dialog to enter the information about the author
                Object[] options = {"Save", "Cancel"};
                AddAuthorDialog aap = new AddAuthorDialog(null);
                String o = aap.showDialog();

                if (o.equals("Save")) { //if the user wants to save the author

                    //get all the information and construct an id
                    String name = aap.getName();
                    String gender = aap.getGender();
                    String dob = aap.getDateOfBirth();
                    String id = name.replaceAll(" ", "") + "_" + dob;

                    Author a = new Author(id, name, gender, dob); //create a new author
                    ModelHandler.addAuthor(a, MainWindow.getModel()); //add it to the model
                    authorComboBox.addItem(id); //add the item to the combobox
                    authorComboBox.setSelectedItem(id); //select the item in the combobox

                } else { //if the user selects cancel reset the selected item in the combobox
                    authorComboBox.setSelectedItem("Please select an author");
                }
            }

        }
    }

    private class PublisherSelected implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED && //if a new item is selected and it is "New Publisher"
                    publisherComboBox.getSelectedItem().toString().equals("New Publisher")) {

                // Show a input dialog where the user enters the publisher name, which is also the id
                String publisher = JOptionPane.showInputDialog(null, "Please enter the publisher name", "New Publisher", JOptionPane.PLAIN_MESSAGE);
                if (publisher != null && !publisher.equals("")) { //if the entered string is not empty
                    if (!RepoHandler.getAll(MainWindow.getRepo(), "Publisher").contains(publisher) && !ModelHandler.contains(MainWindow.getModel(), publisher, RDF.TYPE, "Publisher", 'I')) {
                        Publisher p = new Publisher(publisher); //create a new publisher
                        ModelHandler.addPublisher(p, MainWindow.getModel()); //add the publisher to the model
                        publisherComboBox.addItem(publisher); //add the publisher to the combobox
                        publisherComboBox.setSelectedItem(publisher); //select the publisher in the combobox

                    } else {
                        JOptionPane.showMessageDialog(null, "This publisher ID exists already.", "Error", JOptionPane.ERROR_MESSAGE);
                        publisherComboBox.setSelectedItem("Please select a publisher");
                    }

                } else {
                    //if the entered string is empty, reset the combobox to the intial value
                    publisherComboBox.setSelectedItem("Please select a publisher");

                }
            }
        }
    }

    private class MyPropertyChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String prop = evt.getPropertyName();
            if (isVisible() && evt.getSource() == optionPane && prop.equals(JOptionPane.VALUE_PROPERTY)) {
                boolean okToClose = true;
                if (evt.getNewValue().equals("Save")) { //if the user clicked on save

                    if (!publicationYearTextField.getText().equals("")) {
                        try {
                            Integer.parseInt(publicationYearTextField.getText());
                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(null, "Publication Year needs to be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                            okToClose = false;
                        }
                    }
                } else if (evt.getNewValue().equals("wait")) { //used to keep the dialog open, if the input was not ok
                    okToClose = false;
                }
                if (okToClose) {
                    if (changed(getNewBook())) {
                        ModelHandler.removeBook(book, MainWindow.getModel());
                        ModelHandler.addBook(getNewBook(), MainWindow.getModel());
                        MainWindow.rewriteFile();
                        MainWindow.reloadRepo();
                    }

                    setVisible(false);
                } else { //if the user clicked on "Save" but the input was not ok
                    //set the value of the optionPane to an intermediate value,
                    // so that when the user clicks again on "Save" the listener fires again
                    optionPane.setValue("wait");
                }
            }
        }

    }

    /*public static void main(String[] args) {
        ChangeBookDetailsDialog dialog = new ChangeBookDetailsDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
