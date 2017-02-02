import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

/**
 * Created by christine on 19.01.17.
 */

/**
 * Dialog to manually add a book
 */
public class AddBookDialog extends JDialog {
    public JPanel addBookView;
    private JTextField titleTextField;
    private JTextField publicationYearTextField;
    private JTextField genreTextField;
    private JTextField isbnTextField;
    private JComboBox<String> authorComboBox;
    private JComboBox<String> publisherComboBox;
    private JOptionPane optionPane;
    private JButton buttonSearch;

    public AddBookDialog(Frame aFrame) {
        super(aFrame, true);
        MainWindow.reloadRepo();
        LinkedList<String> authors = RepoHandler.getAll(MainWindow.getRepo(), "Author"); //get all authors in the repo
        LinkedList<String> publisher = RepoHandler.getAll(MainWindow.getRepo(), "Publisher"); //get all publisher in the repo


        buttonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String isbn = getIsbn();
                if (isbn.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please enter an ISBN number!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (isbn.length() < 13) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid ISBN number!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else downloadInfo(isbn);
                }
            }
        });

        authorComboBox.addItem("Please select an author"); //First item that should be shown in the comboBox
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

        Object[] options = {"Save", "Cancel"}; // the two options the user can select in the optionPane
        optionPane = new JOptionPane(getAddBookView(), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
        setContentPane(optionPane);
        optionPane.addPropertyChangeListener(new MyPropertyChangeListener());
        pack();
    }

    /**
     * set the dialog to visible and return "Save" or "Cancel" depending on what the user clicks
     *
     * @return
     */
    public String showDialog() {
        setVisible(true);
        return optionPane.getValue().toString();
    }

    public JPanel getAddBookView() {
        return addBookView;
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getPublicationYear() {
        return publicationYearTextField.getText();
    }

    public String getGenre() {
        return genreTextField.getText();
    }

    public String getIsbn() {
        return isbnTextField.getText();
    }

    public String getAuthor() {
        return authorComboBox.getSelectedItem().toString();
    }

    public String getPublisher() {
        return publisherComboBox.getSelectedItem().toString();
    }

    private void downloadInfo(String isbn) {
        String dbpAuthor = null;
        String dbpTitle = null;
        String dbpPublisher = null;
        String dbpGenre = null;
        String publicationDate = null;

        String dbpQuery = "PREFIX dbpedia: <http://dbpedia.org/ontology/>" +
                "select * " +
                "where {" +
                "?book_uri rdf:type dbpedia:Book ." +
                "?book_uri rdfs:label ?title ." +
                "?book_uri dbpedia:publisher ?publisher_uri ." +
                "?publisher_uri rdfs:label ?publisher ." +
                "?book_uri dbpedia:literaryGenre ?genre_uri ." +
                "?genre_uri rdfs:label ?genre ." +
                "?book_uri dbpedia:author ?author_uri ." +
                "?book_uri dbpedia:isbn ?isbn." +
                "?author_uri dbpedia:birthName ?author." +
                "?author_uri dbpedia:birthDate ?birthDate." +
                "?book_uri dbpedia:publicationDate ?publicationDate." +
                "FILTER (regex(?isbn, '" + isbn + "'))" +
                "FILTER (lang(?author) = 'en')" +
                "FILTER (lang(?genre) = 'en')" +
                "FILTER (lang(?title) = 'en')" +
                "FILTER (lang(?publisher) = 'en')" +
                "}";

        Repository dbpRepo = new SPARQLRepository("http://dbpedia.org/sparql");
        dbpRepo.initialize();
        TupleQueryResult result = RepoHandler.query(dbpRepo, dbpQuery);

        if (result.hasNext()) {
            BindingSet bindingSet = result.next();
            if (bindingSet.hasBinding("author")) {
                dbpAuthor = RepoHandler.cleanString(bindingSet.getValue("author").toString());
                //if author does not exist yet - create it
                String id = "";
                String dateOfBirth = RepoHandler.cleanString(bindingSet.getValue("birthDate").toString());
                id = dbpAuthor.replaceAll(" ", "") + "_" + dateOfBirth;
                if (!ModelHandler.contains(MainWindow.getModel(), dbpAuthor, RDF.TYPE, "Author", 'I')) {
                    System.out.println(dateOfBirth);
                    Author a = new Author(id, dbpAuthor, "", dateOfBirth); //create a new author
                    ModelHandler.addAuthor(a, MainWindow.getModel()); //add it to the model
                    authorComboBox.addItem(id); //add the item to the combobox
                }
                authorComboBox.setSelectedItem(id); //select the author in the combobox
            }
            if (bindingSet.hasBinding("title")) {
                dbpTitle = RepoHandler.cleanString(bindingSet.getValue("title").toString());
                titleTextField.setText(dbpTitle);
            }
            if (bindingSet.hasBinding("publisher")) {
                dbpPublisher = RepoHandler.cleanString(bindingSet.getValue("publisher").toString());
                //if publisher does not exist yet - create it
                if (!ModelHandler.contains(MainWindow.getModel(), dbpPublisher, RDF.TYPE, "Publisher", 'I')) {
                    dbpPublisher = dbpPublisher.replaceAll("[\\W+]", "_");
                    Publisher p = new Publisher(dbpPublisher); //create a new publisher
                    ModelHandler.addPublisher(p, MainWindow.getModel()); //add the publisher to the model
                    publisherComboBox.addItem(dbpPublisher); //add the publisher to the combobox
                }
                publisherComboBox.setSelectedItem(dbpPublisher); //select the publisher in the combobox
            }
            if (bindingSet.hasBinding("genre")) {
                dbpGenre = RepoHandler.cleanString(bindingSet.getValue("genre").toString());
                genreTextField.setText(dbpGenre);
            }
            if (bindingSet.hasBinding("publicationDate")) {
                System.out.println(publicationDate);
                publicationDate = RepoHandler.cleanString(bindingSet.getValue("publicationDate").toString());
                publicationYearTextField.setText(publicationDate);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ISBN was not found on dbpedia.org", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        addBookView = new JPanel();
        addBookView.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 48));
        label1.setHorizontalTextPosition(0);
        label1.setText("Book Manager");
        addBookView.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        addBookView.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Title");
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleTextField = new JTextField();
        panel2.add(titleTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Publication Year");
        panel3.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        publicationYearTextField = new JTextField();
        panel3.add(publicationYearTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Genre");
        panel4.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        genreTextField = new JTextField();
        panel4.add(genreTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Publisher");
        panel5.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        publisherComboBox = new JComboBox();
        panel5.add(publisherComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Author");
        panel6.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        authorComboBox = new JComboBox();
        panel6.add(authorComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("ISBN*");
        panel7.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isbnTextField = new JTextField();
        isbnTextField.setColumns(13);
        isbnTextField.setEditable(true);
        isbnTextField.setEnabled(true);
        panel7.add(isbnTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buttonSearch = new JButton();
        buttonSearch.setText("Search");
        panel7.add(buttonSearch, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label2.setLabelFor(titleTextField);
        label3.setLabelFor(publicationYearTextField);
        label4.setLabelFor(genreTextField);
        label5.setLabelFor(publisherComboBox);
        label6.setLabelFor(authorComboBox);
        label7.setLabelFor(isbnTextField);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return addBookView;
    }

    /**
     * Class to allow the user to add a new author
     */
    private class AuthorSelected implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED  //if a new item is selected and it is "New Author"
                    && authorComboBox.getSelectedItem().toString().equals("New Author")) {

                //Create an new Dialog to enter the information about the author
                Object[] options = {"Save", "Cancel"};
                AddPersonDialog aap = new AddPersonDialog(null, true);
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

    /**
     * Class to allow the user to add a new publisher
     */
    private class PublisherSelected implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED && //if a new item is selected and it is "New Publisher"
                    publisherComboBox.getSelectedItem().toString().equals("New Publisher")) {

                // Show a input dialog where the user enters the publisher name, which is also the id
                String publisher = JOptionPane.showInputDialog(null, "Please enter the publisher name", "New Publisher", JOptionPane.PLAIN_MESSAGE);

                publisher = publisher.replaceAll("[\\W+]", "_");
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

    /**
     * Class used to allow the user to exit from the dialog
     */
    private class MyPropertyChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String prop = evt.getPropertyName();
            if (isVisible() && evt.getSource() == optionPane && prop.equals(JOptionPane.VALUE_PROPERTY)) {
                boolean okToClose = true;
                if (evt.getNewValue().equals("Save")) { //if the user clicked on save

                    //check the input for correctness and that isbn is not missing
                    if (isbnTextField.getText().length() != 13 && isbnTextField.getText().length() != 17) {
                        JOptionPane.showMessageDialog(null, "Please enter a correct ISBN number!", "Error", JOptionPane.ERROR_MESSAGE);
                        okToClose = false;
                    } else if (RepoHandler.searchBookWithFilter(MainWindow.getRepo(), "FILTER(?b = ex:" + isbnTextField.getText() + ")", MainWindow.reader).size() > 0) {
                        JOptionPane.showMessageDialog(null, "There is already a book with this ISBN number! \n Please check if the one you entered is correct!", "Error", JOptionPane.ERROR_MESSAGE);
                        okToClose = false;
                    }

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
                    setVisible(false);
                } else { //if the user clicked on "Save" but the input was not ok
                    //set the value of the optionPane to an intermediate value,
                    // so that when the user clicks again on "Save" the listener fires again
                    optionPane.setValue("wait");
                }
            }
        }
    }
}
