package com.arshana.raje.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arshana.raje.R;

public class IdeasFragment extends Fragment {

    Button btn_submit;
    EditText edt_name,edt_mobile_number,edt_description;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_feedback, container, false);
        btn_submit=v.findViewById(R.id.btn_submit);
        edt_name=v.findViewById(R.id.edt_name);
        edt_mobile_number=v.findViewById(R.id.edt_mobile);
        edt_description=v.findViewById(R.id.edt_description);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_name,str_mobile_number,str_description;
                str_name=edt_name.getText().toString();
                str_mobile_number=edt_mobile_number.getText().toString();
                str_description=edt_description.getText().toString();
                if(str_name.equals(""))
                {
                    Toast.makeText(getContext(),"आपले नांव लिहा",Toast.LENGTH_LONG).show();

                }else if(str_mobile_number.equals(""))
                {
                    Toast.makeText(getContext(),"तुमचा मोबाईल क्रमांक लिहा",Toast.LENGTH_LONG).show();

                }else if(str_mobile_number.length()<10)
                {
                    Toast.makeText(getContext(),"चुकीचा मोबाईल क्रमांक",Toast.LENGTH_LONG).show();

                }else if(str_description.equals(""))
                {
                    Toast.makeText(getContext(),"आपल्या कल्पना लिहा",Toast.LENGTH_LONG).show();

                }
                else {

                    Intent whatsappIntent = new Intent(android.content.Intent.ACTION_SEND);
                    whatsappIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "arshana.admob007@gmail.com" });
                    whatsappIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Ideas");
                    whatsappIntent.putExtra(android.content.Intent.EXTRA_TEXT, "User Name: "+str_name+"\n\n"+"Mobile Numer: "+str_mobile_number+"\n\n"+"Idea:"+str_description);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.google.android.gm");
                    whatsappIntent.setType("message/rfc822");
                    try {
                        startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getContext(), "Email not Installed", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        return v;
    }

}