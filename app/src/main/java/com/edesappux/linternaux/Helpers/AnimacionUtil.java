package com.edesappux.linternaux.Helpers;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class AnimacionUtil {

    private static final int DURACION_ANIMACION = 5000;

    public static void animacionDeRotacion(View view) {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        rotationAnimator.setDuration(DURACION_ANIMACION);
        rotationAnimator.start();
    }

    public static void animacionDeAgrandar(View view) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f, 1.05f, 1.1f, 1.08f, 1.1f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f, 1.05f, 1.1f, 1.08f, 1.1f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(DURACION_ANIMACION);
        animatorSet.start();
    }
}
