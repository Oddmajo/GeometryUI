package backendTest.astTest.figure.delegates.generators;

import java.util.Random;

import backend.FigureWindow;

public abstract class AbstractGenerator
{
    protected Random _rng;
    protected FigureWindow _window;

    public AbstractGenerator()
    {
        _rng = new Random();
        _window = FigureWindow.getWindow();
    }
}
