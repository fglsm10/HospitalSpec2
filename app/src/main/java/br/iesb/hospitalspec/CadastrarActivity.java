package br.iesb.hospitalspec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class CadastrarActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCancelar;
    private Button btnRegistrar;
    private EditText cadastrarNome;
    private EditText cadastrarEmail;
    private EditText cadastrarSenha;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        cadastrarNome = (EditText) findViewById(R.id.cadastrarNome);
        cadastrarEmail = (EditText) findViewById(R.id.cadastrarEmail);
        cadastrarSenha = (EditText) findViewById(R.id.cadastrarSenha);

        btnRegistrar.setOnClickListener(this);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelarRegistro = new Intent(CadastrarActivity.this, LoginActivity.class);
                finish();
                startActivity(cancelarRegistro);

            }
        });

    }

    private void registrarUsuario(){
        String email = cadastrarEmail.getText().toString().trim();
        String senha = cadastrarSenha.getText().toString().trim();
        //String nome = cadastrarNome.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Preencha o Email", Toast.LENGTH_SHORT);
            return;
        }
        if(TextUtils.isEmpty(senha)){
            Toast.makeText(this, "Preencha a Senha", Toast.LENGTH_SHORT);
            return;
        }
        //if(TextUtils.isEmpty(nome)){
        //    Toast.makeText(this, "Preencha o Nome", Toast.LENGTH_SHORT);
        //    return;
        //}

        progressDialog.setMessage("Registrando...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CadastrarActivity.this, "Registrado com sucesso!!", Toast.LENGTH_SHORT ).show();
                            Intent registroSucesso = new Intent(CadastrarActivity.this, LoginActivity.class);
                            finish();
                            startActivity(registroSucesso);


                            //DAR FINISH E CHAMAR PROXIMA TELA
                        }else{
                            Toast.makeText(CadastrarActivity.this, "Falha no registro!!", Toast.LENGTH_SHORT ).show();

                        }
                    }
                });


    }

    @Override
    public void onClick(View view) {
        if(view == btnRegistrar){
            registrarUsuario();
        }

    }
}
