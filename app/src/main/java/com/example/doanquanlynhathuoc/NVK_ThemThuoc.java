package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
<<<<<<< HEAD
import androidx.annotation.Nullable;
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

<<<<<<< HEAD
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
=======
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
<<<<<<< HEAD
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
=======
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class NVK_ThemThuoc extends AppCompatActivity {
    EditText edMaThuoc, edTenThuoc, edGiaBan, edHsd, edThongTinThuoc;
    Spinner spDonViTinh;
    Button btnThemThuoc;
    ImageButton imTrove;
    TextView tvThongBao;
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_them_thuoc);
        setControl();
        setEvent();
<<<<<<< HEAD
//        for(int i = 0;i<spDSSDTKH.getCount();i++)
//        {
//            Toast.makeText(getApplicationContext(), spDSSDTKH.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
//        }
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String noiDung = "Hiện đã có thuốc mới, mời bạn ghé thăm cửa hàng để biết thêm chi tiết !!!";
//                extracted("Halloween");
//            }
//        });
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
    }

    private void setEvent() {
        //quay lại màn hình trước
        imTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
<<<<<<< HEAD
        //chọn ảnh từ thư viện
        layHinhTuDienThoai();
        //lấy danh sách sdt khách hàng
        layDanhSachSDTKH();
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
        //kiểm tra mã thuốc đã tồn tại hay chưa
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
                                tvThongBao.setText("Mã thuốc đã tồn tại trong hệ thống");
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
        //nút thêm thuốc
        btnThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maThuoc = edMaThuoc.getText().toString();
                String tenThuoc = edTenThuoc.getText().toString();
                String dvt = spDonViTinh.getSelectedItem().toString();
                String thongTin = edThongTinThuoc.getText().toString();
<<<<<<< HEAD
=======
                int giaBan;
                int hsd;
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
                if (maThuoc.equals("") || tenThuoc.equals("") || edGiaBan.getText().toString().equals("") ||
                        edHsd.getText().toString().equals("") || thongTin.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Thông tin không được bỏ trống !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
<<<<<<< HEAD
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
                            Toast.makeText(getApplicationContext(), "Thêm ảnh thất bại", Toast.LENGTH_SHORT).show();
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
                                    builder.setTitle("Thông Báo");
                                    builder.setMessage("Thêm thuốc thành công !!!");
                                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemThuoc.this);
                                            builder.setTitle("Thông Báo");
                                            builder.setMessage("Bạn có muốn thông báo đến khách hàng !!!");
                                            builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    extracted(tenThuoc);
                                                }
                                            });
                                            builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                            builder.show();
                                        }
                                    });
                                    builder.show();
                                    //set dữ liệu rỗng
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

    //lấy sdt kh từ firebase
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

    //gửi sms cho dskh
    private void extracted(String tenThuoc) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                for (int i = 0; i < spDSSDTKH.getCount(); i++) {
                    sendSMS("ABC có thuốc mới '" + tenThuoc + "', mời bạn ghé thăm.", spDSSDTKH.getItemAtPosition(i).toString() + "");
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }
    }

    //gửi sms cho khách hàng
    private void sendSMS(String noiDung, String sdt) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sdt, null, noiDung, null, null);
            Toast.makeText(getApplicationContext(), "Gửi thành công đến " + sdt, Toast.LENGTH_SHORT).show();
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

    //lấy hình từ điện thoại
    private void layHinhTuDienThoai() {
        tvThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    //cấp quyền cho phép truy cập ảnh
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

    //cấp quyền cho phép truy cập ảnh
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
            Toast.makeText(getApplicationContext(), "Cho phép truy cập", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Từ chối truy cập", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //cấp quyền cho phép truy cập ảnh
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
            Toast.makeText(this, "Bạn chưa chọn gì", Toast.LENGTH_SHORT).show();
        }
=======
                    giaBan = Integer.parseInt(edGiaBan.getText().toString());
                    hsd = Integer.parseInt(edHsd.getText().toString());
                    String key = StaticConfig.mThuoc.push().getKey();
                    Thuoc t = new Thuoc(giaBan, hsd, key, maThuoc, tenThuoc, dvt, thongTin);
                    StaticConfig.mThuoc.child(key).setValue(t);

                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Thêm thành công !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                    //set dữ liệu rỗng
                    edMaThuoc.setText("");
                    edTenThuoc.setText("");
                    edGiaBan.setText("");
                    edHsd.setText("");
                    edThongTinThuoc.setText("");
                    spDonViTinh.setSelection(0);
                }
            }
        });
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
    }

    private void setControl() {
        tvThongBao = findViewById(R.id.tvThongBao);
<<<<<<< HEAD
        tvThemAnh = findViewById(R.id.tvChonAnh);
        imAnhThuoc = findViewById(R.id.imAnhThuoc);
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
        edMaThuoc = findViewById(R.id.edThemMaThuoc);
        edTenThuoc = findViewById(R.id.edThemTenThuoc);
        edGiaBan = findViewById(R.id.edThemGiaBanThuoc);
        edHsd = findViewById(R.id.edThemHSDTHuoc);
        edThongTinThuoc = findViewById(R.id.edThemThongTinThuoc);
        spDonViTinh = findViewById(R.id.spThemDonViTinh);
        btnThemThuoc = findViewById(R.id.btnThemThuoc);
<<<<<<< HEAD
        btnSend = findViewById(R.id.btnsend);
        imTrove = findViewById(R.id.imTrove);
        spDSSDTKH = findViewById(R.id.spDanhSachSDTKhachHang);
=======
        imTrove = findViewById(R.id.imTrove);
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
    }
}