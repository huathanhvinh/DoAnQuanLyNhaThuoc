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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_thong_tin_thuoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //n??t tr??? v???
        imTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //ch???n ???nh t??? th?? vi???n
        layHinhTuDienThoai();
        //G??n th??ng tin v??o c??c tr?????ng
        LayThongTinThuoc();
        //n??t c???p nh???t
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edTenThuoc.getText().toString().equals("") || edGiaBan.getText().toString().equals("")||
                edHanDung.getText().toString().equals("")||edThongTin.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                    builder.setTitle("Th??ng B??o");
                    builder.setMessage("Kh??ng ???????c ????? tr???ng th??ng tin !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }else
                {
                    Thuoc thongTinThuoc = (Thuoc) getIntent().getSerializableExtra("ThongTinThuoc");
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
                                    thongTinThuoc.setTenThuoc(edTenThuoc.getText().toString());
                                    thongTinThuoc.setGiaBan(Integer.parseInt(edGiaBan.getText().toString()));
                                    thongTinThuoc.setHsd(Integer.parseInt(edHanDung.getText().toString()));
                                    thongTinThuoc.setDonViTinh(spDVT.getSelectedItem().toString());
                                    thongTinThuoc.setNoiDung(edThongTin.getText().toString());
                                    thongTinThuoc.setUrlAnh(uri.toString());

                                    StaticConfig.mThuoc.child(thongTinThuoc.getMaFB()).setValue(thongTinThuoc);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                                    builder.setTitle("Th??ng B??o");
                                    builder.setMessage("L??u th??nh c??ng !!!");
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
                }
            }
        });
        //n??t x??a
        btnXoa.setOnClickListener(new View.OnClickListener() {
            Thuoc thongTinThuoc = (Thuoc) getIntent().getSerializableExtra("ThongTinThuoc");
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                builder.setTitle("X??a Nh??n vi??n");
                builder.setMessage("B???n c?? mu???n x??a kh??ng ?");
                builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StaticConfig.mThuoc.child(thongTinThuoc.getMaFB()).removeValue();
                        AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                        builder.setTitle("Th??ng B??o");
                        builder.setMessage("X??a th??nh c??ng !!!");
                        builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                });
                builder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
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
        else if (dvt.equals("G??i"))
            spDVT.setSelection(1);
        else if (dvt.equals("H???p"))
            spDVT.setSelection(2);
        else if (dvt.equals("L???"))
            spDVT.setSelection(3);
        else if (dvt.equals("V???"))
            spDVT.setSelection(4);
        else
            spDVT.setSelection(5);
        edThongTin.setText(thongTinThuoc.getNoiDung());
        Picasso.get()
                .load(thongTinThuoc.getUrlAnh())
                .fit()
                .into(imAnhThuoc);
    }

    //l???y h??nh t??? ??i???n tho???i
    private void layHinhTuDienThoai() {
        tvChonAnh.setOnClickListener(new View.OnClickListener() {
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
        imTrove = findViewById(R.id.imTrove);
        tvMaThuoc = findViewById(R.id.tvMaThuocTT);
        tvChonAnh = findViewById(R.id.tvChonAnh);
        imAnhThuoc = findViewById(R.id.imAnhThuoc);
        edTenThuoc = findViewById(R.id.edTenThuocTT);
        edGiaBan = findViewById(R.id.edGiaThuocTT);
        edHanDung = findViewById(R.id.edHanSuDungTT);
        edThongTin = findViewById(R.id.edThongTinThuocTT);
        spDVT = findViewById(R.id.spDonViTinhTT);
        btnCapNhat = findViewById(R.id.btnCapNhatTT);
        btnXoa = findViewById(R.id.btnXoaThuocTT);
    }
}