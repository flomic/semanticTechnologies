import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.eclipse.rdf4j.repository.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class ShowBooksDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonClose;
    private JPanel contentPanel = new JPanel();
    private JButton buttonAllBooks;
    private LinkedList<String> books;
    private Repository repo;
    private String isbn;

    private static final String FILE_PATH = "src/main/resources/project.ttl";

    public ShowBooksDialog() {
        new ShowBooksDialog("");
    }

    public ShowBooksDialog(String isbn) {
        this.isbn = isbn;
        $$$setupUI$$$();
        setMinimumSize(new Dimension(800, 400));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonClose);

        buttonAllBooks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showBooks("", false);
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

    public static void main(String[] args) {
        ShowBooksDialog dialog = new ShowBooksDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        repo = FileHandler.readRepositoryFromFile(FILE_PATH);
        books = RepoHandler.getAll(repo, "Book");
        if (isbn.equals("")) {
            showBooks("", true);
        } else {
            showBooks("FILTER(?b = ex:" + isbn + ")", true);
        }
    }

    private void resetGUI() {
        contentPane.repaint();
        contentPane.setPreferredSize(contentPane.getPreferredSize());
        contentPane.validate();
    }

    private void showBooks(String filter, boolean first) {
        if (!first) contentPanel.removeAll();

        LinkedList<Book> books = RepoHandler.searchWithFilter(repo, filter);

        contentPanel.setLayout(new GridLayout(books.size() + 1, 1));
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(1, 6));

        JLabel isbnLabel = new JLabel("ISBN", SwingConstants.CENTER);
        isbnLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        isbnLabel.setBackground(Color.black);
        isbnLabel.setOpaque(true);
        isbnLabel.setForeground(Color.white);
        titlePanel.add(isbnLabel);

        JLabel titleLabel = new JLabel("Title", SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        titleLabel.setBackground(Color.black);
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.white);
        titlePanel.add(titleLabel);

        JLabel authorLabel = new JLabel("Author", SwingConstants.CENTER);
        authorLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        authorLabel.setBackground(Color.black);
        authorLabel.setOpaque(true);
        authorLabel.setForeground(Color.white);
        titlePanel.add(authorLabel);

        JLabel publisherLabel = new JLabel("Publisher", SwingConstants.CENTER);
        publisherLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        publisherLabel.setBackground(Color.black);
        publisherLabel.setOpaque(true);
        publisherLabel.setForeground(Color.white);
        titlePanel.add(publisherLabel);

        JLabel genreLabel = new JLabel("Genre", SwingConstants.CENTER);
        genreLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        genreLabel.setBackground(Color.black);
        genreLabel.setOpaque(true);
        genreLabel.setForeground(Color.white);
        titlePanel.add(genreLabel);

        JLabel publicationYearLabel = new JLabel("Publication Year", SwingConstants.CENTER);
        publicationYearLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        publicationYearLabel.setBackground(Color.black);
        publicationYearLabel.setOpaque(true);
        publicationYearLabel.setForeground(Color.white);
        titlePanel.add(publicationYearLabel);

        contentPanel.add(titlePanel);

        for (Book bookDetails : books) {
            JPanel p = new JPanel();
            p.setLayout(new GridLayout(1, 6));

            JLabel isbnL = new JLabel(bookDetails.getIsbn(), SwingConstants.LEFT);
            isbnL.setBorder(BorderFactory.createLineBorder(Color.black));
            p.add(isbnL);

            if (bookDetails.getTitle() != null && !bookDetails.getTitle().equals("")) {
                JLabel titleL = new JLabel(bookDetails.getTitle(), SwingConstants.LEFT);
                titleL.setBorder(BorderFactory.createLineBorder(Color.black));
                p.add(titleL);
            } else {
                JLabel emptyLabel1 = new JLabel("");
                emptyLabel1.setBorder(BorderFactory.createLineBorder(Color.black));
                p.add(emptyLabel1);
            }

            if (bookDetails.getAuthor() != null && !bookDetails.getAuthor().equals("")) {
                JButton authorButton = new JButton(bookDetails.getAuthor());
                authorButton.addActionListener(new AuthorClicked());
                p.add(authorButton);
            } else {
                JLabel emptyLabel2 = new JLabel("");
                emptyLabel2.setBorder(BorderFactory.createLineBorder(Color.black));
                p.add(emptyLabel2);
            }

            if (bookDetails.getPublisher() != null && !bookDetails.getPublisher().equals("")) {
                JButton publisherButton = new JButton(bookDetails.getPublisher());
                publisherButton.addActionListener(new PublisherClicked());
                p.add(publisherButton);
            } else {
                JLabel emptyLabel3 = new JLabel("");
                emptyLabel3.setBorder(BorderFactory.createLineBorder(Color.black));
                p.add(emptyLabel3);
            }

            if (bookDetails.getGenre() != null && !bookDetails.getGenre().equals("")) {
                JLabel genreL = new JLabel(bookDetails.getGenre(), SwingConstants.LEFT);
                genreL.setBorder(BorderFactory.createLineBorder(Color.black));
                p.add(genreL);
            } else {
                JLabel emptyLabel4 = new JLabel("");
                emptyLabel4.setBorder(BorderFactory.createLineBorder(Color.black));
                p.add(emptyLabel4);
            }

            if (bookDetails.getPublicationYear() != null) {
                JLabel publicationYearL = new JLabel(bookDetails.getPublicationYear().toString(), SwingConstants.LEFT);
                publicationYearL.setBorder(BorderFactory.createLineBorder(Color.black));
                p.add(publicationYearL);
            } else {
                JLabel emptyLabel5 = new JLabel("");
                emptyLabel5.setBorder(BorderFactory.createLineBorder(Color.black));
                p.add(emptyLabel5);
            }

            contentPanel.add(p);
        }

        if (!first) resetGUI();
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
            showBooks("FILTER(?b = ex:" + e.getActionCommand() + ")", false);
        }
    }

    private class PublisherClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showBooks("FILTER(?publisher = ex:" + e.getActionCommand() + ")", false);
        }
    }

    private class AuthorClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showBooks("FILTER(?author = ex:" + e.getActionCommand() + ")", false);
        }
    }
}