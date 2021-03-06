package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class NVK_ThemThuoc extends AppCompatActivity {
    EditText edMaThuoc, edTenThuoc, edGiaBan, edHsd, edThongTinThuoc;
    Spinner spDonViTinh, spDSSDTKH;
    Button btnThemThuoc, btnSend;
    ImageButton imTrove;
    TextView tvThongBao, tvThemAnh;
    ImageView imAnhThuoc;
    String urlAnh = "https://firebasestorage.googleapis.com/v0/b/doanquanlynhathuoc.appspot.com/o/img_anhthuocmacdinh.jpg?alt=media&token=d0cd7b63-546a-4802-8da3-cfa51be099d7";

    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUEST_CODE = 12345;
    boolean isPermissionGranted = false;

    ArrayList<KhachHang> arrKH = new ArrayList<>();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_them_thuoc);
        setControl();
        setEvent();
//        for(int i = 0;i<spDSSDTKH.getCount();i++)
//        {
//            Toast.makeText(getApplicationContext(), spDSSDTKH.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
//        }
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String noiDung = "Hi???n ???? c?? thu???c m???i, m???i b???n gh?? th??m c???a h??ng ????? bi???t th??m chi ti???t !!!";
//                extracted("Halloween");
//            }
//        });
    }

    private void setEvent() {
        //quay l???i m??n h??nh tr?????c
        imTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //ch???n ???nh t??? th?? vi???n
        layHinhTuDienThoai();
        //l???y danh s??ch sdt kh??ch h??ng
        layDanhSachSDTKH();
        //ki???m tra m?? thu???c ???? t???n t???i hay ch??a
        edMaThuoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StaticConfig.mThuoc.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Thuoc t = ds.getValue(Thuoc.class);
                            if (edMaThuoc.getText().toString().equals(t.getMaThuoc())) {
                                tvThongBao.setText("M?? thu???c ???? t???n t???i trong h??? th???ng");
                                btnThemThuoc.setEnabled(false);
                                return;
                            } else {
                                tvThongBao.setText("");
                                btnThemThuoc.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        //n??t th??m thu???c
        btnThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maThuoc = edMaThuoc.getText().toString();
                String tenThuoc = edTenThuoc.getText().toString();
                String dvt = spDonViTinh.getSelectedItem().toString();
                String thongTin = edThongTinThuoc.getText().toString();
                if (maThuoc.equals("") || tenThuoc.equals("") || edGiaBan.getText().toString().equals("") ||
                        edHsd.getText().toString().equals("") || thongTin.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemThuoc.this);
                    builder.setTitle("Th??ng B??o");
                    builder.setMessage("Th??ng tin kh??ng ???????c b??? tr???ng !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    Calendar calendar = Calendar.getInstance();
                    StorageReference refAnhThuoc = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
                    imAnhThuoc.setDrawingCacheEnabled(true);
                    imAnhThuoc.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imAnhThuoc.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();
                    UploadTask uploadTask = refAnhThuoc.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getApplicationContext(), "Th??m ???nh th???t b???i", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            refAnhThuoc.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    int giaBan = 0;
                                    int hsd = 0;
                                    if (edGiaBan.getText().toString().equals("") == false && edHsd.getText().toString().equals("") == false) {
                                        giaBan = Integer.parseInt(edGiaBan.getText().toString());
                                        hsd = Integer.parseInt(edHsd.getText().toString());
                                    }
                                    String key = StaticConfig.mThuoc.push().getKey();
                                    Thuoc t = new Thuoc(giaBan, hsd, key, maThuoc, tenThuoc, dvt, thongTin, uri.toString());
                                    StaticConfig.mThuoc.child(key).setValue(t);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemThuoc.this);
                                    builder.setTitle("Th??ng B??o");
                                    builder.setMessage("Th??m thu???c th??nh c??ng !!!");
                                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemThuoc.this);
                                            builder.setTitle("Th??ng B??o");
                                            builder.setMessage("B???n c?? mu???n th??ng b??o ?????n kh??ch h??ng !!!");
                                            builder.setPositiveButton("c??", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    extracted(tenThuoc);
                                                }
                                            });
                                            builder.setNegativeButton("kh??ng", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                            builder.show();
                                        }
                                    });
                                    builder.show();
                                    //set d??? li???u r???ng
                                    edMaThuoc.setText("");
                                    edTenThuoc.setText("");
                                    edGiaBan.setText("");
                                    edHsd.setText("");
                                    edThongTinThuoc.setText("");
                                    spDonViTinh.setSelection(0);
                                }
                            });
                        }
                    });

                }
            }
        });

    }

    //l???y sdt kh t??? firebase
    private void layDanhSachSDTKH() {
        ArrayList<String> arrSDTKH = new ArrayList<>();
        StaticConfig.mKhachHang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayAdapter<String> adapter;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    KhachHang kh = ds.getValue(KhachHang.class);
                    arrSDTKH.add(kh.getSdt());
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrSDTKH);
                spDSSDTKH.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //g???i sms cho dskh
    private void extracted(String tenThuoc) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                for (int i = 0; i < spDSSDTKH.getCount(); i++) {
                    sendSMS("ABC c?? thu???c m???i '" + tenThuoc + "', m???i b???n gh?? th??m.", spDSSDTKH.getItemAtPosition(i).toString() + "");
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }
    }

    //g???i sms cho kh??ch h??ng
    private void sendSMS(String noiDung, String sdt) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sdt, null, noiDung, null, null);
            Toast.makeText(getApplicationContext(), "G???i th??nh c??ng ?????n " + sdt, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Fail to send", Toast.LENGTH_SHORT).show();
        }
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
//            {
//            }else
//            {
//                requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
//            }
//        }
    }

    //l???y h??nh t??? ??i???n tho???i
    private void layHinhTuDienThoai() {
        tvThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    //c???p quy???n cho ph??p truy c???p ???nh
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete Action Using"), REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, permission, REQUEST_CODE);
        }
    }

    //c???p quy???n cho ph??p truy c???p ???nh
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
            Toast.makeText(getApplicationContext(), "Cho ph??p truy c???p", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "T??? ch???i truy c???p", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //c???p quy???n cho ph??p truy c???p ???nh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imAnhThuoc.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "B???n ch??a ch???n g??", Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl() {
        tvThongBao = findViewById(R.id.tvThongBao);
        tvThemAnh = findViewById(R.id.tvChonAnh);
        imAnhThuoc = findViewById(R.id.imAnhThuoc);
        edMaThuoc = findViewById(R.id.edThemMaThuoc);
        edTenThuoc = findViewById(R.id.edThemTenThuoc);
        edGiaBan = findViewById(R.id.edThemGiaBanThuoc);
        edHsd = findViewById(R.id.edThemHSDTHuoc);
        edThongTinThuoc = findViewById(R.id.edThemThongTinThuoc);
        spDonViTinh = findViewById(R.id.spThemDonViTinh);
        btnThemThuoc = findViewById(R.id.btnThemThuoc);
        btnSend = findViewById(R.id.btnsend);
        imTrove = findViewById(R.id.imTrove);
        spDSSDTKH = findViewById(R.id.spDanhSachSDTKhachHang);
    }
}