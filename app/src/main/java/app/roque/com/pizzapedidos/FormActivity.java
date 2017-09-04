package app.roque.com.pizzapedidos;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private CheckBox checkBox;
    private RadioButton radioButton;
    private Spinner spinnerPizza;
    private Pedidos p1 = new Pedidos();
    private int extra1,extra2,total;
    private String tipopizza;
    private EditText direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        radioGroup =  (RadioGroup) findViewById(R.id.radioGroup);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        spinnerPizza = (Spinner)findViewById(R.id.spinner);
        direccion = (EditText)findViewById(R.id.direccion);

        spinnerPizza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipopizza = adapterView.getItemAtPosition(i).toString();
                switch (tipopizza) {
                    case "Americana":
                        p1.setTipo("Americana");
                        p1.setPreciotipo(40);
                        pagarTotal();
                        break;
                    case "Hawaina":
                        p1.setPreciotipo(45);
                        p1.setTipo("Hawaina");
                        pagarTotal();
                        break;
                    case "Super Suprema":
                        p1.setPreciotipo(65);
                        p1.setTipo("Super Suprema");
                        pagarTotal();
                        break;
                    case "Meat Lover":
                        p1.setPreciotipo(60);
                        p1.setTipo("Meat Lover");
                        pagarTotal();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void radioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioButtonId);
        switch (view.getId()) {
            case R.id.radioButton1:
                p1.setMaza("Masa Gruesa");
                if (checked)
                    Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButton2:
                p1.setMaza("Masa Delgada");
                if (checked)
                    Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButton3:
                p1.setMaza("Masa Artesanal");
                if (checked)
                    Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void androidCheckBoxClicked(View view) {
        switch (view.getId()) {
            case R.id.checkBox:
                CheckBox checkBox = (CheckBox) view;
                if(checkBox.isChecked())
                    extra1 = 8;
                pagarTotal();
                break;
            case R.id.checkBox2:
                CheckBox checkBox2 = (CheckBox) view;
                if(checkBox2.isChecked())
                    extra2 = 12;
                pagarTotal();
                break;
        }
    }

    public void pagarTotal(){
        p1.setPrecioextra(extra1 + extra2);
        total = p1.getPreciotipo()+p1.getPrecioextra();
    }

    public void showDialog(View view){
        String dir = direccion.getText().toString();
        if(dir.isEmpty()){
            direccion.setError("Es obligatorio rellenar este campo");

        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación del pedido");
            alertDialog.setMessage("Su pedido de pizza"+ p1.getTipo()+" con "+p1.getMaza()+" a S/."+total+".00 + IGV esta en proceso de envio");
            // Alert dialog button
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Alert dialog action goes here
                            dialog.dismiss();// use dismiss to cancel alert dialog
                        }
                    });
            alertDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(FormActivity.this, InicioActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PendingIntent pendingIntent = PendingIntent.getActivity(FormActivity.this, 100, intent, PendingIntent.FLAG_ONE_SHOT);

                    // Notification
                    Notification notification = new NotificationCompat.Builder(FormActivity.this)
                            .setContentTitle("PIZZA PEDIDOS")
                            .setContentText("Su pizza "+p1.getTipo()+" esta en proceso de envio")
                            .setSmallIcon(R.drawable.bg_pizza)
                            .setColor(ContextCompat.getColor(FormActivity.this, R.color.colorButtons))
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .build();

                    // Notification manager
                    NotificationManager notificationManager = (NotificationManager) FormActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(100, notification);
                }
            },10000);
        }
    }
}
