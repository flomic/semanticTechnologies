import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.eclipse.rdf4j.repository.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class showBooksDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonClose;
    private JPanel contentPanel = new JPanel();
    private JButton buttonAllBooks;
    private LinkedList<String> books;
    private Repository repo;
    private String isbn;

    private static final String FILE_PATH = "src/main/resources/project.ttl";

    public showBooksDialog() {
        new showBooksDialog("");
    }

    public showBooksDialog(String isbn) {

        this.isbn = isbn;
        $$$setupUI$$$();
        //setSize(getPreferredSize());
        setMinimumSize(new Dimension(400, 400));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonClose);

        buttonAllBooks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonAllBooks();
            }
        });

        buttonClose.addActionListener(new ActionListener() {
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

        setVisible(true);
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onButtonAllBooks() {
        contentPanel.removeAll();
        for (int i = 0; i < books.size(); i++) {
            JButton b = new JButton(books.get(i));
            b.addActionListener(new BookClickedListener());
            contentPanel.add(b);
        }
        resetGUI();
    }

    public static void main(String[] args) {
        showBooksDialog dialog = new showBooksDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        repo = FileHandler.readRepositoryFromFile(FILE_PATH);
        books = RepoHandler.getAll(repo, "Book");
        if (isbn.equals("")) {
            for (int i = 0; i < books.size(); i++) {
                JButton b = new JButton(books.get(i));
                b.addActionListener(new BookClickedListener());
                contentPanel.add(b);
            }
        } else {
            Book bookDetails = (RepoHandler.searchWithFilter(repo, "FILTER(?b = ex:" + isbn + ")")).getFirst();
            contentPanel.add(new JLabel(bookDetails.getIsbn()));
            contentPanel.add(new JLabel(bookDetails.getTitle()));
            contentPanel.add(new JButton(bookDetails.getAuthor()));

            JButton publisherButton = new JButton(bookDetails.getPublisher());
            publisherButton.addActionListener(new PublisherClicked());
            contentPanel.add(publisherButton);

            contentPanel.add(new JLabel(bookDetails.getGenre()));
            contentPanel.add(new JLabel(bookDetails.getPublicationYear().toString()));
        }

    }

    private void resetGUI() {
        contentPane.repaint();
        contentPane.setPreferredSize(contentPane.getPreferredSize());
        contentPane.validate();
    }

    private void showBookDetails(String isbn) {
        contentPanel.removeAll();
        Book bookDetails = (RepoHandler.searchWithFilter(repo, "FILTER(?b = ex:" + isbn + ")")).getFirst();
        contentPanel.add(new JLabel(bookDetails.getIsbn()));
        contentPanel.add(new JLabel(bookDetails.getTitle()));
        contentPanel.add(new JButton(bookDetails.getAuthor()));

        JButton publisherButton = new JButton(bookDetails.getPublisher());
        publisherButton.addActionListener(new PublisherClicked());
        contentPanel.add(publisherButton);

        contentPanel.add(new JLabel(bookDetails.getGenre()));
        contentPanel.add(new JLabel(bookDetails.getPublicationYear().toString()));
        resetGUI();
    }

    private void showBooksForPublisher(String publisherId) {
        contentPanel.removeAll();
        LinkedList<Book> books = RepoHandler.searchWithFilter(repo, "FILTER(?publisher = ex:" + publisherId + ")");
        contentPanel.add(new JLabel(publisherId));
        JPanel p = new JPanel();
        for (Book bookDetails : books) {
            p = new JPanel();
            p.add(new JLabel(bookDetails.getIsbn()));
            p.add(new JLabel(bookDetails.getTitle()));
            p.add(new JButton(bookDetails.getAuthor()));
            p.add(new JLabel(bookDetails.getGenre()));
            p.add(new JLabel(bookDetails.getPublicationYear().toString()));
        }
        contentPanel.add(p);
        resetGUI();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonClose = new JButton();
        buttonClose.setText("Close");
        panel2.add(buttonClose, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAllBooks = new JButton();
        buttonAllBooks.setText("All Books");
        panel1.add(buttonAllBooks, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        contentPane.add(contentPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(100, 100), new Dimension(400, 400), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    private class BookClickedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showBookDetails(e.getActionCommand());
        }
    }

    private class PublisherClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showBooksForPublisher(e.getActionCommand());
        }
    }
}
