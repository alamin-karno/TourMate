package com.teamblank.tourmate.bottomsheet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamblank.tourmate.R;
import com.teamblank.tourmate.activity.MainActivity;
import com.teamblank.tourmate.model_class.Memory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class MemoryPost extends BottomSheetDialogFragment {
    private EditText MemoryTitleET,MemoryDetailsET;
    private ImageView AddMemoryImage,MemoryImageIv,cancelImageIV;
    private Button submitBTN;
    private Bitmap bitmappic;
    private RelativeLayout relativeLayout;
    private String title,details;
    private DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomshet_meomory_post,container,false);

        init(view);

        getImage(view);

        cencelImage(view);

        PostMemory(view);

        return view;

    }

    private void PostMemory(View view) {
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               title = MemoryTitleET.getText().toString();
               details = MemoryDetailsET.getText().toString();

               if(title.isEmpty()){
                   Toast.makeText(getActivity(), "Please set a title.", Toast.LENGTH_SHORT).show();
               }
               else {
                   DatabaseReference memorypostData = databaseReference.child("Memory");
                   String UserId = memorypostData.push().getKey();
                   Memory memory = new Memory(UserId,title,details,bitmapToString(bitmappic));

                   memorypostData.child(UserId).setValue(memory).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(getContext(), "Memory Added Successfully.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra("setScreen","1");
                                startActivity(intent);
                           }
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
                }
            }
        });
    }

    private String bitmapToString(Bitmap bitmappic) {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmappic.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String stringImage= Base64.encodeToString(b, Base64.DEFAULT);
        return stringImage;
    }

    private void cencelImage(View view) {
        cancelImageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmappic = null;
                relativeLayout.setVisibility(View.INVISIBLE);
                cancelImageIV.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getImage(View view) {
        AddMemoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode == 0){
                Bundle bundle = data.getExtras();
                bitmappic = (Bitmap) bundle.get("data");
                relativeLayout.setVisibility(View.VISIBLE);
                MemoryImageIv.setImageBitmap(bitmappic);
                cancelImageIV.setVisibility(View.VISIBLE);
            }
        }
    }

    private void init(View view) {
        MemoryTitleET = view.findViewById(R.id.memoryTitleET);
        MemoryDetailsET = view.findViewById(R.id.memoryDetailsET);
        AddMemoryImage = view.findViewById(R.id.addmemoryImage);
        MemoryImageIv = view.findViewById(R.id.memory_imageIV);
        cancelImageIV = view.findViewById(R.id.cancelimageIV);
        submitBTN = view.findViewById(R.id.submitBTN);
        relativeLayout = view.findViewById(R.id.relativelayer);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
