package src.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FileChooserUI;

import org.netbeans.jemmy.operators.JMenuBarOperator;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListEditor;
import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;

public class SwingMovieListEditorView extends JFrame implements
		MovieListEditorView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<Movie> movieList = null;
	private JTextField newMovieTxt;
	private MovieListEditor editor = null;
	private JComboBox<ImageIcon> ratingCombo;
	private JComboBox<Category> categoryFilterCombo;
	private JComboBox<Category> categoryCombo;
	JMenuItem saveAs;
	JMenu menu;
	public int i = 0;
	public static SwingMovieListEditorView window;

	public SwingMovieListEditorView() {
		super();
	}

	public SwingMovieListEditorView(String title) {
		super(title);
	}

	public static void main(String[] args) {
		SwingMovieListEditorView window = SwingMovieListEditorView.start();
		MovieList movieList = new MovieList();
		MovieListEditor editor = new MovieListEditor(movieList, window);
	}

	@Override
	public void setMovies(Vector<Movie> movies) {
		movieList.setListData(movies);
	}

	public void init() {
		setTitle("Movie List");
		setLayout();
		setJMenuBar(initJMenuBar());
		// getContentPane().add(initButtonSaveAs());
		getContentPane().add(initMovieListPane());
		getContentPane().add(initMovieDetailsPane());
		getContentPane().add(initButtonPanel());
		pack();
	}

	private JMenuBar initJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(initFileMenu());
		menuBar.add(initViewMenu());
		return menuBar;
	}

	private JMenu initViewMenu() {
		JMenu viewMenu = new JMenu("View");
		viewMenu.add(initSortBy("Sort By Name", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.sortByName();

			}
		}));
		viewMenu.add(initSortBy("Sort by Rating", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.sortByRating();
			}
		}));
		return viewMenu;
	}

	private JMenuItem initSortBy(String text, ActionListener actionListener) {
		JMenuItem sortByName = new JMenuItem(text);
		sortByName.addActionListener(actionListener);
		return sortByName;
	}

	private JMenuItem initButtonSaveAs() {
		JMenuItem saveAs = new JMenuItem("SaveAs");
		saveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					editor.saveAs();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		return saveAs;
	}

	private JMenu initFileMenu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(initButtonOpen());
		fileMenu.add(initButtonSave());
		fileMenu.add(initButtonSaveAs());
		return fileMenu;

	}

	private JMenuItem initButtonOpen() {
		JMenuItem open = new JMenuItem("Open");
		open.setName("Open");
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					editor.open();
				} catch (NumberFormatException | DuplicateMovieException
						| IOException | InvalidFileFormatException e1) {
					handleInvalidFormatMoviesFileException();
				}
			}
		});
		return open;
	}

	private void handleInvalidFormatMoviesFileException() {
		JOptionPane
				.showMessageDialog(
						this,
						"Wrong file format. Correct format is: Braveheart|HORROR|5. Please review the format and try again.",
						"Wrong file format", JOptionPane.ERROR_MESSAGE);

	}

	private JMenuItem initButtonSave() {
		JMenuItem save = new JMenuItem("Save");
		save.setName("Save");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					editor.save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		return save;

	}

	private JPanel initButtonPanel() {
		JPanel buttonListPane = new JPanel();
		buttonListPane
				.setLayout(new BoxLayout(buttonListPane, BoxLayout.X_AXIS));
		addStandardEmptyBorder(buttonListPane);
		buttonListPane.add(initAddBtn());
		addHorizontalSpaceBtwComponents(buttonListPane);
		buttonListPane.add(initUpdateBtn());
		return buttonListPane;
	}

	private void addHorizontalSpaceBtwComponents(JPanel buttonListPane) {
		buttonListPane.add(Box.createRigidArea(new Dimension(5, 0)));
	}

	private JPanel initMovieDetailsPane() {
		JPanel movieDetailsPanel = new JPanel();
		addStandardEmptyBorder(movieDetailsPanel);
		movieDetailsPanel.setLayout(new BoxLayout(movieDetailsPanel,
				BoxLayout.Y_AXIS));
		movieDetailsPanel.add(initNewMovieTextField());
		addVerticalStandardBtwComponents(movieDetailsPanel);
		movieDetailsPanel.add(initCategoryComboBox());
		addVerticalStandardBtwComponents(movieDetailsPanel);
		movieDetailsPanel.add(initRatingComboBox());
		return movieDetailsPanel;
	}

	private Component initMovieListPane() {
		JPanel movieListPane = new JPanel();
		movieListPane.setLayout(new BoxLayout(movieListPane, BoxLayout.Y_AXIS));
		addStandardEmptyBorder(movieListPane);
		movieListPane.add(initCategoryFilter());
		addVerticalStandardBtwComponents(movieListPane);
		movieListPane.add(initList());
		return movieListPane;
	}

	private void addVerticalStandardBtwComponents(JPanel panel) {
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
	}

	private void addStandardEmptyBorder(JPanel panel) {
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	private JComponent initCategoryComboBox() {
		categoryCombo = new JComboBox<Category>(Category.values());
		categoryCombo.setName("categoryCombo");
		categoryCombo.setSelectedItem(Category.UNCATEGORIZED);
		return categoryCombo;
	}

	private JComponent initCategoryFilter() {
		categoryFilterCombo = new JComboBox<Category>(Category.values());
		categoryFilterCombo.setName("categoryFilterCombo");
		categoryFilterCombo.setSelectedItem(Category.ALL);
		categoryFilterCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.filter();
			}
		});
		return categoryFilterCombo;
	}

	private JComponent initRatingComboBox() {
		ratingCombo = new JComboBox<>(CustomMovieListRenderer.icons());
		ratingCombo.setName("ratingCombo");
		return ratingCombo;
	}

	private JComponent initAddBtn() {
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.addMovie();
			}
		});
		return addBtn;
	}

	private JComponent initUpdateBtn() {
		JButton updateBtn = new JButton("Update");
		getContentPane().add(updateBtn);
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.updateMovie();
			}
		});
		return updateBtn;
	}

	private JComponent initNewMovieTextField() {
		newMovieTxt = new JTextField(16);
		newMovieTxt.setName("movieName");
		return newMovieTxt;
	}

	private JComponent initList() {
		movieList = new JList<Movie>(new Vector<Movie>());
		movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		movieList.setCellRenderer(new CustomMovieListRenderer());
		movieList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				editor.selectMovie(movieList.getSelectedIndex());
			}
		});

		JScrollPane scroller = new JScrollPane(movieList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		return scroller;
	}

	private void setLayout() {
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	}

	public static SwingMovieListEditorView start() {
		window = new SwingMovieListEditorView();
		window.init();
		window.setVisible(true);
		return window;
	}

	@Override
	public String getNameField() {
		return newMovieTxt.getText();
	}

	@Override
	public void setEditor(MovieListEditor anEditor) {
		this.editor = anEditor;
	}

	@Override
	public void setNameField(String movieName) {
		newMovieTxt.setText(movieName);

	}

	@Override
	public void handleDuplicateMovieException(String movieName) {
		JOptionPane.showMessageDialog(this,
				"Adding this movie will result in a duplicate movie",
				"Duplicate Movie", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void setRatingField(int rating) {
		ratingCombo.setSelectedIndex(rating + 1);
	}

	@Override
	public int getRatingField() {
		return ratingCombo.getSelectedIndex() - 1;
	}

	@Override
	public Category getCategoryField() {
		return (Category) categoryCombo.getSelectedItem();
	}

	@Override
	public Category getCategoryFilter() {
		return (Category) categoryFilterCombo.getSelectedItem();
	}

	@Override
	public void setCategoryField(Category category) {
		categoryCombo.setSelectedItem(category);
	}

	@Override
	public File getFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else {
			return null;
		}
	}

	@Override
	public File getFileToOpen() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else {
			return null;
		}
	}
}
