package com.crud_no_nodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;


import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {



    private Button insert;
    private Button update;
    private Button excluir;
    private Button atualizar;


    private EditText usuario;
    private EditText email;
    private EditText senha;
    private EditText rg;
    private EditText cpf;
    private EditText endereco;
    private EditText estado;
    private EditText pais;
    private EditText cep;
    private EditText fone;
    private TextView erro;

    //-------------------------------------------------------//
    //Declarando o componente de lista
    private ListView listView_ID;
    //Arrays para listagem
    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;


    public static String[] ID_Posicao = {""};
   //-------------------------------------------------------//
     /*
     Chave JEFER0321
     */

   //-------------------------------------------------------//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario  =      (EditText) findViewById(R.id.usuario);
        email    =      (EditText) findViewById(R.id.email);
        senha    =      (EditText) findViewById(R.id.senha);
        rg       =      (EditText) findViewById(R.id.rg);
        cpf      =      (EditText) findViewById(R.id.cpf);
        endereco =      (EditText) findViewById(R.id.endereco);
        estado   =      (EditText) findViewById(R.id.estado);
        pais     =      (EditText) findViewById(R.id.pais);
        cep      =      (EditText) findViewById(R.id.cep);
        fone     =      (EditText) findViewById(R.id.fone);

        erro     =      (TextView) findViewById(R.id.erro);


        insert =      (Button) findViewById(R.id.insert);
        update =      (Button) findViewById(R.id.update);
        excluir =      (Button) findViewById(R.id.excluir);
        atualizar =      (Button) findViewById(R.id.atualizar);

        lista();



        listView_ID = (ListView) findViewById(R.id.listView_ID);

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista();
            }
        });



       erro.setText("Mensagens de Retorno Aqui...");



        //Evento Onclick
        listView_ID.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


             eventoclick(position);


            }//Fim da classe do onclivk
        });  //Fim da classe do onclick

     //---------------------------------------------------------------------------------------------------//


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

//---------------------------------------------------------------------------------------------------//


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                patch(ID_Posicao[0]);
                lista();

            }
        });

//---------------------------------------------------------------------------------------------------//


        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                delete(ID_Posicao[0]);
                lista();
            }
        });




    }//fim do oncreate

    public void delete (String ID){
        String EnviaNome = usuario.getText().toString();
        String EnviaEmail= email.getText().toString();
        String EnviaRG   = rg.getText().toString();
        String EnviaCPF  = cpf.getText().toString();

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            // Este acaba sendo o ponto diferente quando usar ok http para
            // api PHP ou NODEJS
            RequestBody formBody = new FormBody.Builder()
                    .add("id_usuario" , ID)
                    .build();

            Request request = new Request.Builder()
                    .url("http://api.cc10.site/usuarios")
                    .delete(formBody) // patch here.
                    .build();
            //-------------------------------------------------------------


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                String RecebeResposta = response.body().string();

                                usuario.setText(RecebeResposta);
                                usuario.setText("");
                                rg.setText("");
                                cpf.setText("");
                                email.setText("");
                                ID_Posicao[0]="";
                            } catch (IOException e) {
                                erro.setText("Erro: "+e.getMessage());
                            }

                        }
                    });
                }

                ;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//fim do delete



    //------------------------------------------------------------------------------------
    public void patch (String ID){
        String EnviaNome = usuario.getText().toString();
        String EnviaEmail= email.getText().toString();
        String EnviaRG   = rg.getText().toString();
        String EnviaCPF  = cpf.getText().toString();

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            // Este acaba sendo o ponto diferente quando usar ok http para
            // api PHP ou NODEJS
            RequestBody formBody = new FormBody.Builder()
                    .add("id_usuario" , ID)
                    .add("tipo_usuario" , "VINDO DO APP")
                    .add("usuario" , EnviaNome)
                    .add("email"   , EnviaEmail)
                    .add("senha"   , "Put realizado no patch ")
                    .add("rg"      , EnviaRG)
                    .add("cpf"     , EnviaCPF)
                    .add("endereco", "END_TESTE_APP")
                    .add("estado"  , "EST_TESTE_APP")
                    .add("pais"    , "PAIS_TESTE_APP")
                    .add("cep"     , "999999999")
                    .add("fone"    , "999999999")
                    .build();

            Request request = new Request.Builder()
                    .url("http://api.cc10.site/usuarios")
                    .patch(formBody) // patch here.
                    .build();
            //-------------------------------------------------------------


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                String RecebeResposta = response.body().string();

                                usuario.setText(RecebeResposta);
                                usuario.setText("");
                                rg.setText("");
                                cpf.setText("");
                                email.setText("");
                                ID_Posicao[0]="";
                            } catch (IOException e) {
                                erro.setText("Erro: "+e.getMessage());
                            }

                        }
                    });
                }

                ;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//fim do update
    //--------------------------------------------------------------------------------//
    public void eventoclick(final Integer Posicao){



        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.cc10.site/usuarios").newBuilder();


            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                //txtInfo.setText(response.body().string());

                                try {
                                    String data = response.body().string();

                                    JSONArray jsonArray = new JSONArray(data);
                                    JSONObject jsonObject;

                                    //pega pela posição no array
                                    jsonObject = jsonArray.getJSONObject(Posicao);

                                    ID_Posicao[0] = jsonObject.getString("id_usuario");
                                    usuario.setText(jsonObject.getString("usuario"));
                                    email.setText(jsonObject.getString("email"));

                                    //rg.setText(jsonObject.getString("rg"));
                                   // cpf.setText(jsonObject.getString("cpf"));


                                } catch (JSONException e) {
                                    erro.setText("Erro: "+e.getMessage());
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

                ;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // fim do onclick
   //---------------------------------------------------------------------------------//
   public void insert () {
       String EnviaNome = usuario.getText().toString();
       String EnviaEmail= email.getText().toString();
       String EnviaRG   = rg.getText().toString();
       String EnviaCPF  = cpf.getText().toString();

       try {
           StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
           StrictMode.setThreadPolicy(policy);

           OkHttpClient client = new OkHttpClient();

           // Este acaba sendo o ponto diferente quando usar ok http para
           // api PHP ou NODEJS
           RequestBody formBody = new FormBody.Builder()
                   .add("tipo_usuario" , "VINDO DO APP")
                   .add("usuario" , EnviaNome)
                   .add("email"   , EnviaEmail)
                   .add("senha"   , "SENHA_TESTE_APP")
                   .add("rg"      , EnviaRG)
                   .add("cpf"     , EnviaCPF)
                   .add("endereco", "END_TESTE_APP")
                   .add("estado"  , "EST_TESTE_APP")
                   .add("pais"    , "PAIS_TESTE_APP")
                   .add("cep"     , "999999999")
                   .add("fone"    , "999999999")
                   .build();

           Request request = new Request.Builder()
                   .url("http://api.cc10.site/usuarios")
                   .post(formBody) // PUT here.
                   .build();
           //-------------------------------------------------------------


           client.newCall(request).enqueue(new Callback() {
               @Override
               public void onFailure(Call call, IOException e) {

               }

               @Override
               public void onResponse(Call call, final Response response) throws IOException {

                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {

                           try {
                               String RecebeResposta = response.body().string();

                               usuario.setText(RecebeResposta);
                               usuario.setText("");
                               rg.setText("");
                               cpf.setText("");
                               email.setText("");

                           } catch (IOException e) {
                               erro.setText("Erro: "+e.getMessage());
                           }

                       }
                   });
               }

               ;
           });
       } catch (Exception e) {
           e.printStackTrace();
       }
   } //fim do insert

    //---------------------------------------------------------------------------------------//



    public void lista() {


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.cc10.site/usuarios").newBuilder();

            String url = urlBuilder.build().toString();


            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                try {

                                    //Pega o corpo do JSON numa String
                                    String data = response.body().string();

                                  //  String dataSEMRESPONSE = data.replace("\"response\":", "");

                                    //Pega o JSON dentro da String e transporta para um objeto do tipo Json
                                    JSONArray jsonArray = new JSONArray(data);
                                    JSONObject jsonObject;


                                    //Criar adaptador
                                    itens = new ArrayList<String>();


                                    itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_list_item_2,
                                            android.R.id.text2, itens);
                                    listView_ID.setAdapter(itensAdaptador);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        jsonObject = jsonArray.getJSONObject(i);
                                        itens.add("Nome: " + jsonObject.getString("usuario")
                                           + " |  email: " + jsonObject.getString("email")

                                        );

                                        /*
                                        *
                                        *     + " |  RG:"     + jsonObject.getString("rg")
                                           + " |  CPF:"     + jsonObject.getString("cpf")
                                           *
                                           *
                                        * */

                                    }


                                } catch (JSONException e) {
                                    erro.setText("Erro: "+e.getMessage());
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

                ;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }// Fim da função lista





}//fim da classe java
