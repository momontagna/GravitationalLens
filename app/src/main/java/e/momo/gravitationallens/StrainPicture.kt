package e.momo.gravitationallens

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.lang.Math.sqrt
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import android.content.Context
import android.os.Environment


class StrainPicture : AppCompatActivity() {

    private var _imageUri: Uri? = null
    private val REQUEST_SAVE_IMAGE = 1002
    private var Flag_Strain = true //一度だけ歪める．


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_strain_picture)
        _imageUri = intent.getParcelableExtra("uri")
        val ivPicture = findViewById<ImageView>(R.id.ivPicture)
        ivPicture.setImageURI(_imageUri)
        Log.i("onCreate_at_Strain","${_imageUri}" )
    }


    fun onButtonReturnClick(view: View){
        finish()
    }

    fun saveAsPngImage(bmp: Bitmap){
        Log.i("savePicture","here1")
        /*
            val dataFormat = SimpleDateFormat("yyyyMMddHHmmss")
            val now = Date()
            val nowStr = dataFormat.format(now)
            val fileName = "Gravitationallense_${nowStr}.jpg"
        * */
        //val cachePath = File(this.cacheDir, "images")
        //cachePath.mkdirs()
        //val filePath = File(cachePath, "Hogehoge.png")
        val stragefile = File(getExternalFilesDir(Environment.DIRECTORY_DCIM)!!.absolutePath, "/gravity")
        Log.i("savePicture","${stragefile}")
        stragefile.mkdir()
        val filePath = File(stragefile, "hoge.png")
        val fos = FileOutputStream(filePath.absolutePath)
        Log.i("savePicture","here3")
        bmp.compress(Bitmap.CompressFormat.PNG, 95, fos)
        fos.close()
        val contentUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", filePath)
        Log.i("savePicture","${stragefile}")
        val ivPicture = findViewById<ImageView>(R.id.ivPicture)
        ivPicture.setImageURI(contentUri)
    }



    fun onButtonStrainClick(view: View){
        if (Flag_Strain){
            Flag_Strain = false
            //歪めたBitmapを保存して画面に表示する．
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, _imageUri)
            val bitmap2:Bitmap = BlackholeStrainesPicture(bitmap)
            saveAsPngImage(bitmap2)
        }
    }

    //Bitmap -> 歪んだBitmap
    private fun BlackholeStrainesPicture(inBitmap: Bitmap):Bitmap{
        val width : Int = inBitmap.width
        val height : Int = inBitmap.height
        var outBitmap :Bitmap = Bitmap.createBitmap(inBitmap.width, inBitmap.height, Bitmap.Config.ARGB_8888)
        Log.i("BlackholeStrain","x:${width},y:${height}")

        //短辺を単位長さと定義する
        var unit_radius = if (width < height) width else height


        val mass_point_x : Int = width / 2                      //質点の中心点を定義する
        val mass_point_y : Int = height / 2

        val d_mass_x : Double = mass_point_x.toDouble()
        val d_mass_y : Double = mass_point_y.toDouble()

        Log.i("BlackholeStrain","heigth :${height} width:${width}")

        val i_srart : Int = (height * (1.0/3.0)).toInt()
        val i_end : Int   = (height * (2.0/3.0)).toInt()
        val j_srart : Int = (width * (1.0/3.0)).toInt()
        val j_end : Int   = (width * (2.0/3.0)).toInt()

        Log.i("BlackholeStrain","i_s :${i_srart} i_e:${i_end}")
        Log.i("BlackholeStrain","j_s :${j_srart} j_e:${j_end}")

        val strain = listOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)

        //適当なサイズのBHを定義
        val schwarzschild_radius : Int = strain.size

        var pixel_input  = IntArray(width * height)
        var pixel_output = IntArray(width * height)
        inBitmap.getPixels(pixel_input, 0, width, 0,0,  width, height)
        inBitmap.getPixels(pixel_output, 0, width, 0,0,  width, height)


        for (y in i_srart..i_end step 1){
            for ( x in j_srart..j_end step 1){
                val point_x : Double = (d_mass_y - y.toDouble() ) * (d_mass_y - y.toDouble() )
                val point_y : Double = (d_mass_x - x.toDouble() ) * (d_mass_x - x.toDouble() )
                val point_radius : Int = sqrt(point_x + point_y).toInt()
                if (point_radius < 5){
                    pixel_output[y*width + x] = Color.argb(255,255,255,255)
                }else if(point_radius < schwarzschild_radius){
                //strain[1000]で定義されている範囲(<半径 1000以下)だけ重力の影響をうける
                    Log.i("strain","x :${x} y:${y}, r:${point_radius}")
                    val vector_strain = strain[point_radius]
                    val vector_x = vector_strain * abs(x - mass_point_x) / point_radius
                    val vector_y = vector_strain * abs(y - mass_point_y) / point_radius

                    val inp_x = if(x < mass_point_x)  x - vector_x else x + vector_x
                    val inp_y = if(y < mass_point_y)  y - vector_y else y + vector_y

                    val pixel = pixel_input[inp_y * width + inp_x]
                    pixel_output[y*width + x] = Color.argb(Color.alpha(pixel),Color.red(pixel),Color.green(pixel),Color.blue(pixel))
                }
            }
        }
        outBitmap.setPixels(pixel_output,0, width, 0,0,  width, height)
        return outBitmap
    }
}
