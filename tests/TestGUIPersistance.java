package tests;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import src.core.Category;
import src.core.MovieList;
import src.core.MovieListEditor;
import src.core.MovieListXMLFormatter;
import src.ui.MovieListEditorView;

public class TestGUIPersistance {

	@Test
	public void testSaveAsWritesToAFileRequestedToTheView() throws IOException {
		File outputFile = File.createTempFile("testSaveAs", ".dat");
		outputFile.deleteOnExit();

		MovieListEditorView mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		when(mockView.getFile()).thenReturn(outputFile);
		MovieList mockedMovieList = mock(MovieList.class);
		MovieListEditor editor = new MovieListEditor(mockedMovieList, mockView);

		editor.saveAs();

		verify(mockedMovieList).writeTo(Mockito.eq(outputFile),
				Mockito.any(MovieListXMLFormatter.class));
	}
	@Test
	public void testCancelSaveAsWritesNothingToAfile() throws IOException {
		MovieListEditorView mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		when(mockView.getFile()).thenReturn(null);
		MovieList mockedMovieList = mock(MovieList.class);
		MovieListEditor editor = new MovieListEditor(mockedMovieList, mockView);

		assertFalse("when cancel save should not be saving", editor.saveAs());

	}
}
