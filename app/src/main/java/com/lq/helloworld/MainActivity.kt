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
                var filepath = dir + "WechatIMG17297.jpg"
                println("执行路径：${filepath}")
                ImageExifUtils.writeImageExif(
                    filepath,
                    ImageExif("2023-09-26 12:27:08", -27.5825793,152.9825345
                    )
                )
                println("写入geo结果：${ImageExifUtils.getImageExif(filepath)}")
//                ImageExifUtils.writeImageExif(
//                    dir + "IMG_HEAD_257875_installer.png",
//                    ImageExif("2023-03-08 09:25:09", -28.2391242,153.4977817)
//                )
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