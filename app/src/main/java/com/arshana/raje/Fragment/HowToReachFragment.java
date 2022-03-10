package com.arshana.raje.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arshana.raje.Constant.Constant;
import com.arshana.raje.Model.FortModel;
import com.arshana.raje.R;
import com.arshana.raje.newActivity.FortActivity;

import java.util.ArrayList;

public class HowToReachFragment extends Fragment {
    TextView txt_info;
    ArrayList<FortModel> fortModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_how_to_reach, container, false);
        txt_info=v.findViewById(R.id.txt_info);
        fortModels = FortActivity.statusList;
        txt_info.setText(fortModels.get(Constant.fort_position).getHowtoreach());
        return v;
    }
}
