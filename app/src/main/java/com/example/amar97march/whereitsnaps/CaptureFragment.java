package com.example.amar97march.whereitsnaps;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amar97march on 04-09-2017.
 */

public class CaptureFragment extends Fragment {

    private static final int CAMERA_REQUEST=123;
    private ImageView mImageView;
    String mCurrentPhotoPath;
    private Uri mImageUri= Uri.EMPTY;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        //inflate the layout file then get all necessary references
        View view=inflater.inflate(R.layout.fragment_capture,container,false);

        mImageView=(ImageView) view.findViewById(R.id.imageView);
        Button btnCapture=(Button) view.findViewById(R.id.btnCapture);
        Button btnSave=(Button) view.findViewById(R.id.btnSave);

        final EditText mEditTextTitle=(EditText)view.findViewById(R.id.editTextTitle);
        final EditText mEditTextTag1=(EditText)view.findViewById(R.id.editTextTag1);
        final EditText mEditTextTag2=(EditText)view.findViewById(R.id.editTextTag2);
        final EditText mEditTextTag3=(EditText)view.findViewById(R.id.editTextTag3);

        btnCapture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uriPhotoUri=null;
                File photoFile=null;
                try{
                    photoFile= createImageFile();
                    uriPhotoUri=FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID,photoFile);
                }catch(IOException ex){
                    Log.e("Error","Error creating file");
                }
                if(true){
                    mImageUri=uriPhotoUri;//.fromFile(photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,uriPhotoUri);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);
                }
            }

        });
        return view;

    }
    private File createImageFile() throws IOException{
        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="JPEG_"+timeStamp+"_";
        File storageDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image=File.createTempFile(imageFileName,".jpg",storageDir);

        mCurrentPhotoPath="file:"+image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode,int resultcode,Intent data){
        if(requestCode==CAMERA_REQUEST && resultcode== Activity.RESULT_OK){
            try{
                mImageView.setImageURI(Uri.parse(mImageUri.toString()));
            }catch(Exception e){
                Log.e("Error","Uri not set");
            }

        }else{
            mImageUri=Uri.EMPTY;
        }
    }

    public void onDestroy(){
        super.onDestroy();

        BitmapDrawable bd=(BitmapDrawable)mImageView.getDrawable();
        bd.getBitmap().recycle();
        mImageView.setImageBitmap(null);
    }
}
