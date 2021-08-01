package controller.commands;

import controller.commands.interfaces.ICommand;
import controller.commands.interfaces.IUndoable;
import view.Shapes.MasterShapeList;
import view.Shapes.Shape;
import view.gui.PaintCanvas;
import view.interfaces.IShape;

import java.util.ArrayList;

public class PasteCommand implements ICommand, IUndoable {
    private final PaintCanvas paintCanvas;
    private final ArrayList<IShape> pastedShapes = new ArrayList<>();
    private final ArrayList<IShape> masterList = MasterShapeList.masterShapeList.getShapeList();
    private final ArrayList<IShape> clipBoard = MasterShapeList.clipBoard.getShapeList();

   public PasteCommand(PaintCanvas paintCanvas) {
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void run() {
        for (IShape shape : clipBoard){
            Shape copiedShape = new Shape(shape.getPressedPoint(), shape.getReleasedPoint(), shape.getShapeType(), shape.getShadingType(), shape.getPrimaryColor(), shape.getSecondaryColor(),false);
            copiedShape.setX(shape.getX()+200);
            copiedShape.setY(shape.getY()+50);
            masterList.add(copiedShape);
            pastedShapes.add(copiedShape);
        }
        paintCanvas.repaint();
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
       masterList.removeAll(pastedShapes);
       paintCanvas.repaint();
    }

    @Override
    public void redo() {
        masterList.addAll(pastedShapes);
        paintCanvas.repaint();
    }
}