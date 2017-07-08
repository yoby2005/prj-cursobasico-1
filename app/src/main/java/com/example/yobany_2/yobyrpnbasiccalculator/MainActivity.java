package com.example.yobany_2.yobyrpnbasiccalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/*
    Done by Yobany Díaz Roque
    reviewed July 8, 2017
 */

public class MainActivity extends AppCompatActivity {
    private String acumNum = "";//Almacena los numeros insertados
    private List<String> list= new ArrayList<>();//lista de elementos que se mostrar en la pantalla
    private TextView text;//Vista en pantalla de acumNum
    private ListView listView; //Vista de la lista de elementos
    private ArrayAdapter adapterList;
    float numSum2=0;//Ultimo elemento insertado
    float numSum1 =0;//Penultimo elemento insertado
    float numSumResult;//Resultados de operaciones realizadas a los dos ultimos elementos
    int listSize =0;//tamaño de la lista
    int punto = 0;//variable que acumula cantidad de puntos insertados
    int signoNeg = 0;//Acumulador cantidad de signos negativos
    int numValid = 0;//para validar que no haya signo negativo despues de intertar un numero

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textLayout);
        listView = (ListView) findViewById(R.id.listLayout);
        adapterList=new ArrayAdapter<>(this, R.layout.text_view_item, list);
    }

    //Metodos de Operaciones
    public void botonDiv(View view) {
        listSize = list.size();
        if(listSize>1) {//Si la lista tiene elemento realiza la operación
            numSum2 = Float.parseFloat(list.get(listSize - 1));//Convierte a float un string
            numSum1 = Float.parseFloat(list.get(listSize - 2));
            if (numSum2!=0) {
                numSumResult = numSum1 / numSum2;
                list.remove(listSize - 1); //Elimina ultimo elemento de la lista list
                list.remove(listSize - 2); //Elimina penultimo elemento de la lista list
                String resultado = String.valueOf(numSumResult);
                list.add(resultado);
                listView.setAdapter(adapterList);
            }
            else{
                list.remove(listSize - 1);//si el numero con el que se divide es 0 se elimina el elemento
                listView.setAdapter(adapterList);//actualiza la lista
                Toast.makeText(this, "ERROR: División con 0 --Ultimo elemento eliminado", Toast.LENGTH_LONG).show();//Mensaje notificando división entre 0
            }
        }
    }
    public void botonMult(View view) {
        listSize = list.size();
        if(listSize>1) {
            numSum2 = Float.parseFloat(list.get(listSize - 1));//adquiere elemento final insertado
            numSum1 = Float.parseFloat(list.get(listSize - 2));//adquiere penultimo elemento insertado
            numSumResult = numSum2 * numSum1;
            list.remove(listSize - 1);// remueve elemento final de la lista
            list.remove(listSize - 2);// remueve penultimo elemento de la lista
            String resultado = String.valueOf(numSumResult);//convierte resultado de la multiplicacion a String
            list.add(resultado);//agrega el resultado de la operación a la lista
            listView.setAdapter(adapterList);//muestra la lista actualizada
        }
    }

    public void botonResta(View view) {
        listSize = list.size();
        if(listSize>1) {
            numSum2 = Float.parseFloat(list.get(listSize - 1));
            numSum1 = Float.parseFloat(list.get(listSize - 2));
            numSumResult = numSum1- numSum2;
            list.remove(listSize - 1);
            list.remove(listSize - 2);
            String resultado = String.valueOf(numSumResult);
            list.add(resultado);
            listView.setAdapter(adapterList);
        }
    }

    public void botonSuma(View view) {
        listSize = list.size();
        if(listSize>1) {
            numSum2 = Float.parseFloat(list.get(listSize - 1));
            numSum1 = Float.parseFloat(list.get(listSize - 2));
            numSumResult = numSum2 + numSum1;
            list.remove(listSize - 1);
            list.remove(listSize - 2);
            String resultado = String.valueOf(numSumResult);
            list.add(resultado);
            listView.setAdapter(adapterList);
        }
    }
    //Metodos de los botones clear, delete, clear all y enter
    public void botonClear(View view) {
        acumNum = "";
        TextView text = (TextView) findViewById(R.id.textLayout);
        text.setText(acumNum);
    }

    public void botonDel(View view) {
        if(acumNum != "") {
            int tamanoAcumNum = acumNum.length();
            //System.out.println("Acum es antes: "+ acumNum);
            if(tamanoAcumNum>0) {
                acumNum = acumNum.substring(0, tamanoAcumNum - 1);
                text.setText(acumNum);
                //System.out.println("Acum es despues: " + acumNum);
            }
        }
    }

    public void botonEnter(View view) {
        int tamanoAcumNum = acumNum.length();
        if(acumNum != "") {//para evitar introducir a la lista elementos vacios

            if(tamanoAcumNum>0) {//para asegurar que haya algún elemento para almacenar en la lista
                if(signoNeg>0 && numValid==0) {//para evitar que se inserte un signo negativo vacio en la lista
                    acumNum = acumNum.substring(0, tamanoAcumNum);
                    text.setText(acumNum);
                }
                else{
                    list.add(acumNum);
                    listView.setAdapter(adapterList);
                    acumNum = "";
                    text.setText(acumNum);
                    punto = 0;
                    signoNeg = 0;
                    numValid = 0;
                }
            }
        }
    }

    public void botonClearAll(View view) {
        list.clear();
        acumNum = "";
        listView.setAdapter(adapterList);
        text.setText(acumNum);
        punto = 0;
        signoNeg = 0;
        numValid = 0;
    }

    //Metodo que recibe de los botones de numeros y el punto
    public void numPresionado(View view){
        numValid = numValid+1;
        Button numeros =(Button)view;
        acumNum = acumNum + numeros.getText().toString();
        text.setText(acumNum);
    }

    public void botonPunto(View view) {
        punto = punto+1;
        if (punto>1){//para que no haya más de un punto en la operación
            int tamanoAcumNum = acumNum.length();
            if(tamanoAcumNum>0) {
                acumNum = acumNum.substring(0, tamanoAcumNum);
                text.setText(acumNum);
            }
        }
        else{
            Button numeros =(Button)view;
            acumNum = acumNum + numeros.getText().toString();
            text.setText(acumNum);
        }
    }

    public void botonSignoNeg(View view) {
        signoNeg = signoNeg+1;
        int tamanoAcumNum = acumNum.length();
        if (signoNeg>1){//para que no hayan dos signos negativos en la operación
            if(tamanoAcumNum>0) {
                acumNum = acumNum.substring(0, tamanoAcumNum);
                text.setText(acumNum);
            }
        }
        else if(punto>0 || numValid>0){//para que no haya un negativo despues del punto o despues de algun numero
            if(tamanoAcumNum>0) {
                acumNum = acumNum.substring(0, tamanoAcumNum);
                text.setText(acumNum);
            }
        }
        else {//insertar signo negativo
            Button numeros = (Button) view;
            acumNum = acumNum + "-";
            text.setText(acumNum);
        }
    }
}