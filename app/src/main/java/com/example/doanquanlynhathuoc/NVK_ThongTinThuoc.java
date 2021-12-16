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
=======
import android.content.DialogInterface;
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
<<<<<<< HEAD
import android.widget.ImageView;
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
<<<<<<< HEAD
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class NVK_ThongTinThuoc extends AppCompatActivity {
    ImageButton imTrove;
    TextView tvMaThuoc,tvChonAnh;
    ImageView imAnhThuoc;
    EditText edTenThuoc, edGiaBan, edHanDung, edThongTin;
    Spinner spDVT;
    Button btnCapNhat, btnXoa;

    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUEST_CODE = 12345;
    boolean isPermissionGranted = false;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
=======
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class NVK_ThongTinThuoc extends AppCompatActivity {
    ImageButton imTrove;
    TextView tvMaThuoc;
    EditText edTenThuoc, edGiaBan, edHanDung, edThongTin;
    Spinner spDVT;
    Button btnCapNhat, btnXoa;
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_thong_tin_thuoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //nút trở về
        imTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
<<<<<<< HEAD
        //chọn ảnh từ thư viện
        layHinhTuDienThoai();
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
        //Gán thông tin vào các trường
        LayThongTinThuoc();
        //nút cập nhật
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edTenThuoc.getText().toString().equals("") || edGiaBan.getText().toString().equals("")||
                edHanDung.getText().toString().equals("")||edThongTin.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Không được để trống thông tin !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }else
                {
                    Thuoc thongTinThuoc = (Thuoc) getIntent().getSerializableExtra("ThongTinThuoc");
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
                                    thongTinThuoc.setTenThuoc(edTenThuoc.getText().toString());
                                    thongTinThuoc.setGiaBan(Integer.parseInt(edGiaBan.getText().toString()));
                                    thongTinThuoc.setHsd(Integer.parseInt(edHanDung.getText().toString()));
                                    thongTinThuoc.setDonViTinh(spDVT.getSelectedItem().toString());
                                    thongTinThuoc.setNoiDung(edThongTin.getText().toString());
                                    thongTinThuoc.setUrlAnh(uri.toString());

                                    StaticConfig.mThuoc.child(thongTinThuoc.getMaFB()).setValue(thongTinThuoc);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                                    builder.setTitle("Thông Báo");
                                    builder.setMessage("Lưu thành công !!!");
                                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    builder.show();
                                }
                            });
                        }
                    });
=======
                    thongTinThuoc.setTenThuoc(edTenThuoc.getText().toString());
                    thongTinThuoc.setGiaBan(Integer.parseInt(edGiaBan.getText().toString()));
                    thongTinThuoc.setHsd(Integer.parseInt(edHanDung.getText().toString()));
                    thongTinThuoc.setDonViTinh(spDVT.getSelectedItem().toString());
                    thongTinThuoc.setNoiDung(edThongTin.getText().toString());

                    StaticConfig.mThuoc.child(thongTinThuoc.getMaFB()).setValue(thongTinThuoc);

                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Cập nhật thành công !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
                }
            }
        });
        //nút xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            Thuoc thongTinThuoc = (Thuoc) getIntent().getSerializableExtra("ThongTinThuoc");
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                builder.setTitle("Xóa Nhân viên");
                builder.setMessage("Bạn có muốn xóa không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StaticConfig.mThuoc.child(thongTinThuoc.getMaFB()).removeValue();
                        AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Xóa thành công !!!");
                        builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    private void LayThongTinThuoc() {
        Thuoc thongTinThuoc = (Thuoc) getIntent().getSerializableExtra("ThongTinThuoc");
        tvMaThuoc.setText(thongTinThuoc.getMaThuoc());
        edTenThuoc.setText(thongTinThuoc.getTenThuoc());
        edGiaBan.setText(thongTinThuoc.getGiaBan() + "");
        edHanDung.setText(thongTinThuoc.getHsd() + "");
        String dvt = thongTinThuoc.getDonViTinh();
        if (dvt.equals("Chai"))
            spDVT.setSelection(0);
        else if (dvt.equals("Gói"))
            spDVT.setSelection(1);
        else if (dvt.equals("Hộp"))
            spDVT.setSelection(2);
        else if (dvt.equals("Lọ"))
            spDVT.setSelection(3);
        else if (dvt.equals("Vỉ"))
            spDVT.setSelection(4);
        else
            spDVT.setSelection(5);
        edThongTin.setText(thongTinThuoc.getNoiDung());
<<<<<<< HEAD
        Picasso.get()
                .load(thongTinThuoc.getUrlAnh())
                .fit()
                .into(imAnhThuoc);
    }

    //lấy hình từ điện thoại
    private void layHinhTuDienThoai() {
        tvChonAnh.setOnClickListener(new View.OnClickListener() {
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


>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
    }

    private void setControl() {
        imTrove = findViewById(R.id.imTrove);
        tvMaThuoc = findViewById(R.id.tvMaThuocTT);
<<<<<<< HEAD
        tvChonAnh = findViewById(R.id.tvChonAnh);
        imAnhThuoc = findViewById(R.id.imAnhThuoc);
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
        edTenThuoc = findViewById(R.id.edTenThuocTT);
        edGiaBan = findViewById(R.id.edGiaThuocTT);
        edHanDung = findViewById(R.id.edHanSuDungTT);
        edThongTin = findViewById(R.id.edThongTinThuocTT);
        spDVT = findViewById(R.id.spDonViTinhTT);
        btnCapNhat = findViewById(R.id.btnCapNhatTT);
        btnXoa = findViewById(R.id.btnXoaThuocTT);
    }
}