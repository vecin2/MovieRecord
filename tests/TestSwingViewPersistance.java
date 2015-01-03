package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.JMenuBar;

import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;

import src.core.MovieListEditor;
import src.ui.SwingMovieListEditorView;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class TestSwingViewPersistance {

	@Test
	public void testSaveAsCallSaveAsInLogicalLayer() throws IOException {
		MovieListEditor mockedEditor= mock(MovieListEditor.class);
		SwingMovieListEditorView.start();
		JFrameOperator mainWindow = new JFrameOperator("Movie List");
		SwingMovieListEditorView view =(SwingMovieListEditorView)mainWindow.getWindow();
		view.setEditor(mockedEditor);
		JMenuBarOperator menuBarOperator = new JMenuBarOperator(mainWindow);
		menuBarOperator.pushMenu("File|SaveAs","|");
		verify(mockedEditor).saveAs();
	}

}
