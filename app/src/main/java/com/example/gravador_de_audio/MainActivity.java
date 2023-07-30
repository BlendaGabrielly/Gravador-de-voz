package com.example.gravador_de_audio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static int MICROPHONE_PERMISSION_CODE=200;
    private static int WRITE_PERMISSION_CODE=1;
    MediaRecorder mediaRecorder;
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(micro()){
            permissãoMicrofone();
        }
       // permissãoArmazenamento();
    }
    public void btGravar(View view) {
        permissãoArmazenamento();
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordFile());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this,"Gravação iniciada",Toast.LENGTH_SHORT).show();
            Log.d("APP2", "iniciou");
        }catch (Exception e){
            Toast.makeText(this,"Gravação teve Erro",Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }
    public void btnParar(View view) {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
        Toast.makeText(this,"Gravação Finalizada",Toast.LENGTH_SHORT).show();
    }
    public void play(View view) {
        try {
            player = new MediaPlayer();
            player.setDataSource(getRecordFile());
            player.prepare();
            player.start();

            Toast.makeText(this,"Gravação Reproduzida",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private boolean micro(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            return true;
        } else {
            return false;
        }
    }
    private void permissãoMicrofone(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
        }
    }
    private String getRecordFile(){
        ContextWrapper contextWrapper=new ContextWrapper(getApplicationContext());
        File musicDiretorio = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDiretorio,"textRecordingFile.mp3");
        return file.getPath();
    }
    private void permissãoArmazenamento(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION_CODE);
        }
    }
}