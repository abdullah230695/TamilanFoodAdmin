package com.shalla.tamilanfoodadmin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.shalla.tamilanfoodadmin.AllFoodsList.AllFoodLists;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    
    EditText etIngredients,etIngredients1,etIngredients2,etIngredients3,etIngredients4,etIngredients5,etIngredients6,etIngredients7,etIngredients8,etIngredients9,etIngredients10,
            etIngredients11,etIngredients12,etIngredients13,etIngredients14,etIngredients15,etIngredients16,etIngredients17,
            etIngredients18,etIngredients19,etIngredients20,etIngredients21,etIngredients22,etIngredients23,etIngredients24,etDescrption,etTitle;

    ImageView imgRecipePic;
    Button btnUpload,btnViewRecipes;
    Spinner spnrCategory;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;
    String foodID,imageURL,foodCategory=null;
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetUpdID();

        ArrayAdapter<CharSequence> svTypeAdapter=
                ArrayAdapter.createFromResource(this,R.array.foodCategory, android.R.layout.simple_spinner_item);
        svTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnrCategory.setAdapter(svTypeAdapter);
        spnrCategory.setOnItemSelectedListener(this);
        spnrCategory.setSelection(0);

        storageReference = FirebaseStorage.getInstance().getReference("recipeimages");
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        imgRecipePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
        btnViewRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), AllFoodLists.class));
               finish();
            }
        });
    }


    private void openImage() {

        Intent intent =new Intent();
        intent.setType("image/* ");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent,IMAGE_REQUEST);
    }
    private  String getFileExtension(Uri uri){
        ContentResolver contentResolver = MainActivity.this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadImage(){

        if(imageUri!=null &&foodCategory!=null && etDescrption.length()>0 && etTitle.length()>0) {
            final ProgressDialog pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please Wait");
            pd.setTitle("Recipe Uploading");
            pd.show();

            if (imageUri != null) {
                final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                        + "." + getFileExtension(imageUri));

                uploadTask = fileReference.putFile(imageUri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            try {
                                Uri downloadUri = task.getResult();
                                String muri = downloadUri.toString();
                                foodID=UUID.randomUUID().toString();
                                // reference = FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());
                                HashMap<String, Object> hashMap1 = new HashMap<>();
                                hashMap1.put("reciepe_image_url", muri);
                                hashMap1.put("category",foodCategory);
                                hashMap1.put("title", etTitle.getText().toString());
                                hashMap1.put("foodID", foodID);
                                hashMap1.put("uploadTime", Timestamp.now());
                                hashMap1.put("description", etDescrption.getText().toString());
                                hashMap1.put("likes",0);
                                hashMap1.put("dislikes",0);
                               /* hashMap1.put("likedBy",FieldValue.arrayUnion());
                                hashMap1.put("dislikedBy", etDescrption.getText().toString());*/
                              /*  hashMap1.put("ing0", etIngredients.getText().toString());
                                hashMap1.put("ing1", etIngredients1.getText().toString());
                                hashMap1.put("ing2", etIngredients2.getText().toString());
                                hashMap1.put("ing3", etIngredients3.getText().toString());
                                hashMap1.put("ing4", etIngredients4.getText().toString());

                                hashMap1.put("ing5", etIngredients5.getText().toString());
                                hashMap1.put("ing6", etIngredients6.getText().toString());
                                hashMap1.put("ing7", etIngredients7.getText().toString());
                                hashMap1.put("ing8", etIngredients8.getText().toString());
                                hashMap1.put("ing9", etIngredients9.getText().toString());

                                hashMap1.put("ing10", etIngredients10.getText().toString());
                                hashMap1.put("ing11", etIngredients11.getText().toString());
                                hashMap1.put("ing12", etIngredients12.getText().toString());
                                hashMap1.put("ing13", etIngredients13.getText().toString());
                                hashMap1.put("ing14", etIngredients14.getText().toString());

                                hashMap1.put("ing15", etIngredients15.getText().toString());
                                hashMap1.put("ing16", etIngredients16.getText().toString());
                                hashMap1.put("ing17", etIngredients17.getText().toString());
                                hashMap1.put("ing18", etIngredients18.getText().toString());
                                hashMap1.put("ing19", etIngredients19.getText().toString());

                                hashMap1.put("ing20", etIngredients20.getText().toString());
                                hashMap1.put("ing21", etIngredients21.getText().toString());
                                hashMap1.put("ing22", etIngredients22.getText().toString());
                                hashMap1.put("ing23", etIngredients23.getText().toString());
                                hashMap1.put("ing24", etIngredients24.getText().toString());*/
                                hashMap1.put("ingredients", FieldValue.arrayUnion(etIngredients.getText().toString(),
                                        etIngredients1.getText().toString(),
                                        etIngredients2.getText().toString(),
                                        etIngredients3.getText().toString(),
                                        etIngredients4.getText().toString(),
                                        etIngredients5.getText().toString(),
                                        etIngredients6.getText().toString(),
                                        etIngredients7.getText().toString(),
                                        etIngredients8.getText().toString(),
                                        etIngredients9.getText().toString(),
                                        etIngredients10.getText().toString(),
                                        etIngredients11.getText().toString(),
                                        etIngredients12.getText().toString(),
                                        etIngredients13.getText().toString(),
                                        etIngredients14.getText().toString(),
                                        etIngredients15.getText().toString(),
                                        etIngredients16.getText().toString(),
                                        etIngredients17.getText().toString(),
                                        etIngredients18.getText().toString(),
                                        etIngredients19.getText().toString()
                                        /*etIngredients20.getText().toString(),
                                        etIngredients21.getText().toString(),
                                        etIngredients22.getText().toString(),
                                        etIngredients23.getText().toString(),
                                        etIngredients24.getText().toString()*/));

                                // reference.updateChildren(hashMap);
                                db.collection("Recipes").document(foodID).set(hashMap1)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(MainActivity.this, "Food Uploaded", Toast.LENGTH_SHORT).show();
                                                pd.dismiss();
                                            }
                                        });

                            } catch (Exception e) {
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            } else {
                Toast.makeText(MainActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "தேவையான புலங்களைத் தேர்ந்தெடுக்கவும்", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                    && data != null && data.getData() != null){
                imageUri = data.getData();
                imgRecipePic.setImageURI(imageUri);

                /*if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else  {
                    //uploadImage();
                }*/
            }
        }catch (Exception e){}
    }

    private void SetUpdID() {
        etIngredients=findViewById(R.id.etIngredients);
                etIngredients1=findViewById(R.id.etIngredients1);etIngredients2=findViewById(R.id.etIngredients2);etIngredients3=findViewById(R.id.etIngredients3);
                etIngredients4=findViewById(R.id.etIngredients4);etIngredients5=findViewById(R.id.etIngredients5);etIngredients6=findViewById(R.id.etIngredients6);
                etIngredients7=findViewById(R.id.etIngredients7);etIngredients8=findViewById(R.id.etIngredients8);etIngredients9=findViewById(R.id.etIngredients9);
                etIngredients10=findViewById(R.id.etIngredients10);
                etIngredients11=findViewById(R.id.etIngredients11);etIngredients12=findViewById(R.id.etIngredients12);etIngredients13=findViewById(R.id.etIngredients13);
                etIngredients14=findViewById(R.id.etIngredients14);etIngredients15=findViewById(R.id.etIngredients15);etIngredients16=findViewById(R.id.etIngredients16);
                etIngredients17=findViewById(R.id.etIngredients17);etIngredients18=findViewById(R.id.etIngredients18);etIngredients19=findViewById(R.id.etIngredients19);
                etIngredients20=findViewById(R.id.etIngredients20);
                etIngredients20=findViewById(R.id.etIngredients21);
                etIngredients20=findViewById(R.id.etIngredients22);
                etIngredients20=findViewById(R.id.etIngredients23);
                etIngredients20=findViewById(R.id.etIngredients24);

                etDescrption=findViewById(R.id.etDescription);
                imgRecipePic=findViewById(R.id.imgRecipePic);
                btnUpload=findViewById(R.id.btnUpload);
                spnrCategory=findViewById(R.id.spnrCategory);
                etTitle=findViewById(R.id.etTitle);
                btnViewRecipes=findViewById(R.id.btnViewRecipes);
                etDescrption.setMovementMethod(new ScrollingMovementMethod());

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        foodCategory=parent.getItemAtPosition(position).toString();
        if(foodCategory.equals("உணவு வகை")) {
            Toast.makeText(this, "உணவு வகையைத் தேர்ந்தெடுக்கவும்", Toast.LENGTH_SHORT).show();
            foodCategory=null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}