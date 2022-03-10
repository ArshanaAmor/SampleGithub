package com.arshana.raje.Fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arshana.raje.R;
import com.arshana.raje.sharedPref.SharedPref;

public class BookPageIntroFragment extends Fragment {
    String title = "";
    String subtitle = "";

    public static BookPageIntroFragment newInstance(String title, String subtitle) {
        BookPageIntroFragment fragment = new BookPageIntroFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("subtitle", subtitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title", "");
        subtitle = getArguments().getString("subtitle", "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_page_intro, container, false);
        TextView txtTitle = rootView.findViewById(R.id.textView2);
        TextView txtSubTitle = rootView.findViewById(R.id.textView3);
        txtSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, SharedPref.getFontSize(getContext()));
        txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, SharedPref.getFontSize(getContext())+2);
        txtTitle.setText(title);
        txtSubTitle.setText(subtitle);
        return rootView;
    }
}
