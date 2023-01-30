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
                    dir + "20220912_141052.jpeg",
                    ImageExif("", -27.4245439 ,153.0771826)
                )
//                ImageExifUtils.writeImageExif(
//                    dir + "1669703074578.png",
//                    ImageExif("", -12.390869, 130.8668757)
//                )
//
//                ImageExifUtils.writeImageExif(
//                    dir + "1673399604476.png",
//                    ImageExif("", -35.0646301 ,138.8834305)
//                )

//                ImageExifUtils.writeImageExif(
//                    dir + "Dec_05_2022_13_34_41179.png",
//                    ImageExif("", -33.9787432 ,150.8216389)
//                )
//
//                ImageExifUtils.writeImageExif(
//                    dir + "Dec_05_2022_13_34_53155.png",
//                    ImageExif("", -33.9794432 ,150.8206389)
//                )
                ToastUtils.showShort("写入成功！")
            }
        }
    }
}