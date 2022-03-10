package com.arshana.raje.newActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arshana.raje.Adapter.BooksAdapter;
import com.arshana.raje.Constant.API;
import com.arshana.raje.Model.BooksModel;
import com.arshana.raje.R;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    ArrayList<BooksModel> booksModelArrayList = new ArrayList<>();
    ImageView img_back;
    TextView txt_title;
    RecyclerView recyclerPhotoGallery;

    BooksAdapter photoGalleryAdapter;

    final int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        recyclerPhotoGallery = findViewById(R.id.recyclerVideoGallery);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(getApplicationContext());
        recyclerPhotoGallery.setLayoutManager(layoutManager);
        recyclerPhotoGallery.setItemAnimator(new DefaultItemAnimator());
        img_back = findViewById(R.id.img_back);
        txt_title = findViewById(R.id.txt_tool_title);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_title.setText(getApplicationContext().getResources().getString(R.string.books));

        getBooks();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finish();
    }

    private void getBooks() {
        booksModelArrayList.clear();
        BooksModel booksModel = new BooksModel();
        booksModel.setBook_name("छत्रपती शिवाजी महाराज माहिती ");
        booksModel.setBook_writer_name(API.PDF_PATH+"shivajimaharajmahitu.pdf");

        BooksModel booksModel1 = new BooksModel();
        booksModel1.setBook_name("छत्रपती शिवाजी महाराज");
        booksModel1.setBook_writer_name(API.PDF_PATH+"shivajimaharaj.pdf");

        BooksModel booksModel2 = new BooksModel();
        booksModel2.setBook_name("किल्ले रायगड ");
        booksModel2.setBook_writer_name(API.PDF_PATH+"killeraygad.pdf");

        BooksModel booksModel3 = new BooksModel();
        booksModel3.setBook_name("छत्रपति शिवरायाांचे जाहीरनामे ");
        booksModel3.setBook_writer_name(API.PDF_PATH+"kanunjabta.pdf");

        BooksModel booksModel4 = new BooksModel();
        booksModel4.setBook_name("दुर्गरत्न शिवछत्रपती");
        booksModel4.setBook_writer_name(API.PDF_PATH+"durgratna.pdf");

        booksModelArrayList.add(booksModel);
        booksModelArrayList.add(booksModel1);
        booksModelArrayList.add(booksModel2);
        booksModelArrayList.add(booksModel3);
        booksModelArrayList.add(booksModel4);

        photoGalleryAdapter = new BooksAdapter(booksModelArrayList, BookActivity.this);
        recyclerPhotoGallery.setAdapter(photoGalleryAdapter);


    }
}
