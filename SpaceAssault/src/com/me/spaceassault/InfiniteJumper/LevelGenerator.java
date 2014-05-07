package com.me.mygdxgame;



import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
 
public class LevelGenerator {
 
        private Body environment;
        private float leftEdge, rightEdge, minGap, maxGap, minWidth, maxWidth, height, angle, y;
        private float oldTopEdge;
        private int numberOfPlatformsToDelete;
        private boolean updateOld = true;
        private Array<Float> arrayOfY = new Array<Float>();
    	
        /**
         * Metodo <I>LevelGenerator</I>, constructor de la clase <code>LevelGenerator</code>, En este
         * metodo se reciben parametros para diseñar las posiciones de las plataformas.
         * @paramenvironment tipo de dato <code>Body</code> es el objeto utilizado
         * para incluir las fixtures del nivel.
         * @paramleftEdge tipo de dato <code>float</code> es el valor utilizado para
         * manejar el borde izquierdo de la camara.
         * @paramrightEdge tipo de dato <code>float</code> es el valor utilizado para
         * manejar el borde derecho de la camara.
         * @paramminGap tipo de dato <code>float</code> es el valor utilizado para
         * manejar el minimo espacio entre las plataformas.
         * @parammaxGap tipo de dato <code>float</code> es el valor utilizado para
         * manejar el maximo espacio entre las plataformas.
         * @paramminWidth tipo de dato <code>float</code> es el valor utilizado para
         * manejar la minima separacion en x entre las plataformas.
         * @parammaxWidth tipo de dato <code>float</code> es el valor utilizado para
         * manejar la maxima separacion en x entre las plataformas.
         * @paramheight tipo de dato <code>float</code> es el valor utilizado para
         * manejar la altura del cuerpo del jugador.
         * @paramangle tipo de dato <code>float</code> es el valor utilizado para
         * manejar el angulo de las plataformas.
         */
        public LevelGenerator(Body environment, float leftEdge, float rightEdge,
                float minGap, float maxGap, float minWidth, float maxWidth,
                float height, float angle) {
                this.environment = environment;
                this.leftEdge = leftEdge;
                this.rightEdge = rightEdge;
                this.minGap = minGap;
                this.maxGap = maxGap;
                this.minWidth = minWidth;
                this.maxWidth = maxWidth;
                this.height = height;
                this.angle = angle;
        }
        /**
         * Metodo <I>generate</I> constructor de la clase <code>LevelGenerator</code>, En este
         * metodo se construyen las plataformas.
         * @paramtopEdge tipo de dato <code>float</code> es el valor utilizado para
         * manejar el limite de de altura de la pantalla.
         * @paramplayerY tipo de dato <code>float</code> es el valor utlizado para 
         * manejar la posicion en y del jugador.
         */
        public void generate(float topEdge, float playerY){
                if(y + MathUtils.random(minGap, maxGap) > topEdge)
                        return;
               
                if(updateOld){
                        oldTopEdge = topEdge;
                        updateOld = false;
                }
               
                y = topEdge;
                float width = MathUtils.random(minWidth, maxWidth);
                float x = MathUtils.random(leftEdge, rightEdge - width);
               
                PolygonShape shape = new PolygonShape();
               
                shape.setAsBox(width / 2, height / 10, new Vector2(x + width / 2, y + height / 2), MathUtils.random(-angle / 2, angle / 2));
       
                //ARRAY LIST OF Y COORDINATES
                arrayOfY.add(new Float(y + height / 2));
               
                environment.createFixture(shape, 0);
               
                for(Float y : arrayOfY){
                        if(y < oldTopEdge - 20){
                                numberOfPlatformsToDelete++;
                                arrayOfY.removeValue(y, true);
                        }
                }

                if(playerY > oldTopEdge){
                        updateOld = true;
                        for(int i = numberOfPlatformsToDelete; i != 0;  i--){
                                environment.destroyFixture(environment.getFixtureList().get(i));
                        }
                        numberOfPlatformsToDelete = 0;
                }
               
                shape.dispose();
               
        }
 
        public Body getEnvironment() {
                return environment;
        }
 
        public void setEnvironment(Body environment) {
                this.environment = environment;
        }
 
        public float getLeftEdge() {
                return leftEdge;
        }
 
        public void setLeftEdge(float leftEdge) {
                this.leftEdge = leftEdge;
        }
 
        public float getRightEdge() {
                return rightEdge;
        }
 
        public void setRightEdge(float rightEdge) {
                this.rightEdge = rightEdge;
        }
 
        public float getMinGap() {
                return minGap;
        }
 
        public void setMinGap(float minGap) {
                this.minGap = minGap;
        }
 
        public float getMaxGap() {
                return maxGap;
        }
 
        public void setMaxGap(float maxGap) {
                this.maxGap = maxGap;
        }
 
        public float getMidWidth() {
                return minWidth;
        }
 
        public void setMidWidth(float midWidth) {
                this.minWidth = midWidth;
        }
 
        public float getMaxWidth() {
                return maxWidth;
        }
 
        public void setMaxWidth(float maxWidth) {
                this.maxWidth = maxWidth;
        }
 
        public float getHeight() {
                return height;
        }
 
        public void setHeight(float height) {
                this.height = height;
        }
       
}