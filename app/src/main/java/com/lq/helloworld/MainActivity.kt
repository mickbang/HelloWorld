package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.lq.helloworld.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dir = getExternalFilesDir(null)?.absolutePath + "/Photo/"
        FileUtils.createOrExistsDir(dir)
        binding.apply {
            btnWrite.setOnClickListener {
                val dir = getExternalFilesDir(null)?.absolutePath + "/Photo/"
                ImageExifUtils.writeImageExif(
                    dir + "1670019899885.png",
                    ImageExif("", -27.9340242, 153.3107258)
                )
                ImageExifUtils.writeImageExif(
                    dir + "1670036030867.png",
                    ImageExif("", -27.9350242, 153.3097258)
                )
                ImageExifUtils.writeImageExif(
                    dir + "1670036250884.png",
                    ImageExif("", -27.9360242, 153.3077258)
                )
                ToastUtils.showShort("写入成功！")
            }
        }
    }
}