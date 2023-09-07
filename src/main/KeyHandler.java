package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import tools.MoveConstants;

public class KeyHandler implements KeyListener {

    public boolean keyPressed = false;
    public boolean resetPending = false;
    public int keyPressedDirection;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int keyCode = e.getKeyCode();
        keyPressed = true;

        switch (keyCode) {

            case KeyEvent.VK_UP:
                keyPressedDirection = MoveConstants.UP;
                break;

            case KeyEvent.VK_DOWN:
                keyPressedDirection = MoveConstants.DOWN;
                break;

            case KeyEvent.VK_LEFT:
                keyPressedDirection = MoveConstants.LEFT;
                break;

            case KeyEvent.VK_RIGHT:
                keyPressedDirection = MoveConstants.RIGHT;
                break;

            case KeyEvent.VK_R:
                resetPending = true;
                break;

            default:

                keyPressed = false;
                
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
