package com.Kebaili_Bloise.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JoystickView extends View {

   private PointF baseCenter = new PointF();
   private PointF stickPosition = new PointF();
   private float baseRadius;
   private float stickRadius;

   private OnJoystickMoveListener listener;

   public JoystickView(Context context) {
      super(context);
      initJoystick();
   }

   public JoystickView(Context context, AttributeSet attrs) {
      super(context, attrs);
      initJoystick();
   }

   private void initJoystick() {
      baseRadius = dpToPx(150);
      stickRadius = dpToPx(50);
      resetStick();
   }

   public void resetStick() {
      stickPosition.set(baseCenter.x, baseCenter.y);
      invalidate(); // Redessiner après réinitialisation
   }

   @Override
   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
      baseCenter.set(w / 2f, h / 2f);
      resetStick();
   }

   @Override
   protected void onDraw(Canvas canvas) {
      Paint basePaint = new Paint();
      basePaint.setColor(Color.GRAY);
      canvas.drawCircle(baseCenter.x, baseCenter.y, baseRadius, basePaint);

      Paint stickPaint = new Paint();
      stickPaint.setColor(Color.RED);
      canvas.drawCircle(stickPosition.x, stickPosition.y, stickRadius, stickPaint);
   }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
      float x = event.getX();
      float y = event.getY();
      float dx = x - baseCenter.x;
      float dy = y - baseCenter.y;

      if (event.getAction() == MotionEvent.ACTION_UP) {
         resetStick(); // Recentrer après relâchement
      } else {
         double distance = Math.sqrt(dx * dx + dy * dy);
         if (distance > baseRadius) {
            dx *= baseRadius / distance;
            dy *= baseRadius / distance;
         }
         stickPosition.set(baseCenter.x + dx, baseCenter.y + dy);
      }

      // Calcul du déplacement du joystick en angle et en force
      if (listener != null) {
         listener.onMove(calculateAngle(dx, dy), calculateStrength(dx, dy));
      }

      invalidate(); // Redessiner la vue
      return true;
   }

   private int calculateAngle(float dx, float dy) {
      double angle = Math.toDegrees(Math.atan2(-dy, dx));
      return (int) ((angle + 360) % 360);
   }

   private int calculateStrength(float dx, float dy) {
      float distance = (float) Math.sqrt(dx * dx + dy * dy);
      return (int) ((distance / baseRadius) * 100);
   }

   private float dpToPx(int dp) {
      return dp * getResources().getDisplayMetrics().density;
   }

   public interface OnJoystickMoveListener {
      void onMove(int angle, int strength);
   }

   public void setOnJoystickMoveListener(OnJoystickMoveListener listener) {
      this.listener = listener;
   }
}
