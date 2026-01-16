package com.example.iotmqttcontroller;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.*;

public class MainActivity extends AppCompatActivity {

    private static final String BROKER_URL = "tcp://154.19.37.27:1883";
    private static final String TOPIC_CONTROL = "esp8266/led";
    private static final String TOPIC_STATUS = "esp8266/status";
    private static final String USERNAME = "yudhi";
    private static final String PASSWORD = "yudhi123";
    private static final String TOPIC_STATE = "esp8266/state";

    TextView txtIoTStatus;
    Button btnHijau, btnMerah, btnAllOff;

    MqttClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIoTStatus = findViewById(R.id.txtIoTStatus);
        btnHijau = findViewById(R.id.btnHijau);
        btnMerah = findViewById(R.id.btnMerah);
        btnAllOff = findViewById(R.id.btnAllOff);

        btnHijau.setEnabled(false);
        btnMerah.setEnabled(false);
        btnAllOff.setEnabled(false);

        new Thread(this::connectMqtt).start();

        btnHijau.setOnClickListener(v -> toggleHijau());
        btnMerah.setOnClickListener(v -> toggleMerah());
        btnAllOff.setOnClickListener(v -> allOff());
    }

    private void connectMqtt() {
        try {
            client = new MqttClient(
                    BROKER_URL,
                    MqttClient.generateClientId(),
                    null
            );

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(USERNAME);
            options.setPassword(PASSWORD.toCharArray());
            options.setAutomaticReconnect(true);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    runOnUiThread(() -> {
                        txtIoTStatus.setText("🔴 IoT DISCONNECTED");
                        txtIoTStatus.setTextColor(Color.RED);
                        btnHijau.setEnabled(false);
                        btnMerah.setEnabled(false);
                    });
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    String payload = message.toString().trim();

                    if (topic.equals(TOPIC_STATUS)) {
                        runOnUiThread(() -> {
                            if (payload.equals("ONLINE")) {
                                txtIoTStatus.setText("🟢 IoT CONNECTED");
                                txtIoTStatus.setTextColor(Color.GREEN);

                                btnHijau.setEnabled(true);
                                btnMerah.setEnabled(true);
                                btnAllOff.setEnabled(true);
                            } else if (payload.equals("OFFLINE")) {
                                txtIoTStatus.setText("🔴 IoT DISCONNECTED");
                                txtIoTStatus.setTextColor(Color.RED);

                                btnHijau.setEnabled(false);
                                btnMerah.setEnabled(false);
                                btnAllOff.setEnabled(false);

                                btnHijau.setText("OFF");
                                btnMerah.setText("OFF");
                            }
                        });
                    }



                    if (topic.equals(TOPIC_STATE)) {
                        runOnUiThread(() -> {
                            switch (payload) {
                                case "HIJAU_ON":
                                    btnHijau.setText("ON");
                                    btnMerah.setText("OFF");
                                    break;

                                case "MERAH_ON":
                                    btnMerah.setText("ON");
                                    btnHijau.setText("OFF");
                                    break;

                                case "ALL_OFF":
                                    btnHijau.setText("OFF");
                                    btnMerah.setText("OFF");
                                    break;
                            }
                        });
                    }
                }


                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

            client.connect(options);
            client.subscribe(TOPIC_STATUS);
            client.subscribe(TOPIC_STATE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleHijau() {
        publish(TOPIC_CONTROL, "ON Hijau");
    }

    private void toggleMerah() {
        publish(TOPIC_CONTROL, "ON Merah");
    }

    private void allOff() {
        publish(TOPIC_CONTROL, "OFF ALL");
    }


    private void publish(String topic, String msg) {
        new Thread(() -> {
            try {
                if (client != null && client.isConnected()) {
                    client.publish(topic, msg.getBytes(), 1, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
