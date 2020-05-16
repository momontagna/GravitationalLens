package e.momo.gravitationallens

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.sqrt


class StrainPicture : AppCompatActivity() {

    private var _imageUri: Uri? = null
    private val REQUEST_SAVE_IMAGE = 1002
    private var Flag_Strain = true


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

    /*
    fun onButtonSaveClick(view: View){

        val dataFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val now = Date()
        val nowStr = dataFormat.format(now)
        val fileName = "Strained_${nowStr}.jpg"

        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_TITLE, fileName)
        startActivityForResult(intent, REQUEST_SAVE_IMAGE)
    }
     */
     */



    fun onButtonStrainClick(view: View){
        if (Flag_Strain){
            //歪めたBitmapを保存して画面に表示する．
            val ivPicture = findViewById<ImageView>(R.id.ivPicture)
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, _imageUri)
            val bitmap2:Bitmap = BlackholeStrainesPicture(bitmap)
            ivPicture.setImageBitmap(bitmap2)
            Log.i("onStrainButton","here")
            Flag_Strain = false
        }
    }

    //Bitmap -> Bitmap
    private fun BlackholeStrainesPicture(inBitmap: Bitmap):Bitmap{
        val width : Int = inBitmap.width
        val height : Int = inBitmap.height
        var outBitmap :Bitmap = Bitmap.createBitmap(inBitmap.width, inBitmap.height, Bitmap.Config.ARGB_8888)
        Log.i("BlackholeStrain","x:${width},y:${height}")

        //短辺を単位長さと定義する
        var unit_radius = width
        if( width > height ){
            unit_radius = height
        }

        //適当なサイズのBHを定義
        val schwarzschild_radius : Int = unit_radius / 10 //適当なSW半径を定義する
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

        val strain = listOf(0,340,682,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023,1024,1025,1026,1027,1028,1029,1030,1031,1032,1033,1034,1035,1036,1037,1038,1039,1040,1041,1042,-491,-489,-486,-479,-469,40,31,27,24,22,21,19,18,17,16,16,15,14,14,13,13,12,12,11,11,11,10,10,10,10,9,9,9,9,8,8,8,8,8,7,7,7,7,7,7,7,6,6,6,6,6,6,6,6,5,5,5,5,5,5,5,5,5,5,5,5,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)

        var pixel_input  = IntArray(width * height)
        var pixel_output = IntArray(width * height)
        inBitmap.getPixels(pixel_input, 0, width, 0,0,  width, height)
        inBitmap.getPixels(pixel_output, 0, width, 0,0,  width, height)

        for (y in i_srart..i_end step 1){
            for ( x in j_srart..j_end step 1){
                val tmp_x : Double = (d_mass_y - y ) * (d_mass_y - y )
                val tmp_y : Double = (d_mass_x - x ) * (d_mass_x - x )
                val tmp_radius : Int = sqrt(tmp_x + tmp_y).toInt()
                if (tmp_radius < 5){
                //重力中心は黒 pixelをとらなくてのもいい
                    val pixel = pixel_input[y*width + x]
                    pixel_output[y*width + x] = Color.argb(Color.alpha(pixel),0,0,0)
                }else if(tmp_radius < 500){
                //strain[1000]で定義されている範囲(<半径 1000以下)だけ重力の影響をうける
                    val vector_strain = strain[tmp_radius]
                    //val vector_x = vector_strain * x / tmp_radius
                    //val vector_y = vector_strain * y / tmp_radius
                    var inp_x = 0
                    var inp_y = 0

                    /*
                    if( x < mass_point_x ){
                        inp_x = x + vector_x
                    }else{
                        inp_x = x - vector_x
                    }
                    if( y < mass_point_y ){
                        inp_y = y + vector_y
                    }else{
                        inp_y = y - vector_y
                    }
                    */

                    //val pixel = pixel_input[inp_y * width + inp_x]
                    val pixel = pixel_input[y * width + x]
                    //pixel_output[y*width + x] = Color.argb(Color.alpha(pixel),Color.red(pixel),Color.green(pixel),Color.blue(pixel))
                    pixel_output[y*width + x] = Color.argb(100,0,255,255)
                }
            }
        }
        outBitmap.setPixels(pixel_output,0, width, 0,0,  width, height)
        return outBitmap
    }
}
