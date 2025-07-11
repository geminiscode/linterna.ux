package com.edesappux.linternaux.Helpers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class FlashlightController {

    private final CameraManager cameraManager;
    private String cameraId;
    private final String className = getClass().getSimpleName();
    private boolean isFlashlightOn = false;
    private final Context context;


    //=============================================================================================
    //CONSTRUCTOR ---------------------------------------------------------------------------------
    //=============================================================================================



    public FlashlightController(@NonNull Context context) {
        this.context = context;
        this.cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        try {
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                cameraId = cameraManager.getCameraIdList()[0]; // Use the first camera
            } else {
                // Manejar el caso en el que el dispositivo no tiene flash
                Log.e(className, "El dispositivo no tiene flash.");
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }




    //=============================================================================================
    //INIT. ---------------------------------------------------------------------------------------
    //=============================================================================================



    public void turnOnFlashlight() {
        try {
            if (cameraId != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, true);
                    isFlashlightOn = true;
                }  // Código para versiones anteriores a Android 6.0
                // Aquí puedes utilizar métodos o clases específicas para versiones antiguas

            }
        } catch (CameraAccessException e) {
            Log.e(className, "Error al acceder a la cámara: " + e.getMessage());
            showToast("Error al encender la linterna. Por favor, inténtalo de nuevo.");
            //reportErrorToCrashlytics("Error al encender la linterna", e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(className, "Error inesperado al encender la linterna: " + e.getMessage());
            showToast("¡Ups! Algo salió mal. Por favor, inténtalo de nuevo.");
            //reportErrorToCrashlytics("Error inesperado al encender la linterna", e);
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        // Muestra un mensaje Toast para informar al usuario
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

/*

    private void reportErrorToCrashlytics(String message, Exception e) {
        // Registra el error en Firebase Crashlytics con un mensaje personalizado
        // También puedes incluir más detalles como parámetros adicionales según tus necesidades
        FirebaseCrashlytics.getInstance().log(message);
        FirebaseCrashlytics.getInstance().recordException(e);
    }

*/


    public void turnOffFlashlight() {
        try {
            if (cameraId != null) {
                cameraManager.setTorchMode(cameraId, false);
                isFlashlightOn = false;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void toggleFlashlight() {
        if (isFlashlightOn) {
            turnOffFlashlight();
        } else {
            turnOnFlashlight();
        }
    }



    //=============================================================================================
    //UTILS. --------------------------------------------------------------------------------------
    //=============================================================================================



    //BOOLEANS**********************************
    public boolean isFlashlightOn() {
        return isFlashlightOn;
    }





}
