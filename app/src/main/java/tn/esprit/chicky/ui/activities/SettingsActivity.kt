package tn.esprit.chicky.ui.activities

import android.app.AlertDialog
import android.app.UiModeManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import tn.esprit.chicky.R
import tn.esprit.chicky.models.User
import tn.esprit.chicky.utils.Constants

class SettingsActivity : AppCompatActivity(),PaymentResultListener {

    var darkmodeSwitch: SwitchCompat? = null
    private var uiModeManager: UiModeManager? = null
    var btnqr: Button? = null
    var btPay: Button? = null
    var qrimage: ImageView? = null
    private var currentUser: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager?;
        btnqr = findViewById(R.id.btnqr)
        btPay = findViewById(R.id.btPay)
        qrimage = findViewById(R.id.qrimage)
        darkmodeSwitch = findViewById(R.id.darkmodeSwitch)
        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_SESSION, MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", null)

        val sessionUser: User? = Gson().fromJson(userData, User::class.java)
        currentUser = intent.getSerializableExtra("user") as User?

        if (currentUser == null) {
            currentUser = sessionUser
        }



        darkmodeSwitch!!.setOnClickListener {
            if (darkmodeSwitch!!.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        btnqr!!.setOnClickListener{
            qrimage!!.setImageBitmap(getQrCodeBitmap())
        }
        btPay!!.setOnClickListener(View.OnClickListener {
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_118uKtYlZWcLv1")
            val `object` = JSONObject()
            try {
                `object`.put("name", "Chicky")
                `object`.put("description", "Donate for us ")
                `object`.put("currency", "USD")
                    `object`.put("amount", 1000)
                `object`.put("prefill.contact", "+21623292574")
                `object`.put("prefill.email", "chicky@gmail.com")
                checkout.open(this@SettingsActivity, `object`)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        })

    }
    fun getQrCodeBitmap(): Bitmap {
        val size = 512 //pixels
        val i = packageManager.getLaunchIntentForPackage("tn.esprit.chicky")
        val qrCodeContent = "chicky://"+currentUser!!._id
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
       val builder = AlertDialog.Builder(this)
        builder.setTitle("Payment Id")
        builder.setMessage(p0)
        builder.show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
    Toast.makeText(applicationContext, p1, Toast.LENGTH_SHORT).show()
    }
}