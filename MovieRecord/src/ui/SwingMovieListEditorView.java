package src.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.netbeans.jemmy.operators.JTextFieldOperator;

import src.core.Movie;
import src.core.MovieListEditor;

public class SwingMovieListEditorView extends JFrame implements
		MovieListEditorView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<Movie> movieList = null;
	private JTextField newMovieTxt;
	private MovieListEditor editor=null;

	public SwingMovieListEditorView() {
		super();
	}

	public SwingMovieListEditorView(String title) {
		super(title);
	}

	@Override
	public void setMovies(Vector<Movie> movies) {
		movieList.setListData(movies);
	}

	public void init() {
		setTitle("Movie List");
		setLayout();
		initList();
		initNewMovieTextField();
		initAddBtn();
		pack();
	}

	private void initAddBtn() {
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.addMovie();
			}	
		});
		getContentPane().add(addBtn);
	}

	private void initNewMovieTextField() {
		newMovieTxt = new JTextField(16);
		getContentPane().add(newMovieTxt);
	}

	private void initList() {
		movieList = new JList<Movie>(new Vector<Movie>());
		movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		movieList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				editor.selectMovie(e.getFirstIndex());
			}
		});
		
		JScrollPane scroller = new JScrollPane(movieList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scroller);
	}

	private void setLayout() {
		getContentPane().setLayout(new FlowLayout());
	}

	public static void start() {
		SwingMovieListEditorView window = new SwingMovieListEditorView();
		window.init();
		window.setVisible(true);
	}

	@Override
	public String getMovieName() {
		return newMovieTxt.getText();
	}

	@Override
	public void setEditor(MovieListEditor anEditor) {
		this.editor = anEditor;
	}

	@Override
	public void setMovieName(String movieName) {
		newMovieTxt.setText(movieName);
		
	}

}
