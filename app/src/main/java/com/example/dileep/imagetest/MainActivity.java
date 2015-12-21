package com.example.dileep.imagetest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    Button myButton;
    ImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton = (Button) findViewById(R.id.my_button);
        myImage = (ImageView) findViewById(R.id.my_image);

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri imageData = data.getData();
            Bitmap bitmap = ProcessingBitmap(imageData);
            myImage.setImageBitmap(bitmap);
        }
    }

    private Bitmap ProcessingBitmap(Uri imageData){
        Bitmap bm1 = null;
        Bitmap newBitmap = null;

        try {
            bm1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageData));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap.Config config = bm1.getConfig();
        if(config == null){
            config = Bitmap.Config.ARGB_8888;
        }

        newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
        Canvas newCanvas = new Canvas(newBitmap);

        newCanvas.drawBitmap(bm1, 0, 0, null);

        String captionString = "hello asgard";
        if(captionString != null){

            Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintText.setColor(Color.BLUE);
            paintText.setTextSize(50);
            paintText.setStyle(Paint.Style.FILL);

            Rect rectText = new Rect();
            paintText.getTextBounds(captionString, 0, captionString.length(), rectText);

            newCanvas.drawText(captionString, 0, rectText.height(), paintText);

            Toast.makeText(getApplicationContext(),
                    "drawText: " + captionString,
                    Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getApplicationContext(),
                    "caption empty!",
                    Toast.LENGTH_LONG).show();
        }

        return newBitmap;
    }

}
