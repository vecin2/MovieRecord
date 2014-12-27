package src.ui;

import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import src.core.Movie;
import src.core.exceptions.UnratedMovieException;

public class CustomMovieListRenderer extends JLabel implements
		ListCellRenderer<Movie> {

	private static ImageIcon[] ratingIcons = {
			new ImageIcon("images/no-rating.jpg"),
			new ImageIcon("images/zero-stars.jpg"),
			new ImageIcon("images/one-star.jpg"),
			new ImageIcon("images/two-stars.jpg"),
			new ImageIcon("images/three-stars.jpg"),
			new ImageIcon("images/four-stars.jpg"),
			new ImageIcon("images/five-stars.jpg") };

	@Override
	public Component getListCellRendererComponent(JList<? extends Movie> list,
			Movie value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.getName());
		try {
			FileReader file = new FileReader("images/no-rating.jpg");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			setIcon(ratingIcons[value.getRating()+1]);
		} catch (UnratedMovieException e) {
			setIcon(ratingIcons[0]);
		}
		return this;
	}

	public static ImageIcon iconForRating(int index) {
		return ratingIcons[index];
	}
	public static ImageIcon[] icons(){
		return ratingIcons;
	}

}
