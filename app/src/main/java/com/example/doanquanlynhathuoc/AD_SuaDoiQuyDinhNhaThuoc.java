package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.doanquanlynhathuoc.Class.QuyDinhNhaThuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class AD_SuaDoiQuyDinhNhaThuoc extends AppCompatActivity {
    EditText edQuyDinh1,edQuyDinh2,edQuyDinh3,edQuyDinh4,edQuyDinh5;
    Button btnLuu,btnTroVe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_sua_doi_quy_dinh_nha_thuoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //nút trở về
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //lấy dữ liệu về các quy định
        layDuLieuQuyDinh();
        //nút lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1;i<6;i++)
                {
                    if(i == 1)
                    {
                        String noiDung = edQuyDinh1.getText().toString();
                        int finalI = i;
                        StaticConfig.mQuyDinh.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                QuyDinhNhaThuoc qd = snapshot.getValue(QuyDinhNhaThuoc.class);
                                if(qd.getStt() == finalI)
                                {
                                    qd.setNoidung(noiDung);
                                    StaticConfig.mQuyDinh.child(qd.getMaFB()).setValue(qd);
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if(i == 2)
                    {
                        String noiDung = edQuyDinh2.getText().toString();
                        int finalI = i;
                        StaticConfig.mQuyDinh.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                QuyDinhNhaThuoc qd = snapshot.getValue(QuyDinhNhaThuoc.class);
                                if(qd.getStt() == finalI)
                                {
                                    qd.setNoidung(noiDung);
                                    StaticConfig.mQuyDinh.child(qd.getMaFB()).setValue(qd);
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if(i == 3)
                    {
                        String noiDung = edQuyDinh3.getText().toString();
                        int finalI = i;
                        StaticConfig.mQuyDinh.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                QuyDinhNhaThuoc qd = snapshot.getValue(QuyDinhNhaThuoc.class);
                                if(qd.getStt() == finalI)
                                {
                                    qd.setNoidung(noiDung);
                                    StaticConfig.mQuyDinh.child(qd.getMaFB()).setValue(qd);
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if(i == 4)
                    {
                        String noiDung = edQuyDinh4.getText().toString();
                        int finalI = i;
                        StaticConfig.mQuyDinh.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                QuyDinhNhaThuoc qd = snapshot.getValue(QuyDinhNhaThuoc.class);
                                if(qd.getStt() == finalI)
                                {
                                    qd.setNoidung(noiDung);
                                    StaticConfig.mQuyDinh.child(qd.getMaFB()).setValue(qd);
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if(i == 5)
                    {
                        String noiDung = edQuyDinh5.getText().toString();
                        int finalI = i;
                        StaticConfig.mQuyDinh.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                QuyDinhNhaThuoc qd = snapshot.getValue(QuyDinhNhaThuoc.class);
                                if(qd.getStt() == finalI)
                                {
                                    qd.setNoidung(noiDung);
                                    StaticConfig.mQuyDinh.child(qd.getMaFB()).setValue(qd);
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                AlertDialog.Builder builder1 = new AlertDialog.Builder(AD_SuaDoiQuyDinhNhaThuoc.this);
                builder1.setTitle("Thông Báo");
                builder1.setMessage("Lưu thành công ?");
                builder1.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder1.show();
            }
        });
    }

    private void layDuLieuQuyDinh() {
        StaticConfig.mQuyDinh.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                QuyDinhNhaThuoc qd = snapshot.getValue(QuyDinhNhaThuoc.class);
                if(qd.getStt() == 1)
                {
                    edQuyDinh1.setText(qd.getNoidung());
                }
                if(qd.getStt() == 2)
                {
                    edQuyDinh2.setText(qd.getNoidung());
                }
                if(qd.getStt() == 3)
                {
                    edQuyDinh3.setText(qd.getNoidung());
                }
                if(qd.getStt() == 4)
                {
                    edQuyDinh4.setText(qd.getNoidung());
                }
                if(qd.getStt() == 5)
                {
                    edQuyDinh5.setText(qd.getNoidung());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        edQuyDinh1 = findViewById(R.id.edQuyDinh1);
        edQuyDinh2 = findViewById(R.id.edQuyDinh2);
        edQuyDinh3 = findViewById(R.id.edQuyDinh3);
        edQuyDinh4 = findViewById(R.id.edQuyDinh4);
        edQuyDinh5 = findViewById(R.id.edQuyDinh5);
        btnLuu = findViewById(R.id.btnLuu);
        btnTroVe = findViewById(R.id.btnTrove);
    }
}