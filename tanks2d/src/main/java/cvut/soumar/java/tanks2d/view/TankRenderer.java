package cvut.soumar.java.tanks2d.view;

import cvut.soumar.java.tanks2d.model.GameModel;
import cvut.soumar.java.tanks2d.model.Tank;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * Class responsible for rendering a tank
 */
public class TankRenderer {

    private final Rotate rotate;
    private final GraphicsContext graphicsContext;
    private final GameModel gameModel;
    private final Tank tank;
    private final Image tankImage;


    public TankRenderer(GameModel gameModel, Tank tank, GraphicsContext graphicsContext, Rotate rotate){
        tankImage = new Image(tank.getImage());
        this.gameModel = gameModel;
        this.rotate = rotate;
        this.graphicsContext = graphicsContext;
        this.tank = tank;
    }

    /**
     * renders the tank based on its rotation and position
     */
    public void render(){
        graphicsContext.clearRect(0,0, gameModel.getWidth(), gameModel.getHeight());
        rotate.setAngle(tank.getCurrentAngle());
        rotate.setPivotX(tank.getPivotX());
        rotate.setPivotY(tank.getPivotY());
        graphicsContext.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
        graphicsContext.drawImage(tankImage, tank.getPivotX()-tank.getWidth()/2, tank.getPivotY()-tank.getHeight()/2);
    }
}
