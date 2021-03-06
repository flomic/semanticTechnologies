import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by christine on 19.01.17.
 */

/**
 * This class is used to create a panel and search for books.
 */
public class SearchBookDialog extends JDialog {
    private JTextField isbnTextField;
    public JPanel searchBookView;
    private String scope;
    private JOptionPane optionPane;

    /**
     * Constructer do add an action listener to the search button
     */
    public SearchBookDialog(String scope) {
        this.scope = scope;

        Object[] options = {"Search", "Back"}; // the two options the user can select in the optionPane
        optionPane = new JOptionPane(searchBookView, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
        setContentPane(optionPane);
        optionPane.addPropertyChangeListener(new MyPropertyChangeListener());
        pack();
        setVisible(true);
    }

    /**
     * @return returns the text of the isbn text field.
     */
    public String getIsbn() {
        return isbnTextField.getText();
    }

    /**
     * @return returns the JPanel to show in a JOptionPane
     */
    public JPanel getSearchBookView() {
        return searchBookView;
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
        searchBookView = new JPanel();
        searchBookView.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 48));
        label1.setHorizontalTextPosition(0);
        label1.setText("Book Manager");
        searchBookView.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        searchBookView.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("ISBN");
        panel1.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isbnTextField = new JTextField();
        panel1.add(isbnTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return searchBookView;
    }


    private class MyPropertyChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String prop = evt.getPropertyName();
            if (isVisible() && evt.getSource() == optionPane && prop.equals(JOptionPane.VALUE_PROPERTY)) {
                if (evt.getNewValue().equals("Search")) {
                    String isbn = getIsbn();

                    if (isbn.length() == 0) {
                        JOptionPane.showMessageDialog(null, "Please insert an ISBN.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (RepoHandler.searchBookWithFilter(MainWindow.getRepo(), "FILTER(?b = ex:" + isbn + ")", MainWindow.reader).size() == 0) {
                        JOptionPane.showMessageDialog(null, "No book with this ISBN was found.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (scope.equals("Show")) {
                            setVisible(false);
                            ShowBooksDialog sbd = new ShowBooksDialog(isbn);
                        } else {
                            setVisible(false);
                            ChangeBookDetailsDialog cbdd = new ChangeBookDetailsDialog(isbn);
                            cbdd.showDialog();
                        }

                    }
                } else {
                    setVisible(false);
                }
            }
        }
    }
}
