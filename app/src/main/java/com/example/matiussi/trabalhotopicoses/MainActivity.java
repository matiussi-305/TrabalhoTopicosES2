package com.example.matiussi.trabalhotopicoses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin;
    //Alterar variavel HOST
    private String HOST = "http://192.168.5.109/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        //Acao realizada ao clicar no botao
        btnLogin.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
                String vEmail = email.getText().toString();
                String vPassword = password.getText().toString();

                //Definindo a url responsavel pela autenticacao
                String URL = HOST + "/login.php";

                //Biblioteca externa para realizar as requisicoes
               //Disponivel em https://github.com/koush/ion
               Ion.with(MainActivity.this)
                        .load(URL)
                        .setBodyParameter("email", vEmail)
                        .setBodyParameter( "password", vPassword)
                        .asJsonObject()
                       .setCallback(new FutureCallback<JsonObject>(){
                          @Override
                          public void onCompleted(Exception e, JsonObject result){
                              try{
                                  //Toast.makeText(context: MainActivity.this, text: "Retorno: " + result.toString(), Toast.LENGTH_LONG.show());
                                  String RETORNO = result.get("LOGIN").getAsString();

                                  if(RETORNO.equals("LOGIN_ERRO")){
                                      Toast.makeText(MainActivity.this, "Dados incorretos", Toast.LENGTH_LONG).show();
                                      //Toast.makeText(context: MainActivity.this, text: "Dados incorretos", Toast.LENGTH_LONG.show());
                                  }else if (RETORNO.equals("LOGIN_SUCESSO")){
                                      Toast.makeText(MainActivity.this, "Login realizado com sucesso", Toast.LENGTH_LONG).show();
                                      //Toast.makeText(context: MainActivity.this, text: "Login realizado com sucesso", Toast.LENGTH_LONG.show());
                                  }else{
                                      Toast.makeText(MainActivity.this, "Ocorreu um erro", Toast.LENGTH_LONG).show();
                                  }
                              }catch(Exception erro){
                                  Toast.makeText(MainActivity.this, "Ocorreu um erro" + erro, Toast.LENGTH_LONG).show();
                                  //Toast.makeText(MainActivity.this, text: "Ocorreu um erro" + erro, Toast.LENGTH_LONG.show());
                              }
                          }
                       });

           }
        });


    }
}
