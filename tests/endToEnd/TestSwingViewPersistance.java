package tests.endToEnd;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;

import src.core.MovieListEditor;
import src.ui.SwingMovieListEditorView;
import testUtils.ApplicationRunner;
public class TestSwingViewPersistance {

	@Test
	public void testSaveAsCallSaveAsInLogicalLayer() throws IOException {
		MovieListEditor mockedEditor= mock(MovieListEditor.class);
		SwingMovieListEditorView.start();
		ApplicationRunner appRunner = new ApplicationRunner("Movie List");
		appRunner.getView().setEditor(mockedEditor);
		appRunner.pushSaveAs();
		verify(mockedEditor).saveAs();
	}
	@Test
	public void testSaveCallSaveInLogicalLayer() throws IOException {
		MovieListEditor mockedEditor= mock(MovieListEditor.class);
		SwingMovieListEditorView.start();
		JFrameOperator mainWindow = new JFrameOperator("Movie List");
		SwingMovieListEditorView view =(SwingMovieListEditorView)mainWindow.getWindow();
		view.setEditor(mockedEditor);
		JMenuBarOperator menuBarOperator = new JMenuBarOperator(mainWindow);
		menuBarOperator.pushMenu("File|Save","|");
		verify(mockedEditor).save();
	}
}
