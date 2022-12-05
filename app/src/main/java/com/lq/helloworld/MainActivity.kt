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
                    dir + "1669930844474.png",
                    ImageExif("", -33.164024, 151.6262088)
                )
                ImageExifUtils.writeImageExif(
                    dir + "1669930851813.png",
                    ImageExif("", -33.1634134, 151.6252078)
                )
                ImageExifUtils.writeImageExif(
                    dir + "1669945258137.png",
                    ImageExif("", -33.163914, 151.6242098)
                )
                ImageExifUtils.writeImageExif(
                    dir + "1669945279470.png",
                    ImageExif("", -33.163804, 151.6242068)
                )
                ToastUtils.showShort("写入成功！")
            }
        }
    }
}