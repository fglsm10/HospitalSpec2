package br.iesb.hospitalspec;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnLogar;
    private EditText loginEmail, loginSenha;
    private TextView textCadastrar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        /*if(firebaseAuth.getCurrentUser() != null){
            //ja ta logado vai pro maps
            //finish();
           // startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            //startActivity(new Intent(this, LoginActivity.class));

        }*/

        btnLogar = (Button) findViewById(R.id.btnLogar);
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginSenha = (EditText) findViewById(R.id.loginSenha);
        textCadastrar = (TextView) findViewById(R.id.textCadastrar);

        btnLogar.setOnClickListener(this);

        textCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreCadastro = new Intent(LoginActivity.this, CadastrarActivity.class);
                startActivity(abreCadastro);
                finish();
            }
        });
    }

    private void usuarioLoga(){
        String email = loginEmail.getText().toString().trim();
        String senha = loginSenha.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Preencha o Email", Toast.LENGTH_SHORT);
            return;
        }
        if(TextUtils.isEmpty(senha)){
            Toast.makeText(this, "Preencha a Senha", Toast.LENGTH_SHORT);
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //inicia o aplicativo principal
                            startActivity(new Intent(getApplicationContext(), testeActivity.class));
                            finish();

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogar){
            usuarioLoga();
        }

    }
}
