package entity;

import math.*;
import utility.Timer;
import window.Window;

public class Camera {
    public Float3 forward = new Float3(0.0f, 0.0f, 1.0f);
    public Float3 position = new Float3(0.0f);
    public float aspect = 1.7778f;
    public float fov = 75.0f;
    public float near = 0.01f;
    public float far = 500.0f;
    public float speed = 2.0f;
    public float sens = 0.1f;

    public Camera() {}

    public void updateAspect(Int2 frameSize) {
        aspect = (float)(frameSize.x) / frameSize.y;
    }
    public void updateAspect(Float2 frameSize) {
        aspect = frameSize.x / frameSize.y;
    }

    public Float3 getForward() {
        return forward.norm();
    }
    public Float3 getRight() {
        return Float3.getPosY().cross(getForward()).norm();
    }
    public Float3 getUp() {
        return getForward().cross(getRight()).norm();
    }

    public void moveForward(float deltaTime) {
        position = position.add(getForward().mul(speed * deltaTime));
    }
    public void moveBack(float deltaTime) {
        position = position.sub(getForward().mul(speed * deltaTime));
    }
    public void moveRight(float deltaTime) {
        position = position.add(getRight().mul(speed * deltaTime));
    }
    public void moveLeft(float deltaTime) {
        position = position.sub(getRight().mul(speed * deltaTime));
    }
    public void moveUp(float deltaTime) {
        position = position.add(Float3.getPosY().mul(speed * deltaTime));
    }
    public void moveDown(float deltaTime) {
        position = position.sub(Float3.getPosY().mul(speed * deltaTime));
    }

    public void rotate(Int2 mousePos, Int2 frameCenter, float verticalAngleLimit) {
	    final int dx = mousePos.x - frameCenter.x;
	    final int dy = mousePos.y - frameCenter.y;
        final float xRotation = dx * sens;
        final float yRotation = dy * sens;
        Float3 forwardVert = getForward().rotate(yRotation, getRight());
        if (Math.abs(forwardVert.angle(Float3.getPosY()) - 90.0f) <= verticalAngleLimit) {
            forward = forwardVert;
        }
        forward = getForward().rotate(xRotation, Float3.getPosY());
    }

    public Mat4 matrix() {
	    final Mat4 view = Mat4.lookAt(position, position.add(getForward()), Float3.getPosY());
	    final Mat4 proj = Mat4.persp(fov, aspect, near, far);
        return proj.mul(view);
    }

    private boolean firstClick = true;
    private boolean camMoving = false;
    public void setDefaultMovement(Window window, Timer timer) {
        window.keyboard.w.down = () -> moveForward(timer.getDeltaT());
        window.keyboard.a.down = () -> moveLeft(timer.getDeltaT());
        window.keyboard.s.down = () -> moveBack(timer.getDeltaT());
        window.keyboard.d.down = () -> moveRight(timer.getDeltaT());
        window.keyboard.e.down = () -> moveUp(timer.getDeltaT());
        window.keyboard.q.down = () -> moveDown(timer.getDeltaT());
        window.keyboard.shift.press = () -> speed = 5.0f;
        window.keyboard.shift.release = () -> speed = 2.0f;

        window.mouse.rmb.press = () -> {
            window.mouse.hide();
            camMoving = true;
        };
        window.mouse.rmb.down = () -> {
            if (camMoving) {
			    final Int2 frameCenter = window.getSize().div(2);
                if (firstClick) {
                    window.mouse.position = frameCenter;
                    firstClick = false;
                }
                rotate(window.mouse.position, frameCenter, 85.0f);
                window.mouse.move(frameCenter);
            }
        };
        window.mouse.rmb.release = () -> {
            window.mouse.show();
            firstClick = true;
            camMoving = false;
        };
    }
}
