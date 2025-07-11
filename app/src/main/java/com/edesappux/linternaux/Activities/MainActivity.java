package com.edesappux.linternaux.Activities;


import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edesappux.linternaux.Helpers.AnimacionUtil;
import com.edesappux.linternaux.Helpers.FlashlightController;
import com.edesappux.linternaux.Helpers.SimpleIntervalTime;
import com.edesappux.linternaux.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity {
//types: layer, button, container, element, utils


    //IMPORTS
    SimpleIntervalTime intervalTime;
    FlashlightController flashlightController;


    //BACKGROUNDS
    LinearLayout layer_background;
    LinearLayout layer_bodyMain;


    //SECTION TORCH-ON-OFF
    LinearLayout button_torchOnOff;
    CardView container_torchOnOff;
    TextView element_torchOnOff;


    //SECTION TORCH-SCREEN
    LinearLayout button_torchScreen;
    TextView element_torchScreen;


    //SECTION DECORATION & SPECIALS
    List<View> util_identityOnOff;
    List<View> util_Battery;





    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        INIT_VARIABLES();
        BACKGROUND_ANIMATED();
        TURN_ON_OFF();
        TURN_ON_OFF_SCREEN();
        ACTIONS_BATTERY_CHANGE();
        CHANGE_STATUS_NAVIGATION_BAR_COLOR(R.color.colorNavigationBar1);

    }


    //INIT VARIABLES
    public void INIT_VARIABLES() {


        //IMPORTS
        this.intervalTime = new SimpleIntervalTime();
        this.flashlightController = new FlashlightController(getApplicationContext());


        //BACKGROUNDS
        this.layer_background = findViewById(R.id.layer_background);
        this.layer_bodyMain = findViewById(R.id.layer_bodyMain);


        //SECTION TORCH-ON-OFF
        this.button_torchOnOff = findViewById(R.id.button_torchOnOff);
        this.element_torchOnOff = findViewById(R.id.element_torchOnOff);
        this.container_torchOnOff = findViewById(R.id.container_torchOnOff);


        //SECTION TORCH-SCREEN
        this.button_torchScreen = findViewById(R.id.button_torchScreen);
        this.element_torchScreen = findViewById(R.id.element_torchScreen);


        //SECTION DECORATION & SPECIALS
        this.util_identityOnOff = new ArrayList<>();
        this.util_identityOnOff.add(findViewById(R.id.identity1));
        this.util_identityOnOff.add(findViewById(R.id.identity2));

        this.util_Battery = new ArrayList<>();
        this.util_Battery.add(findViewById(R.id.utils_iconBattery));
        this.util_Battery.add(findViewById(R.id.utils_iconBatteryPercent));


    }


    //CHANGE STATUS AND NAVIGATION BAR COLOR
    public void CHANGE_STATUS_NAVIGATION_BAR_COLOR(@ColorRes int color) {
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, color));
        window.setNavigationBarColor(ContextCompat.getColor(this, color));
    }


    //ANIMATE IN BACKGROUND
    public void BACKGROUND_ANIMATED (){

        intervalTime.setInterval(() -> runOnUiThread(() -> {
            AnimacionUtil.animacionDeAgrandar(util_identityOnOff.get(0));
            AnimacionUtil.animacionDeRotacion(util_identityOnOff.get(0));
        }), 5000);

    }


    //FUNCTIONALITY OF TORCH
    public void TURN_ON_OFF (){
        button_torchOnOff.setOnClickListener(view -> {

            flashlightController.toggleFlashlight();
            if (flashlightController.isFlashlightOn()){
                util_identityOnOff.get(0).setVisibility(View.INVISIBLE);
                util_identityOnOff.get(1).setVisibility(View.VISIBLE);
                element_torchOnOff.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconPowerOn));
            } else {
                util_identityOnOff.get(0).setVisibility(View.VISIBLE);
                util_identityOnOff.get(1).setVisibility(View.GONE);
                element_torchOnOff.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconPower));
            }

        });
    }


    //FUNCTIONALITY OF TORCH SCREEN
    @SuppressLint("ResourceType")
    public void TURN_ON_OFF_SCREEN (){
        AtomicBoolean isActive = new AtomicBoolean(false);

        int color1 = ContextCompat.getColor(getApplicationContext(), R.color.colorBackground1);
        int color2 = ContextCompat.getColor(getApplicationContext(), R.color.colorBackground2);

        // Creando ColorStateList para los colores de tinte
        ColorStateList colorStateList1 = ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorIcons3));
        ColorStateList colorStateList2 = ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorIcons4));


        button_torchScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isActive.get()) {
                    element_torchScreen.setBackgroundTintList(colorStateList2);
                    element_torchOnOff.setBackgroundTintList(colorStateList2);
                    button_torchOnOff.setBackgroundColor(color2);
                    layer_background.setBackgroundColor(color2);
                    layer_bodyMain.setVisibility(View.INVISIBLE);
                    CHANGE_STATUS_NAVIGATION_BAR_COLOR(R.color.colorNavigationBar2);
                    flashlightController.turnOffFlashlight();
                    util_identityOnOff.get(1).setVisibility(View.INVISIBLE);
                    element_torchOnOff.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconPower));


                    isActive.set(true);
                } else {
                    element_torchScreen.setBackgroundTintList(colorStateList1);
                    element_torchOnOff.setBackgroundTintList(colorStateList1);
                    button_torchOnOff.setBackgroundColor(color1);
                    layer_background.setBackgroundColor(color1);
                    layer_bodyMain.setVisibility(View.VISIBLE);
                    CHANGE_STATUS_NAVIGATION_BAR_COLOR(R.color.colorNavigationBar1);
                    flashlightController.turnOffFlashlight();
                    util_identityOnOff.get(1).setVisibility(View.INVISIBLE);
                    element_torchOnOff.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.iconPower));

                    isActive.set(false);
                }
            }
        });
    }


    //BATTERY
    public int GET_BATTERY_LEVEL() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, intentFilter);
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            if (level != -1 && scale != -1) {
                float batteryPct = ((float) level / (float) scale) * 100.0f;
                return Math.round(batteryPct); // Redondear al entero más cercano
            }
        }
        return -1; // Valor predeterminado si no se puede obtener el nivel de la batería
    }

    public boolean IS_CHARGING() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, intentFilter);
        if (batteryStatus != null) {
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
        }
        return false; // Valor predeterminado si no se puede determinar el estado de carga
    }

    @SuppressLint("SetTextI18n")
    public void ACTIONS_BATTERY_CHANGE() {
        Drawable[] batteryDrawables = {
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_battery_0),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_battery_1),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_battery_2),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_battery_3),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_battery_4),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_battery_5),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_battery_6),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_battery_7),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_battery_8)
        };

        TextView iconBattery = (TextView) util_Battery.get(0);
        TextView iconBatteryPercent = (TextView) util_Battery.get(1);
        AtomicBoolean wasCharging = new AtomicBoolean(IS_CHARGING()); // Variable para rastrear si estaba cargando

        // Inicialmente, establece el ícono de la batería en batteryDrawables[0]
        iconBattery.setBackground(batteryDrawables[0]);

        SimpleIntervalTime time = new SimpleIntervalTime();

        time.setInterval(() -> {
            int percentage = GET_BATTERY_LEVEL();
            Drawable batteryDrawable;

            if (percentage >= 0 && percentage <= 5) {
                batteryDrawable = batteryDrawables[0];
            } else if (percentage >= 5 && percentage < 12) {
                batteryDrawable = batteryDrawables[1];
            } else if (percentage >= 12 && percentage < 35) {
                batteryDrawable = batteryDrawables[2];
            } else if (percentage >= 35 && percentage < 50) {
                batteryDrawable = batteryDrawables[3];
            } else if (percentage >= 50 && percentage < 60) {
                batteryDrawable = batteryDrawables[4];
            } else if (percentage >= 60 && percentage < 70) {
                batteryDrawable = batteryDrawables[5];
            } else if (percentage >= 70 && percentage < 95) {
                batteryDrawable = batteryDrawables[6];
            } else if (percentage >= 95 && percentage <= 100) {
                batteryDrawable = batteryDrawables[7];
            } else {
                // En caso de un porcentaje no válido, usa un ícono predeterminado
                batteryDrawable = batteryDrawables[0];
            }

            boolean isCharging = IS_CHARGING();

            runOnUiThread(() -> {
                iconBattery.setBackground(isCharging ? batteryDrawables[8] : batteryDrawable);
                iconBatteryPercent.setText(String.valueOf(percentage) + "%");
            });

            // Actualiza el estado de carga anterior
            wasCharging.set(isCharging);

        }, 1000);
    }





}
