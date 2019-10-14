package cr.una.chat22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cr.una.chat22.adapter.chatadapter;
import cr.una.chat22.model.Chat;
import cr.una.chat22.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference myDatabase;

    CircleImageView profile_image;
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Intent intent;

    ImageButton btn_enviar;
    EditText txt_enviar;

    RecyclerView recyclerView;
    List<Chat> mchat;

    chatadapter Chatadapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_enviar = findViewById(R.id.bt_enviar);
        txt_enviar = findViewById(R.id.txt_send);
        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent = getIntent();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = intent.getStringExtra("userid");

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = txt_enviar.getText().toString();
                if (!msg.equals("")){
                    enviarMensaje("Carlos","roberto",msg);
                }
                txt_enviar.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Chats");//.child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                //username.setText(user.getUsername());
                //if(user.getImageURL().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                //}else{
                  //  Glide.with(MainActivity.this).load(user.getImageURL()).into(profile_image);
                //}


                //mostrarmensaje("Carlos","roberto",user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void enviarMensaje(String emisor, String receptor, String mensaje)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Emisor", emisor );
        hashMap.put("Receptor", receptor);
        hashMap.put("Mensaje", mensaje);

        reference.child("Chats").push().setValue(hashMap);
    }
    private void mostrarmensaje(String emisor, String receptor, final String Mensaje){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Chat chat = dataSnapshot1.getValue(Chat.class);
                    mchat.add(chat);
                    Chatadapter = new chatadapter(MainActivity.this, mchat, Mensaje);
                    recyclerView.setAdapter(Chatadapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}