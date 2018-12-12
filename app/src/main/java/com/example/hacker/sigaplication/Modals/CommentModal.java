package com.example.hacker.sigaplication.Modals;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.hacker.sigaplication.Api.Api;
import com.example.hacker.sigaplication.Model.CommentsModel;
import com.example.hacker.sigaplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentModal extends BottomSheetDialogFragment {
    TextView User;
    Button send;
    String names;
    EditText comment;
    Spinner city;
    Context context;
    Button cancel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout,container,false);
        send = view.findViewById(R.id.NewComments);
        User = view.findViewById(R.id.UsuarioComentario);
        comment = view.findViewById(R.id.commentario);
        city = view.findViewById(R.id.Zonas);
        context = view.getContext();
        cancel = view.findViewById(R.id.Cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        names = Build.BOARD;
        User.setText(names);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMarket();
               dismiss();
            }
        });
        return view;
    }

    public void sendMarket()
    {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);
        final CommentsModel commentsModel = new CommentsModel();
        commentsModel.setUser(names);
        if(!comment.getText().toString().isEmpty()){
            commentsModel.setComment(comment.getText().toString());
        }else {
            Toast toast = Toast.makeText(context,"Comentario Vacio", Toast.LENGTH_SHORT);
            toast.show();

        }
        commentsModel.setPlace_visit(city.getSelectedItem().toString());
        commentsModel.setDate(filename);


        Call<CommentsModel> call = Api.instance().PostComments(commentsModel);
        call.enqueue(new Callback<CommentsModel>() {
            @Override
            public void onResponse(Call<CommentsModel> call, Response<CommentsModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Comentario Exitoso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentsModel> call, Throwable t) {
                Toast toast = Toast.makeText(context,"Revisar Conexion De Internet", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}
