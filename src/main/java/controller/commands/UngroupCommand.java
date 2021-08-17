package controller.commands;

import controller.commands.interfaces.ICommand;
import controller.commands.interfaces.IUndoable;
import view.Shapes.MasterShapeList;
import view.Shapes.ShapeGroup;
import view.gui.PaintCanvas;
import view.interfaces.IShape;

import java.util.ArrayList;

public class UngroupCommand implements ICommand, IUndoable {
    private final PaintCanvas paintCanvas;
    private final ArrayList<IShape> masterList = MasterShapeList.masterShapeList.getShapeList();
    private final ArrayList<IShape> groupList = MasterShapeList.groupList.getShapeList();
    private ArrayList<IShape> tempList = new ArrayList<>();
    private ArrayList<IShape> childList = new ArrayList<>();

    public UngroupCommand(PaintCanvas paintCanvas) {
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void run() {
        for (IShape shape : groupList) {
            if (shape.getSelected() && shape instanceof ShapeGroup ) {
                childList.addAll(((ShapeGroup) shape).getChildren());
                tempList.add(shape);
                //((ShapeGroup) shape).unGroup();
                masterList.remove(shape);
            }
            //masterList.addAll(tempList);
        }
        groupList.removeAll(tempList);
        //masterList.removeAll(tempList);
        masterList.addAll(childList);
        CommandHistory.add(this);
        paintCanvas.repaint();
        System.out.println("Master list size: " + masterList.size());
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
